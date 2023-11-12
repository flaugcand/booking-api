package com.booking.api.service;

import com.booking.api.rest.dto.BlockDTO;

public interface BlockService {

	Long createBlock(BlockDTO dto);

	void updateBlock(Long id, BlockDTO dto);
	
	void deleteBlock(Long id);

}
