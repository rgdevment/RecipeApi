package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.entities.UserAdditionalData;
import cl.tica.portfolio.recipeapi.auth.entities.UserAdditionalDataTestStub;
import cl.tica.portfolio.recipeapi.auth.entities.UserTestStub;
import cl.tica.portfolio.recipeapi.auth.enums.GenderType;
import cl.tica.portfolio.recipeapi.auth.repositories.AuthRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserAdditionalDataJpaTest {
    private AuthRepository authRepository;
    private UserAdditionalDataService service;

    @BeforeEach
    void setUp() {
        this.authRepository = mock(AuthRepository.class);
        this.service = new UserAdditionalDataServiceJpa(authRepository);
    }

    @Test
    void testFindUserByUsername() {
        User user = UserTestStub.random();
        when(authRepository.findByUsernameIgnoreCase(user.getUsername())).thenReturn(Optional.of(user));
        User result = service.findUserByUsername(user.getUsername());

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());

        verify(authRepository, times(1)).findByUsernameIgnoreCase(user.getUsername());
    }

    @Test
    void testFindUserByUsernameAndNotFound() {
        Faker faker = new Faker();
        String username = faker.internet().username();
        when(authRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class, () -> service.findUserByUsername(username)
        );

        assertEquals(username, exception.getMessage());

        verify(authRepository, times(1)).findByUsernameIgnoreCase(username);
    }

    @Test
    void testUpdateUserDataSuccessfully() {
        Faker faker = new Faker();
        User user = UserTestStub.random();
        UserAdditionalData userAdditionalData = UserAdditionalDataTestStub.create(user);
        userAdditionalData.setName(faker.name().firstName());
        userAdditionalData.setLastname(faker.name().lastName());
        userAdditionalData.setGender(GenderType.OTHER);
        user.setUserData(userAdditionalData);

        when(authRepository.save(user)).thenReturn(user);

        User userSaved = service.updateUserData(user);

        assertEquals(userAdditionalData.getName(), userSaved.getUserData().getName());
        assertEquals(userAdditionalData.getLastname(), userSaved.getUserData().getLastname());
        assertEquals(userAdditionalData.getGender(), userSaved.getUserData().getGender());
        assertEquals(userAdditionalData.getUser().getUsername(), userSaved.getUsername());

        verify(authRepository, times(1)).save(user);
    }
}
