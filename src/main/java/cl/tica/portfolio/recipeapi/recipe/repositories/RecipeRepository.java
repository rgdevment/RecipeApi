package cl.tica.portfolio.recipeapi.recipe.repositories;

import cl.tica.portfolio.recipeapi.recipe.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository for the Recipe entity.
 * <p>
 * This repository provides methods to interact with the database.
 * Remember to create the GIN or GiST indexes in the database to optimize the full text search queries.
 * Example for English: CREATE INDEX idx_recipes_title_english ON recipes USING gin(to_tsvector('english', title));
 * Example for Spanish: CREATE INDEX idx_recipes_title_spanish ON recipes USING gin(to_tsvector('spanish', title));
 * The indexes are not created automatically by Spring Data JPA.
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query(value =
            "SELECT * FROM recipes WHERE to_tsvector('english', title) @@ websearch_to_tsquery('english', :title) "
                    + "ORDER BY ts_rank(to_tsvector('english', title), websearch_to_tsquery('english', :title)) DESC",
            nativeQuery = true)
    List<Recipe> findTitleByRelevanceInEnglish(String title);

    @Query(value =
            "SELECT * FROM recipes WHERE to_tsvector('spanish', title) @@ websearch_to_tsquery('spanish', :title) "
                    + "ORDER BY ts_rank(to_tsvector('spanish', title), websearch_to_tsquery('spanish', :title)) DESC",
            nativeQuery = true)
    List<Recipe> findTitleByRelevanceInSpanish(String title);
}
