package com.flagexplorer.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flagexplorer.model.Country;
import com.flagexplorer.model.CountryDetails;

public class CountryServiceTest {
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        countryService = new CountryService();
    }

    @Test
    void getAllCountries_shouldReturnList() {
        List<Country> countries = countryService.getCountries();
        assertNotNull(countries);
        assertTrue(countries.size() > 0);
    }

    @Test
    void getCountryByName_shouldReturnDetails() {
        Optional<CountryDetails> detailsOpt = countryService.getCountryByName("France");
        assertTrue(detailsOpt.isPresent());
        CountryDetails details = detailsOpt.get();
        assertEquals("France", details.getName());
        assertNotNull(details.getCapital());
        assertTrue(details.getPopulation() > 0);
        assertNotNull(details.getFlag());
    }

    @Test
    void getCountryByName_shouldReturnEmptyForInvalidCountry() {
        Optional<CountryDetails> result = countryService.getCountryByName("InvalidCountry");
        assertTrue(result.isEmpty());
    }
}
