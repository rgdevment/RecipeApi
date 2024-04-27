package cl.tica.portfolio.recipeapi.auth.security.jwt;

import cl.tica.portfolio.recipeapi.auth.dto.ValidationFieldsError;
import cl.tica.portfolio.recipeapi.auth.exceptions.InvalidCredentialsException;
import cl.tica.portfolio.recipeapi.auth.exceptions.UserAlreadyExistException;
import cl.tica.portfolio.recipeapi.exceptions.ApiException;
import cl.tica.portfolio.recipeapi.models.ExceptionWrappingError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }

    @ExceptionHandler({UserAlreadyExistException.class, InvalidCredentialsException.class})
    public ResponseEntity<ExceptionWrappingError> handleInvalidLoginException(ApiException exception) {
        ExceptionWrappingError error = new ExceptionWrappingError(
                new Date(System.currentTimeMillis()),
                exception.getClass().getSimpleName(),
                exception.getReason(),
                exception.getStatusCode().value(),
                new ArrayList<>()
        );

        return ResponseEntity.status(exception.getStatusCode()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionWrappingError> handleValidationAuthException(MethodArgumentNotValidException exception) {
        List<ValidationFieldsError> errors = exception.getFieldErrors().stream()
                .map(fieldError ->
                        new ValidationFieldsError(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage())
                )
                .toList();

        ExceptionWrappingError error = new ExceptionWrappingError(
                new Date(System.currentTimeMillis()),
                exception.getClass().getSimpleName(),
                exception.getBody().getDetail(),
                exception.getStatusCode().value(),
                errors
        );

        return ResponseEntity.status(exception.getStatusCode()).body(error);
    }

    //Verificar por que este handler exception no funciona-
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ExceptionWrappingError> handleAuthenticationTokenException(AuthenticationCredentialsNotFoundException exception) {
        ExceptionWrappingError error = new ExceptionWrappingError(
                new Date(System.currentTimeMillis()),
                exception.getClass().getSimpleName(),
                "Invalid or expired token.",
                HttpServletResponse.SC_UNAUTHORIZED,
                new ArrayList<>()
        );

        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(error);
    }
}
