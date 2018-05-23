package com.putnam.online.travel.agent.application.service.dto;

import com.putnam.online.travel.agent.application.asset.model.entity.Room;

public class AvailableRoom {
    private Room room;
    private Integer count;
    private Double tariff;

    public AvailableRoom() {
    }

    public AvailableRoom(Room room, Integer count, Double tariff) {
	this.room = room;
	this.count = count;
	this.tariff = tariff;
    }

    public Integer getCount() {
	return count;
    }

    public void setCount(Integer count) {
	this.count = count;
    }

    public Room getRoom() {
	return room;
    }

    public void setRoom(Room room) {
	this.room = room;
    }

    public Double getTariff() {
	return tariff;
    }

    public void setTariff(Double tariff) {
	this.tariff = tariff;
    }
}
