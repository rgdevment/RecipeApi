package cl.tica.portfolio.recipeapi.auth.controllers;

import cl.tica.portfolio.recipeapi.auth.dto.request.LoginRequest;
import cl.tica.portfolio.recipeapi.auth.dto.request.SignupRequest;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.entities.UserTestStub;
import cl.tica.portfolio.recipeapi.auth.security.jwt.JwtUtils;
import cl.tica.portfolio.recipeapi.auth.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService service;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void registerUser() throws Exception {
        Faker faker = new Faker();
        SignupRequest request = new SignupRequest(faker.internet().username(),
                faker.internet().emailAddress(), faker.internet().password());

        User user = UserTestStub.create(request.username(), request.email(), request.password());
        when(service.register(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.password").doesNotExist());

        verify(service, times(1)).register(any(User.class));
    }

    @Test
    void loginUser() throws Exception {
        Faker faker = new Faker();
        LoginRequest request = new LoginRequest(faker.internet().username(), faker.internet().password());
        Authentication authentication = mock(Authentication.class);
        authentication.setAuthenticated(true);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateToken(authentication)).thenReturn("token");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("token"));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, times(1)).generateToken(authentication);
    }

    @Test
    void loginUserInvalidCredentialsException() throws Exception {
        Faker faker = new Faker();
        LoginRequest request = new LoginRequest(faker.internet().username(), faker.internet().password());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(
                new BadCredentialsException("bad credentials"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("InvalidCredentialsException"))
                .andExpect(jsonPath("$.message").value("Credentials do not match or the user is not activated."));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, never()).generateToken(any(Authentication.class));
    }

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
