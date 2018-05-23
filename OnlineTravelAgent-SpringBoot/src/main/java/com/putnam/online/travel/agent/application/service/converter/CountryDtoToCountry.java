package com.putnam.online.travel.agent.application.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.putnam.online.travel.agent.application.asset.model.entity.Country;
import com.putnam.online.travel.agent.application.service.dto.CountryDTO;

@Component
public class CountryDtoToCountry implements Converter<CountryDTO, Country> {

    @Override
    public Country convert(CountryDTO from) {
	Country to = new Country(from.getName());
	return to;
    }

}
