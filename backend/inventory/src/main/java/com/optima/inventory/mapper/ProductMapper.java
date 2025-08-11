package com.optima.inventory.mapper;

import com.optima.inventory.dto.response.ProductResponseDto;
import com.optima.inventory.entity.BrandEntity;
import com.optima.inventory.entity.CategoryEntity;
import com.optima.inventory.entity.ManufacturingLocationEntity;
import com.optima.inventory.entity.ProductEntity;
import com.optima.inventory.repository.ProductRepository;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = {BrandMapper.class, CategoryMapper.class, ManufacturingLocationMapper.class})
public interface ProductMapper {
    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "request.seoTitle", target = "seoTitle")
    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.status", target = "status")
    @Mapping(source = "request.metaKeyword", target = "metaKeyword")
    @Mapping(source = "request.createBy", target = "createBy")
    @Mapping(source = "request.updateBy", target = "updateBy")

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)

    @Mapping(target = "brand", source = "brandEntity")
    @Mapping(target = "category", source = "categoryEntity")
    @Mapping(target = "manufacturingLocation", source = "manufacturingLocationEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductEntity toProduct(ProductResponseDto request, BrandEntity brandEntity, CategoryEntity categoryEntity, ManufacturingLocationEntity manufacturingLocationEntity);

    @Mapping(source = "request.id", target = "id")
    @Mapping(target = "createBy", source = "request.createBy")
    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "request.seoTitle", target = "seoTitle")
    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.status", target = "status")
    @Mapping(source = "request.metaKeyword", target = "metaKeyword")
    @Mapping(source = "request.updateBy", target = "updateBy")
    @Mapping(target = "brand", source = "brandEntity")
    @Mapping(target = "category", source = "categoryEntity")
    @Mapping(target = "manufacturingLocation", source = "manufacturingLocationEntity")

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProduct(@MappingTarget ProductEntity productEntity, ProductResponseDto request, BrandEntity brandEntity,
                       CategoryEntity categoryEntity, ManufacturingLocationEntity manufacturingLocationEntity);

    @Mapping(source = "brand.name", target = "brandResponseDto.name")
    @Mapping(source = "category.name", target = "categoryResponseDto.name")
    @Mapping(source = "manufacturingLocation.name", target = "manufacturingLocationResponseDto.name")
    @Mapping(source = "brand.id", target = "brandResponseDto.id")
    @Mapping(source = "category.id", target = "categoryResponseDto.id")
    @Mapping(source = "manufacturingLocation.id", target = "manufacturingLocationResponseDto.id")
    ProductResponseDto toProductResponseDto(ProductEntity productEntity);

    @Mapping(source = "brandName", target = "brandResponseDto.name")
    @Mapping(source = "categoryName", target = "categoryResponseDto.name")
    @Mapping(source = "manufacturingLocationName", target = "manufacturingLocationResponseDto.name")
    @Mapping(source = "brandId", target = "brandResponseDto.id")
    @Mapping(source = "categoryId", target = "categoryResponseDto.id")
    @Mapping(source = "manufacturingLocationId", target = "manufacturingLocationResponseDto.id")
    @Mapping(source = "id", target = "id")
    ProductResponseDto fromProjection(ProductRepository.ProductView view);
}
