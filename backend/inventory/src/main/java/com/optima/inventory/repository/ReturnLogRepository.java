package com.optima.inventory.repository;

import com.optima.inventory.dto.response.ReturnLogResponseDto;
import com.optima.inventory.entity.ReturnLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnLogRepository extends JpaRepository<ReturnLogEntity, Long> {

}
