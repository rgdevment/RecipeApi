package cl.tica.portfolio.recipeapi.auth.secutiry;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public final class TokenJwtConfig {
    private TokenJwtConfig() {
    }

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final Integer EXPIRATION_TIME = 60 * 60 * 1000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE_JSON = "application/json";
}
