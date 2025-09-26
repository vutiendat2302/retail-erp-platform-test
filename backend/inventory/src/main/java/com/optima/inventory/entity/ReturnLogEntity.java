package com.optima.inventory.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table(name = "return_log")
@Entity
public class ReturnLogEntity {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String reason;

    @Column(name = "from_warehouse_id")
    private Long fromWarehouseId;

    @Column(name = "to_supplier_id")
    private Long toSupplierId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "create_by")
    private long createBy;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_by")
    private long updateBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "total_refund")
    private long totalRefund;

    private boolean status;

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }
}
