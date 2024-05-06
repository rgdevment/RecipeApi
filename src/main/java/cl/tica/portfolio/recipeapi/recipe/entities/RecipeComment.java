package cl.tica.portfolio.recipeapi.recipe.entities;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "recipe_comments")
public class RecipeComment extends RecipeBase {
    @NotBlank
    private String content;

    private Boolean isVisible = Boolean.TRUE;
    private Boolean isDeleted = Boolean.FALSE;
    private Boolean isEdited = Boolean.FALSE;
    private Boolean isReply = Boolean.FALSE;

    @OneToMany(mappedBy = "recipeComment")
    private List<UserLike> likes;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id", referencedColumnName = "id")
    private RecipeComment parent;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;
}
