package com.optima.inventory.dto.response;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class ImportProductLogResponseDto {
    @Valid
    private ImportLogResponseDto importLogResponseDto;

    @Valid
    private List<ImportProductResponseDto> importProductResponseDtoList;
}
