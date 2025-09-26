package com.optima.inventory.dto.response;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class ReturnProductLogResponseDto {
    @Valid
    private ReturnLogResponseDto returnLogResponseDto;

    @Valid
    private List<ReturnProductResponseDto> returnProductResponseDtoList;
}
