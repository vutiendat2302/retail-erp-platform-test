package com.optima.inventory.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreProductResponseDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Long storeId;
    private Long productId;
    private int quantity;
    private Long createBy;
    private LocalDateTime createAt;
    private Long updateBy;
    private LocalDateTime updateAt;
    private Long batchId;
    private boolean status;

    @JsonGetter("status")
    public String getStatusString() {
        return this.status ? "active" : "inactive";
    }
}
