package com.optima.inventory.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "import_product")
@Data
public class ImportProductEntity {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Column(name = "log_id")
    private Long logId;

    @Column(name = "product_id")
    private Long productId;

    private int quantity;

    private long price;

    private String note;

    @Column(name = "batch_id")
    private Long batchId;
}
