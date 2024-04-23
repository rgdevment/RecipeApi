package cl.tica.portfolio.recipeapi.auth.controllers;

import cl.tica.portfolio.recipeapi.auth.exceptions.InvalidCredentialsException;
import cl.tica.portfolio.recipeapi.auth.exceptions.UserAlreadyExistException;
import cl.tica.portfolio.recipeapi.exceptions.ApiException;
import cl.tica.portfolio.recipeapi.models.ExceptionWrappingError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class UnauthorizedExceptionHandler {
    @ExceptionHandler(value = {UserAlreadyExistException.class, InvalidCredentialsException.class})
    public ResponseEntity<Object> handleUserAlreadyExistException(ApiException exception) {
        ExceptionWrappingError error = new ExceptionWrappingError(
                new Date(System.currentTimeMillis()),
                exception.getClass().getSimpleName(),
                exception.getReason(),
                exception.getStatusCode().value()
        );

        return ResponseEntity.status(exception.getStatusCode()).body(error);
    }
}
