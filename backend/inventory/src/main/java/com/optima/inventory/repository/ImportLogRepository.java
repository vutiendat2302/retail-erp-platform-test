package com.optima.inventory.repository;

import com.optima.inventory.dto.response.ImportLogResponseDto;
import com.optima.inventory.entity.ImportLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface ImportLogRepository extends JpaRepository<ImportLogEntity, Long> {
    public interface ImportView {
        Long getId();
        String getName();
        String getDescription();
        String getStatus();
        long getTotalAmount();
        Long getFromSupplierId();
        Long getToWarehouseId();
        Long getCreateBy();
        Long getUpdateBy();
        LocalDateTime getCreateAt();
        LocalDateTime getUpdateAt();
        LocalDateTime getStartTime();
        LocalDateTime getEndTime();
        String getFromSupplierName();
        String getToWarehouseName();
    }

    @Query("""
    select
        a.id as id,
        a.name as name,
        a.description as description,
        a.status as status,
        b.name as fromSupplierName,
        c.name as toWarehouseName,
        a.toWarehouseId as toWarehouseId,
        a.fromSupplierId as fromSupplierId,
        a.startTime as startTime,
        a.endTime as endTime,
        a.createBy as createBy,
        a.updateBy as updateBy,
        a.createAt as createAt,
        a.updateAt as updateAt,
        a.totalAmount as totalAmount
    from ImportLogEntity a
    join SupplierEntity b on a.fromSupplierId = b.id
    join WarehouseEntity c on a.toWarehouseId = c.id
     where
        (
            :search IS NULL
            or lower(a.name) like  lower(concat('%', :search, '%'))
        )
        and (:status is null or a.status = :status)
    """)
    Page<ImportLogRepository.ImportView> getSearchAllIn4(
            @Param("search") String search,
            @Param("status") Boolean status,
            Pageable pageable
    );
}
