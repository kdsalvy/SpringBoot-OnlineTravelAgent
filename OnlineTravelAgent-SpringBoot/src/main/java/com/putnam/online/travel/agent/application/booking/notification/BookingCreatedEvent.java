package com.putnam.online.travel.agent.application.booking.notification;

import com.putnam.online.travel.agent.application.booking.model.entity.BookingRecord;

public class BookingCreatedEvent implements CreationEvent<BookingRecord> {

    private final BookingRecord bookingDetail;

    public BookingCreatedEvent(BookingRecord bookingDetail) {
	this.bookingDetail = bookingDetail;
    }

    @Override
    public BookingRecord getEntity() {
	return bookingDetail;
    }

}
