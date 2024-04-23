package cl.tica.portfolio.recipeapi.auth.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import static cl.tica.portfolio.recipeapi.auth.security.jwt.JwtTokenConfig.EXPIRATION_TIME;
import static cl.tica.portfolio.recipeapi.auth.security.jwt.JwtTokenConfig.SECRET_KEY;

@Component
public class JwtUtils {

    public String generateToken(Authentication authentication) throws IOException {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        Claims claims = Jwts.claims().add("authorities", new ObjectMapper().writeValueAsString(roles)).build();

        return Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(claims)
                .signWith(SECRET_KEY)
                .compact();
    }
}
