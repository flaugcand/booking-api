package com.booking.api.rest.resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.booking.api.rest.dto.BlockDTO;

@RequestMapping("/block")
public interface BlockResource {

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> createBlock(@RequestBody BlockDTO block);

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> updateBlock(@PathVariable("id") Long id, @RequestBody BlockDTO block);
	
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteBlock(@PathVariable("id") Long id);

}

