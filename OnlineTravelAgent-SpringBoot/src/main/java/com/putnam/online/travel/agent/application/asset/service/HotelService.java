package com.putnam.online.travel.agent.application.asset.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.putnam.online.travel.agent.application.asset.model.entity.Amenity;
import com.putnam.online.travel.agent.application.asset.model.entity.City;
import com.putnam.online.travel.agent.application.asset.model.entity.Hotel;
import com.putnam.online.travel.agent.application.asset.model.repository.AmenityRepository;
import com.putnam.online.travel.agent.application.asset.model.repository.CityRepository;
import com.putnam.online.travel.agent.application.asset.model.repository.HotelRepository;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AmenityRepository amenityRepository;

    public Long addHotelInCity(String countryName, String cityName, Hotel hotel) {
	Optional<City> city = cityRepository.findByCountryNameIgnoreCaseAndNameIgnoreCase(countryName, cityName);
	if (city.isPresent()) {
	    hotel.setCity(city.get());
	    hotel.setCountry(city.get().getCountry());
	    return hotelRepository.save(hotel).getHotelId();
	}
	throw new RuntimeException("City: " + cityName + " or Country: " + countryName + " not found");
    }

    public List<Hotel> fetchAllHotelsInCity(String countryName, String cityName) {
	return hotelRepository.findAllByCountryNameIgnoreCaseAndCityNameIgnoreCaseAndActiveTrue(countryName, cityName);
    }

    public Optional<Hotel> fetchHotelAttributes(String countryName, String cityName, String hotelName) {
	Optional<Hotel> hotel = hotelRepository
		.findByCountryNameIgnoreCaseAndCityNameIgnoreCaseAndNameIgnoreCase(countryName, cityName, hotelName);
	if (!hotel.isPresent())
	    throw new RuntimeException("Hotel Not Found in City: " + cityName + ", " + countryName);
	return hotel;
    }

    public Set<Amenity> fetchAllAmenitiesForHotel(String countryName, String cityName, String hotelName) {
	Optional<Hotel> hotel = hotelRepository
		.findByCountryNameIgnoreCaseAndCityNameIgnoreCaseAndNameIgnoreCase(countryName, cityName, hotelName);
	if (hotel.isPresent())
	    return hotel.get().getAmenities();
	throw new RuntimeException("Unable to fetch amenities for Hotel: " + hotelName + " in City: " + cityName);
    }

    public void mapAmenityToHotel(String countryName, String cityName, String hotelName, String amenityName) {
	Optional<Hotel> hotel = hotelRepository
		.findByCountryNameIgnoreCaseAndCityNameIgnoreCaseAndNameIgnoreCase(countryName, cityName, hotelName);
	Optional<Amenity> amenity = amenityRepository.findByAmenityNameIgnoreCase(amenityName);
	if (hotel.isPresent() && amenity.isPresent()) {
	    hotel.get().getAmenities().add(amenity.get());
	    hotelRepository.save(hotel.get());
	} else {
	    throw new RuntimeException(
		    "Unable to map Amenity: " + amenityName + " to Hotel: " + hotelName + " in City: " + cityName);
	}
    }

    public void updateHotelAttributes(String countryName, String cityName, Hotel hotel) {
	Optional<Hotel> hotelOptionalObject = hotelRepository
		.findByCountryNameIgnoreCaseAndCityNameIgnoreCaseAndNameIgnoreCase(countryName, cityName,
			hotel.getName());
	Hotel hotelUpdateObject = null;
	if (hotelOptionalObject.isPresent()) {
	    hotelUpdateObject = updateAttributes(hotelOptionalObject.get(), hotel);
	    hotelRepository.save(hotelUpdateObject);
	} else {
	    throw new RuntimeException("Hotel Not Found in City: " + cityName + ", " + countryName);
	}
    }

    public void deleteAmenityFromHotel(String countryName, String cityName, String hotelName, String amenityName) {
	Optional<Hotel> hotelOptionalObject = hotelRepository
		.findByCountryNameIgnoreCaseAndCityNameIgnoreCaseAndNameIgnoreCase(countryName, cityName, hotelName);
	if (hotelOptionalObject.isPresent()) {
	    Set<Amenity> amenityList = hotelOptionalObject.get().getAmenities();
	    amenityList.stream().filter(x -> x.getAmenityName().equals(amenityName)).findFirst()
		    .map(x -> amenityList.remove(x))
		    .orElseThrow(() -> new RuntimeException("Amenity: " + amenityName + " not found"));
	    hotelRepository.save(hotelOptionalObject.get());
	} else {
	    throw new RuntimeException("Hotel: " + hotelName + " not found");
	}
    }

    private Hotel updateAttributes(Hotel hotelDbObject, Hotel hotel) {
	if (hotel.isActive() != null)
	    hotelDbObject.setActive(hotel.isActive());
	if (hotel.getAmenities() != null && !hotel.getAmenities().isEmpty())
	    hotel.setAmenities(hotel.getAmenities());
	if (hotel.getCity() != null && !"".equals(hotel.getCity().getName()))
	    hotelDbObject.setName(hotel.getName());
	if (hotel.getContacts() != null && !hotel.getContacts().isEmpty())
	    hotelDbObject.setContacts(hotel.getContacts());
	if (hotel.getCountry() != null && !"".equals(hotel.getCountry().getName()))
	    hotelDbObject.setCountry(hotel.getCountry());
	if (!"".equals(hotel.getDescription()))
	    hotelDbObject.setDescription(hotel.getDescription());
	if (!"".equals(hotel.getName()))
	    hotelDbObject.setName(hotel.getName());
	if (hotel.getVersion() != null)
	    hotelDbObject.setVersion(hotel.getVersion());
	return hotelDbObject;
    }
}
