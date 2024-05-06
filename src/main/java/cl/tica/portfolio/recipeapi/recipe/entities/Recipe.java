package cl.tica.portfolio.recipeapi.recipe.entities;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.recipe.enums.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Recipe extends RecipeBase {
    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Size(min = 20, max = 1000)
    @Column(nullable = false)
    private String preparation;

    @NotNull
    @Schema(description = "The time it takes to prepare the recipe, in minutes.")
    private Integer cookingTime;

    @NotNull
    @Schema(description = "The number of servings the recipe makes.")
    private Integer servingSize;

    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(description = "Origin or regional adaptation of this recipe.")
    private String originVersion;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ElementCollection
    private List<String> images;

    @ElementCollection
    private List<String> tags;

    @NotEmpty
    @ElementCollection
    private List<String> categories;

    @Schema(description = "Ideal accompaniment for the recipe, drinks, snacks, etc..")
    @ElementCollection
    private List<String> pairings;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;

    @NotEmpty
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeComment> comments;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeRating> ratings;
}
