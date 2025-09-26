package com.optima.inventory.repository;

import com.optima.inventory.entity.ReturnProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnProductRepository extends JpaRepository<ReturnProductEntity, Long> {
    List<ReturnProductEntity> findReturnProductByLogId(Long logId);



}
