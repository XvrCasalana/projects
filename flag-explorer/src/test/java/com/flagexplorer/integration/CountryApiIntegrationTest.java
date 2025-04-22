package com.flagexplorer.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.flagexplorer.model.Country;
import com.flagexplorer.model.CountryDetails;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryApiIntegrationTest {
     @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @SuppressWarnings("null")
    @Test
    public void testGetCountriesEndpoint() {
        String url = getBaseUrl() + "/countries";

        ResponseEntity<Country[]> response = restTemplate.getForEntity(url, Country[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);

        Country firstCountry = response.getBody()[0];
        assertNotNull(firstCountry.getName());
        assertNotNull(firstCountry.getFlag());
    }

    @Test
    public void testGetCountryDetailsEndpoint() {
        String countryName = "Grenada";
        String url = getBaseUrl() + "/countries/" + countryName;

        ResponseEntity<CountryDetails> response = restTemplate.getForEntity(url, CountryDetails.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        CountryDetails details = response.getBody();

        assertNotNull(details);
        assertEquals("Grenada", details.getName());
        assertNotNull(details.getCapital());
        assertTrue(details.getPopulation() > 0);
        assertNotNull(details.getFlag());
    }

    @Test
    public void testGetCountryDetails_NotFound() {
        String url = getBaseUrl() + "/countries/ThisCountryDoesNotExist";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
