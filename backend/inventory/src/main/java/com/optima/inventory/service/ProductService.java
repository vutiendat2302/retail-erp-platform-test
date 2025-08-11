package com.optima.inventory.service;

import com.optima.inventory.dto.response.ProductResponseDto;
import com.optima.inventory.entity.BrandEntity;
import com.optima.inventory.entity.CategoryEntity;
import com.optima.inventory.entity.ManufacturingLocationEntity;
import com.optima.inventory.entity.ProductEntity;
import com.optima.inventory.mapper.ProductMapper;
import com.optima.inventory.repository.BrandRepository;
import com.optima.inventory.repository.CategoryRepository;
import com.optima.inventory.repository.ManufacturingLocationRepository;
import com.optima.inventory.repository.ProductRepository;
import com.optima.inventory.utils.SnowflakeIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ManufacturingLocationRepository manufacturingLocationRepository;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          BrandRepository brandRepository,
                          CategoryRepository categoryRepository,
                          ManufacturingLocationRepository manufacturingLocationRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturingLocationRepository = manufacturingLocationRepository;
    }

    @Transactional
    public ProductResponseDto createProduct(ProductResponseDto request) {
        BrandEntity brand = brandRepository.findById(request.getBrandResponseDto().getId())
                .orElseThrow(() -> new RuntimeException("Brand not found with ID: " + request.getBrandResponseDto().getId()));
        CategoryEntity category = categoryRepository.findById(request.getCategoryResponseDto().getId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + request.getCategoryResponseDto().getId()));
        ManufacturingLocationEntity manufacturingLocation = manufacturingLocationRepository.findById(request.getManufacturingLocationResponseDto().getId())
                .orElseThrow(() -> new RuntimeException("Manufacturing Location not found with ID: " + request.getManufacturingLocationResponseDto().getId()));

        ProductEntity productEntity = productMapper.toProduct(request, brand, category, manufacturingLocation);

        long newProductId = SnowflakeIdGenerator.nextId();
        while (productRepository.existsById(newProductId)) {
            newProductId = SnowflakeIdGenerator.nextId();
        }
        productEntity.setId(newProductId);



        return productMapper.toProductResponseDto(productRepository.save(productEntity));
    }

    @Transactional
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProduct(long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not find" + productId));
        return productMapper.toProductResponseDto(productEntity);
    }

    public ProductResponseDto updateProduct(long productId, ProductResponseDto request) {

        BrandEntity brand = brandRepository.findById(request.getBrandResponseDto().getId())
                .orElseThrow(() -> new RuntimeException("Brand not found with ID: " + request.getBrandResponseDto().getId()));
        CategoryEntity category = categoryRepository.findById(request.getCategoryResponseDto().getId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + request.getCategoryResponseDto().getId()));
        ManufacturingLocationEntity manufacturingLocation = manufacturingLocationRepository.findById(request.getManufacturingLocationResponseDto().getId())
                .orElseThrow(() -> new RuntimeException("Manufacturing Location not found with ID: " + request.getManufacturingLocationResponseDto().getId()));
        ProductEntity productEntity = productMapper.toProduct(request, brand, category, manufacturingLocation);

        productMapper.updateProduct(productEntity, request, brand, category, manufacturingLocation);
        ProductEntity afterUpdateProduct = productRepository.save(productEntity);
        return productMapper.toProductResponseDto(afterUpdateProduct);
    }

    @Transactional
    public void deleteProduct(long productId) {
        productRepository.deleteById(productId);
    }

    public List<ProductResponseDto> getProductWithBrandCategoryManufacturing() {
        return productRepository.findAllWithBrandCategoryManufacturing().stream()
                .map(productMapper::fromProjection)
                .collect(Collectors.toList());
    }

    public Page<ProductResponseDto> getAllPage(Pageable pageable) {
        return productRepository.findAllIn4(pageable).map(productMapper::fromProjection);
    }
}
