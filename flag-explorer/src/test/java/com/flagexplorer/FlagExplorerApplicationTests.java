package com.flagexplorer;

import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.flagexplorer.model.Country;
import com.flagexplorer.service.CountryService;

@WebMvcTest
class FlagExplorerApplicationTests {

	@Autowired
    private MockMvc mockMvc;

    private CountryService countryService;

    @Test
    void shouldReturnListOfCountries() throws Exception {
        Country country = new Country();
        country.setName("South Africa");
        country.setFlag("https://flag.png");

        when(countryService.getCountries()).thenReturn(Collections.singletonList(country));

        mockMvc.perform(get("/countries"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCountryDetails() throws Exception {
        mockMvc.perform(get("/countries/France"))
                .andExpect(status().isOk());
    }

}
