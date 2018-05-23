package com.putnam.online.travel.agent.application.booking.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.putnam.online.travel.agent.application.booking.model.entity.BookingRecord;

public interface BookingRecordRepository extends CrudRepository<BookingRecord, Long> {

}
