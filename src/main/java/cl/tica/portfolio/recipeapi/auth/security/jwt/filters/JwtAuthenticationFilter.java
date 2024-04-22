package cl.tica.portfolio.recipeapi.auth.security.jwt.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cl.tica.portfolio.recipeapi.auth.security.jwt.TokenJwtConfig.CONTENT_TYPE_JSON;
import static cl.tica.portfolio.recipeapi.auth.security.jwt.TokenJwtConfig.EXPIRATION_TIME;
import static cl.tica.portfolio.recipeapi.auth.security.jwt.TokenJwtConfig.HEADER_AUTHORIZATION;
import static cl.tica.portfolio.recipeapi.auth.security.jwt.TokenJwtConfig.SECRET_KEY;
import static cl.tica.portfolio.recipeapi.auth.security.jwt.TokenJwtConfig.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            cl.tica.portfolio.recipeapi.auth.entities.User user = new ObjectMapper().readValue(
                    request.getInputStream(),
                    cl.tica.portfolio.recipeapi.auth.entities.User.class);
            String username = user.getUsername();
            String password = user.getPassword();

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            return this.authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String username = user.getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        Claims claims = Jwts.claims().add("authorities", new ObjectMapper().writeValueAsString(roles)).build();

        String token = Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(claims)
                .signWith(SECRET_KEY)
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, TOKEN_PREFIX + token);

        Map<String, String> body = new HashMap<>();
        body.put("token", token);

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE_JSON);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("error", failed.getMessage());
        body.put("error_description", failed.getLocalizedMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE_JSON);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
