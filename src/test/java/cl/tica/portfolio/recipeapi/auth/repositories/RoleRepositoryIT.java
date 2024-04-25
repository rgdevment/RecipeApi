package cl.tica.portfolio.recipeapi.auth.repositories;

import cl.tica.portfolio.recipeapi.auth.entities.Role;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class RoleRepositoryIT {
    @Autowired
    RoleRepository repository;

    @Test
    void findByName() {
        Optional<Role> role = repository.findByName("ROLE_USER");
        assertTrue(role.isPresent());
        assertEquals("ROLE_USER", role.get().getName());
    }

    @Test
    void findByNameThrowsException() {
        Faker faker = new Faker();
        Optional<Role> role = repository.findByName(faker.name().name());
        assertThrows(NoSuchElementException.class, role::get);
        assertFalse(role.isPresent());
    }
}