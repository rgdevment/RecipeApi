package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.Role;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.repositories.RoleRepository;
import cl.tica.portfolio.recipeapi.auth.repositories.AuthRepository;
import cl.tica.portfolio.recipeapi.auth.repositories.UserConfirmationRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceJpaTest {

    private AuthRepository authRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserConfirmationRepository userConfirmationRepository;
    private EmailService emailService;
    private AuthService service;

    @BeforeEach
    void setUp() {
        this.authRepository = mock(AuthRepository.class);
        this.roleRepository = mock(RoleRepository.class);
        this.userConfirmationRepository = mock(UserConfirmationRepository.class);
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.emailService = mock(EmailService.class);
        this.service = new AuthServiceJpa(authRepository, passwordEncoder,
                roleRepository, userConfirmationRepository, emailService);
    }

    @Test
    void save() {
        Faker faker = new Faker();
        String roleUser = "ROLE_USER";

        String username = faker.internet().username();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        User user = new User(username, email, password);

        when(roleRepository.findByName(roleUser)).thenReturn(Optional.of(new Role(roleUser)));
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
        assertFalse(result.isAccountLocked());
        assertNull(result.getUserData());
    }

    @Test
    void saveWithOutRole() {
        Faker faker = new Faker();
        String role = "ROLE_OTHER";

        String username = faker.internet().username();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        User user = new User(username, email, password);

        when(roleRepository.findByName(role)).thenReturn(null);
        when(authRepository.save(user)).thenReturn(user);

        User result = service.register(user);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertNotEquals(password, result.getPassword());
        assertTrue(passwordEncoder.matches(password, result.getPassword()));
        assertEquals(new ArrayList<>(), result.getRoles());
    }
}
