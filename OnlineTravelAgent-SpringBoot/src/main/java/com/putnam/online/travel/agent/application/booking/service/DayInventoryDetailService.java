package com.putnam.online.travel.agent.application.booking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.putnam.online.travel.agent.application.asset.model.entity.Room;
import com.putnam.online.travel.agent.application.asset.model.repository.RoomRepository;
import com.putnam.online.travel.agent.application.booking.model.entity.DailyInventoryDetail;
import com.putnam.online.travel.agent.application.booking.model.repository.DayInventoryDetailRepository;

@Service
public class DayInventoryDetailService {

    @Autowired
    private DayInventoryDetailRepository dayInventoryDetailRepository;
    @Autowired
    private RoomRepository roomRepository;

    public Long addDayInventoryDetail(DailyInventoryDetail dayInventoryDetail) {
	Optional<Room> room = roomRepository.findById(dayInventoryDetail.getRoom().getId());
	if (room.isPresent())
	    return dayInventoryDetailRepository.save(dayInventoryDetail).getId();
	throw new RuntimeException("Room with given room id not found");
    }
}
