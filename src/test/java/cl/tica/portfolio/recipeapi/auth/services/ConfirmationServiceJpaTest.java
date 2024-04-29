package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.entities.UserTestStub;
import cl.tica.portfolio.recipeapi.auth.entities.UserVerificationToken;
import cl.tica.portfolio.recipeapi.auth.entities.UserVerificationTokenTestStub;
import cl.tica.portfolio.recipeapi.auth.repositories.AuthRepository;
import cl.tica.portfolio.recipeapi.auth.repositories.UserConfirmationRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ConfirmationServiceJpaTest {
    private AuthRepository authRepository;
    private UserConfirmationRepository userConfirmationRepository;
    private ConfirmationServiceJpa service;

    @BeforeEach
    void setUp() {
        this.authRepository = mock(AuthRepository.class);
        this.userConfirmationRepository = mock(UserConfirmationRepository.class);

        this.service = new ConfirmationServiceJpa(authRepository, userConfirmationRepository);
    }

    @Test
    void confirmEmailTokenNotFound() {
        Faker faker = new Faker();
        String code = faker.internet().uuid();
        when(userConfirmationRepository.findUserConfirmationByCode(code)).thenReturn(Optional.empty());

        assertFalse(service.confirmEmail(code));

        verify(userConfirmationRepository, times(1)).findUserConfirmationByCode(code);
    }

    @Test
    void confirmEmailTokenFoundUserNotPersistOrError() {
        UserVerificationToken userVerificationToken = UserVerificationTokenTestStub.random();
        when(userConfirmationRepository.findUserConfirmationByCode(userVerificationToken.getCode())).thenReturn(Optional.of(userVerificationToken));
        when(authRepository.findByUsernameIgnoreCase(userVerificationToken.getUser().getUsername())).thenReturn(Optional.empty());

        assertFalse(service.confirmEmail(userVerificationToken.getCode()));

        verify(userConfirmationRepository, times(1)).findUserConfirmationByCode(userVerificationToken.getCode());
        verify(authRepository, times(1)).findByUsernameIgnoreCase(userVerificationToken.getUser().getUsername());
    }

    @Test
    void confirmEmailSuccessfully() {
        UserVerificationToken userVerificationToken = UserVerificationTokenTestStub.random();
        when(userConfirmationRepository.findUserConfirmationByCode(userVerificationToken.getCode())).thenReturn(Optional.of(userVerificationToken));
        when(authRepository.findByUsernameIgnoreCase(userVerificationToken.getUser().getUsername())).thenReturn(Optional.of(userVerificationToken.getUser()));

        assertTrue(service.confirmEmail(userVerificationToken.getCode()));
        assertTrue(userVerificationToken.getUser().isEmailVerified());
        assertTrue(userVerificationToken.getUser().isAccountEnabled());
        assertInstanceOf(LocalDateTime.class, userVerificationToken.getUser().getCreatedAt());

        verify(userConfirmationRepository, times(1)).findUserConfirmationByCode(userVerificationToken.getCode());
        verify(authRepository, times(1)).findByUsernameIgnoreCase(userVerificationToken.getUser().getUsername());
    }

    @Test
    void generateConfirmationTokenSuccessfully() {
        User user = UserTestStub.random();
        service.generateVerificationToken(user);

        verify(userConfirmationRepository, times(1)).save(any(UserVerificationToken.class));
    }
}
