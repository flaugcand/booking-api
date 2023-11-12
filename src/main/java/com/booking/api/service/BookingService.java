package com.booking.api.service;

import java.util.List;

import com.booking.api.rest.dto.BookingRequestDTO;
import com.booking.api.rest.dto.BookingResponseDTO;

public interface BookingService {

	Long createBooking(BookingRequestDTO dto);

	void updateBooking(Long id, BookingRequestDTO dto);
	
	void cancelBooking(Long id);

	List<BookingResponseDTO> findAll();

	BookingResponseDTO findById(Long id);

}
