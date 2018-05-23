package com.putnam.online.travel.agent.application.asset.model.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.putnam.online.travel.agent.application.asset.model.entity.Country;

public interface CountryRepository extends CrudRepository<Country, Long> {

    public Optional<Country> findByNameIgnoreCase(String name);

    public Optional<Country> findByName(String expr);
}
