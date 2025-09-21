package com.optima.inventory.repository;

import com.optima.inventory.dto.response.ImportLogResponseDto;
import com.optima.inventory.entity.ImportLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;

@Repository
public interface ImportLogRepository extends JpaRepository<ImportLogEntity, Long> {

}
