package com.optima.inventory.service;

import com.optima.inventory.dto.response.ProductResponseDto;
import com.optima.inventory.dto.response.WarehouseResponseDto;
import com.optima.inventory.entity.ProductEntity;
import com.optima.inventory.entity.WarehouseEntity;
import com.optima.inventory.mapper.WarehouseMapper;
import com.optima.inventory.repository.WarehouseRepository;
import com.optima.inventory.utils.SnowflakeIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private WarehouseMapper warehouseMapper;

    private final Random random = new Random();

    @Transactional
    public WarehouseResponseDto createWarehouse(WarehouseResponseDto warehouseResponseDto) {
        WarehouseEntity warehouseEntity = warehouseMapper.toWarehouse(warehouseResponseDto);

        long newWarehouseId = SnowflakeIdGenerator.nextId();
        while (warehouseRepository.existsById(newWarehouseId)) {
            newWarehouseId = SnowflakeIdGenerator.nextId();
        }
        warehouseEntity.setId(newWarehouseId);
        warehouseEntity.setStatus(warehouseResponseDto.getStatusString().equalsIgnoreCase("active"));

        return warehouseMapper.toWarehouseResponseDto(warehouseRepository.save(warehouseEntity));
    }

    public List<WarehouseResponseDto> getWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(warehouseMapper::toWarehouseResponseDto)
                .collect(Collectors.toList());
    }


    public WarehouseEntity getWarehouse(long warehouseId) {
        return warehouseRepository.findById(warehouseId).orElseThrow(() -> new RuntimeException("Warehouse not found"));
    }

    @Transactional
    public WarehouseEntity updateWarehouse(long warehouseId, WarehouseResponseDto request) {
        WarehouseEntity warehouseEntity = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        warehouseMapper.updateWarehouseResponseDto(warehouseEntity, request);

        return warehouseRepository.save(warehouseEntity);
    }

    @Transactional
    public void deleteWarehouse(long warehouseId) {
        warehouseRepository.deleteById(warehouseId);
    }

    public Optional<WarehouseResponseDto> findRandWarehouse() {
        long count = warehouseRepository.count();
        if (count == 0) {
            return Optional.empty();
        }

        int randomIndex = random.nextInt((int) count);
        Page<WarehouseEntity> warehouse = warehouseRepository.findAll(PageRequest.of(randomIndex, 1));
        if (warehouse.hasContent()) {
            WarehouseEntity warehouseEntity = warehouse.getContent().get(0);
            return Optional.of(warehouseMapper.toWarehouseResponseDto(warehouseEntity));
        }
        return Optional.empty();
    }
}
