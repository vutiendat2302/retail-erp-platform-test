package com.optima.inventory.mapper;

import com.optima.inventory.dto.response.ImportProductResponseDto;
import com.optima.inventory.entity.ImportLogEntity;
import com.optima.inventory.entity.ImportProductEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ImportProductMapper {

    @Mapping(target = "id", ignore = true)
    ImportProductEntity toImportProduct(ImportProductResponseDto importProductResponseDto);

    ImportProductResponseDto toImportProductDto(ImportProductEntity importProductEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateImportProduct(@MappingTarget ImportProductEntity importProductEntity, ImportProductResponseDto importProductResponseDto);

}
