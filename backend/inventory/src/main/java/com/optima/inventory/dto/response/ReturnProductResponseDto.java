package com.optima.inventory.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ReturnProductResponseDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Long logId;
    private Long productId;
    private int quantity;
    private long price;
    private Long batchId;
}
