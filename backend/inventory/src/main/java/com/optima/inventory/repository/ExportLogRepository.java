package com.optima.inventory.repository;

import com.optima.inventory.entity.ExportLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExportLogRepository extends JpaRepository<ExportLogEntity, Long> {
}
