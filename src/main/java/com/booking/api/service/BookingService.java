package com.booking.api.service;

import com.booking.api.rest.dto.BookingDTO;

public interface BookingService {

	Long createBooking(BookingDTO dto);

	void updateBooking(Long id, BookingDTO dto);
	
	void cancelBooking(Long id);

}
