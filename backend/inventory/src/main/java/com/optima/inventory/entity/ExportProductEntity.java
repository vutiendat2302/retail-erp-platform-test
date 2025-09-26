package com.optima.inventory.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "export_product")
@Data
@Entity
public class ExportProductEntity {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Column(name = "log_id")
    private Long logId;

    @Column(name = "product_id")
    private Long productId;

    private int quantity;

    @Column(name = "batch_id")
    private Long batchId;
}
