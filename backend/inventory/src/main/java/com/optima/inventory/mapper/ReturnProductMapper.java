package com.optima.inventory.mapper;

import com.optima.inventory.dto.response.ReturnProductResponseDto;
import com.optima.inventory.entity.ReturnProductEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReturnProductMapper {
    @Mapping(target = "id", ignore = true)
    ReturnProductEntity toReturnProduct(ReturnProductResponseDto returnProductResponseDto);

    ReturnProductResponseDto toReturnProductDto(ReturnProductEntity returnProductEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateReturnProduct(@MappingTarget ReturnProductEntity returnProductEntity, ReturnProductResponseDto returnProductResponseDto);
}
