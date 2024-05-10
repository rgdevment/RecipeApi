package cl.tica.portfolio.recipeapi.thirdparty.countriesapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @Operation(summary = "Get a cacheable list of countries and regions for region adaptation on recipe.")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CountryResponse.class))))
    @ApiResponse(responseCode = "502", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CountryException.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema()))
    @GetMapping
    public ResponseEntity<List<CountryResponse>> getCountries() {
        List<CountryResponse> countries = countryService.getCountries();
        return ResponseEntity.ok(countries);
    }

    @Operation(summary = "Refresh the cache of countries and regions.")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CountryResponse.class))))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema()))
    @PostMapping("/refresh")
    @PreAuthorize("hasRole('ADMIN')")
    public void refreshCountries() {
        countryService.refreshCountries();
    }
}