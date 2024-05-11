package cl.tica.portfolio.recipeapi.thirdparty.countriesapi;

import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.dto.api.Country;
import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.dto.response.CountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CountryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    private static final String URL = "https://restcountries.com/v3.1/all";

    @Cacheable("countries")
    public List<CountryResponse> getCountries() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Country>> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<Country> countries = response.getBody();
        if (countries == null) {
            LOGGER.error("No countries retrieved from external API.");
            throw new CountryException(HttpStatus.BAD_GATEWAY, "No countries retrieved from external API.");
        }

        return countries.stream()
                .map(country -> new CountryResponse(country.name().common(), country.flags().png()))
                .toList();
    }

    @CacheEvict(value = "countries", allEntries = true)
    public void refreshCountries() {
        // This will reset the cache, forcing the next request to repopulate it.
    }
}
