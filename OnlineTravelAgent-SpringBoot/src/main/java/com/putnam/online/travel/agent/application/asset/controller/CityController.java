package com.putnam.online.travel.agent.application.asset.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.putnam.online.travel.agent.application.asset.model.entity.City;
import com.putnam.online.travel.agent.application.asset.service.CityService;

@RestController
public class CityController {
    @Autowired
    private CityService cityService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);

    @PostMapping(path = "countries/{name}/cities", consumes = "application/json", produces = "application/json")
    public Long addCity(@RequestBody City city, @PathVariable("name") String countryName) {
	LOGGER.debug("Add City Request: Country: " + countryName + " City: " + city);
	return cityService.addCity(city, countryName);
    }

    @GetMapping(path = "countries/{name}/cities", produces = "application/json")
    public List<City> getAllCities(@PathVariable("name") String countryName) {
	LOGGER.debug("Request to get cities for Country: " + countryName);
	return cityService.getAllCities(countryName);
    }

    @GetMapping(path = "countries/{countryName}/cities/{cityName}", produces = "application/json")
    public Optional<City> getCityById(@PathVariable("countryName") String countryName,
	    @PathVariable("cityName") String cityName) {
	LOGGER.debug("Request to get City " + cityName + " in Country: " + countryName);
	return cityService.getCityByCountryNameAndName(countryName, cityName);
    }
}
