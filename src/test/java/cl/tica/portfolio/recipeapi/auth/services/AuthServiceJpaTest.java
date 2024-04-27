package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.Role;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.repositories.RoleRepository;
import cl.tica.portfolio.recipeapi.auth.repositories.AuthRepository;
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
    private AuthService service;

    @BeforeEach
    void setUp() {
        this.authRepository = mock(AuthRepository.class);
        this.roleRepository = mock(RoleRepository.class);
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.service = new AuthServiceJpa(authRepository, passwordEncoder, roleRepository);
    }

    @Test
    void findByUsername() {
        Faker faker = new Faker();
        String username = faker.internet().username();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        when(authRepository.findByUsernameIgnoreCase(username)).thenReturn(
                Optional.of(new User(username, email, password))
        );

        Optional<User> result = service.findByUsername(username);
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        assertEquals(email, result.get().getEmail());
        assertEquals(password, result.get().getPassword());
    }

    @Test
    void existsByUsername() {
        Faker faker = new Faker();
        String username = faker.internet().username();
        when(authRepository.existsByUsername(username)).thenReturn(true);

        assertTrue(service.existsByUsername(username));
    }

    @Test
    void existsByEmail() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        when(authRepository.existsByEmail(email)).thenReturn(true);

        assertTrue(service.existsByEmail(email));
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

        User result = service.save(user);
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

        User result = service.save(user);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertNotEquals(password, result.getPassword());
        assertTrue(passwordEncoder.matches(password, result.getPassword()));
        assertEquals(new ArrayList<>(), result.getRoles());
    }
}
