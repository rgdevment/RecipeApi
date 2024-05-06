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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
