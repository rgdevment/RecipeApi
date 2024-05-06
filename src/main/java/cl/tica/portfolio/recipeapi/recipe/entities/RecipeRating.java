package cl.tica.portfolio.recipeapi.recipe.entities;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class RecipeRating extends RecipeBase {
    @NotNull
    private Integer rating;

    @Size(min = 10, max = 255)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;
}
