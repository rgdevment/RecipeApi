package cl.tica.portfolio.recipeapi.thirdparty.countriesapi;

import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.dto.api.Country;
import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.dto.api.Flags;
import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.dto.api.Name;
import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.dto.response.CountryResponse;
import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.exceptions.CountryRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CountryServiceTest {
    private CountryService service;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        this.service = new CountryService(restTemplate);
    }

    @Test
    void testsGetAllCountries() {
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country(new Name("Chile"), new Flags("chile.png")));
        countryList.add(new Country(new Name("Argentina"), new Flags("argentina.png")));
        countryList.add(new Country(new Name("Perú"), new Flags("peru.png")));
        countryList.add(new Country(new Name("Colombia"), new Flags("colombia.png")));
        countryList.add(new Country(new Name("Mèxico"), new Flags("mexico.png")));

        ResponseEntity<List<Country>> mockResponse = ResponseEntity.ok(countryList);
        ParameterizedTypeReference<List<Country>> typeRef = new ParameterizedTypeReference<>() {
        };

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(typeRef)
        )).thenReturn(mockResponse);

        List<CountryResponse> response = service.getCountries();
        assertEquals(5, response.size());
        assertEquals("Chile", response.getFirst().name());
        assertEquals("chile.png", response.getFirst().flag());

        assertEquals("Argentina", response.get(1).name());
        assertEquals("argentina.png", response.get(1).flag());

        assertEquals("Perú", response.get(2).name());
        assertEquals("peru.png", response.get(2).flag());

        assertEquals("Colombia", response.get(3).name());
        assertEquals("colombia.png", response.get(3).flag());

        assertEquals("Mèxico", response.get(4).name());
        assertEquals("mexico.png", response.get(4).flag());

        verify(restTemplate, times(1)).exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(typeRef)
        );
    }

    @Test
    void testsGetAllCountriesOnError() {
        ResponseEntity<List<Country>> mockResponse = ResponseEntity.ok(null);
        ParameterizedTypeReference<List<Country>> typeRef = new ParameterizedTypeReference<>() {
        };

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(typeRef)
        )).thenReturn(mockResponse);

        CountryRequestException exception = assertThrows(
                CountryRequestException.class, () -> service.getCountries()
        );

        assertEquals("No countries retrieved from external API.", exception.getReason());
        assertEquals("INVALID_COUNTRY_REQUEST", exception.getInternalCode());
    }

    @Test
    void testRefreshCountries() {
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country(new Name("Chile"), new Flags("chile.png")));

        ResponseEntity<List<Country>> mockResponse = ResponseEntity.ok(countryList);
        ParameterizedTypeReference<List<Country>> typeRef = new ParameterizedTypeReference<>() {
        };

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(typeRef)
        )).thenReturn(mockResponse);

        List<CountryResponse> firstResult = service.getCountries();
        List<CountryResponse> secondResult = service.getCountries();
        assertEquals(firstResult, secondResult);

        service.refreshCountries();

        assertNull(Objects.requireNonNull(cacheManager.getCache("countries")).get("countries"));
    }
}
