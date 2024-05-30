package cl.tica.portfolio.recipeapi.recipe.dto.request;

import cl.tica.portfolio.recipeapi.recipe.validations.ValidCountry;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RecipeRequest(
        @NotBlank @Size(min = 3, max = 100) String title,
        @NotBlank @Size(min = 20) String preparation,
        @NotNull Integer cookingTime,
        @NotNull Integer servingSize,
        @ValidCountry String countryAdaptation
) {

}
