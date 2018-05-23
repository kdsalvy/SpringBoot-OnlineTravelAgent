package com.putnam.online.travel.agent.application.booking.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.putnam.online.travel.agent.application.booking.exception.NoRollbackException;
import com.putnam.online.travel.agent.application.booking.exception.RollbackException;
import com.putnam.online.travel.agent.application.booking.model.entity.BookingDetail;
import com.putnam.online.travel.agent.application.booking.model.entity.BookingDetail.Status;
import com.putnam.online.travel.agent.application.booking.model.entity.BookingRecord;
import com.putnam.online.travel.agent.application.booking.model.entity.Customer;
import com.putnam.online.travel.agent.application.booking.model.entity.DailyInventoryDetail;
import com.putnam.online.travel.agent.application.booking.model.repository.BookingDetailRepository;
import com.putnam.online.travel.agent.application.booking.model.repository.BookingRecordRepository;
import com.putnam.online.travel.agent.application.booking.model.repository.CustomerRepository;
import com.putnam.online.travel.agent.application.booking.model.repository.DayInventoryDetailRepository;
import com.putnam.online.travel.agent.application.booking.notification.BookingCreatedEvent;
import com.putnam.online.travel.agent.application.service.dto.AvailableRoom;
import com.putnam.online.travel.agent.application.service.dto.BookingRequest;
import com.putnam.online.travel.agent.application.service.dto.CancelBookingRequest;
import com.putnam.online.travel.agent.application.service.dto.CustomerHistory;

@Service
public class BookingService {

    @Autowired
    private BookingRecordRepository bookingRecordRepository;
    @Autowired
    private BookingDetailRepository bookingDetailRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DayInventoryDetailRepository dayInventoryDetailRepository;
    @Autowired
    private ApplicationEventPublisher publisher;

    private static final Logger LOG = LoggerFactory.getLogger(BookingService.class);

    @Transactional(noRollbackFor = NoRollbackException.class)
    public Long bookRoom(BookingRequest bookingRequest) throws RollbackException, NoRollbackException {
	Long customerId = bookingRequest.getCustomerId();
	Map<Long, Integer> roomIdToCountMap = bookingRequest.getRoomIdToCountMap();

	Customer customer = getCustomer(customerId);

	List<DailyInventoryDetail> dayInventoryDetails = getInventoryDetails(roomIdToCountMap.keySet(),
		bookingRequest.getCheckinDateTime(), bookingRequest.getCheckoutDateTime());

	List<BookingDetail> bookingDetails = createBookingDetailList(bookingRequest.getCheckinDateTime(),
		roomIdToCountMap, bookingRequest.getCheckoutDateTime());

	updateInventoryDetails(dayInventoryDetails, roomIdToCountMap);

	BookingRecord bookingRecord = saveBookingRecord(bookingRequest.getCheckinDateTime(),
		bookingRequest.getCheckoutDateTime(), customer, bookingDetails);

	// Post commit event publish
	publisher.publishEvent(new BookingCreatedEvent(bookingRecord));

	return bookingRecord.getId();
    }

    private void updateInventoryDetails(List<DailyInventoryDetail> dayInventoryDetails,
	    Map<Long, Integer> roomIdToCountMap) {
	dayInventoryDetails.forEach(dayInventoryDetail -> {
	    Integer bookedCount = roomIdToCountMap.get(dayInventoryDetail.getRoom().getId());
	    dayInventoryDetail.setAvailableCount(dayInventoryDetail.getAvailableCount() - bookedCount);
	});
	dayInventoryDetailRepository.saveAll(dayInventoryDetails);
    }

    private List<DailyInventoryDetail> getInventoryDetails(Set<Long> roomIds, LocalDateTime checkinDateTime,
	    LocalDateTime checkoutDateTime) throws NoRollbackException {
	List<DailyInventoryDetail> dayInventoryDetails = dayInventoryDetailRepository.findByRoomIdForDateRange(roomIds,
		checkinDateTime.toLocalDate(), checkoutDateTime.toLocalDate());
	if (dayInventoryDetails.isEmpty())
	    throw new NoRollbackException("Invalid Room Ids");
	return dayInventoryDetails;
    }

    private BookingRecord saveBookingRecord(LocalDateTime checkinDateTime, LocalDateTime checkoutDateTime,
	    Customer customer, List<BookingDetail> bookingRoomMaps) {
	BookingRecord bookingDetail = new BookingRecord(customer, checkinDateTime, checkoutDateTime, bookingRoomMaps);
	LOG.debug(bookingDetail.toString());
	bookingRoomMaps.forEach(x -> x.setBookingRecord(bookingDetail));
	bookingRecordRepository.save(bookingDetail);
	return bookingDetail;
    }

    private Customer getCustomer(Long customerId) throws NoRollbackException {
	Optional<Customer> customerRow = customerRepository.findById(customerId);
	if (!customerRow.isPresent())
	    throw new NoRollbackException("No Customer found against customer id: " + customerId);
	else
	    return customerRow.get();
    }

    private List<BookingDetail> createBookingDetailList(LocalDateTime checkinDateTime,
	    Map<Long, Integer> roomIdToCountMap, LocalDateTime checkoutDateTime) throws NoRollbackException {
	List<BookingDetail> bookingDetails = new ArrayList<>();

	for (Map.Entry<Long, Integer> entry : roomIdToCountMap.entrySet()) {
	    Long roomId = entry.getKey();
	    Integer count = entry.getValue();

	    Optional<AvailableRoom> availableRoomRow = dayInventoryDetailRepository.findRoomDetailForDate(roomId,
		    checkinDateTime.toLocalDate(), checkoutDateTime.toLocalDate());
	    if (!availableRoomRow.isPresent())
		throw new NoRollbackException("Room with id: " + roomId + " is not found");
	    AvailableRoom availableRoom = availableRoomRow.get();
	    if (availableRoom.getCount() < count)
		throw new NoRollbackException("Room with id: " + roomId + " is not available");

	    BookingDetail bookingDetail = new BookingDetail(availableRoom.getRoom(), count, Status.BOOKED,
		    availableRoom.getTariff());
	    bookingDetails.add(bookingDetail);

	}
	return bookingDetails;
    }

    /*
     * private DailyInventoryDetail getDayTariffDetailRowForRoom(LocalDateTime
     * checkinDateTime, Long roomId) throws RollbackException {
     * Optional<DailyInventoryDetail> dayTariffDetail =
     * dayInventoryDetailRepository.findByRoomIdAndDate(roomId,
     * checkinDateTime.toLocalDate()); if (!dayTariffDetail.isPresent()) { throw
     * new RollbackException( "No Tariff found for the requested date: " +
     * checkinDateTime + " for room id: " + roomId); } return
     * dayTariffDetail.get(); }
     */

    /*
     * private Room getRoomRow(Long roomId) throws RollbackException {
     * Optional<Room> room = roomRepository.findById(roomId); if
     * (!room.isPresent()) { throw new
     * RollbackException("No room found against room id: " + roomId); } return
     * room.get(); }
     */

    /*
     * private boolean isAvailable(Room room, Integer count, LocalDateTime
     * checkinDateTime, LocalDateTime checkout) { Integer availableCount =
     * dayInventoryDetailRepository.findAvailableRoomsCountForDate(room.getId(),
     * checkinDateTime.toLocalDate(), checkout.toLocalDate());
     * 
     * bookingDetailRepository.findBookedCountForRoomId(room.getId(),
     * checkinDateTime, checkout); availableCount = availableCount == null ? 0 :
     * availableCount; return availableCount >= count; }
     */

    @Transactional(noRollbackFor = NoRollbackException.class)
    public void cancelRoom(CancelBookingRequest cancelRequest) throws NoRollbackException, RollbackException {
	Long bookingId = cancelRequest.getBookingId();
	Map<Long, Integer> cancelRoomCountMap = cancelRequest.getRoomCountMap();
	// Validate customer id
	getCustomer(cancelRequest.getCustomerId());
	// Cancel Previous Bookings with cancelled Room Count
	cancelPreviousRoomBookings(cancelRoomCountMap, bookingId);
    }

    private void cancelPreviousRoomBookings(Map<Long, Integer> cancelRoomCountMap, Long bookingId)
	    throws RollbackException, NoRollbackException {

	List<DailyInventoryDetail> updatedDayInventoryDetail = new ArrayList<>();
	List<BookingDetail> updatedBookingDetails = new ArrayList<>();

	for (Map.Entry<Long, Integer> cancelEntry : cancelRoomCountMap.entrySet()) {
	    Long roomId = cancelEntry.getKey();
	    Integer roomCancelCount = cancelEntry.getValue();

	    BookingDetail bookingDetail = getRoomBookingDetails(bookingId, roomId);
	    if (roomCancelCount > bookingDetail.getNumberOfRooms())
		throw new NoRollbackException("Rooms to cancel count exceeds booked room for room id: " + roomId);
	    if (bookingDetail.getNumberOfRooms() - roomCancelCount == 0)
		bookingDetail.setStatus(Status.CANCELLED);
	    else {
		BookingDetail updatedBookingDetail = new BookingDetail(bookingDetail.getRoom(), roomCancelCount,
			Status.CANCELLED, bookingDetail.getTariff());
		updatedBookingDetail.setBookingRecord(bookingDetail.getBookingRecord());
		updatedBookingDetails.add(updatedBookingDetail);
		bookingDetail.setNumberOfRooms(bookingDetail.getNumberOfRooms() - roomCancelCount);
	    }
	    updatedBookingDetails.add(bookingDetail);
	    Set<Long> roomIds = new HashSet<>();
	    roomIds.add(roomId);
	    List<DailyInventoryDetail> dayInventoryDetails = getInventoryDetails(roomIds,
		    bookingDetail.getBookingRecord().getCheckinDateTime(),
		    bookingDetail.getBookingRecord().getCheckoutDateTime());
	    dayInventoryDetails
		    .forEach(inventory -> inventory.setAvailableCount(inventory.getAvailableCount() + roomCancelCount));
	    updatedDayInventoryDetail.addAll(dayInventoryDetails);
	}

	bookingDetailRepository.saveAll(updatedBookingDetails);
	dayInventoryDetailRepository.saveAll(updatedDayInventoryDetail);
    }

    private BookingDetail getRoomBookingDetails(Long bookingId, Long roomId) throws NoRollbackException {
	Optional<BookingDetail> bookingRoomMapRow = bookingDetailRepository.findByBookingRecordIdAndRoomId(bookingId,
		roomId);
	if (!bookingRoomMapRow.isPresent())
	    throw new NoRollbackException("Booking not found for booking id: " + bookingId);
	return bookingRoomMapRow.get();
    }

    public List<BookingDetail> getBookingDetails(Long bookingId) throws NoRollbackException {
	List<BookingDetail> bookingDetails = bookingDetailRepository.findAllByBookings(bookingId, Status.BOOKED,
		Status.CANCELLED);
	if (bookingDetails.isEmpty())
	    throw new NoRollbackException("No booking exists with given booking id");
	return bookingDetails;
    }

    private List<BookingDetail> getBookedRoomsDetail(Long bookingId) throws NoRollbackException {
	List<BookingDetail> bookingRoomMaps = bookingDetailRepository.findAllByBookings(bookingId, Status.BOOKED);
	if (bookingRoomMaps == null || bookingRoomMaps.isEmpty())
	    throw new NoRollbackException("Booking Details not found against booking id: " + bookingId);
	return bookingRoomMaps;
    }

    @Transactional(noRollbackFor = NoRollbackException.class)
    public void cancelBooking(CancelBookingRequest cancelRequest) throws NoRollbackException, RollbackException {
	// verify customer
	getCustomer(cancelRequest.getCustomerId());
	// fetch bookingRoomMap
	List<BookingDetail> bookingDetails = getBookedRoomsDetail(cancelRequest.getBookingId());
	List<DailyInventoryDetail> updatedDayInventoryDetails = new ArrayList<>();
	for (BookingDetail bookingDetail : bookingDetails) {
	    bookingDetail.setStatus(Status.CANCELLED);
	    Set<Long> roomIds = new HashSet<>();
	    Long roomId = bookingDetail.getRoom().getId();
	    int count = bookingDetail.getNumberOfRooms();
	    roomIds.add(roomId);
	    List<DailyInventoryDetail> dailyInventoryDetails = getInventoryDetails(roomIds,
		    bookingDetail.getBookingRecord().getCheckinDateTime(),
		    bookingDetail.getBookingRecord().getCheckoutDateTime());
	    dailyInventoryDetails.forEach(x -> x.setAvailableCount(x.getAvailableCount() + count));
	    updatedDayInventoryDetails.addAll(dailyInventoryDetails);
	}

	try {
	    bookingDetailRepository.saveAll(bookingDetails);
	    dayInventoryDetailRepository.saveAll(updatedDayInventoryDetails);
	} catch (Exception ex) {
	    throw new RollbackException(ex.getMessage());
	}
    }

    public List<AvailableRoom> searchAvailableRooms(String countryName, String cityName, LocalDateTime checkin,
	    LocalDateTime checkout) throws RollbackException {
	List<AvailableRoom> availableRooms = dayInventoryDetailRepository.findAllRoomsAvailableBetweenDateTime(
		countryName, cityName, checkin.toLocalDate(), checkout.toLocalDate());
	return availableRooms;
    }

    public CustomerHistory getCustomerHistory(Long customerId) {
	List<BookingDetail> bookingList = bookingDetailRepository.findAllByBookingRecordCustomerId(customerId);
	return new CustomerHistory(bookingList);
    }
}
