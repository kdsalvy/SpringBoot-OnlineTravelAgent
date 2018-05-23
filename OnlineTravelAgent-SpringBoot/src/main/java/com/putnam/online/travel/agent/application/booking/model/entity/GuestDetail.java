package com.putnam.online.travel.agent.application.booking.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class GuestDetail {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long guestId;
    private String name;
    private String gender;
    private Integer age;
    @JsonIgnore
    @ManyToOne
    private Customer customer;

    public GuestDetail() {
    }

    public GuestDetail(String name, String gender, Integer age, Customer customer) {
	this.name = name;
	this.gender = gender;
	this.age = age;
	this.customer = customer;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getGender() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

    public Integer getAge() {
	return age;
    }

    public void setAge(Integer age) {
	this.age = age;
    }

    public Long getGuestId() {
	return guestId;
    }

    public void setGuestId(Long guestId) {
	this.guestId = guestId;
    }

    public Customer getCustomer() {
	return customer;
    }

    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

}
