package cl.tica.portfolio.recipeapi.auth.repositories;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("h2")
class AuthRepositoryIT {
    @Autowired
    private AuthRepository repository;

    @Test
    void findByUsername() {
        Optional<User> optionalUser = repository.findByUsernameIgnoreCase("user_admin");
        assertTrue(optionalUser.isPresent());
        assertEquals(3, optionalUser.get().getId());
        assertEquals("user_admin", optionalUser.get().getUsername());
        assertEquals("rgdevment@linkedin.com", optionalUser.get().getEmail());
        assertNotNull(optionalUser.get().getPassword());
        assertNotNull(optionalUser.get().getRoles());
        assertNotNull(optionalUser.get().getUserData());
        assertInstanceOf(LocalDateTime.class, optionalUser.get().getCreatedAt());
        assertInstanceOf(LocalDateTime.class, optionalUser.get().getUpdatedAt());
    }

    @Test
    void findByUsernameThrowsException() {
        Faker faker = new Faker();
        Optional<User> optionalUser = repository.findByUsernameIgnoreCase(faker.internet().username());
        assertThrows(NoSuchElementException.class, optionalUser::get);
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void existsByUsername() {
        boolean exists = repository.existsByUsername("user_admin");
        assertTrue(exists);
    }

    @Test
    void notExistsByUsername() {
        Faker faker = new Faker();
        boolean exists = repository.existsByUsername(faker.internet().username());
        assertFalse(exists);
    }

    @Test
    void existsByEmail() {
        boolean exists = repository.existsByEmail("rgdevment@github.com");
        assertTrue(exists);
    }

    @Test
    void notExistsByEmail() {
        Faker faker = new Faker();
        boolean exists = repository.existsByEmail(faker.internet().emailAddress());
        assertFalse(exists);
    }
}
