package com.optima.inventory.repository;

import com.optima.inventory.entity.ProductBatchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductBatchRepository extends JpaRepository<ProductBatchEntity, Long> {
    long countByProductId(Long productId);

    Page<ProductBatchEntity> findByProductId(Long productId, Pageable pageable);

    public interface ProductBatchView {
        Long getId();
        String getName();
    }

    @Query("""
    select
        a.id as id,
        a.name as name
    from ProductBatchEntity a
    """)
    List<ProductBatchView> getProductBatchName();

    Optional<ProductBatchEntity> findTopByOrderByIdDesc();

}
