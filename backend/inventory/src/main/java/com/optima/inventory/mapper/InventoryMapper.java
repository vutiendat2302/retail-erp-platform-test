package com.optima.inventory.mapper;

import com.optima.inventory.dto.request.InventoryRequestDto;
import com.optima.inventory.dto.response.InventoryResponseDto;
import com.optima.inventory.dto.response.ProductBatchResponseDto;
import com.optima.inventory.dto.response.ProductResponseDto;
import com.optima.inventory.dto.response.WarehouseResponseDto;
import com.optima.inventory.entity.InventoryEntity;
import com.optima.inventory.entity.ProductBatchEntity;
import com.optima.inventory.entity.ProductEntity;
import com.optima.inventory.entity.WarehouseEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = {ProductMapper.class, ProductBatchMapper.class, WarehouseMapper.class})
public interface InventoryMapper {

    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.status", target = "status")
    @Mapping(source = "request.createBy", target = "createBy")
    @Mapping(source = "request.updateBy", target = "updateBy")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productBatchEntity", source = "productBatchEntity")
    @Mapping(target = "warehouseEntity", source = "warehouseEntity")
    @Mapping(target = "productEntity", source = "productEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InventoryEntity toInventory(InventoryResponseDto request, WarehouseEntity warehouseEntity,
                                ProductBatchEntity productBatchEntity, ProductEntity productEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.status", target = "status")
    @Mapping(source = "request.createBy", target = "createBy")  // Nên xóa cái này đi
    @Mapping(source = "request.updateBy", target = "updateBy")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productBatchEntity", source = "productBatchEntity")
    @Mapping(target = "warehouseEntity", source = "warehouseEntity")
    @Mapping(target = "productEntity", source = "productEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInventory(@MappingTarget InventoryEntity inventoryEntity, InventoryRequestDto request);



    InventoryResponseDto toInventoryResponseDto(InventoryEntity);
}

