package cl.tica.portfolio.recipeapi.auth.entities;

import cl.tica.portfolio.recipeapi.auth.enums.GenderType;
import net.datafaker.Faker;

import java.util.Random;

public class UserAdditionalDataTestStub {
    public static UserAdditionalData create(User user) {
        return new UserAdditionalData(user);
    }

    public static UserAdditionalData random() {
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        Random random = new Random();
        GenderType randomGender = GenderType.values()[random.nextInt(GenderType.values().length)];

        User user = UserTestStub.random();
        UserAdditionalData userAdditionalData = create(user);
        userAdditionalData.setName(name);
        userAdditionalData.setLastname(lastname);
        userAdditionalData.setGender(randomGender);

        return userAdditionalData;
    }
}
