package com.putnam.online.travel.agent.application.asset.model.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(indexes = {
	@Index(columnList = "name") }, uniqueConstraints = @UniqueConstraint(columnNames = { "city_id", "hotelId" }))
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Contact> contacts;
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @ManyToMany
    @JoinTable(name = "hotel_amenity_map", joinColumns = @JoinColumn(name = "hotel_id"), inverseJoinColumns = @JoinColumn(name = "amenity_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
	    "hotel_id", "amenity_id" }))
    private Set<Amenity> amenities;
    @Version
    private Integer version;

    public Hotel() {
    }

    public Hotel(Long hotelId, String name, String description, List<Contact> contacts, Boolean active, City city,
	    Country country, Integer version) {
	this.hotelId = hotelId;
	this.name = name;
	this.description = description;
	this.contacts = contacts;
	this.active = active;
	this.city = city;
	this.country = country;
	this.version = version;
    }

    public Long getHotelId() {
	return hotelId;
    }

    public void setHotelId(Long hotelId) {
	this.hotelId = hotelId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List<Contact> getContacts() {
	return contacts;
    }

    public void setContacts(List<Contact> contacts) {
	this.contacts = contacts;
    }

    public City getCity() {
	return city;
    }

    public void setCity(City city) {
	this.city = city;
    }

    public Country getCountry() {
	return country;
    }

    public void setCountry(Country country) {
	this.country = country;
    }

    public Boolean isActive() {
	return active;
    }

    public void setActive(Boolean active) {
	this.active = active;
    }

    public Set<Amenity> getAmenities() {
	return amenities;
    }

    public void setAmenities(Set<Amenity> amenities) {
	this.amenities = amenities;
    }

    public Integer getVersion() {
	return version;
    }

    public void setVersion(Integer version) {
	this.version = version;
    }
}
