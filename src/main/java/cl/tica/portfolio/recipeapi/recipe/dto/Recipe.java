package cl.tica.portfolio.recipeapi.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Recipe(
        @NotBlank @Size(min = 3, max = 100) String title,
        @NotBlank @Size(min = 20) String preparation,
        @NotNull Integer cookingTime,
        @NotNull Integer servingSize,
        String originVersion
) {

}
