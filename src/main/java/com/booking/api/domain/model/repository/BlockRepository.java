package com.booking.api.domain.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.api.domain.model.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
	
}
	