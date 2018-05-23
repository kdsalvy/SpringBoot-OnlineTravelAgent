package com.putnam.online.travel.agent.application.booking.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "booking_records")
public class BookingRecord {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "customer_id")
    @ManyToOne
    private Customer customer;
    @JsonIgnore
    @OneToMany(mappedBy = "bookingRecord", cascade = CascadeType.ALL)
    private List<BookingDetail> bookingDetails;
    private LocalDateTime checkinDateTime;
    private LocalDateTime checkoutDateTime;
    @JsonIgnore
    private boolean notified;

    public BookingRecord() {

    }

    public BookingRecord(Customer customer, LocalDateTime checkinDateTime, LocalDateTime checkoutDateTime,
	    List<BookingDetail> bookingList) {
	this.customer = customer;
	this.checkinDateTime = checkinDateTime;
	this.checkoutDateTime = checkoutDateTime;
	this.bookingDetails = bookingList;
	this.notified = false;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Customer getCustomer() {
	return customer;
    }

    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    public LocalDateTime getCheckinDateTime() {
	return checkinDateTime;
    }

    public void setCheckinDateTime(LocalDateTime checkinDateTime) {
	this.checkinDateTime = checkinDateTime;
    }

    public LocalDateTime getCheckoutDateTime() {
	return checkoutDateTime;
    }

    public void setCheckoutDateTime(LocalDateTime checkoutDateTime) {
	this.checkoutDateTime = checkoutDateTime;
    }

    @Override
    public String toString() {
	return "BookingDetail [id=" + id + ", customer=" + customer + ", bookingRoomMaps=" + bookingDetails
		+ ", checkinDateTime=" + checkinDateTime + ", checkoutDateTime=" + checkoutDateTime + "]";
    }

    public boolean isNotified() {
	return notified;
    }

    public void setNotified(boolean notified) {
	this.notified = notified;
    }

    public List<BookingDetail> getBookingDetails() {
	return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetail> bookingDetails) {
	this.bookingDetails = bookingDetails;
    }

}
