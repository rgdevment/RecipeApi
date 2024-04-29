package cl.tica.portfolio.recipeapi.auth.listeners;

import cl.tica.portfolio.recipeapi.auth.events.OnRegistrationCompleteEvent;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SendVerificationEmailListenerTest {
    @InjectMocks
    private SendVerificationEmailListener sendVerificationEmailListener;

    private static final Logger LOGGER = spy(LoggerFactory.getLogger(SendVerificationEmailListener.class));

    @Test
    void testHandleUserRegistrationEvent() {
        Faker faker = new Faker();
        String username = faker.internet().username();
        OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(this, username);
        assertEquals(username, event.getUsername());

        sendVerificationEmailListener.handleSendVerificationEmailListener(event);

        verify(LOGGER, times(1)).info("Sending verification email with username {}", event.getUsername());
    }
}