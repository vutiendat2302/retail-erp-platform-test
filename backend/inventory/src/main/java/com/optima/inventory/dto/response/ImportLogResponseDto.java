package com.optima.inventory.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImportLogResponseDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String description;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fromSupplierId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long toWarehouseId;

    private String fromSupplierName;
    private String toWarehouseName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long createBy;
    private LocalDateTime createAt;
    private long updateBy;
    private LocalDateTime updateAt;
    private boolean status;
    private long totalAmount;
    private String name;

    @JsonGetter("status")
    public String getStatusString() {
        return this.status ? "active" : "inactive";
    }

    @JsonSetter("status")
    public void setStatusString(String status) {
        this.status = "active".equalsIgnoreCase(status);
    }
}
