package com.putnam.online.travel.agent.application.asset.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.putnam.online.travel.agent.application.asset.model.entity.Amenity;
import com.putnam.online.travel.agent.application.asset.model.entity.Hotel;
import com.putnam.online.travel.agent.application.asset.service.HotelService;

@RestController
public class HotelController {

    @Autowired
    private HotelService hotelService;
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelService.class);

    @PostMapping(path = "countries/{countryName}/cities/{cityName}/hotels", consumes = "application/json", produces = "application/json")
    public Long addHotelInCity(@PathVariable("countryName") String countryName,
	    @PathVariable("cityName") String cityName, @RequestBody Hotel hotel) {
	return hotelService.addHotelInCity(countryName, cityName, hotel);
    }

    @GetMapping(path = "countries/{countryName}/cities/{cityName}/hotels", produces = "application/json")
    public List<Hotel> fetchAllHotelsInCity(@PathVariable("countryName") String countryName,
	    @PathVariable("cityName") String cityName) {
	return hotelService.fetchAllHotelsInCity(countryName, cityName);
    }

    @GetMapping(path = "countries/{countryName}/cities/{cityName}/hotels/{hotelName}")
    public Optional<Hotel> fetchHotelAttributes(@PathVariable("countryName") String countryName,
	    @PathVariable("cityName") String cityName, @PathVariable("hotelName") String hotelName) {
	return hotelService.fetchHotelAttributes(countryName, cityName, hotelName);
    }

    @GetMapping(path = "countries/{countryName}/cities/{cityName}/hotels/{hotelName}/amenities")
    public Set<Amenity> fetchAllAmenitiesForHotel(@PathVariable("countryName") String countryName,
	    @PathVariable("cityName") String cityName, @PathVariable("hotelName") String hotelName) {
	return hotelService.fetchAllAmenitiesForHotel(countryName, cityName, hotelName);
    }

    @PutMapping(path = "countries/{countryName}/cities/{cityName}/hotels/{hotelName}/amenities")
    public void mapAmenityToHotel(@PathVariable("countryName") String countryName,
	    @PathVariable("cityName") String cityName, @PathVariable("hotelName") String hotelName,
	    @RequestBody() Amenity amenity) {
	hotelService.mapAmenityToHotel(countryName, cityName, hotelName, amenity.getAmenityName());
    }

    @PutMapping(path = "countries/{countryName}/cities/{cityName}/hotels", consumes = "application/json")
    public void updateHotelAttributes(@PathVariable("countryName") String countryName,
	    @PathVariable("cityName") String cityName, @RequestBody() Hotel hotel) {
	hotelService.updateHotelAttributes(countryName, cityName, hotel);
    }

    @DeleteMapping(path = "countries/{countryName}/cities/{cityName}/hotels/{hotelName}/amenities/{amenityName}")
    public void deleteAmenityFromHotel(@PathVariable("countryName") String countryName,
	    @PathVariable("cityName") String cityName, @PathVariable("hotelName") String hotelName,
	    @PathVariable("amenityName") String amenityName) {
	LOGGER.debug("Request to delete amenity: Country: " + countryName + " City: " + cityName + " Hotel: "
		+ hotelName + " Amenity: " + amenityName);
	hotelService.deleteAmenityFromHotel(countryName, cityName, hotelName, amenityName);
    }
}
