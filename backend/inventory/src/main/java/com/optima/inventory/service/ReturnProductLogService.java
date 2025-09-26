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
public class ReturnProductLogService {
    @Autowired
    private ReturnProductRepository returnProductRepository;
    @Autowired
    private ReturnLogRepository returnLogRepository;
    @Autowired
    private ReturnLogMapper returnLogMapper;
    @Autowired
    private ReturnProductMapper returnProductMapper;
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
    private ImportProductRepository importProductRepository;

    public List<ReturnProductResponseDto> getReturnProduct(Long logId) {
        List<ReturnProductEntity> returnProductEntities = returnProductRepository.findReturnProductByLogId(logId);
        List<ReturnProductResponseDto> returnProductResponseDtoList = new ArrayList<>();
        for (ReturnProductEntity returnProductEntity : returnProductEntities) {
            returnProductResponseDtoList.add(returnProductMapper.toReturnProductDto(returnProductEntity));
        }
        return returnProductResponseDtoList;
    }

    public ReturnLogResponseDto getReturnLog(Long id) {
        ReturnLogEntity returnLogEntity = returnLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay" + id));
        return returnLogMapper.toReturnLogDto(returnLogEntity);
    }

    @Transactional
    public ReturnLogResponseDto createReturnLogDto(ReturnProductLogResponseDto returnProductLog) {
        // Thêm log
        ReturnLogEntity returnLogEntity = returnLogMapper.toReturnLog(returnProductLog.getReturnLogResponseDto());
        Long logId = returnProductLog.getReturnLogResponseDto().getId();
        returnLogEntity.setId(logId);
        returnLogEntity.setCreateAt(LocalDateTime.now());
        returnLogEntity.setUpdateAt(LocalDateTime.now());
        returnLogRepository.save(returnLogEntity);

        List<ReturnProductEntity> returnProductEntities = new ArrayList<>();
        long totalPrice = 0;

        // them return product
        for (var dto : returnProductLog.getReturnProductResponseDtoList()) {
            ReturnProductEntity entity = returnProductMapper.toReturnProduct(dto);
            entity.setId(SnowflakeIdGenerator.nextId());
            entity.setLogId(logId);
            totalPrice += entity.getPrice() * entity.getQuantity();
            returnProductEntities.add(entity);
        }

        returnProductRepository.saveAll(returnProductEntities);
        returnLogEntity.setTotalRefund(totalPrice);

        returnProductRepository.saveAll(returnProductEntities);
        returnLogRepository.save(returnLogEntity);
        updateInventory(logId);

        return returnLogMapper.toReturnLogDto(returnLogEntity);
    }

    @Transactional
    public void updateInventory(Long returnLogId) {
        ReturnLogEntity returnLogEntity = returnLogRepository.findById(returnLogId)
                .orElseThrow(() -> new RuntimeException("Import Log Id not found" + returnLogId));

        List<ReturnProductEntity> returnProductEntities = returnProductRepository.findReturnProductByLogId(returnLogId);
        for (ReturnProductEntity returnProductEntity : returnProductEntities) {
            Long productId = returnProductEntity.getProductId();
            Long batchId = returnProductEntity.getBatchId();
            Long warehouseID = returnLogEntity.getFromWarehouseId();
            int quantity = returnProductEntity.getQuantity();

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

    public HistoryPayResponseDto getHistoryPayRepository(Long logId) {
        HistoryPayEntity historyPayEntity = historyPayRepository.findById(logId)
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
        historyPayEntity.setType("OUT");
        return historyPayMapper.toHistoryPayDto(historyPayRepository.save(historyPayEntity));
    }

    @Transactional
    public ReturnLogResponseDto updateReturnLog(Long returnLogId, boolean status) {
        ReturnLogEntity returnLogEntity = returnLogRepository.findById(returnLogId)
                .orElseThrow(() -> new RuntimeException("return Log not found with id: " + returnLogId));

        returnLogEntity.setStatus(status);
        updateHistoryPay(returnLogId, status);
        return returnLogMapper.toReturnLogDto(returnLogRepository.save(returnLogEntity));
    }

    @Transactional
    public boolean checkReturnProduct(ReturnProductResponseDto dto) {
        int quantityReturn = dto.getQuantity();
        Long id = dto.getId();
        int quantityImport = importProductRepository.findImportProductById(id);
        return quantityReturn <= quantityImport;
    }
}
