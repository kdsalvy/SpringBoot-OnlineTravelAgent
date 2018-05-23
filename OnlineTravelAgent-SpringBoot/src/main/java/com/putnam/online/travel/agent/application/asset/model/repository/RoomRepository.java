package com.putnam.online.travel.agent.application.asset.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.putnam.online.travel.agent.application.asset.model.entity.Room;

public interface RoomRepository extends CrudRepository<Room, Long> {

    @Query("SELECT room FROM Room room WHERE upper(room.hotel.name) = upper(:hotelName) "
	    + "AND upper(room.hotel.city.name) = upper(:cityName) "
	    + "AND upper(room.hotel.country.name) = upper(:countryName)")
    public List<Room> findAllRoomsWithCriteria(@Param("countryName") String countryName,
	    @Param("cityName") String cityName, @Param("hotelName") String hotelName);

    @Query("SELECT room FROM Room room WHERE " + "upper(room.hotel.city.name) = upper(:cityName) "
	    + "AND upper(room.hotel.country.name) = upper(:countryName)")
    public List<Room> findAllRoomsWithCountryAndCityName(@Param("countryName") String countryName,
	    @Param("cityName") String cityName);

    // @Query(value = "SELECT r1.* room, r1.total_count - IFNULL(jt.bookedCount,
    // 0) count" + "FROM room r1 "
    // + "JOIN hotel h1 on r1.hotel_id = h1.hotel_id " + "JOIN city c on c.id =
    // h1.city_id "
    // + "JOIN country c1 on c1.id = h1.country_id " + "LEFT OUTER JOIN "
    // + "( SELECT SUM(brm2.number_of_rooms) bookedCount, brm2.room_id room_id
    // FROM booking_to_room_map brm2 JOIN booking_details bd ON bd.id =
    // brm2.booking_details_id WHERE (:checkin BETWEEN bd.checkin_date_time AND
    // bd.checkout_date_time OR :checkout BETWEEN bd.checkin_date_time AND
    // bd.checkout_date_time) AND brm2.status = 'BOOKED' GROUP BY brm2.room_id )
    // jt ON jt.room_id = r1.id "
    // + "WHERE c1.name = :countryName AND c.name = :cityName", nativeQuery =
    // true)
    // public List<AvailableRoom> findAllAvailableRooms(@Param("countryName")
    // String countryName,
    // @Param("cityName") String cityName, @Param("checkin") LocalDateTime
    // checkin,
    // @Param("checkout") LocalDateTime checkout);
}
