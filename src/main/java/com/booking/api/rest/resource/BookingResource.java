package com.booking.api.rest.resource;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.booking.api.rest.dto.BookingRequestDTO;
import com.booking.api.rest.dto.BookingResponseDTO;

@RequestMapping("/booking")
public interface BookingResource {

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> createBooking(@RequestBody BookingRequestDTO block);

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> updateBooking(@PathVariable("id") Long id, @RequestBody BookingRequestDTO block);
	
	@DeleteMapping(path = "/{id}")
	ResponseEntity<?> cancelBooking(@PathVariable("id") Long id);

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<BookingResponseDTO>> findAll();
	
	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BookingResponseDTO> findById(@PathVariable("id") Long id);
	
}
