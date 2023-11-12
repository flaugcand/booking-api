package com.booking.api.domain.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.api.domain.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
