package com.putnam.online.travel.agent.application.service.dto;

import java.util.Map;

public class CancelBookingRequest {

    private Long bookingId;
    private Long customerId;
    Map<Long, Integer> roomCountMap;

    public Long getBookingId() {
	return bookingId;
    }

    public void setBookingId(Long bookingId) {
	this.bookingId = bookingId;
    }

    public Map<Long, Integer> getRoomCountMap() {
	return roomCountMap;
    }

    public void setRoomCountMap(Map<Long, Integer> roomCountMap) {
	this.roomCountMap = roomCountMap;
    }

    public Long getCustomerId() {
	return customerId;
    }

    public void setCustomerId(Long customerId) {
	this.customerId = customerId;
    }

}
