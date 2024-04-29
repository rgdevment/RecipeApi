package cl.tica.portfolio.recipeapi.auth.entities;

public class UserVerificationTokenTestStub {
    public static UserVerificationToken create(User user) {
        UserVerificationToken userVerificationToken = new UserVerificationToken(user);
        userVerificationToken.onCreate();

        return userVerificationToken;
    }

    public static UserVerificationToken random() {
        User user = UserTestStub.random();

        return create(user);
    }
}
