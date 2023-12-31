package com.booking.api.rest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.booking.api.rest.dto.BlockRequestDTO;
import com.booking.api.rest.dto.BlockResponseDTO;
import com.booking.api.rest.resource.BlockResource;
import com.booking.api.service.BlockService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BlockController implements BlockResource {

	private final BlockService service;

	@Override
	public ResponseEntity<String> createBlock(BlockRequestDTO block) {
		Long id = service.createBlock(block);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}

	@Override
	public ResponseEntity<?> updateBlock(Long id, @RequestBody BlockRequestDTO block) {
		service.updateBlock(id, block);

		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<?> deleteBlock(Long id) {
		service.deleteBlock(id);

		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<List<BlockResponseDTO>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@Override
	public ResponseEntity<BlockResponseDTO> findById(Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

}
