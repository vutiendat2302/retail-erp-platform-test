package com.optima.inventory.controller;

import com.optima.inventory.dto.request.ProductBatchRequestDto;
import com.optima.inventory.dto.response.ProductBatchNameResponse;
import com.optima.inventory.dto.response.ProductBatchResponseDto;
import com.optima.inventory.entity.ProductBatchEntity;
import com.optima.inventory.repository.ProductBatchRepository;
import com.optima.inventory.service.ProductBatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/productBatch")
public class ProductBatchController {
    @Autowired
    private ProductBatchService productBatchService;
    @Autowired
    private ProductBatchRepository productBatchRepository;

    @PostMapping("/createProductBatch")
    public ProductBatchResponseDto createProductBatch(@RequestBody @Valid ProductBatchResponseDto productBatchResponseDto) {
        return productBatchService.createProductBatch(productBatchResponseDto);
    }

    @GetMapping
    public List<ProductBatchEntity> getProductBatches() {
        return productBatchService.getProductBatches();
    }

    @GetMapping("/{productBatchId}")
    public ProductBatchEntity getProductBatch(@PathVariable("productBatchId") Long productBatchId) {
        return productBatchService.getProductBatch(productBatchId);
    }

    @PutMapping("/{productBatchId}")
    public ProductBatchEntity updateProductBatch(@PathVariable long productBatchId, @RequestBody ProductBatchRequestDto request) {
        return productBatchService.updateProductBatch(productBatchId, request);
    }

    @DeleteMapping("/{productBatchId}")
    public String deleteProductBatch(@PathVariable("productBatchId") long productBatchId) {
        productBatchService.deleteProductBatch(productBatchId);
        return "Product Batch has been deleted";
    }

    @GetMapping("/productBatchName")
    public List<ProductBatchNameResponse> getProductBatchName() {
        return productBatchService.getProductBatchName();
    }

}
