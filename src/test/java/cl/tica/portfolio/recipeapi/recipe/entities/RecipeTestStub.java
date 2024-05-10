package cl.tica.portfolio.recipeapi.recipe.entities;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.entities.UserTestStub;
import cl.tica.portfolio.recipeapi.recipe.enums.Difficulty;
import net.datafaker.Faker;

class RecipeTestStub {
    public static void create(String title,
                              String preparation,
                              Integer cookingTime,
                              Integer servingSize,
                              String originVersion,
                              Difficulty difficulty,
                              User author) {
    }

    public static void random() {
        Faker faker = new Faker();
        String title = faker.food().dish();
        String preparation = faker.lorem().paragraph();
        Integer cookingTime = faker.number().numberBetween(30, 240);
        Integer servingSize = faker.number().numberBetween(2, 12);
        String originVersion = faker.country().name();
        Difficulty difficulty = Difficulty.values()[Difficulty.values().length - 1];
        User author = UserTestStub.random();

        create(title, preparation, cookingTime, servingSize, originVersion, difficulty, author);
    }
}
