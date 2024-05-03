package cl.tica.portfolio.recipeapi.auth.controllers;

import cl.tica.portfolio.recipeapi.auth.dto.request.AdditionalDataRequest;
import cl.tica.portfolio.recipeapi.auth.entities.User;
import cl.tica.portfolio.recipeapi.auth.entities.UserAdditionalData;
import cl.tica.portfolio.recipeapi.auth.entities.UserTestStub;
import cl.tica.portfolio.recipeapi.auth.enums.GenderType;
import cl.tica.portfolio.recipeapi.auth.security.SecurityConfig;
import cl.tica.portfolio.recipeapi.auth.services.UserAdditionalDataService;
import cl.tica.portfolio.recipeapi.auth.utils.UserPrincipalMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(UserAdditionalDataController.class)
class UserAdditionalDataControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAdditionalDataService service;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void addUserAdditionalData() throws Exception {
        Faker faker = new Faker();
        User userFake = UserTestStub.random();
        UserAdditionalData userAdditionalData = userFake.getUserData();
        userAdditionalData.setName(faker.name().firstName());
        userAdditionalData.setLastname(faker.name().lastName());
        userAdditionalData.setGender(GenderType.OTHER);

        AdditionalDataRequest request = new AdditionalDataRequest(userAdditionalData.getName(),
                userAdditionalData.getLastname(), userAdditionalData.getGender().name());

        when(service.findUserByUsername(userFake.getUsername())).thenReturn(userFake);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/user/additional-data")
                        .with(user(new UserPrincipalMock(userFake.getUsername())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(service, times(1)).findUserByUsername(userFake.getUsername());
        verify(service, times(1)).updateUserData(userFake);
    }
}
