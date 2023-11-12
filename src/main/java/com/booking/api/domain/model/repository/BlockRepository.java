package com.booking.api.domain.model.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.booking.api.domain.model.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
	
	@Query("""
			SELECT b FROM Block b
			WHERE ((:startDate BETWEEN b.startDate AND b.endDate) OR (:endDate BETWEEN b.startDate AND b.endDate))
			""")
	List<Block> findByPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
