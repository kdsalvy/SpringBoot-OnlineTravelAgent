package com.putnam.online.travel.agent.application.asset.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.putnam.online.travel.agent.application.asset.model.entity.City;
import com.putnam.online.travel.agent.application.asset.model.entity.Country;
import com.putnam.online.travel.agent.application.asset.model.repository.CityRepository;
import com.putnam.online.travel.agent.application.asset.model.repository.CountryRepository;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Transactional
    public Long addCity(City city, String countryName) {
	Optional<Country> country = countryRepository.findByNameIgnoreCase(countryName);
	if (country.isPresent()) {
	    city.setCountry(country.get());
	    return cityRepository.save(city).getId();
	}
	throw new RuntimeException("Country: " + countryName + " not found");
    }

    public List<City> getAllCities(String countryName) {
	return cityRepository.findAllByCountryNameIgnoreCase(countryName);
    }

    public Optional<City> getCityByCountryNameAndName(String countryName, String cityName) {
	return cityRepository.findByCountryNameIgnoreCaseAndNameIgnoreCase(countryName, cityName);
    }

}
