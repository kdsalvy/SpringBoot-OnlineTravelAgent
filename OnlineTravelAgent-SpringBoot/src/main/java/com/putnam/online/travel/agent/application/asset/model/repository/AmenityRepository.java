package com.putnam.online.travel.agent.application.asset.model.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.putnam.online.travel.agent.application.asset.model.entity.Amenity;

public interface AmenityRepository extends CrudRepository<Amenity, Long> {

    public Optional<Amenity> findByAmenityNameIgnoreCase(String name);
}
