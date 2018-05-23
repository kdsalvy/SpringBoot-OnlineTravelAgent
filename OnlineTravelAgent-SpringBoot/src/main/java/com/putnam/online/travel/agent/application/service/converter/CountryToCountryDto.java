package com.putnam.online.travel.agent.application.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.putnam.online.travel.agent.application.asset.model.entity.Country;
import com.putnam.online.travel.agent.application.service.dto.CountryDTO;

@Component
public class CountryToCountryDto implements Converter<Country, CountryDTO> {

    @Override
    public CountryDTO convert(Country from) {
	CountryDTO to = new CountryDTO(from.getId(), from.getName());
	return to;
    }

}
