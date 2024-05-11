package cl.tica.portfolio.recipeapi.thirdparty.countriesapi;

import cl.tica.portfolio.recipeapi.auth.security.SecurityConfig;
import cl.tica.portfolio.recipeapi.thirdparty.countriesapi.dto.response.CountryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(CountryController.class)
class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService service;

    @Test
    @WithMockUser(username = "@rgdevment")
    void getCountryListData() throws Exception {
        List<CountryResponse> response = new ArrayList<>();
        response.add(new CountryResponse("Chile", "chl.svg"));
        response.add(new CountryResponse("Argentina", "arg.svg"));
        response.add(new CountryResponse("Perú", "per.svg"));
        response.add(new CountryResponse("Colombia", "col.svg"));
        response.add(new CountryResponse("Mexico", "mex.svg"));

        when(service.getCountries()).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/countries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(response.size()))
                .andExpect(jsonPath("$[0].name").value("Chile"))
                .andExpect(jsonPath("$[0].flag").value("chl.svg"));

        verify(service, times(1)).getCountries();
    }
}
