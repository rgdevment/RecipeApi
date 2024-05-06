package cl.tica.portfolio.recipeapi.recipe.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ingredients_nutrition")
public class IngredientNutrition extends RecipeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredients_nutrition_seq")
    @SequenceGenerator(name = "ingredients_nutrition_seq", allocationSize = 1)
    private Long id;

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
