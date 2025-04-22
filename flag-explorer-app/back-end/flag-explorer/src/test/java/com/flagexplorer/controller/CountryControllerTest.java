package com.flagexplorer.controller;

import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.flagexplorer.model.Country;
import com.flagexplorer.model.CountryDetails;
import com.flagexplorer.service.CountryService;

@ExtendWith(MockitoExtension.class)
public class CountryControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    void getAllCountries_shouldReturnListOfCountries() throws Exception {
        Country mockCountry = new Country();
        mockCountry.setName("Germany");
        mockCountry.setFlag("https://flagcdn.com/de.svg");

        when(countryService.getCountries()).thenReturn(Collections.singletonList(mockCountry));

        mockMvc.perform(get("/countries")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Germany"))
                .andExpect(jsonPath("$[0].flag").value("https://flagcdn.com/de.svg"));
    }

    @Test
    void getCountryByName_shouldReturnCountryDetails() throws Exception {
        CountryDetails mockDetails = new CountryDetails();
        mockDetails.setName("Germany");
        mockDetails.setCapital("Berlin");
        mockDetails.setPopulation(83000000);
        mockDetails.setFlag("https://flagcdn.com/de.svg");

        when(countryService.getCountryByName("Germany")).thenReturn(Optional.of(mockDetails));

        mockMvc.perform(get("/countries/Germany")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Germany"))
                .andExpect(jsonPath("$.capital").value("Berlin"))
                .andExpect(jsonPath("$.population").value(83000000))
                .andExpect(jsonPath("$.flag").value("https://flagcdn.com/de.svg"));
    }

    @Test
    void getCountryByName_shouldReturn500WhenNotFound() throws Exception {
        when(countryService.getCountryByName("Wakanda")).thenReturn(Optional.empty());

        mockMvc.perform(get("/countries/Wakanda"))
                .andExpect(status().isInternalServerError());
    }

}
