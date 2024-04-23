package cl.tica.portfolio.recipeapi.auth.security.jwt;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.concurrent.TimeUnit;

public final class JwtTokenConfig {
    private JwtTokenConfig() {
    }

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final long EXPIRATION_TIME = TimeUnit.HOURS.toMillis(1);
}
