package cl.tica.portfolio.recipeapi.recipe.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "ingredients", indexes = {
        @Index(name = "idx_name", columnList = "name")
})
public class Ingredient extends RecipeBase {
    @NotBlank
    @Size(min = 3, max = 50)
    @Column(unique = true, nullable = false)
    private String name;

    @Size(min = 3, max = 50)
    @ElementCollection
    private List<String> alternateNames;

    @OneToOne(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private IngredientNutrition defaultNutritionInfo;
}
