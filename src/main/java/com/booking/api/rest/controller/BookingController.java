package com.booking.api.rest.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.booking.api.rest.dto.BookingDTO;
import com.booking.api.rest.resource.BookingResource;
import com.booking.api.service.BookingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BookingController implements BookingResource {

	private final BookingService service;

	@Override
	public ResponseEntity<?> createBooking(BookingDTO block) {
		Long id = service.createBooking(block);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}

	@Override
	public ResponseEntity<?> updateBooking(Long id, BookingDTO block) {
		service.updateBooking(id, block);

		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<?> cancelBooking(Long id) {
		service.cancelBooking(id);
		
		return ResponseEntity.noContent().build();
	}

}
