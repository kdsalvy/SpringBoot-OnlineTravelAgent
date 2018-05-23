package com.putnam.online.travel.agent.application.asset.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.putnam.online.travel.agent.application.asset.model.entity.Room;
import com.putnam.online.travel.agent.application.asset.service.RoomService;

@RestController
@RequestMapping("rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Long addRoom(@RequestBody Room room) {
	return roomService.addRoom(room);
    }

    @GetMapping(produces = "application/json")
    public List<Room> fetchAllRoomsWithCriteria(@RequestParam("countryName") String countryName,
	    @RequestParam("cityName") String cityName, @RequestParam("hotelName") String hotelName) {
	return roomService.fetchAllRoomsWithCriteria(countryName, cityName, hotelName);
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public Optional<Room> fetchRoomById(@PathVariable("id") Long id) {
	return roomService.fetchRoomById(id);
    }
}
