package cl.tica.portfolio.recipeapi.recipe.entities;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "recipe_comments")
public class RecipeComment extends RecipeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_comments_seq")
    @SequenceGenerator(name = "recipe_comments_seq", allocationSize = 1)
    private Long id;

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
