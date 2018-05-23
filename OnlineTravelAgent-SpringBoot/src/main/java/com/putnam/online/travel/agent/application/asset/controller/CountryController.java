package com.putnam.online.travel.agent.application.asset.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.putnam.online.travel.agent.application.asset.model.entity.Country;
import com.putnam.online.travel.agent.application.asset.service.CountryService;

@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping(path = "countries", consumes = "application/json", produces = "application/json")
    public Long addCountry(@RequestBody Country country) {
	return countryService.addCountry(country);
    }

    @GetMapping(path = "countries", produces = "application/json")
    public List<Country> getAllCountries() {
	return countryService.getAllCountries();
    }

    @GetMapping(path = "countries/{name}", produces = "application/json")
    public Optional<Country> getCountryByName(@PathVariable("name") String name) {
	return countryService.getCountryByName(name);
    }
}
