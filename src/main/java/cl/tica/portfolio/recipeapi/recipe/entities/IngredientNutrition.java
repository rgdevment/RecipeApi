package cl.tica.portfolio.recipeapi.recipe.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ingredients_nutrition")
public class IngredientNutrition extends RecipeBase {
    @NotNull
    private double calories;

    @NotNull
    private double defaultQuantity;

    @ManyToOne
    @JoinColumn(name = "unit_measure_id", referencedColumnName = "id")
    private UnitMeasure unitMeasure;

    @OneToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient;
}
