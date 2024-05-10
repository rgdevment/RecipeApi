package cl.tica.portfolio.recipeapi.thirdparty.countriesapi;

import cl.tica.portfolio.recipeapi.application.exceptions.ApiException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class CountryException extends ResponseStatusException implements ApiException {

    public static final String INVALID_CONFIRMATION = "INVALID_CONFIRMATION";

    public CountryException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public String getInternalCode() {
        return INVALID_CONFIRMATION;
    }
}

