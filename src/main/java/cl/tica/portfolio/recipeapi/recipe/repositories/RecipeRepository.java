package cl.tica.portfolio.recipeapi.recipe.repositories;

import cl.tica.portfolio.recipeapi.recipe.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query(value = "SELECT * FROM recipes WHERE to_tsvector(:lang, title) @@ to_tsquery(:lang, :title) "
            + "ORDER BY ts_rank(to_tsvector(:lang, title), to_tsquery(:lang, :title)) DESC", nativeQuery = true)
    List<Recipe> findTitleByRelevance(String lang, String title);
}
