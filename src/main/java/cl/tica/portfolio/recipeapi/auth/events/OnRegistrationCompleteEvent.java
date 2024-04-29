package cl.tica.portfolio.recipeapi.auth.events;

import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private final String username;

    public OnRegistrationCompleteEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
