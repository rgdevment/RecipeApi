package cl.tica.portfolio.recipeapi.application.exceptions;

import org.springframework.http.HttpStatusCode;

public interface ApiException {
    HttpStatusCode getStatusCode();
    String getReason();
}
