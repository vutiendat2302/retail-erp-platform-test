package com.optima.inventory.repository;

import com.optima.inventory.entity.StoreProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProductEntity, Long> {
    Optional<StoreProductEntity> findByStoreIdAndProductIdAndBatchId(Long storeId, Long productId, Long batchId);
}
