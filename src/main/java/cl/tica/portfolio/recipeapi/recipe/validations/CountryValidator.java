package cl.tica.portfolio.recipeapi.recipe.validations;

import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.CountryService;
import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.dto.response.CountryResponse;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CountryValidator implements ConstraintValidator<ValidCountry, String> {
    private final CountryService countryService;

    public CountryValidator(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<CountryResponse> countries = countryService.getCountries();
        return countries.stream().anyMatch(country -> country.name().equalsIgnoreCase(value));
    }
}
