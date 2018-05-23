package com.putnam.online.travel.agent.application.asset.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(indexes = { @Index(columnList = "amenityName") })
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String amenityName;
    @Version
    private Integer version;

    public Amenity(Long id, String amenityName, Integer version) {
	this.id = id;
	this.amenityName = amenityName;
	this.version = version;
    }

    public Amenity() {
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Integer getVersion() {
	return version;
    }

    public void setVersion(Integer version) {
	this.version = version;
    }

    public String getAmenityName() {
	return amenityName;
    }

    public void setAmenityName(String amenityName) {
	this.amenityName = amenityName;
    }
}
