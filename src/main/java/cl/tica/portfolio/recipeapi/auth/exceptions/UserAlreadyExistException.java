package cl.tica.portfolio.recipeapi.auth.exceptions;

import cl.tica.portfolio.recipeapi.application.exceptions.ApiException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistException extends ResponseStatusException implements ApiException {

    public static final String USER_ALREADY_EXIST = "USER_ALREADY_EXIST";

    public UserAlreadyExistException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public String getInternalCode() {
        return USER_ALREADY_EXIST;
    }
}
