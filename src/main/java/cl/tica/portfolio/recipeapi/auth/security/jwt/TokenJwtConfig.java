package cl.tica.portfolio.recipeapi.auth.security.jwt;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.concurrent.TimeUnit;

public final class TokenJwtConfig {
    private TokenJwtConfig() {
    }

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final long EXPIRATION_TIME = TimeUnit.HOURS.toMillis(1);
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE_JSON = "application/json";
}
