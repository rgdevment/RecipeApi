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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    private static final String URL = "https://restcountries.com/v3.1/all";

    @Cacheable("countries")
    public List<CountryResponse> getCountries() {
        try {
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

            List<CountryResponse> countriesResponse = new ArrayList<>();
            for (Country countryDTO : countries) {
                CountryResponse countryResponse = new CountryResponse(countryDTO.name().common(), countryDTO.flags());
                countriesResponse.add(countryResponse);
            }

            return countriesResponse;
        } catch (RestClientException exception) {
            LOGGER.error("Error retrieving countries from external API.", exception);
            throw new CountryException(HttpStatus.BAD_GATEWAY, "Error retrieving countries from external API.");
        }
    }

    @CacheEvict(value = "countries", allEntries = true)
    public void refreshCountries() {
        // This will reset the cache, forcing the next request to repopulate it.
    }
}
