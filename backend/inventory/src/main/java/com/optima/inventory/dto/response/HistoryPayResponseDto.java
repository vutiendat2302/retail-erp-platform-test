package com.optima.inventory.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryPayResponseDto
{
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private LocalDateTime timePay;
    private String method;
    private Long amount;
    private long createBy;
    private LocalDateTime createAt;
    private long updateBy;
    private LocalDateTime updateAt;
    private boolean status;
    private Long logId;
}
