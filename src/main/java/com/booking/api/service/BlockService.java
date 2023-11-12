package com.booking.api.service;

import java.util.List;

import com.booking.api.rest.dto.BlockRequestDTO;
import com.booking.api.rest.dto.BlockResponseDTO;

public interface BlockService {

	Long createBlock(BlockRequestDTO dto);

	void updateBlock(Long id, BlockRequestDTO dto);
	
	void deleteBlock(Long id);
	
	List<BlockResponseDTO> findAll();

	BlockResponseDTO findById(Long id);

}
