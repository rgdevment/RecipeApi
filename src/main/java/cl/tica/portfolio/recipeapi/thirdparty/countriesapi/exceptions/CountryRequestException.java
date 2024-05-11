package cl.tica.portfolio.recipeapi.thirdparty.countriesapi.exceptions;

import cl.tica.portfolio.recipeapi.application.exceptions.ApiException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class CountryRequestException extends ResponseStatusException implements ApiException {

    public static final String INVALID_CONFIRMATION = "INVALID_COUNTRY_REQUEST";

    public CountryRequestException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public String getInternalCode() {
        return INVALID_CONFIRMATION;
    }
}
