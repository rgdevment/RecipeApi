package cl.tica.portfolio.recipeapi.recipe.controllers;

import cl.tica.portfolio.recipeapi.application.models.ExceptionWrappingError;
import cl.tica.portfolio.recipeapi.auth.dto.response.RegisteredUserResponse;
import cl.tica.portfolio.recipeapi.recipe.dto.request.RecipeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/recipes")
public class RecipeController {

    @Operation(summary = "Create a new recipe.")
    @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisteredUserResponse.class)))
    @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrappingError.class)))
    @ApiResponse(responseCode = "401")
    @PostMapping("/create")
    public ResponseEntity<Void> createRecipe(@Valid @RequestBody RecipeRequest request) {
        return ResponseEntity.ok().build();
    }
}
