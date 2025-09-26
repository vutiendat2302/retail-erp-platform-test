package com.optima.inventory.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Cleanup;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_batch")
@Data
public class ProductBatchEntity {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    private String description;

    private String name;

    @Column(name = "create_by")
    private long createBy;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_by")
    private long updateBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "import_date")
    private LocalDateTime importDate;

    private Boolean status;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

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
