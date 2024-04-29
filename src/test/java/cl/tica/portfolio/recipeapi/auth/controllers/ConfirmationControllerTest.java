package cl.tica.portfolio.recipeapi.auth.controllers;

import cl.tica.portfolio.recipeapi.auth.services.ConfirmationService;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConfirmationController.class)
@AutoConfigureMockMvc(addFilters = false)
class ConfirmationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfirmationService service;

    @Test
    void confirmUserAccount() throws Exception {
        Faker faker = new Faker();
        String code = faker.internet().uuid();
        when(service.confirmEmail(code)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/auth/confirm-account/" + code))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User and email verify successfully."));

        verify(service, times(1)).confirmEmail(code);
    }

    @Test
    void confirmUserAccountInvalidToken() throws Exception {
        Faker faker = new Faker();
        String code = faker.internet().uuid();
        when(service.confirmEmail(code)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/auth/confirm-account/" + code))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("InvalidConfirmationException"))
                .andExpect(jsonPath("$.message").value("Validation was not possible, the token or user is not valid."));

        verify(service, times(1)).confirmEmail(code);
    }
}
