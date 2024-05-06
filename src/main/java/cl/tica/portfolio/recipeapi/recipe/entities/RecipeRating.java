package cl.tica.portfolio.recipeapi.recipe.entities;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "recipe_ratings")
public class RecipeRating extends RecipeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_ratings_seq")
    @SequenceGenerator(name = "recipe_ratings_seq", allocationSize = 1)
    private Long id;

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
