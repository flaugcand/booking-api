package com.booking.api.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booking.api.domain.model.Block;
import com.booking.api.domain.model.repository.BlockRepository;
import com.booking.api.exception.NotAcceptableException;
import com.booking.api.exception.NotFoundException;
import com.booking.api.rest.dto.BlockDTO;
import com.booking.api.service.impl.BlockServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BlockServiceTest {

	@Mock
	private BlockRepository repository;
	private BlockService blockService;

	@BeforeEach
	public void setUp() {
		this.blockService = new BlockServiceImpl(repository);
	}

	@Test
	public void testCreateBlockValidInput() {
		BlockDTO validDTO = BlockDTO.builder().startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build();
		when(repository.save(any(Block.class))).thenReturn(Block.builder().id(1L).startDate(LocalDate.of(2023, 11, 10))
				.endDate(LocalDate.of(2023, 11, 22)).build());
		Long result = blockService.createBlock(validDTO);
		assertNotNull(result);
	}

	@Test
	public void testCreateBlockMissingStartDateShouldThrowNotAcceptable() {
		BlockDTO invalidDTO = BlockDTO.builder().endDate(LocalDate.of(2023, 11, 22)).build();
		assertThrows(NotAcceptableException.class, () -> {
			blockService.createBlock(invalidDTO);
		});
	}
	
	@Test
	public void testCreateBlockMissingEndDateShouldThrowNotAcceptable() {
		BlockDTO invalidDTO = BlockDTO.builder().startDate(LocalDate.of(2023, 11, 22)).build();
		assertThrows(NotAcceptableException.class, () -> {
			blockService.createBlock(invalidDTO);
		});
	}

	@Test
	public void testCreateBlockStartDateAfterEndDateShouldThrowNotAcceptable() {
		BlockDTO invalidDTO = BlockDTO.builder().startDate(LocalDate.of(2023, 11, 10)).build();
		assertThrows(NotAcceptableException.class, () -> {
			blockService.createBlock(invalidDTO);
		});
	}

	@Test
	public void testUpdateBlockValidInput() {
		Long existingBlockId = 1L;
		BlockDTO validDTO = BlockDTO.builder().startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22))
				.build();
		when(repository.findById(existingBlockId)).thenReturn(Optional.of(Block.builder().id(1L).startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build()));
		assertDoesNotThrow(() -> {
			blockService.updateBlock(existingBlockId, validDTO);
		});
	}

	@Test
	public void testUpdateBlockShouldThrowBlockNotFound() {
		Long nonExistentBlockId = 99L;
		BlockDTO validDTO = BlockDTO.builder().startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build();
		when(repository.findById(nonExistentBlockId)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> {
			blockService.updateBlock(nonExistentBlockId, validDTO);
		});
	}

	@Test
	public void testDeleteBlockValidInput() {
		Long existingBlockId = 1L;
		when(repository.findById(existingBlockId)).thenReturn(Optional.of(Block.builder().id(1L).startDate(LocalDate.of(2023, 11, 10)).endDate(LocalDate.of(2023, 11, 22)).build()));
		assertDoesNotThrow(() -> {
			blockService.deleteBlock(existingBlockId);
		});
	}

	@Test
	public void testDeleteBlockShouldThrowBlockNotFound() {
		Long nonExistentBlockId = 99L;
		when(repository.findById(nonExistentBlockId)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> {
			blockService.deleteBlock(nonExistentBlockId);
		});
	}

}