package com.optima.inventory.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryResponseDto {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Column(name = "warehouse_id")
    private long warehouseId;

    @Column(name = "product_id")
    private long productId;

    @Column(name = "quantity_available")
    private int quantityAvailable;

    @Column(name = "minimum_quantity")
    private int minimumQuantity;

    @Column(name = "maximum_quantity")
    private int maximumQuantity;

    private boolean status;

    @Column(name = "create_by")
    private long createBy;

    @Column(name = "update_by")
    private long updateBy;

    @Column(name = "suggest_day_minimum_warehouse")
    private LocalDateTime suggestDayMinimumWarehouse;

    private ProductBatchResponseDto productBatchResponseDto;
    private WarehouseResponseDto warehouseResponseDto;
    private ProductResponseDto productResponseDto;

    @JsonGetter("status")
    public String getStatusString() {
        return this.status ? "Active" : "Inactive";
    }
}
