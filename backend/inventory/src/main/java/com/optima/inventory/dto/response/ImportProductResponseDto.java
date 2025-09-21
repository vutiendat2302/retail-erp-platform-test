package com.optima.inventory.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImportProductResponseDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Long logId;
    private Long productId;
    private int quantity;
    private long price;
    private String note;
    private Long batchId;
}
