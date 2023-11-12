package com.booking.api.rest.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.booking.api.rest.dto.BookingDTO;

@RequestMapping("/booking")
public interface BookingResource {

	@PostMapping
	ResponseEntity<?> createBooking(@RequestBody BookingDTO block);

	@PutMapping(path = "/{id}")
	ResponseEntity<?> updateBooking(@PathVariable("id") Long id, @RequestBody BookingDTO block);
	
	@DeleteMapping(path = "/{id}")
	ResponseEntity<?> cancelBooking(@PathVariable("id") Long id);
	
}
