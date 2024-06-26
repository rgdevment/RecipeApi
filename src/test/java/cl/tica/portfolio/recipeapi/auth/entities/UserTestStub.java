package cl.tica.portfolio.recipeapi.auth.entities;

import cl.tica.portfolio.recipeapi.auth.enums.GenderType;
import net.datafaker.Faker;

public class UserTestStub {
    public static User create(String username, String email, String password) {
        User user = new User(username, email, password);
        user.onCreate();
        user.onUpdate();

        return user;
    }

    public static User random() {
        Faker faker = new Faker();
        String username = faker.internet().username();
        String email = faker.internet().username();
        String password = faker.internet().password();

        User user = create(username, email, password);

        user.setAccountEnabled(false);
        user.setEmailVerified(false);
        UserAdditionalData userAdditionalData = new UserAdditionalData(user);
        userAdditionalData.setName(faker.name().firstName());
        userAdditionalData.setLastname(faker.name().lastName());
        userAdditionalData.setGender(GenderType.OTHER);
        user.setUserData(userAdditionalData);

        return user;
    }
}
