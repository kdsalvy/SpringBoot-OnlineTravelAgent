package com.putnam.online.travel.agent.application.asset.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.putnam.online.travel.agent.application.asset.model.entity.Amenity;
import com.putnam.online.travel.agent.application.asset.service.AmenityService;

@RestController
public class AmenityController {

    @Autowired
    private AmenityService amenityService;

    @PostMapping(path = "amenities", consumes = "application/json", produces = "application/json")
    public Long addAmenity(@RequestBody Amenity amenity) {
	return amenityService.addAmenity(amenity);
    }

    @GetMapping(path = "amenities", produces = "application/json")
    public Set<Amenity> fetchAllAmenities() {
	return amenityService.fetchAllAmenities();
    }

    @GetMapping(path = "amenities/{amenityName}", produces = "application/json")
    public Optional<Amenity> fetchAmenityByName(@PathVariable("amenityName") String amenityName) {
	return amenityService.fetchAmenityByName(amenityName);
    }
}
