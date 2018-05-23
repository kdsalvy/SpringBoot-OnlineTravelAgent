package com.putnam.online.travel.agent.application.asset.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "country_id", "id" }), indexes = {
	@Index(columnList = "name") })
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String pincode;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @Version
    private Integer version;

    public City(Long id, String name, String pincode, Country country, Integer version) {
	this.id = id;
	this.name = name;
	this.pincode = pincode;
	this.country = country;
	this.version = version;
    }

    public City() {
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPincode() {
	return pincode;
    }

    public void setPincode(String pincode) {
	this.pincode = pincode;
    }

    public Country getCountry() {
	return country;
    }

    public void setCountry(Country country) {
	this.country = country;
    }

    public Integer getVersion() {
	return version;
    }

    public void setVersion(Integer version) {
	this.version = version;
    }
}
