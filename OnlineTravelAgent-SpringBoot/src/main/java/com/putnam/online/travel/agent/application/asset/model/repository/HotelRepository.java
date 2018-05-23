package com.putnam.online.travel.agent.application.asset.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.putnam.online.travel.agent.application.asset.model.entity.Hotel;

public interface HotelRepository extends CrudRepository<Hotel, Long> {
    public List<Hotel> findAllByCountryNameIgnoreCaseAndCityNameIgnoreCaseAndActiveTrue(String countryName,
	    String cityName);

    public Optional<Hotel> findByNameIgnoreCase(String name);

    public Optional<Hotel> findByCountryNameIgnoreCaseAndCityNameIgnoreCaseAndNameIgnoreCase(String countryName,
	    String cityName, String hotelName);
}
