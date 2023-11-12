package com.booking.api.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.booking.api.domain.model.Block;
import com.booking.api.domain.model.repository.BlockRepository;
import com.booking.api.rest.dto.BlockDTO;
import com.booking.api.service.BlockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlockServiceImpl implements BlockService {

	private final BlockRepository repository;

	@Override
	public Long createBlock(final BlockDTO dto) {
		ObjectMapper mapper = getMapper();
		
		var entity = mapper.convertValue(dto, Block.class);
		entity = repository.save(entity);

		return entity.getId();
	}


	@Override
	public void updateBlock(final Long id, final BlockDTO dto) {
		ObjectMapper mapper = getMapper();
		var entity = mapper.convertValue(dto, Block.class);
		repository.save(entity);

	}

	private ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}


	@Override
	public void deleteBlock(Long id) {
		Optional<Block> block = repository.findById(id);
		if (block.isPresent())
			repository.delete(block.get());
	}
}
