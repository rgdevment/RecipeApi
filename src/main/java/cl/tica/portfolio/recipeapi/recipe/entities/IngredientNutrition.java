package cl.tica.portfolio.recipeapi.recipe.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class IngredientNutrition extends RecipeBase {
    @NotNull
    private double calories;

    @NotNull
    private double defaultQuantity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "unit_measure_id")
    private UnitMeasure unitMeasure;

    @NotNull
    @OneToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
}
