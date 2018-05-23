package com.putnam.online.travel.agent.application.booking.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.putnam.online.travel.agent.application.asset.model.entity.Room;

@Entity()
@Table(name = "booking_detail")
public class BookingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private BookingRecord bookingRecord;
    @ManyToOne
    private Room room;
    private Integer numberOfRooms;
    private Double tariff;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Version
    private int version;
    private LocalDateTime createdOn;

    public BookingDetail(Room room, Integer numberOfRooms, Status status, Double tariff) {
	this.createdOn = LocalDateTime.now();
	this.room = room;
	this.numberOfRooms = numberOfRooms;
	this.status = status;
	this.tariff = tariff;
    }

    public BookingDetail() {
    }

    public BookingRecord getBookingRecord() {
	return bookingRecord;
    }

    public void setBookingRecord(BookingRecord bookingDetails) {
	this.bookingRecord = bookingDetails;
    }

    public Room getRoom() {
	return room;
    }

    public void setRoom(Room room) {
	this.room = room;
    }

    public Integer getNumberOfRooms() {
	return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
	this.numberOfRooms = numberOfRooms;
    }

    public Status getStatus() {
	return status;
    }

    public void setStatus(Status status) {
	this.status = status;
    }

    public static enum Status {
	BOOKED, CANCELLED
    }

    public Double getTariff() {
	return tariff;
    }

    public void setTariff(Double tariff) {
	this.tariff = tariff;
    }

    @Override
    public String toString() {
	return "BookingRoomMap [id=" + id + ", room=" + room + ", numberOfRooms=" + numberOfRooms + ", tariff=" + tariff
		+ ", status=" + status + ", version=" + version + "]";
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public int getVersion() {
	return version;
    }

    public void setVersion(int version) {
	this.version = version;
    }

    public LocalDateTime getCreatedOn() {
	return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
	this.createdOn = createdOn;
    }
}
