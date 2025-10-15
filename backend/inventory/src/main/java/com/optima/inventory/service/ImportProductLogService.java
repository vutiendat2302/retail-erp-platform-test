package com.optima.inventory.service;

import com.optima.inventory.dto.response.*;
import com.optima.inventory.entity.*;
import com.optima.inventory.mapper.*;
import com.optima.inventory.repository.*;
import com.optima.inventory.utils.SnowflakeIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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
    @Autowired
    private ProductService productService;



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


        List<ImportProductEntity> importProductEntities = new ArrayList<>();
        long totalPrice = 0;

        for (var dto : importProductLog.getImportProductResponseDtoList()) {
            ImportProductEntity entity = importProductMapper.toImportProduct(dto);
            entity.setId(SnowflakeIdGenerator.nextId());
            entity.setLogId(newImportLogId);
            totalPrice += entity.getPrice() * entity.getQuantity();
            importProductEntities.add(entity);
        }

        importProductRepository.saveAll(importProductEntities);
        importLogEntity.setTotalAmount(totalPrice);

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
        historyPayEntity.setType("IN");
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

    public ImportProductResponseDto createImportProduct(ImportProductResponseDto importProductResponseDto) {
        ImportProductEntity importProductEntity = importProductMapper.toImportProduct(importProductResponseDto);

        importProductEntity.setId(SnowflakeIdGenerator.nextId());
        return importProductMapper.toImportProductDto(importProductRepository.save(importProductEntity));
    }

    public List<ImportLogResponseDto> getAllImportLogs() {
        return importLogRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(importLogMapper::toImportLogDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Page<ImportLogResponseDto> getSearchAllIn4(String search, Boolean status, Pageable pageable) {
        return importLogRepository.getSearchAllIn4(search,status, pageable).map(importLogMapper::fromProjection);
    }

    @Transactional
    public void deleteImport(Long logId) {
        importLogRepository.deleteById(logId);
        importProductRepository.deleteByLogId(logId);
        historyPayRepository.deleteByLogId(logId);
    }
}

