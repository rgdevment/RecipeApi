package cl.tica.portfolio.recipeapi.auth.security.jwt;

import cl.tica.portfolio.recipeapi.application.exceptions.ApiException;
import cl.tica.portfolio.recipeapi.application.models.ExceptionWrappingError;
import cl.tica.portfolio.recipeapi.auth.exceptions.InvalidConfirmationException;
import cl.tica.portfolio.recipeapi.auth.exceptions.InvalidCredentialsException;
import cl.tica.portfolio.recipeapi.auth.exceptions.UserAlreadyExistException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@ControllerAdvice
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }

    @ExceptionHandler({
            UserAlreadyExistException.class,
            InvalidCredentialsException.class,
            InvalidConfirmationException.class
    })
    public ResponseEntity<ExceptionWrappingError> handleInvalidLoginException(ApiException exception, WebRequest request) {
        String fullPath = getFullPath(request);

        ExceptionWrappingError error = new ExceptionWrappingError(
                exception.getClass().getSimpleName(),
                exception.getStatusCode().value(),
                exception.getReason(),
                fullPath
        );

        return ResponseEntity.status(exception.getStatusCode()).body(error);
    }

    private static String getFullPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}
