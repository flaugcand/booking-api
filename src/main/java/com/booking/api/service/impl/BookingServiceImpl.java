package com.booking.api.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.booking.api.domain.model.Booking;
import com.booking.api.domain.model.repository.BookingRepository;
import com.booking.api.rest.dto.BookingDTO;
import com.booking.api.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

	private final BookingRepository repository;

	@Override
	public Long createBooking(final BookingDTO dto) {
		ObjectMapper mapper = getMapper();
		var entity = mapper.convertValue(dto, Booking.class);
		entity = repository.save(entity);

		return entity.getId();
	}

	@Override
	public void updateBooking(final Long id, final BookingDTO dto) {
		ObjectMapper mapper = getMapper();
		var entity = mapper.convertValue(dto, Booking.class);
		repository.save(entity);

	}

	private ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

	@Override
	public void cancelBooking(Long id) {
		Optional<Booking> optionalBooking = repository.findById(id);
		if (optionalBooking.isPresent()) {
			Booking booking = optionalBooking.get();
			booking.setActive(Boolean.FALSE);
			repository.save(booking);
		}
	}
}
