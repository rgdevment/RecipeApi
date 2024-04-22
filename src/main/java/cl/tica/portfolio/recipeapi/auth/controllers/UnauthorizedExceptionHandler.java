package cl.tica.portfolio.recipeapi.auth.controllers;

import cl.tica.portfolio.recipeapi.auth.exceptions.UserAlreadyExistException;
import cl.tica.portfolio.recipeapi.models.ExceptionWrappingError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UnauthorizedExceptionHandler {
    @ExceptionHandler(value = {UserAlreadyExistException.class})
    public ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException exception) {
        ExceptionWrappingError error = new ExceptionWrappingError(
                exception.getClass().getSimpleName(),
                exception.getReason(),
                exception.getStatusCode().value()
        );

        return ResponseEntity.status(exception.getStatusCode()).body(error);
    }

}
