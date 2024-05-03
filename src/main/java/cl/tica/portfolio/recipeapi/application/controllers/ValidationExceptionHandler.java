package cl.tica.portfolio.recipeapi.application.controllers;

import cl.tica.portfolio.recipeapi.auth.security.dto.ValidationFieldsError;
import cl.tica.portfolio.recipeapi.application.models.ExceptionWrappingError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionWrappingError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
        List<ValidationFieldsError> errors = exception.getFieldErrors().stream()
                .map(fieldError ->
                        new ValidationFieldsError(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage())
                )
                .toList();

        String fullPath = getFullPath(request);

        ExceptionWrappingError error = new ExceptionWrappingError(
                exception.getStatusCode().value(),
                exception.getClass().getSimpleName(),
                exception.getBody().getDetail(),
                fullPath,
                errors
        );

        return ResponseEntity.status(exception.getStatusCode()).body(error);
    }

    private static String getFullPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}
