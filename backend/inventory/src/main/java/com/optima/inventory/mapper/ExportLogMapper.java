package com.optima.inventory.mapper;

import com.optima.inventory.dto.response.ExportLogResponseDto;
import com.optima.inventory.dto.response.ReturnLogResponseDto;
import com.optima.inventory.entity.ExportLogEntity;
import com.optima.inventory.entity.ReturnLogEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ExportLogMapper {
    @Mapping(target = "id", ignore = true)
    ExportLogEntity toExportLog(ExportLogResponseDto exportLogResponseDto);

    ExportLogResponseDto toExportLogDto(ExportLogEntity exportLogEntity);

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateExportLog(@MappingTarget ExportLogEntity exportLogEntity, ExportLogResponseDto exportLogResponseDto);

}
