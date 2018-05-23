package com.putnam.online.travel.agent.application.asset.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.putnam.online.travel.agent.application.asset.model.entity.Hotel;
import com.putnam.online.travel.agent.application.asset.model.entity.Room;
import com.putnam.online.travel.agent.application.asset.model.repository.HotelRepository;
import com.putnam.online.travel.agent.application.asset.model.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;

    public Long addRoom(Room room) {
	String countryName = room.getHotel().getCountry().getName();
	String cityName = room.getHotel().getCity().getName();
	String hotelName = room.getHotel().getName();
	Optional<Hotel> hotel = hotelRepository
		.findByCountryNameIgnoreCaseAndCityNameIgnoreCaseAndNameIgnoreCase(countryName, cityName, hotelName);
	if (hotel.isPresent()) {
	    room.setHotel(hotel.get());
	    return roomRepository.save(room).getId();
	}
	throw new RuntimeException("unable to add room");
    }

    public List<Room> fetchAllRoomsWithCriteria(String countryName, String cityName, String hotelName) {
	return roomRepository.findAllRoomsWithCriteria(countryName, cityName, hotelName);
    }

    public Optional<Room> fetchRoomById(Long id) {
	return roomRepository.findById(id);
    }
}
