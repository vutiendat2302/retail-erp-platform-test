package com.optima.inventory.mapper;

import com.optima.inventory.dto.response.HistoryPayResponseDto;
import com.optima.inventory.entity.HistoryPayEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface HistoryPayMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "logId", ignore = true)
    HistoryPayEntity toHistoryPay(HistoryPayResponseDto historyResponseDto);

    HistoryPayResponseDto toHistoryPayDto(HistoryPayEntity historyPayEntity);

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateHistoryPay(@MappingTarget HistoryPayEntity historyPayEntity, HistoryPayResponseDto historyResponseDto);

}
