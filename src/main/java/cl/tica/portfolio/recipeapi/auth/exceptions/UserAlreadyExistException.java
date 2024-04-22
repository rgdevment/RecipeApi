package cl.tica.portfolio.recipeapi.auth.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistException extends ResponseStatusException {
    public UserAlreadyExistException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
