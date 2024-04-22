package cl.tica.portfolio.recipeapi.auth.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends ResponseStatusException {
    public UserAlreadyExistsException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
