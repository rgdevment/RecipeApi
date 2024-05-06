package cl.tica.portfolio.recipeapi.recipe.entities;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private RecipeComment recipeComment;

    // getters y setters...
}
