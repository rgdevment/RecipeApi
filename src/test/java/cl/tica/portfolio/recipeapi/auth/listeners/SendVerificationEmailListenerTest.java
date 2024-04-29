package cl.tica.portfolio.recipeapi.auth.listeners;

import cl.tica.portfolio.recipeapi.auth.events.OnRegistrationCompleteEvent;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
@SpringBootTest
class SendVerificationEmailListenerTest {
    @InjectMocks
    private SendVerificationEmailListener sendVerificationEmailListener;


    @Test
    void testHandleUserRegistrationEvent(CapturedOutput output) {
        Faker faker = new Faker();
        String username = faker.internet().username();
        OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(this, username);
        assertEquals(username, event.getUsername());

        sendVerificationEmailListener.handleSendVerificationEmailListener(event);
        assertThat(output).contains(String.format("Sending verification email with username %s.", event.getUsername()));
    }
}
