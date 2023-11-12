package com.booking.api.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booking.api.domain.model.Block;
import com.booking.api.domain.model.Booking;
import com.booking.api.domain.model.repository.BlockRepository;
import com.booking.api.domain.model.repository.BookingRepository;
import com.booking.api.exception.ConflictException;
import com.booking.api.exception.NotAcceptableException;
import com.booking.api.exception.NotFoundException;
import com.booking.api.rest.dto.BookingDTO;
import com.booking.api.service.impl.BookingServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

	@Mock
	private BookingRepository bookingRepository;
	@Mock
	private BlockRepository blockRepository;
	
	private BookingService bookingService;

	@BeforeEach
	public void setUp() {
		this.bookingService = new BookingServiceImpl(bookingRepository, blockRepository);
	}

	@Test
	public void testCreateBookingValidInput() {
		BookingDTO validDTO = BookingDTO.builder().guestName("Guest Name").startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build();
		when(bookingRepository.findByPeriod(isNull(), any(LocalDate.class), any(LocalDate.class))).thenReturn(Collections.emptyList());
		when(blockRepository.findByPeriod(any(LocalDate.class), any(LocalDate.class))).thenReturn(Collections.emptyList());
		when(bookingRepository.save(any(Booking.class))).thenReturn(Booking.builder().id(1L).guestName("Guest Name").startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build());
		Long result = bookingService.createBooking(validDTO);
		assertNotNull(result);
	}

	@Test
	public void testCreateBookingStartDateAfterEndDateShouldThrowNotAcceptable() {
		BookingDTO invalidDTO = BookingDTO.builder().guestName("Guest Name").startDate(LocalDate.of(2023, 11, 22))
				.endDate(LocalDate.of(2023, 11, 10)).build();
		assertThrows(NotAcceptableException.class, () -> {
			bookingService.createBooking(invalidDTO);
		});
	}

	@Test
	public void testCreateBookingMissingStartDateShouldThrowNotAcceptable() {
		BookingDTO invalidDTO = BookingDTO.builder().guestName("Guest Name").endDate(LocalDate.of(2023, 11, 22))
				.build();
		assertThrows(NotAcceptableException.class, () -> {
			bookingService.createBooking(invalidDTO);
		});
	}

	@Test
	public void testCreateBookingMissingEndDateShouldThrowNotAcceptable() {
		BookingDTO invalidDTO = BookingDTO.builder().guestName("Guest Name").startDate(LocalDate.of(2023, 11, 10))
				.build();
		assertThrows(NotAcceptableException.class, () -> {
			bookingService.createBooking(invalidDTO);
		});
	}

	@Test
	public void testCreateBookingMissingGuestNameShouldThrowNotAcceptable() {
		BookingDTO invalidDTO = BookingDTO.builder().startDate(LocalDate.of(2023, 11, 10))
				.endDate(LocalDate.of(2023, 11, 22)).build();
		assertThrows(NotAcceptableException.class, () -> {
			bookingService.createBooking(invalidDTO);
		});
	}

	@Test
	public void testCreateBookingBookedPeriodShouldThrowConflict() {
		BookingDTO bookedDTO = BookingDTO.builder().guestName("Guest Name").startDate(LocalDate.of(2023, 11, 10))
				.endDate(LocalDate.of(2023, 11, 22)).build();
		when(bookingRepository.findByPeriod(isNull(), any(LocalDate.class), any(LocalDate.class)))
				.thenReturn(Collections.singletonList(Booking.builder().id(1L).guestName("Guest Name")
						.startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build()));
		assertThrows(ConflictException.class, () -> {
			bookingService.createBooking(bookedDTO);
		});
	}
	
	@Test
	public void testCreateBookingPeriodBlockedShouldThrowConflictException() {
		BookingDTO bookedDTO = BookingDTO.builder().guestName("Guest Name").startDate(LocalDate.of(2023, 11, 10))
				.endDate(LocalDate.of(2023, 11, 22)).build();
		when(blockRepository.findByPeriod(any(LocalDate.class), any(LocalDate.class))).thenReturn(Collections.singletonList(Block.builder().id(1L)
				.startDate(LocalDate.of(2023, 11, 8)).endDate(LocalDate.of(2023, 11, 25)).build()));
		when(bookingRepository.findByPeriod(isNull(), any(LocalDate.class), any(LocalDate.class)))
				.thenReturn(Collections.emptyList());
		assertThrows(ConflictException.class, () -> {
			bookingService.createBooking(bookedDTO);
		});
	}
	
	@Test
	public void testUpdateBookingValidInput() {
		BookingDTO updatedDTO = BookingDTO.builder().guestName("Guest Name").startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build();
		Long existingBookingId = 1L;
		when(bookingRepository.findById(existingBookingId)).thenReturn(Optional.of(Booking.builder().id(1L).guestName("Guest Name").startDate(LocalDate.of(2023, 11, 10))
				.endDate(LocalDate.of(2023, 11, 22)).build()));
		assertDoesNotThrow(() -> {
			bookingService.updateBooking(existingBookingId, updatedDTO);
		});
	}

	@Test
	public void testUpdateBookingBookingShouldThrowNotFound() {
		BookingDTO updatedDTO = BookingDTO.builder().guestName("Guest Name").startDate(LocalDate.of(2023, 11, 10))				.endDate(LocalDate.of(2023, 11, 22)).build();
		Long nonExistentBookingId = 99L;
		when(bookingRepository.findById(nonExistentBookingId)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> {
			bookingService.updateBooking(nonExistentBookingId, updatedDTO);
		});

	}

	@Test
	public void testUpdateBookingStartDateAfterEndDateShouldThrowNotAcceptable() {
		BookingDTO invalidDTO = BookingDTO.builder().guestName("Guest Name").startDate(LocalDate.of(2023, 11, 22))
				.endDate(LocalDate.of(2023, 11, 10)).build();
		Long existingBookingId = 1L;
		when(bookingRepository.findById(existingBookingId))
				.thenReturn(Optional.of(Booking.builder().id(1L).guestName("Guest Name")
						.startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build()));
		assertThrows(NotAcceptableException.class, () -> {
			bookingService.updateBooking(existingBookingId, invalidDTO);
		});
	}
	
	@Test
	public void testUpdateBookingMissingStartDateShouldThrowNotAcceptable() {
		BookingDTO invalidDTO = BookingDTO.builder().guestName("Guest Name").startDate(LocalDate.of(2023, 11, 22)).build();
		Long existingBookingId = 1L;
		when(bookingRepository.findById(existingBookingId))
				.thenReturn(Optional.of(Booking.builder().id(1L).guestName("Guest Name")
						.startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build()));
		assertThrows(NotAcceptableException.class, () -> {
			bookingService.updateBooking(existingBookingId, invalidDTO);
		});
	}

	@Test
	public void testUpdateBookingMissingEndDateShouldThrowNotAcceptable() {
		BookingDTO invalidDTO = BookingDTO.builder().guestName("Guest Name").endDate(LocalDate.of(2023, 11, 10)).build();
		Long existingBookingId = 1L;
		when(bookingRepository.findById(existingBookingId))
		.thenReturn(Optional.of(Booking.builder().id(existingBookingId).guestName("Guest Name")
				.startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build()));
		assertThrows(NotAcceptableException.class, () -> {
			bookingService.updateBooking(existingBookingId, invalidDTO);
		});
	}
	
	@Test
	public void testUpdateBookingMissingGuestNameShouldThrowNotAcceptable() {
		Long existingBookingId = 1L;
		BookingDTO invalidDTO = BookingDTO.builder().startDate(LocalDate.of(2023, 11, 10))
				.endDate(LocalDate.of(2023, 11, 22)).build();
		when(bookingRepository.findById(existingBookingId)).thenReturn(Optional.of(Booking.builder().id(existingBookingId).guestName("Guest Name").startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build()));
		assertThrows(NotAcceptableException.class, () -> {
			bookingService.updateBooking(existingBookingId, invalidDTO);
		});
	}
	
	@Test
	public void testUpdateBookingBookedPeriodShouldThrowNotAcceptable() {
		BookingDTO invalidDTO = BookingDTO.builder().startDate(LocalDate.of(2023, 11, 22)).startDate(LocalDate.of(2023, 11, 22)).build();
		Long existingBookingId = 1L;
		when(bookingRepository.findById(existingBookingId))
				.thenReturn(Optional.of(Booking.builder().id(1L).guestName("Guest Name")
						.startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build()));
		assertThrows(NotAcceptableException.class, () -> {
			bookingService.updateBooking(existingBookingId, invalidDTO);
		});
	}
	
	@Test
	public void testUpdateBookingPeriodBlockedShouldThrowConflict() {
		Long existingBookingId = 1L;
		BookingDTO bookedDTO = BookingDTO.builder().guestName("Guest Name").startDate(LocalDate.of(2023, 11, 10))
				.endDate(LocalDate.of(2023, 11, 22)).build();
		when(blockRepository.findByPeriod(any(LocalDate.class), any(LocalDate.class))).thenReturn(Collections.singletonList(Block.builder().id(1L)
				.startDate(LocalDate.of(2023, 11, 8)).endDate(LocalDate.of(2023, 11, 25)).build()));
		when(bookingRepository.findById(existingBookingId)).thenReturn(Optional.of(Booking.builder().id(existingBookingId).guestName("Guest Name").startDate(LocalDate.of(2023, 11, 10))
				.endDate(LocalDate.of(2023, 11, 22)).build()));
		assertThrows(ConflictException.class, () -> {
			bookingService.updateBooking(existingBookingId, bookedDTO);
		});
	}
	
	@Test
	public void testCancelBookingValidInput() {
		Long existingBookingId = 1L;
		when(bookingRepository.findById(existingBookingId)).thenReturn(Optional.of(Booking.builder().id(1L).guestName("Guest Name").startDate(LocalDate.of(2023, 11, 10))
				.endDate(LocalDate.of(2023, 11, 22)).build()));
		assertDoesNotThrow(() -> {
			bookingService.cancelBooking(existingBookingId);
		});
	}

	@Test
	public void testCancelBookingBookingShouldThrowNotFound() {
		Long nonExistentBookingId = 99L;
		when(bookingRepository.findById(nonExistentBookingId)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> {
			bookingService.cancelBooking(nonExistentBookingId);
		});

	}

}