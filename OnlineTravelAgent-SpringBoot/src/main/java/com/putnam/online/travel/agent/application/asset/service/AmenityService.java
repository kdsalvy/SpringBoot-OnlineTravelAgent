package com.putnam.online.travel.agent.application.asset.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.putnam.online.travel.agent.application.asset.model.entity.Amenity;
import com.putnam.online.travel.agent.application.asset.model.repository.AmenityRepository;

@Service
public class AmenityService {

    @Autowired
    private AmenityRepository amenityRepository;

    public Long addAmenity(Amenity amenity) {
	return amenityRepository.save(amenity).getId();
    }

    public Set<Amenity> fetchAllAmenities() {
	Set<Amenity> amenities = new HashSet<>();
	amenityRepository.findAll().forEach(amenities::add);
	return amenities;
    }

    public Optional<Amenity> fetchAmenityByName(String amenityName) {
	return amenityRepository.findByAmenityNameIgnoreCase(amenityName);
    }
}
