package com.optima.inventory.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExportLogResponseDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long fromWarehouseId;
    private Long toStoreId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long createBy;
    private LocalDateTime createAt;
    private long updateBy;
    private LocalDateTime updateAt;
    private String description;
    private boolean status;
    @JsonGetter("status")
    public String getStatusString() {
        return this.status ? "active" : "inactive";
    }

    @JsonSetter("status")
    public void setStatusString(String status) {
        this.status = "active".equalsIgnoreCase(status);
    }
}
