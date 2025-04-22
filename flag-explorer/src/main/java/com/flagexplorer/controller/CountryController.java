package com.flagexplorer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flagexplorer.model.Country;
import com.flagexplorer.model.CountryDetails;
import com.flagexplorer.service.CountryService;

@RestController
@RequestMapping("/countries")
@CrossOrigin
public class CountryController {
    
    @Autowired
    private CountryService countryService;

    @GetMapping
    public List<Country> getCountries(){
         return countryService.getCountries();
    }

    @GetMapping("/{name}")
    public CountryDetails getCountryByName(@PathVariable String name) {
        return countryService.getCountryByName(name)
                .orElseThrow(() -> new RuntimeException("Country not found"));
    }

    
}
