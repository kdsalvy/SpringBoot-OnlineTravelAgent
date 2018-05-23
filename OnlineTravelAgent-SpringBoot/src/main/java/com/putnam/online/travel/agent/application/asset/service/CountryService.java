package com.putnam.online.travel.agent.application.asset.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.putnam.online.travel.agent.application.asset.model.entity.Country;
import com.putnam.online.travel.agent.application.asset.model.repository.CountryRepository;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public Long addCountry(Country country) {
	return countryRepository.save(country).getId();
    }

    public List<Country> getAllCountries() {
	List<Country> countryList = new ArrayList<>();
	countryRepository.findAll().forEach(countryList::add);
	return countryList;
    }

    public Optional<Country> getCountryByName(String name) {
	return countryRepository.findByName(name);
    }

}
