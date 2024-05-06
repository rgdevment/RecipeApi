package cl.tica.portfolio.recipeapi.recipe.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class RecipeIngredient extends RecipeBase {
    @NotNull
    private double quantity;

    @ManyToOne
    @JoinColumn(name = "unit_measure_id", referencedColumnName = "id")
    private UnitMeasure unitMeasure;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient;

}
