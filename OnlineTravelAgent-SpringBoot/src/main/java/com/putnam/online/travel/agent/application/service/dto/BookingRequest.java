package com.putnam.online.travel.agent.application.service.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class BookingRequest {

    private Long customerId;
    private Map<Long, Integer> roomIdToCountMap;
    private LocalDateTime checkinDateTime;
    private LocalDateTime checkoutDateTime;

    public Long getCustomerId() {
	return customerId;
    }

    public void setCustomerId(Long customerId) {
	this.customerId = customerId;
    }

    public Map<Long, Integer> getRoomIdToCountMap() {
	return roomIdToCountMap;
    }

    public void setRoomIdToCountMap(Map<Long, Integer> roomIdToCountMap) {
	this.roomIdToCountMap = roomIdToCountMap;
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
	return "BookingRequest [customerId=" + customerId + ", roomIdToCountMap=" + roomIdToCountMap
		+ ", checkinDateTime=" + checkinDateTime + ", checkoutDateTime=" + checkoutDateTime + "]";
    }
}
