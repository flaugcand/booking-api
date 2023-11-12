package com.booking.api.rest.resource;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.booking.api.rest.dto.BlockRequestDTO;
import com.booking.api.rest.dto.BlockResponseDTO;

@RequestMapping("/block")
public interface BlockResource {

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> createBlock(@RequestBody BlockRequestDTO block);

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> updateBlock(@PathVariable("id") Long id, @RequestBody BlockRequestDTO block);
	
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteBlock(@PathVariable("id") Long id);
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<BlockResponseDTO>> findAll();
	
	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BlockResponseDTO> findById(@PathVariable("id") Long id);

}

