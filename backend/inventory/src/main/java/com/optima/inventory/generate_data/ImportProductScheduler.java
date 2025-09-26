package com.optima.inventory.generate_data;

import com.optima.inventory.dto.response.*;
import com.optima.inventory.entity.HistoryPayEntity;
import com.optima.inventory.entity.ProductBatchEntity;
import com.optima.inventory.service.*;
import com.optima.inventory.utils.SnowflakeIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class ImportProductScheduler {
    private final ImportProductLogService importProductLogService;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final WarehouseService warehouseService;
    private final SupplierService supplierService;
    private final ProductBatchService productBatchService;
    private final ExportProductLogService exportProductLogService;
    private Random random = new Random();
    private final Long USER_ID = 1450492617670998016L;

    public ImportProductScheduler(ImportProductLogService importProductLogService,
                                  ProductService productService, InventoryService inventoryService,
                                  WarehouseService warehouseService, SupplierService supplierService,
                                  ProductBatchService productBatchService, ExportProductLogService exportProductLogService) {
        this.importProductLogService = importProductLogService;
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.warehouseService = warehouseService;
        this.supplierService = supplierService;
        this.productBatchService = productBatchService;
        this.exportProductLogService = exportProductLogService;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void generateFullImportProcess() {
        System.out.println("Start:");

        int numberOfProducts = random.nextInt(6) + 1;
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        List<ImportProductResponseDto> importProductResponseDtoList = new ArrayList<>();
        for (int i = 0; i < numberOfProducts; i++) {
            ProductResponseDto productImport = productService.findRandProduct()
                    .orElseThrow(() -> new IllegalStateException("Khog co san pham"));
            productResponseDtoList.add(productImport);
        }
        System.out.println("Tham san pham thanh cong");
        long totalAmount = 0;
        for (ProductResponseDto productResponseDto : productResponseDtoList) {
            ProductBatchEntity productBatchEntity;
            ProductBatchResponseDto productBatchResponseDto = new ProductBatchResponseDto();
            productBatchResponseDto.setProductId(productResponseDto.getId());
            productBatchResponseDto.setImportDate(LocalDateTime.now());
            productBatchResponseDto.setCreateBy(USER_ID);

            if (random.nextDouble() < 0.7) {
                Optional<ProductBatchEntity> batch = productBatchService.findRandBatch(productResponseDto.getId());
                if (batch.isPresent()) {
                    productBatchEntity = batch.get();
                } else {
                    productBatchEntity = productBatchService.createProductBatchEntity(productBatchResponseDto);
                }
            } else {
                productBatchEntity = productBatchService.createProductBatchEntity(productBatchResponseDto);
            }
            System.out.println("them batch thanh cong");
            int quantity = random.nextInt(1000);
            totalAmount += quantity * productResponseDto.getPriceNormal();
            ImportProductResponseDto importProductResponseDto = new ImportProductResponseDto();
            importProductResponseDto.setProductId(productResponseDto.getId());
            importProductResponseDto.setId(SnowflakeIdGenerator.nextId());
            importProductResponseDto.setPrice(productResponseDto.getPriceNormal());
            importProductResponseDto.setQuantity(quantity);
            importProductResponseDto.setBatchId(productBatchEntity.getId());
            System.out.println("print batch id"  + productBatchEntity.getId());
            importProductResponseDtoList.add(importProductResponseDto);
        }
        System.out.println("them import thanh cong");
        ImportLogResponseDto importLogResponseDto = generateLog();
        System.out.println("them log thanh cong");
        ImportProductLogResponseDto importProductLogResponseDto = new ImportProductLogResponseDto();
        importProductLogResponseDto.setImportLogResponseDto(importLogResponseDto);
        importProductLogResponseDto.setImportProductResponseDtoList(importProductResponseDtoList);
        System.out.println("them import product log thanh cong");
        ImportLogResponseDto importLogResponseDto1 = importProductLogService.createImportLogDto(importProductLogResponseDto);
        Long logId = importLogResponseDto1.getId();
        HistoryPayResponseDto historyPayResponseDto = createPay(logId, totalAmount);
        System.out.println("Taoj hiss pay thanh cong");
        importProductLogService.updateImportLog(logId, true);
    }

    public ImportLogResponseDto generateLog() {
        ImportLogResponseDto logDetails = new ImportLogResponseDto();
        SupplierResponseDto supplierResponseDto = supplierService.findRandSupplier()
                .orElseThrow(() -> new IllegalStateException("Khog co nha cung cap"));
        WarehouseResponseDto warehouseResponseDto = warehouseService.findRandWarehouse()
                .orElseThrow(() -> new IllegalStateException("Khog co kho"));

        logDetails.setFromSupplierId(supplierResponseDto.getId());
        logDetails.setToWarehouseId(warehouseResponseDto.getId());
        logDetails.setStatus(false);
        logDetails.setDescription("Đơn hàng tự động ngày " + LocalDateTime.now());
        return logDetails;
    }

    // Tao hoa don thanh toan
    public HistoryPayResponseDto createPay(Long logId, long amount) {
        HistoryPayResponseDto historyPayResponseDto = new HistoryPayResponseDto();
        historyPayResponseDto.setTimePay(LocalDateTime.now());
        historyPayResponseDto.setCreateBy(USER_ID);
        historyPayResponseDto.setCreateAt(LocalDateTime.now());
        historyPayResponseDto.setStatus(false);
        historyPayResponseDto.setAmount(amount);


        return importProductLogService.createHistoryPay(logId, historyPayResponseDto);
    }
}

