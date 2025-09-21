package com.optima.inventory.repository;

import com.optima.inventory.dto.response.HistoryPayResponseDto;
import com.optima.inventory.entity.HistoryPayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryPayRepository extends JpaRepository<HistoryPayEntity, Long> {
    Optional<HistoryPayEntity> findByLogId(Long logId);
}
