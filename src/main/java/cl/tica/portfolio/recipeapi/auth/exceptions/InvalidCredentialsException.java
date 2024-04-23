package cl.tica.portfolio.recipeapi.auth.exceptions;

import cl.tica.portfolio.recipeapi.exceptions.ApiException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCredentialsException extends ResponseStatusException implements ApiException {
    public InvalidCredentialsException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}

