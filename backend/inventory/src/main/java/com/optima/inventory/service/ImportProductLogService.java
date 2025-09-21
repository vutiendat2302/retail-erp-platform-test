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
public class ImportProductLogService {
    @Autowired
    private ImportProductRepository importProductRepository;
    @Autowired
    private ImportLogRepository importLogRepository;
    @Autowired
    private ImportLogMapper importLogMapper;
    @Autowired
    private ImportProductMapper importProductMapper;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductBatchRepository productBatchRepository;
    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private ProductBatchMapper productBatchMapper;
    @Autowired
    private HistoryPayRepository historyPayRepository;
    @Autowired
    private HistoryPayMapper historyPayMapper;


    public List<ImportProductResponseDto> getImportProduct(Long logId) {
        List<ImportProductEntity> importProductEntities = importProductRepository.findImportProductByLogId(logId);
        List<ImportProductResponseDto> importProductResponseDtoList = new ArrayList<>();
        for (ImportProductEntity importProductEntity : importProductEntities) {
            importProductResponseDtoList.add(importProductMapper.toImportProductDto(importProductEntity));
        }
        return importProductResponseDtoList;
    }

    public ImportLogResponseDto getImportLog(Long id) {
        ImportLogEntity importLogEntity = importLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay" + id));
        return importLogMapper.toImportLogDto(importLogEntity);
    }

    @Transactional
    public ImportLogResponseDto createImportLogDto(ImportProductLogResponseDto importProductLog) {
        // Thêm log
        ImportLogEntity importLogEntity = importLogMapper.toImportLog(importProductLog.getImportLogResponseDto());

        Long newImportLogId = SnowflakeIdGenerator.nextId();
        importLogEntity.setId(newImportLogId);
        importLogEntity.setCreateAt(LocalDateTime.now());
        importLogEntity.setUpdateAt(LocalDateTime.now());
        importLogRepository.save(importLogEntity);

        // them import product
        List<ImportProductEntity> importProductEntities = importProductLog.getImportProductResponseDtoList().stream()
                .map(dto -> {
                    ImportProductEntity importProductEntity = importProductMapper.toImportProduct(dto);
                    importProductEntity.setId(SnowflakeIdGenerator.nextId());
                    importProductEntity.setLogId(newImportLogId);
                    return importProductEntity;
                })
                .toList();

        importProductRepository.saveAll(importProductEntities);

        return importLogMapper.toImportLogDto(importLogRepository.save(importLogEntity));
    }

    @Transactional
    public void updateInventory(Long importLogId) {
        ImportLogEntity importLogEntity = importLogRepository.findById(importLogId)
                .orElseThrow(() -> new RuntimeException("Import Log Id not found" + importLogId));

        List<ImportProductEntity> importProductEntities = importProductRepository.findImportProductByLogId(importLogId);
        for (ImportProductEntity importProductEntity : importProductEntities) {
            Long productId = importProductEntity.getProductId();
            Long batchId = importProductEntity.getBatchId();
            Long warehouseID = importLogEntity.getToWarehouseId();
            int quantity = importProductEntity.getQuantity();

            Optional<InventoryEntity> inventoryEntity = inventoryRepository.findByWarehouseIdAndProductIdAndProductBatchId(
                    warehouseID,
                    productId,
                    batchId
            );

            if (inventoryEntity.isPresent()) {
                InventoryEntity inventory = inventoryEntity.get();
                inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
                inventory.setUpdateAt(LocalDateTime.now());
                inventoryRepository.save(inventory);
            } else {
                InventoryEntity inventory = new InventoryEntity();
                inventory.setId(SnowflakeIdGenerator.nextId());
                inventory.setQuantityAvailable(quantity);
                inventory.setWarehouseId(warehouseID);
                inventory.setProductId(productId);
                inventory.setProductBatchId(batchId);
                inventory.setCreateAt(LocalDateTime.now());
                inventoryRepository.save(inventory);
            }
        }
    }

    public HistoryPayResponseDto getHistoryPayRepository(Long logId) {
        HistoryPayEntity historyPayEntity = historyPayRepository.findByLogId(logId)
                .orElseThrow(() -> new RuntimeException("Null" + logId));
        return historyPayMapper.toHistoryPayDto(historyPayEntity);
    }

    @Transactional
    public void updateHistoryPay(Long logId, boolean status
    ) {
        HistoryPayEntity historyPayEntity = historyPayRepository.findByLogId(logId)
                .orElseThrow(() -> new RuntimeException("Null" + logId));

        historyPayEntity.setStatus(status);
        historyPayRepository.save(historyPayEntity);
    }

    public HistoryPayResponseDto createHistoryPay(Long logId, HistoryPayResponseDto historyPayResponseDto) {
        HistoryPayEntity historyPayEntity = historyPayMapper.toHistoryPay(historyPayResponseDto);
        historyPayEntity.setId(SnowflakeIdGenerator.nextId());
        historyPayEntity.setLogId(logId);
        return historyPayMapper.toHistoryPayDto(historyPayRepository.save(historyPayEntity));
    }

    @Transactional
    public ImportLogResponseDto updateImportLog(Long importLogId, boolean status) {
        ImportLogEntity importLogEntity = importLogRepository.findById(importLogId)
                .orElseThrow(() -> new RuntimeException("Import Log not found with id: " + importLogId));

        importLogEntity.setStatus(status);
        updateHistoryPay(importLogId, status);
        updateInventory(importLogId);
        return importLogMapper.toImportLogDto(importLogRepository.save(importLogEntity));
    }
}

