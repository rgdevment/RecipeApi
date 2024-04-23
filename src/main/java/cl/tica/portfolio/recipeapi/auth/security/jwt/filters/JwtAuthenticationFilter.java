package cl.tica.portfolio.recipeapi.auth.security.jwt.filters;

import cl.tica.portfolio.recipeapi.auth.security.GrantedAuthorityJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static cl.tica.portfolio.recipeapi.auth.security.jwt.JwtTokenConfig.SECRET_KEY;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";

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

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException(e.getMessage());
        }
    }

    public Collection<GrantedAuthority> getAuthorities(Claims claims) throws IOException {
        Object authoritiesClaims = claims.get("authorities");

        return Arrays.asList(
                new ObjectMapper()
                        .addMixIn(SimpleGrantedAuthority.class, GrantedAuthorityJson.class)
                        .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));
    }
}
