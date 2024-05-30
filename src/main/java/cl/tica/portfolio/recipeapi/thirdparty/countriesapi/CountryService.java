package cl.tica.portfolio.recipeapi.thirdparty.countriesapi;

import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.dto.api.Country;
import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.dto.response.CountryResponse;
import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.exceptions.CountryRequestException;
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

    private static final String URL_COUNTRY_API = "https://countries.restapi.cl/v1/all";

    private final RestTemplate restTemplate;

    public CountryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("countries")
    public List<CountryResponse> getCountries() {
        ResponseEntity<List<Country>> response = restTemplate.exchange(
                URL_COUNTRY_API,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<Country> countries = response.getBody();
        if (countries == null) {
            LOGGER.error("No countries retrieved from external API.");
            throw new CountryRequestException(HttpStatus.BAD_GATEWAY, "No countries retrieved from external API.");
        }

        return countries.stream()
                .map(country -> new CountryResponse(country.name(), country.flags().ico()))
                .toList();
    }

    @CacheEvict(value = "countries", allEntries = true)
    public void refreshCountries() {
        // This will reset the cache, forcing the next request to repopulate it.
    }
}
