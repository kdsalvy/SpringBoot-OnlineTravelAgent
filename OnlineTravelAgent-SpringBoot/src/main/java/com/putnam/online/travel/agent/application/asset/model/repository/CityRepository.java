package com.putnam.online.travel.agent.application.asset.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.putnam.online.travel.agent.application.asset.model.entity.City;

public interface CityRepository extends CrudRepository<City, Long> {

    public List<City> findAllByCountryNameIgnoreCase(String name);

    public Optional<City> findByCountryNameIgnoreCaseAndNameIgnoreCase(String countryName, String cityName);

}
