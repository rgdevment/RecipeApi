package cl.tica.portfolio.recipeapi.auth.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCredentialsException extends ResponseStatusException {
    public InvalidCredentialsException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}

