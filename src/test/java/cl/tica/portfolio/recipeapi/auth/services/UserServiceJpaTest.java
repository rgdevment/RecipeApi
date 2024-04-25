package cl.tica.portfolio.recipeapi.auth.services;

import cl.tica.portfolio.recipeapi.auth.entities.Role;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.entities.UserData;
import cl.tica.portfolio.recipeapi.auth.repositories.RoleRepository;
import cl.tica.portfolio.recipeapi.auth.repositories.UserRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceJpaTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserService service;

    @BeforeEach
    void setUp() {
        this.userRepository = mock(UserRepository.class);
        this.roleRepository = mock(RoleRepository.class);
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.service = new UserServiceJpa(userRepository, passwordEncoder, roleRepository);
    }

    @Test
    void findAll() {
        Faker faker = new Faker();

        List<User> users = new ArrayList<>();
        users.add(new User(faker.internet().username(), faker.internet().emailAddress(), faker.internet().password()));
        users.add(new User(faker.internet().username(), faker.internet().emailAddress(), faker.internet().password()));
        users.add(new User(faker.internet().username(), faker.internet().emailAddress(), faker.internet().password()));
        users.add(new User(faker.internet().username(), faker.internet().emailAddress(), faker.internet().password()));

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = service.findAll();
        assertEquals(users, result);
        assertEquals(users.size(), result.size());
        assertEquals(users.getFirst(), result.getFirst());
        assertEquals(users.getLast(), result.getLast());
    }

    @Test
    void findByUsername() {
        Faker faker = new Faker();
        String username = faker.internet().username();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        when(userRepository.findByUsername(username)).thenReturn(
                Optional.of(new User(username, email, password))
        );

        Optional<User> result = service.findByUsername(username);
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        assertEquals(email, result.get().getEmail());
        assertEquals(password, result.get().getPassword());
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
        when(userRepository.save(user)).thenReturn(user);

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
        assertEquals(null, result.getUserData());
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
        when(userRepository.save(user)).thenReturn(user);

        User result = service.save(user);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertNotEquals(password, result.getPassword());
        assertTrue(passwordEncoder.matches(password, result.getPassword()));
        assertEquals(new ArrayList<>(), result.getRoles());
    }

    @Test
    void existsByUsername() {
        Faker faker = new Faker();
        String username = faker.internet().username();
        when(userRepository.existsByUsername(username)).thenReturn(true);

        assertTrue(service.existsByUsername(username));
    }

    @Test
    void existsByEmail() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertTrue(service.existsByEmail(email));
    }
}
