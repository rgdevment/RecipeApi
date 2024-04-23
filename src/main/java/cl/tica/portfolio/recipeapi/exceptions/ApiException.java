package cl.tica.portfolio.recipeapi.exceptions;

import org.springframework.http.HttpStatusCode;

public interface ApiException {
    HttpStatusCode getStatusCode();
    String getReason();
}
