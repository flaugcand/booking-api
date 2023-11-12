package com.booking.api.rest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.booking.api.rest.dto.BookingRequestDTO;
import com.booking.api.rest.dto.BookingResponseDTO;
import com.booking.api.rest.resource.BookingResource;
import com.booking.api.service.BookingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BookingController implements BookingResource {

	private final BookingService service;

	@Override
	public ResponseEntity<?> createBooking(BookingRequestDTO block) {
		Long id = service.createBooking(block);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}

	@Override
	public ResponseEntity<?> updateBooking(Long id, BookingRequestDTO block) {
		service.updateBooking(id, block);

		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<?> cancelBooking(Long id) {
		service.cancelBooking(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@Override
	public ResponseEntity<List<BookingResponseDTO>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@Override
	public ResponseEntity<BookingResponseDTO> findById(Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

}
