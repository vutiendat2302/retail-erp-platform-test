package com.optima.inventory.repository;

import com.optima.inventory.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    public interface StoreView {
        Long getId();
        String getEmail();
        String getAddress();
        String getName();
        String getDescription();
        String getStatus();
        Long getCreateBy();
        Long getUpdateBy();
        LocalDateTime getCreateAt();
        LocalDateTime getUpdateAt();
    }

    @Query("""
    select sum(a.quantity)
    from StoreProductEntity a
    join StoreEntity b on b.id = a.storeId
    join ProductEntity c on a.productId = c.id
    where b.id = :storeId
    """)

    int getTotalProducts(@Param("storeId") Long storeId);

    @Query("""
    select sum(c.priceNormal * a.quantity)
    from StoreProductEntity a
    join StoreEntity b on b.id = a.storeId
    join ProductEntity c on a.productId = c.id
    where b.id = :storeId
    """)

    long getTotalPrices(@Param("storeId") Long storeId);
}
