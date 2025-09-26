package com.optima.inventory.repository;

import com.optima.inventory.dto.response.ExportProductResponseDto;
import com.optima.inventory.entity.ExportProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExportProductRepository extends JpaRepository<ExportProductEntity, Long> {
    List<ExportProductEntity> findExportProductByLogId(Long logId);
}
