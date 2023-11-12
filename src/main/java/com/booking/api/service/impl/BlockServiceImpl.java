package com.booking.api.service.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.booking.api.domain.model.Block;
import com.booking.api.domain.model.repository.BlockRepository;
import com.booking.api.exception.NotAcceptableException;
import com.booking.api.exception.NotFoundException;
import com.booking.api.rest.dto.BlockDTO;
import com.booking.api.service.BlockService;
import com.booking.api.utils.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlockServiceImpl implements BlockService {

	private final BlockRepository repository;

	@Override
	public Long createBlock(final BlockDTO dto) {
		blockValidation(dto);
		ObjectMapper mapper = getMapper();
		var entity = mapper.convertValue(dto, Block.class);
		entity = repository.save(entity);

		return entity.getId();
	}


	@Override
	public void updateBlock(final Long id, final BlockDTO dto) {
		repository.findById(id).orElseThrow(() -> new NotFoundException("Block not found for id: " + id));
		blockValidation(dto);

		ObjectMapper mapper = getMapper();
		var entity = mapper.convertValue(dto, Block.class);
		repository.save(entity);

	}

	private void blockValidation(final BlockDTO dto) {
		if (Objects.isNull(dto.getStartDate()) || Objects.isNull(dto.getEndDate()))
			throw new NotAcceptableException("The start date and end date can't be null");

		if (!DateUtil.dateValidation(dto.getStartDate(), dto.getEndDate()))
			throw new NotAcceptableException("The start date must be afte the end date");
	}

	private ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}


	@Override
	public void deleteBlock(Long id) {
		Block block = repository.findById(id).orElseThrow(() -> new NotFoundException("Block not found for id: " + id));
		repository.delete(block);
	}
}
