package cl.tica.portfolio.recipeapi.auth.security.jwt.filters;

import cl.tica.portfolio.recipeapi.application.models.ExceptionWrappingError;
import cl.tica.portfolio.recipeapi.auth.security.GrantedAuthorityJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import static cl.tica.portfolio.recipeapi.auth.security.jwt.JwtTokenConfig.SECRET_KEY;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String NOT_ALLOWED = "NOT_ALLOWED";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(TOKEN_PREFIX, "");

        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();

            String username = claims.getSubject();
            Collection<GrantedAuthority> authorities = getAuthorities(claims);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            wrapAndHandleError(request, response, e);
        }
    }

    public Collection<GrantedAuthority> getAuthorities(Claims claims) throws IOException {
        Object authoritiesClaims = claims.get("authorities");

        return Arrays.asList(
                new ObjectMapper()
                        .addMixIn(SimpleGrantedAuthority.class, GrantedAuthorityJson.class)
                        .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));
    }

    private void wrapAndHandleError(HttpServletRequest request, HttpServletResponse response,
                                    Exception e) throws IOException {
        String errMsg = e instanceof SignatureException
                ? "Invalid or expired token."
                : e.getMessage();

        ExceptionWrappingError error = new ExceptionWrappingError(
                HttpServletResponse.SC_FORBIDDEN,
                NOT_ALLOWED,
                errMsg,
                request.getRequestURI()
        );

        response.setStatus(error.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        OutputStream out = response.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        writer.write(error.toJSONString());
        writer.flush();
    }
}
