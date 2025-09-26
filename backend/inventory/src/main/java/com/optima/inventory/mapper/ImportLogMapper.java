package com.optima.inventory.mapper;

import com.optima.inventory.dto.response.ImportLogResponseDto;
import com.optima.inventory.dto.response.ImportProductResponseDto;
import com.optima.inventory.dto.response.ProductResponseDto;
import com.optima.inventory.entity.ImportLogEntity;
import com.optima.inventory.entity.ImportProductEntity;
import com.optima.inventory.repository.ImportLogRepository;
import com.optima.inventory.repository.ProductRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ImportLogMapper {
    @Mapping(target = "id", ignore = true)
    ImportLogEntity toImportLog(ImportLogResponseDto importLogResponseDto);

    ImportLogResponseDto toImportLogDto(ImportLogEntity importLogEntity);

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateImportLog(@MappingTarget ImportLogEntity importLogEntity, ImportLogResponseDto importLogResponseDto);

    @Mapping(source = "id", target = "id")
    ImportLogResponseDto fromProjection(ImportLogRepository.ImportView view);
}
