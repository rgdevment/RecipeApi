package cl.tica.portfolio.recipeapi.auth.listeners;

import cl.tica.portfolio.recipeapi.auth.events.OnRegistrationCompleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendVerificationEmailListener {
    private final Logger logger = LoggerFactory.getLogger(SendVerificationEmailListener.class);

    @Async
    @EventListener
    public void handleSendVerificationEmailListener(OnRegistrationCompleteEvent event) {
        logger.info("Sending verification email with username {}", event.getUsername());
    }
}
