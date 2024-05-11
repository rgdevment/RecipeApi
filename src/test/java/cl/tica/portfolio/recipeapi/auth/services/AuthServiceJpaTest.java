package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.Role;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.entities.UserTestStub;
import cl.tica.portfolio.recipeapi.auth.events.OnRegistrationCompleteEvent;
import cl.tica.portfolio.recipeapi.auth.exceptions.UserAlreadyExistException;
import cl.tica.portfolio.recipeapi.auth.repositories.AuthRepository;
import cl.tica.portfolio.recipeapi.auth.repositories.RoleRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static cl.tica.portfolio.recipeapi.auth.entities.Role.DEFAULT_ROLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceJpaTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private AuthRepository authRepository;
    private RoleRepository roleRepository;
    private ApplicationEventPublisher eventPublisher;
    private ConfirmationService confirmationService;
    private AuthenticationService service;

    @BeforeEach
    void setUp() {
        this.authRepository = mock(AuthRepository.class);
        this.roleRepository = mock(RoleRepository.class);
        this.eventPublisher = mock(ApplicationEventPublisher.class);
        this.confirmationService = mock(ConfirmationService.class);

        this.service = new AuthenticationServiceJpa(authRepository, passwordEncoder,
                roleRepository, eventPublisher, confirmationService);
    }

    @Test
    void registerSuccessfully() {
        Faker faker = new Faker();

        String username = faker.internet().username();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        User user = UserTestStub.create(username, email, password);

        when(roleRepository.findByName(DEFAULT_ROLE)).thenReturn(Optional.of(new Role(DEFAULT_ROLE)));
        when(authRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(authRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(authRepository.save(user)).thenReturn(user);

        User result = service.register(user);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertNotEquals(password, result.getPassword());
        assertTrue(passwordEncoder.matches(password, result.getPassword()));
        assertEquals(user.getRoles(), result.getRoles());
        assertEquals(user.getRoles().getFirst().getName(), result.getRoles().getFirst().getName());
        assertFalse(result.isAccountEnabled());
        assertFalse(result.isEmailVerified());
        assertNotNull(result.getUserData());
        assertInstanceOf(LocalDateTime.class, user.getCreatedAt());
        assertInstanceOf(LocalDateTime.class, user.getUpdatedAt());
        assertFalse(result.isEmailVerified());

        verify(roleRepository, times(1)).findByName(DEFAULT_ROLE);
        verify(authRepository, times(1)).existsByUsername(user.getUsername());
        verify(authRepository, times(1)).existsByEmail(user.getEmail());
        verify(authRepository, times(1)).save(user);
        verify(confirmationService, times(1)).generateVerificationToken(user);
        verify(eventPublisher, times(1)).publishEvent(any(OnRegistrationCompleteEvent.class));
    }

    @Test
    void registerWithOutRole() {
        Faker faker = new Faker();

        String username = faker.internet().username();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        User user = UserTestStub.create(username, email, password);

        when(roleRepository.findByName(DEFAULT_ROLE)).thenReturn(Optional.empty());
        when(authRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(authRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(authRepository.save(user)).thenReturn(user);

        User result = service.register(user);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertNotEquals(password, result.getPassword());
        assertTrue(passwordEncoder.matches(password, result.getPassword()));
        assertEquals(new ArrayList<>(), result.getRoles());

        verify(roleRepository, times(1)).findByName(DEFAULT_ROLE);
        verify(authRepository, times(1)).existsByUsername(user.getUsername());
        verify(authRepository, times(1)).existsByEmail(user.getEmail());
        verify(authRepository, times(1)).save(user);
        verify(confirmationService, times(1)).generateVerificationToken(user);
        verify(eventPublisher, times(1)).publishEvent(any(OnRegistrationCompleteEvent.class));
    }

    @Test
    void registerUsernameAlreadyExists() {
        User user = UserTestStub.random();

        when(authRepository.existsByUsername(user.getUsername())).thenReturn(true);

        UserAlreadyExistException exception =
                assertThrows(UserAlreadyExistException.class, () -> service.register(user));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Username is already taken.", exception.getReason());
        assertEquals(UserAlreadyExistException.USER_ALREADY_EXIST, exception.getInternalCode());

        verify(authRepository, times(1)).existsByUsername(user.getUsername());
        verify(authRepository, never()).existsByEmail(anyString());
        verify(roleRepository, never()).findByName(anyString());
        verify(authRepository, never()).save(any());
        verify(confirmationService, never()).generateVerificationToken(any());
        verify(eventPublisher, never()).publishEvent(any());
    }


    @Test
    void registerEmailAlreadyExists() {
        User user = UserTestStub.random();

        when(authRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(authRepository.existsByEmail(user.getEmail())).thenReturn(true);

        UserAlreadyExistException exception =
                assertThrows(UserAlreadyExistException.class, () -> service.register(user));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("The email is already registered.", exception.getReason());

        verify(authRepository, times(1)).existsByUsername(user.getUsername());
        verify(authRepository, times(1)).existsByEmail(user.getEmail());
        verify(roleRepository, never()).findByName(anyString());
        verify(authRepository, never()).save(any());
        verify(confirmationService, never()).generateVerificationToken(any());
        verify(eventPublisher, never()).publishEvent(any());
    }
}
