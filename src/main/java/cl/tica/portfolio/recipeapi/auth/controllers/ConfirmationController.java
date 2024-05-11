package cl.tica.portfolio.recipeapi.auth.controllers;

import cl.tica.portfolio.recipeapi.application.models.ExceptionWrappingError;
import cl.tica.portfolio.recipeapi.auth.dto.response.UserConfirmationResponse;
import cl.tica.portfolio.recipeapi.auth.exceptions.InvalidConfirmationException;
import cl.tica.portfolio.recipeapi.auth.services.ConfirmationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Confirmation Controller")
@RestController
@RequestMapping("/v1/auth")
public class ConfirmationController {
    private final ConfirmationService service;

    public ConfirmationController(ConfirmationService service) {
        this.service = service;
    }

    @Operation(summary = "Confirm user and email.")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema =
    @Schema(implementation = UserConfirmationResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json", schema =
    @Schema(implementation = ExceptionWrappingError.class)))
    @GetMapping("/confirm-account/{code}")
    public ResponseEntity<UserConfirmationResponse> confirmUserAccount(@PathVariable String code) {
        boolean isSuccess = service.confirmEmail(code);
        if (isSuccess) {
            UserConfirmationResponse response = new UserConfirmationResponse("User and email verify successfully.");
            return ResponseEntity.ok(response);
        }
        throw new InvalidConfirmationException(HttpStatus.UNAUTHORIZED,
                "Validation was not possible, the token or user is not valid.");
    }
}
