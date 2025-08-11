package com.optima.inventory.repository;

import com.optima.inventory.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    public interface ProductView {
        Long getId();
        String getName();
        String getDescription();
        Boolean getStatus();
        BigDecimal getPriceNormal();
        String getBrandName();
        String getCategoryName();
        String getManufacturingLocationName();
        Long getBrandId();
        Long getCategoryId();
        Long getManufacturingLocationId();
    }

    @Query("""
    select 
        a.id as id,
        a.name as name,
        a.description as description,
        a.priceNormal as priceNormal,
        a.status as status,
        a.brand.name as brandName,
        a.category.name as categoryName,
        a.manufacturingLocation.name as manufacturingLocationName,
        a.brand.id as brandId,
        a.category.id as categoryId,
        a.manufacturingLocation.id as manufacturingLocationId
    from ProductEntity a
    left join BrandEntity b on a.brand.id = b.id
    left join CategoryEntity c on a.category.id = c.id
    left join ManufacturingLocationEntity d on a.manufacturingLocation.id = d.id
    """)

    List<ProductView> findAllWithBrandCategoryManufacturing();

    @Query("""
    select 
        a.id as id,
        a.name as name,
        a.description as description,
        a.priceNormal as priceNormal,
        a.status as status,
        a.brand.name as brandName,
        a.category.name as categoryName,
        a.manufacturingLocation.name as manufacturingLocationName,
        a.brand.id as brandId,
        a.category.id as categoryId,
        a.manufacturingLocation.id as manufacturingLocationId
    from ProductEntity a
    left join BrandEntity b on a.brand.id = b.id
    left join CategoryEntity c on a.category.id = c.id
    left join ManufacturingLocationEntity d on a.manufacturingLocation.id = d.id
    """)
    Page<ProductView> findAllIn4(Pageable pageable);
}
