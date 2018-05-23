package com.putnam.online.travel.agent.application.asset.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "type", "hotel_id" }))
public class Room {

    public static enum Type {
	DELUX_SINGLE, DELUX_DOUBLE, PREMIUM_SINGLE, PREMIUM_DOUBLE, SUITE_SINGLE, SUITE_DOUBLE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Type type;
    private Integer totalCount;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @Version
    private int version;

    public Room(Long id, Type type, Integer totalCount, Hotel hotel) {
	this.id = id;
	this.type = type;
	this.totalCount = totalCount;
	this.hotel = hotel;
    }

    public Room() {
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Type getType() {
	return type;
    }

    public void setType(Type type) {
	this.type = type;
    }

    public Integer getTotalCount() {
	return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
	this.totalCount = totalCount;
    }

    public Hotel getHotel() {
	return hotel;
    }

    public int getVersion() {
	return version;
    }

    public void setVersion(int version) {
	this.version = version;
    }

    public void setHotel(Hotel hotel) {
	this.hotel = hotel;
    }
}
