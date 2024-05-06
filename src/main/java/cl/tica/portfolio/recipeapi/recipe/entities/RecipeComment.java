package cl.tica.portfolio.recipeapi.recipe.entities;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class RecipeComment extends RecipeBase {
    @NotBlank
    private String content;

    private Integer likes = 0;
    private Boolean isVisible = Boolean.TRUE;
    private Boolean isDeleted = Boolean.FALSE;;
    private Boolean isEdited = Boolean.FALSE;;
    private Boolean isReply = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private RecipeComment parent;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
