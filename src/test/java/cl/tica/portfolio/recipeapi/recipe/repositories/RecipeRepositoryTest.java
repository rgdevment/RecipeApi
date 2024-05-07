package cl.tica.portfolio.recipeapi.recipe.repositories;

import cl.tica.portfolio.recipeapi.recipe.entities.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("postgres")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RecipeRepositoryTest {
    @Autowired
    private RecipeRepository repository;

    @Test
    void findTitleByRelevanceInEnglish() {
        List<Recipe> recipes = repository.findTitleByRelevanceInEnglish("marisco");
        assertNotNull(recipes);
        assertEquals(2, recipes.size());
        Recipe recipe = recipes.getFirst();
        assertEquals(1, recipe.getId());
        assertEquals("Chupe de marisco", recipe.getTitle());
        assertEquals("Preparación del chupe...", recipe.getPreparation());
        assertNotNull(recipe.getIngredients());
        assertEquals(60, recipe.getCookingTime());
        assertEquals(4, recipe.getServingSize());
        assertEquals("Chile", recipe.getOriginVersion());
        assertEquals("BEGINNER", recipe.getDifficulty().name());
    }

    @Test
    void findTitleByRelevanceInSpanish() {
    }
}
