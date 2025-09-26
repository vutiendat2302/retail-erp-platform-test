package com.optima.inventory.mapper;

import com.optima.inventory.dto.response.ExportProductResponseDto;
import com.optima.inventory.entity.ExportProductEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ExportProductMapper {
    @Mapping(target = "id", ignore = true)
    ExportProductEntity toExportProduct(ExportProductResponseDto exportProductResponseDto);

    ExportProductResponseDto toExportProductDto(ExportProductEntity exportProductEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateExportProduct(@MappingTarget ExportProductEntity exportProductEntity, ExportProductResponseDto exportProductResponseDto);

}
