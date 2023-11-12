package com.booking.api.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.booking.api.domain.model.Block;
import com.booking.api.domain.model.Booking;
import com.booking.api.domain.model.repository.BlockRepository;
import com.booking.api.domain.model.repository.BookingRepository;
import com.booking.api.exception.ConflictException;
import com.booking.api.exception.NotAcceptableException;
import com.booking.api.exception.NotFoundException;
import com.booking.api.rest.dto.BookingDTO;
import com.booking.api.service.BookingService;
import com.booking.api.utils.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

	private final BookingRepository repository;
	private final BlockRepository blockRepository;

	@Override
	public Long createBooking(final BookingDTO dto) {
		bookingValidation(null, dto);
		ObjectMapper mapper = getMapper();
		var entity = mapper.convertValue(dto, Booking.class);
		entity = repository.save(entity);

		return entity.getId();
	}

	@Override
	public void updateBooking(final Long id, final BookingDTO dto) {
		repository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found for id: " + id));

		bookingValidation(id, dto);

		ObjectMapper mapper = getMapper();
		var entity = mapper.convertValue(dto, Booking.class);
		repository.save(entity);

	}

	private void bookingValidation(final Long id, final BookingDTO dto) {
		if (Objects.isNull(dto.getStartDate()) || Objects.isNull(dto.getEndDate()))
			throw new NotAcceptableException("The start date and end date can't be null");

		if (!DateUtil.dateValidation(dto.getStartDate(), dto.getEndDate()))
			throw new NotAcceptableException("The start date must be before the end date");

		if (StringUtils.isBlank(dto.getGuestName()))
			throw new NotAcceptableException("The guest name can't be empty");

		List<Booking> bookings = repository.findByPeriod(id, dto.getStartDate(), dto.getEndDate());
		if (bookings != null && !bookings.isEmpty())
			throw new NotAcceptableException("The selected period is busy");

		List<Block> blocks = blockRepository.findByPeriod(dto.getStartDate(), dto.getEndDate());

		if (blocks != null && !blocks.isEmpty())
			throw new NotAcceptableException("This period is blocked");
	}

	private ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

	@Override
	public void cancelBooking(Long id) {
		Booking booking = repository.findById(id)
				.orElseThrow(() -> new NotFoundException("Booking not found for id: " + id));
		booking.setActive(Boolean.FALSE);

		repository.save(booking);
	}
}
