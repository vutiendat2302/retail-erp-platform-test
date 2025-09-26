package com.optima.inventory.service;

import com.optima.inventory.dto.request.ProductBatchRequestDto;
import com.optima.inventory.dto.response.ProductBatchNameResponse;
import com.optima.inventory.dto.response.ProductBatchResponseDto;
import com.optima.inventory.dto.response.ProductResponseDto;
import com.optima.inventory.entity.ProductBatchEntity;
import com.optima.inventory.entity.ProductEntity;
import com.optima.inventory.mapper.ProductBatchMapper;
import com.optima.inventory.mapper.ProductMapper;
import com.optima.inventory.repository.ProductBatchRepository;
import com.optima.inventory.utils.SnowflakeIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductBatchService {
    @Autowired
    private ProductBatchRepository productBatchRepository;
    @Autowired
    private ProductBatchMapper productBatchMapper;
    @Autowired
    private ProductMapper productMapper;

    @Transactional
    public ProductBatchResponseDto createProductBatch(ProductBatchResponseDto productBatchResponseDto) {
        ProductBatchEntity productBatchEntity = productBatchMapper.toProductBatch(productBatchResponseDto);

        String nameNew =  productBatchResponseDto.getName();
        Optional<ProductBatchEntity> lastBatchOpt = productBatchRepository.findTopByOrderByIdDesc();
        if (lastBatchOpt.isPresent()) {
            if (lastBatchOpt.isPresent() && lastBatchOpt.get().getName() != null) {
                String lastBatchCode = lastBatchOpt.get().getName(); // Lấy đúng trường batchCode
                String numericPart = lastBatchCode.substring(lastBatchCode.indexOf("-") + 1);
                int nextNumber = Integer.parseInt(numericPart) + 1;
                nameNew = String.format("BATCH-%04d", nextNumber);
            } else {
                nameNew = "BATCH-0001";
            }
        } else {
            nameNew = "Batch-0001";
        }

        productBatchEntity.setName(nameNew);

        long newProductBatchId = SnowflakeIdGenerator.nextId();
        while (productBatchRepository.existsById(newProductBatchId)) {
            newProductBatchId = SnowflakeIdGenerator.nextId();
        }
        productBatchEntity.setId(newProductBatchId);

        return productBatchMapper.toProductBatchDto(productBatchRepository.save(productBatchEntity));
    }

    @Transactional
    public ProductBatchEntity createProductBatchEntity(ProductBatchResponseDto productBatchResponseDto) {
        Random random = new Random();
        Optional<ProductBatchEntity> lastBatchOpt = productBatchRepository.findTopByOrderByIdDesc();
        ProductBatchEntity productBatchEntity = productBatchMapper.toProductBatch(productBatchResponseDto);
        String nameNew =  productBatchResponseDto.getName();

        if (lastBatchOpt.isPresent()) {
            if (lastBatchOpt.isPresent() && lastBatchOpt.get().getName() != null) {
                String lastBatchCode = lastBatchOpt.get().getName(); // Lấy đúng trường batchCode
                String numericPart = lastBatchCode.substring(lastBatchCode.indexOf("-") + 1);
                int nextNumber = Integer.parseInt(numericPart) + 1;
                nameNew = String.format("BATCH-%04d", nextNumber);
            } else {
                nameNew = "BATCH-0001";
            }
        } else {
            nameNew = "Batch-0001";
        }

        productBatchEntity.setName(nameNew);

        int minDays = 180;
        int maxDays = 365;

        int randomDays = minDays + random.nextInt(maxDays - minDays + 1);
        LocalDateTime expiryDate = productBatchEntity.getImportDate().plusDays(randomDays);
        productBatchEntity.setExpiryDate(expiryDate);
        long newProductBatchId = SnowflakeIdGenerator.nextId();
        while (productBatchRepository.existsById(newProductBatchId)) {
            newProductBatchId = SnowflakeIdGenerator.nextId();
        }
        productBatchEntity.setId(newProductBatchId);

        return productBatchRepository.save(productBatchEntity);
    }

    public List<ProductBatchEntity> getProductBatches() {
        return productBatchRepository.findAll();
    }

    @Transactional
    public ProductBatchEntity getProductBatch(long productBatchId) {
        return productBatchRepository.findById(productBatchId).orElseThrow(() -> new RuntimeException("Product Batch not find"));
    }

    @Transactional
    public ProductBatchEntity updateProductBatch(long productBatchId, ProductBatchRequestDto request) {
        ProductBatchEntity productBatchEntity = productBatchRepository.findById(productBatchId)
                .orElseThrow(() -> new RuntimeException(("Product Batch not found")));
        productBatchMapper.updateProductBatch(productBatchEntity, request);

        return productBatchRepository.save(productBatchEntity);
    }

    public void deleteProductBatch(long productBatchId) {
        productBatchRepository.deleteById(productBatchId);
    }

    @Transactional
    public List<ProductBatchNameResponse> getProductBatchName() {
        return productBatchRepository.getProductBatchName().stream()
                .map(productBatchMapper::toProductBatchName)
                .collect(Collectors.toList());
    }


    public Optional<ProductBatchEntity> findRandBatch(Long productId) {
        Random random = new Random();
        long count = productBatchRepository.countByProductId(productId);
        if (count == 0) {
            return Optional.empty();
        }

        int randomIndex = random.nextInt((int) count);
        Page<ProductBatchEntity> productBatchEntityPage = productBatchRepository.findByProductId(
                productId,
                PageRequest.of(randomIndex, 1));
        if (productBatchEntityPage.hasContent()) {
            ProductBatchEntity randomBatch = productBatchEntityPage.getContent().get(0);
            return Optional.of(randomBatch);
        }
        return Optional.empty();
    }
}

