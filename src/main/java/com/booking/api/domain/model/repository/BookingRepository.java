package com.booking.api.domain.model.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.booking.api.domain.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Query("""
			SELECT b FROM Booking b
			WHERE (b.id !=:id OR :id IS NULL)
				AND ((:startDate BETWEEN b.startDate AND b.endDate) OR (:endDate BETWEEN b.startDate AND b.endDate))
			""")
	List<Booking> findByPeriod(@Param("id") Long id, @Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

}
