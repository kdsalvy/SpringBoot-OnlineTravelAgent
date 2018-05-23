package com.putnam.online.travel.agent.application.booking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.putnam.online.travel.agent.application.booking.exception.NoRollbackException;
import com.putnam.online.travel.agent.application.booking.exception.RollbackException;
import com.putnam.online.travel.agent.application.booking.model.entity.BookingDetail;
import com.putnam.online.travel.agent.application.booking.service.BookingService;
import com.putnam.online.travel.agent.application.service.dto.AvailableRoom;
import com.putnam.online.travel.agent.application.service.dto.BookingRequest;
import com.putnam.online.travel.agent.application.service.dto.CancelBookingRequest;
import com.putnam.online.travel.agent.application.service.dto.CustomerHistory;

@RestController
@RequestMapping("bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    private static Logger LOG = LoggerFactory.getLogger(BookingController.class);

    @GetMapping(path = "searchRooms")
    public ResponseEntity<?> searchRoom(@RequestParam("countryName") String countryName,
	    @RequestParam("cityName") String cityName, @RequestParam("checkin") String checkin,
	    @RequestParam("checkout") String checkout) {
	LocalDateTime cIn = LocalDateTime.parse(checkin);
	LocalDateTime cOut = LocalDateTime.parse(checkout);
	try {
	    List<AvailableRoom> availableRooms = bookingService.searchAvailableRooms(countryName, cityName, cIn, cOut);
	    return new ResponseEntity<>(availableRooms, HttpStatus.OK);
	} catch (RollbackException e) {
	    LOG.error(e.getMessage(), e);
	    return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
	}
    }

    @PostMapping(path = "bookRooms", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> bookRoom(@RequestBody BookingRequest bookingRequest, UriComponentsBuilder builder) {
	LOG.debug(bookingRequest.toString());
	try {
	    Long id = bookingService.bookRoom(bookingRequest);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(builder.path("/bookings/bookingDetail/{id}").buildAndExpand(id).toUri());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
	} catch (RollbackException | NoRollbackException e) {
	    LOG.error(e.getMessage(), e);
	    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
    }

    @GetMapping(path = "bookingDetail/{bookingId}")
    public ResponseEntity<?> getBookingDetails(@PathVariable("bookingId") Long bookingId) {
	try {
	    List<BookingDetail> bookingDetails = bookingService.getBookingDetails(bookingId);
	    return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
	} catch (NoRollbackException e) {
	    LOG.error(e.getMessage(), e);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
    }

    @PutMapping(path = "cancelBooking", consumes = "application/json")
    public ResponseEntity<?> cancelBooking(@RequestBody CancelBookingRequest cancelRequest) throws NoRollbackException {
	try {
	    bookingService.cancelBooking(cancelRequest);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	} catch (RollbackException e) {
	    LOG.error(e.getMessage(), e);
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
    }

    @PutMapping(path = "cancelRooms", consumes = "application/json")
    public ResponseEntity<?> cancelRoom(@RequestBody CancelBookingRequest cancelRequest) {
	try {
	    bookingService.cancelRoom(cancelRequest);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	} catch (RollbackException | NoRollbackException e) {
	    LOG.error(e.getMessage(), e);
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
    }

    @GetMapping(path = "userHistory", produces = "application/json")
    public ResponseEntity<?> getCustomerHistory(@RequestParam("customerId") Long customerId) {
	try {
	    CustomerHistory customerHistory = bookingService.getCustomerHistory(customerId);
	    return new ResponseEntity<>(customerHistory, HttpStatus.OK);
	} catch (Exception e) {
	    LOG.error(e.getMessage(), e);
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
    }

}
