package com.optima.inventory.mapper;

import com.optima.inventory.dto.response.ReturnLogResponseDto;
import com.optima.inventory.entity.ReturnLogEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReturnLogMapper {
    @Mapping(target = "id", ignore = true)
    ReturnLogEntity toReturnLog(ReturnLogResponseDto returnLogResponseDto);

    ReturnLogResponseDto toReturnLogDto(ReturnLogEntity returnLogEntity);

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateReturnLog(@MappingTarget ReturnLogEntity returnLogEntity, ReturnLogResponseDto returnLogResponseDto);

}
