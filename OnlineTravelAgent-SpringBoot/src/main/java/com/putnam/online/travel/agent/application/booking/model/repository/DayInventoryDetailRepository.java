package com.putnam.online.travel.agent.application.booking.model.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.putnam.online.travel.agent.application.booking.model.entity.DailyInventoryDetail;
import com.putnam.online.travel.agent.application.service.dto.AvailableRoom;

public interface DayInventoryDetailRepository extends CrudRepository<DailyInventoryDetail, Long> {

    @Query("select did from DailyInventoryDetail did where did.room.id in :roomIds and "
	    + "did.date >= :checkin and did.date <= :checkout")
    public List<DailyInventoryDetail> findByRoomIdForDateRange(@Param("roomIds") Set<Long> roomIds,
	    @Param("checkin") LocalDate checkin, @Param("checkout") LocalDate checkout);

    @Query("select new com.putnam.online.travel.agent.application.service.dto.AvailableRoom(did.room, MIN(did.availableCount), AVG(did.tariff)) "
	    + "from DailyInventoryDetail did where did.date >= :checkin "
	    + "and did.date <= :checkout and did.room.hotel.country.name = :countryName "
	    + "and did.room.hotel.city.name = :cityName group by did.room.id")
    public List<AvailableRoom> findAllRoomsAvailableBetweenDateTime(@Param("countryName") String countryName,
	    @Param("cityName") String cityName, @Param("checkin") LocalDate checkin,
	    @Param("checkout") LocalDate checkout);

    @Query("select new com.putnam.online.travel.agent.application.service.dto.AvailableRoom(did.room, MIN(did.availableCount), AVG(did.tariff)) "
	    + "from DailyInventoryDetail did where did.room.id = :roomId and did.date >= :checkin and did.date <= :checkout")
    public Optional<AvailableRoom> findRoomDetailForDate(@Param("roomId") Long roomId,
	    @Param("checkin") LocalDate checkin, @Param("checkout") LocalDate checkout);
}
