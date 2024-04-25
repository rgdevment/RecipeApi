package cl.tica.portfolio.recipeapi.auth.repositories;

import cl.tica.portfolio.recipeapi.auth.entities.User;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryIT {
    @Autowired
    UserRepository repository;

    @Test
    void findByUsername() {
        Optional<User> optionalUser = repository.findByUsername("admin");
        assertTrue(optionalUser.isPresent());
        assertEquals("admin", optionalUser.get().getUsername());
    }

    @Test
    void findByUsernameThrowsException() {
        Faker faker = new Faker();
        Optional<User> optionalUser = repository.findByUsername(faker.internet().username());
        assertThrows(NoSuchElementException.class, optionalUser::get);
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void existsByUsername() {
        boolean exists = repository.existsByUsername("admin");
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