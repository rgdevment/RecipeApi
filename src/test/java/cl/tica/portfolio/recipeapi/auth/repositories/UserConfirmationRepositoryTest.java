package cl.tica.portfolio.recipeapi.auth.repositories;

import cl.tica.portfolio.recipeapi.auth.entities.UserVerificationToken;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserConfirmationRepositoryTest {
    @Autowired
    private UserConfirmationRepository repository;

    @Test
    void findUserConfirmationByCode() {
        Optional<UserVerificationToken> optionalUserVerificationToken = repository.findUserConfirmationByCode("fake_code_1");
        assertTrue(optionalUserVerificationToken.isPresent());
        assertEquals("fake_code_1", optionalUserVerificationToken.get().getCode());
        assertEquals(1, optionalUserVerificationToken.get().getId());
        assertInstanceOf(LocalDateTime.class, optionalUserVerificationToken.get().getCreatedAt());
    }

    @Test
    void findUserConfirmationByCodeThrowsException() {
        Faker faker = new Faker();
        Optional<UserVerificationToken> optionalUserVerificationToken = repository.findUserConfirmationByCode(faker.internet().uuid());
        assertThrows(NoSuchElementException.class, optionalUserVerificationToken::get);
        assertFalse(optionalUserVerificationToken.isPresent());
    }
}
