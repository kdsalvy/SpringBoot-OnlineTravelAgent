package com.putnam.online.travel.agent.application.service.dto;

import java.util.List;

import com.putnam.online.travel.agent.application.booking.model.entity.BookingDetail;

public class CustomerHistory {
    private List<BookingDetail> bookingDetails;

    public CustomerHistory() {
    }

    public CustomerHistory(List<BookingDetail> bookingDetails) {
	this.bookingDetails = bookingDetails;
    }

    public List<BookingDetail> getBookingDetails() {
	return bookingDetails;
    }

    public void setBookingRoomMaps(List<BookingDetail> bookingDetails) {
	this.bookingDetails = bookingDetails;
    }
}
