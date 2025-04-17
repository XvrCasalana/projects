package com.flagexplorer.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.flagexplorer.model.Country;
import com.flagexplorer.model.CountryDetails;

/**
 * @author thanduxolo fokwebe
 */
@Service
public class CountryService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://restcountries.com/v3.1";

    @SuppressWarnings("unchecked")
    public List<Country> getCountries() {
        var response = restTemplate.getForObject(API_URL + "/all", Object[].class);
        return Arrays.stream(response)
                .map(obj -> {
                    var map = (java.util.Map<String, Object>) obj;
                    var nameMap = (java.util.Map<String, Object>) map.get("name");
                    var flagsMap = (java.util.Map<String, Object>) map.get("flags");
                    Country country = new Country();
                    country.setName((String) nameMap.get("common"));
                    country.setFlag((String) flagsMap.get("png"));
                    return country;
                })
                .toList();
    }
    @SuppressWarnings("unchecked")
    public Optional<CountryDetails> getCountryByName(String name) {
        var response = restTemplate.getForObject(API_URL + "/name/" + name, Object[].class);
        if (response == null || response.length == 0)
            return Optional.empty();
        var map = (java.util.Map<String, Object>) response[0];
        var nameMap = (java.util.Map<String, Object>) map.get("name");
        var flagsMap = (java.util.Map<String, Object>) map.get("flags");
        var capitalList = (List<String>) map.get("capital");

        CountryDetails countryDetails = new CountryDetails();
        countryDetails.setName((String) nameMap.get("common"));
        countryDetails.setFlag((String) flagsMap.get("png"));
        countryDetails.setPopulation((Integer) map.get("population"));
        countryDetails.setCapital(capitalList != null && !capitalList.isEmpty() ? capitalList.get(0) : "N/A");

        return Optional.of(countryDetails);
    }
}
