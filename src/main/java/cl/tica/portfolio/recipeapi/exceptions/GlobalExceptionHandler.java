package cl.tica.portfolio.recipeapi.exceptions;

import cl.tica.portfolio.recipeapi.auth.exceptions.InvalidConfirmationException;
import cl.tica.portfolio.recipeapi.auth.exceptions.InvalidCredentialsException;
import cl.tica.portfolio.recipeapi.auth.exceptions.UserAlreadyExistException;
import cl.tica.portfolio.recipeapi.models.ExceptionWrappingError;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            UserAlreadyExistException.class,
            InvalidCredentialsException.class,
            InvalidConfirmationException.class
    })
    public ResponseEntity<ExceptionWrappingError> handleInvalidLoginException(ApiException exception) {
        ExceptionWrappingError error = new ExceptionWrappingError(
                new Date(System.currentTimeMillis()),
                exception.getClass().getSimpleName(),
                exception.getStatusCode().value(),
                exception.getReason(),
                "",
                new ArrayList<>()
        );

        return ResponseEntity.status(exception.getStatusCode()).body(error);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionWrappingError> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        ExceptionWrappingError error = new ExceptionWrappingError(
                new Date(System.currentTimeMillis()),
                exception.getClass().getSimpleName(),
                HttpServletResponse.SC_UNAUTHORIZED,
                exception.getMessage(),
                "",
                new ArrayList<>()
        );

        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ExceptionWrappingError> handleAuthenticationTokenException(AuthenticationCredentialsNotFoundException exception) {
        ExceptionWrappingError error = new ExceptionWrappingError(
                new Date(System.currentTimeMillis()),
                exception.getClass().getSimpleName(),
                HttpServletResponse.SC_UNAUTHORIZED,
                "Invalid or expired token.",
                "",
                new ArrayList<>()
        );

        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllOtherExceptions(Exception ex, WebRequest request) {
        ExceptionWrappingError error = new ExceptionWrappingError(
                new Date(System.currentTimeMillis()),
                ex.getClass().getSimpleName(),
                HttpServletResponse.SC_UNAUTHORIZED,
                ex.getMessage(),
                "",
                new ArrayList<>()
        );


        //return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(error);
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
