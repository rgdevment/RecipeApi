package cl.tica.portfolio.recipeapi.recipe.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient extends RecipeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_ingredients_seq")
    @SequenceGenerator(name = "recipe_ingredients_seq", allocationSize = 1)
    private Long id;

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
