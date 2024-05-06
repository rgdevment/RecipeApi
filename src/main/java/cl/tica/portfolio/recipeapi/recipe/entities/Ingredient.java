package cl.tica.portfolio.recipeapi.recipe.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Ingredient extends RecipeBase {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3, max = 50)
    @ElementCollection
    private List<String> alternateNames;

    @NotNull
    @OneToOne(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private IngredientNutrition defaultNutritionInfo;
}
