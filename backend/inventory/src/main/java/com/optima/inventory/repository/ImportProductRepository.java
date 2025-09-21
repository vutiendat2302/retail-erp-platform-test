package com.optima.inventory.repository;

import com.optima.inventory.dto.response.ImportProductResponseDto;
import com.optima.inventory.entity.ImportProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportProductRepository extends JpaRepository<ImportProductEntity, Long> {
    List<ImportProductEntity> findImportProductByLogId(Long logId);
}
