package com.putnam.online.travel.agent.application.booking.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.putnam.online.travel.agent.application.booking.model.entity.BookingDetail;
import com.putnam.online.travel.agent.application.booking.model.entity.BookingDetail.Status;

public interface BookingDetailRepository extends CrudRepository<BookingDetail, Long> {

    @Query("select bd from BookingDetail bd where bd.bookingRecord.id = :bookingId AND bd.status in :status")
    public List<BookingDetail> findAllByBookings(@Param("bookingId") Long bookingId, @Param("status") Status... status);

    public Optional<BookingDetail> findByBookingRecordIdAndRoomId(Long bookingId, Long roomId);

    public List<BookingDetail> findAllByBookingRecordCustomerId(Long customerId);

}
