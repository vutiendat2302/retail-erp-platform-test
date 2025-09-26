package com.optima.inventory.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class ExportProductLogResponseDto {
    @Valid
    private ExportLogResponseDto exportLogResponseDto;

    @Valid
    private List<ExportProductResponseDto> exportProductResponseDto;
}
