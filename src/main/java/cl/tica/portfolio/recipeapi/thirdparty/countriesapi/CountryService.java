package cl.tica.portfolio.recipeapi.thirdparty.countriesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CountryService {
    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);

    private static final String URL = "https://restcountries.com/v3.1/all";

    @Cacheable("countries")
    public List<CountryResponse> getCountries() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            List<Map<String, Object>> response = restTemplate.getForObject(URL, List.class);

            List<CountryResponse> countries = new ArrayList<>();
            for (Map<String, Object> country : response) {
                CountryResponse countryResponse = new CountryResponse();
                Map<String, Object> name = (Map<String, Object>) country.get("name");
                countryResponse.setCommonName((String) name.get("common"));
                countryResponse.setFlags((Map<String, String>) country.get("flags"));
                countries.add(countryResponse);
            }

            return countries;
        } catch (RestClientException exception) {
            logger.error("Error retrieving countries from external API.", exception);
            throw new CountryException(HttpStatus.BAD_GATEWAY, "Error retrieving countries from external API.");
        }
    }

    @CacheEvict(value = "countries", allEntries = true)
    public void refreshCountries() {
        // This will reset the cache, forcing the next request to repopulate it.
    }
}
