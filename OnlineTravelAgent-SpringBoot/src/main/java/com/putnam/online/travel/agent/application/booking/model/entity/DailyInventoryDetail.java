package com.putnam.online.travel.agent.application.booking.model.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.putnam.online.travel.agent.application.asset.model.entity.Room;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "room_id", "date" }))
public class DailyInventoryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    private Integer availableCount;
    private Double tariff;
    private LocalDate date;
    @Version
    private int version;

    public DailyInventoryDetail() {
    }

    public DailyInventoryDetail(Long id, Room room, Integer availableCount, Double tariff, LocalDate date,
	    int version) {
	this.id = id;
	this.room = room;
	this.availableCount = availableCount;
	this.tariff = tariff;
	this.date = date;
	this.version = version;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Double getTariff() {
	return tariff;
    }

    public void setTariff(Double tariff) {
	this.tariff = tariff;
    }

    public LocalDate getDate() {
	return date;
    }

    public void setDate(LocalDate date) {
	this.date = date;
    }

    public Integer getAvailableCount() {
	return availableCount;
    }

    public void setAvailableCount(Integer availableCount) {
	this.availableCount = availableCount;
    }

    public Room getRoom() {
	return room;
    }

    public void setRoom(Room room) {
	this.room = room;
    }
}
