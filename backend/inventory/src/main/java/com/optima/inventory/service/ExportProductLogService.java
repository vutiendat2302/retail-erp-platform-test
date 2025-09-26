package com.optima.inventory.service;

import com.optima.inventory.dto.response.*;
import com.optima.inventory.entity.*;
import com.optima.inventory.mapper.*;
import com.optima.inventory.repository.*;
import com.optima.inventory.utils.SnowflakeIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExportProductLogService {
    @Autowired
    private ExportLogMapper exportLogMapper;
    @Autowired
    private ExportLogRepository exportLogRepository;
    @Autowired
    private ExportProductMapper exportProductMapper;
    @Autowired
    private ExportProductRepository exportProductRepository;
    @Autowired
    private ProductBatchRepository productBatchRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private StoreProductRepository storeProductRepository;


    public List<ExportProductResponseDto> getExportProduct(Long logId) {
        List<ExportProductEntity> exportProductEntities = exportProductRepository.findExportProductByLogId(logId);
        List<ExportProductResponseDto> exportProductResponseDtoList = new ArrayList<>();
        for (ExportProductEntity exportProductEntity : exportProductEntities) {
            exportProductResponseDtoList.add(exportProductMapper.toExportProductDto(exportProductEntity));
        }
        return exportProductResponseDtoList;
    }

    public ExportLogResponseDto getExportLog(Long id) {
        ExportLogEntity exportLogEntity = exportLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay" + id));
        return exportLogMapper.toExportLogDto(exportLogEntity);
    }

    @Transactional
    public ExportLogResponseDto createExportLogDto(ExportProductLogResponseDto exportProductLogResponseDto) {
        // Thêm log
        ExportLogEntity exportLogEntity = exportLogMapper.toExportLog(exportProductLogResponseDto.getExportLogResponseDto());

        Long newExportLogId = SnowflakeIdGenerator.nextId();
        exportLogEntity.setId(newExportLogId);
        exportLogEntity.setCreateAt(LocalDateTime.now());
        exportLogEntity.setUpdateAt(LocalDateTime.now());
        exportLogRepository.save(exportLogEntity);


        List<ExportProductEntity> exportProductEntities = new ArrayList<>();

        for (var dto : exportProductLogResponseDto.getExportProductResponseDto()) {
            ExportProductEntity entity = exportProductMapper.toExportProduct(dto);
            entity.setId(SnowflakeIdGenerator.nextId());
            entity.setLogId(newExportLogId);
            exportProductEntities.add(entity);
        }

        exportProductRepository.saveAll(exportProductEntities);
        exportLogRepository.save(exportLogEntity);
        updateInventory(newExportLogId);

        return exportLogMapper.toExportLogDto(exportLogEntity);
    }

    @Transactional
    public void updateInventory(Long logId) {
        ExportLogEntity exportLogEntity = exportLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Export Log Id not found" + logId));

        List<ExportProductEntity> exportProductEntities = exportProductRepository.findExportProductByLogId(logId);
        for (ExportProductEntity exportProductEntity : exportProductEntities) {
            Long productId = exportProductEntity.getProductId();
            Long batchId = exportProductEntity.getBatchId();
            Long warehouseID = exportLogEntity.getFromWarehouseId();
            int quantity = exportProductEntity.getQuantity();

            Optional<InventoryEntity> inventoryEntity = inventoryRepository.findByWarehouseIdAndProductIdAndProductBatchId(
                    warehouseID,
                    productId,
                    batchId
            );

            if (inventoryEntity.isPresent()) {
                InventoryEntity inventory = inventoryEntity.get();
                inventory.setQuantityAvailable(inventory.getQuantityAvailable() - quantity);
                inventory.setUpdateAt(LocalDateTime.now());
                inventoryRepository.save(inventory);
            }
        }
    }


    @Transactional
    public ExportLogResponseDto updateExportLog(Long logId, boolean status) {
        ExportLogEntity exportLogEntity = exportLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Import Log not found with id: " + logId));

        exportLogEntity.setStatus(status);
        updateStoreProduct(logId);
        return exportLogMapper.toExportLogDto(exportLogRepository.save(exportLogEntity));
    }

    @Transactional
    public void updateStoreProduct(Long logId) {
        ExportLogEntity exportLogEntity = exportLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Import Log Id not found" + logId));

        List<ExportProductEntity> exportProductEntities = exportProductRepository.findExportProductByLogId(logId);
        for (ExportProductEntity exportProductEntity : exportProductEntities) {
            Long productId = exportProductEntity.getProductId();
            Long batchId = exportProductEntity.getBatchId();
            Long storeId = exportLogEntity.getToStoreId();
            int quantity = exportProductEntity.getQuantity();

            Optional<StoreProductEntity> storeProductEntity = storeProductRepository.findByStoreIdAndProductIdAndBatchId(
                    storeId,
                    productId,
                    batchId
            );

            if (storeProductEntity.isPresent()) {
                StoreProductEntity storeProduct = storeProductEntity.get();
                storeProduct.setQuantity(storeProduct.getQuantity() + quantity);
                storeProduct.setUpdateAt(LocalDateTime.now());
                storeProductRepository.save(storeProduct);
            }
        }
    }

    @Transactional
    public boolean checkExportProduct(Long warehouseId, ExportProductResponseDto exportProductResponseDto) {
        int quantityExport = exportProductResponseDto.getQuantity();
        System.out.println(quantityExport);
        Long productId = exportProductResponseDto.getProductId();
        System.out.println(productId);
        System.out.println(warehouseId);
        long quantityInventory = inventoryRepository.getSumQuantityByProductIdAndWarehouseId(warehouseId, exportProductResponseDto.getProductId());
        return quantityExport <= quantityInventory;
    }
}
