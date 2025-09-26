package com.optima.inventory.service;

import com.optima.inventory.dto.request.SupplierRequestDto;
import com.optima.inventory.dto.response.ProductResponseDto;
import com.optima.inventory.dto.response.SupplierResponseDto;
import com.optima.inventory.entity.ProductEntity;
import com.optima.inventory.entity.SupplierEntity;
import com.optima.inventory.mapper.SupplierMapper;
import com.optima.inventory.repository.SupplierRepository;
import com.optima.inventory.utils.SnowflakeIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SupplierMapper supplierMapper;

    @Transactional
    public SupplierResponseDto createSupplier(SupplierResponseDto supplierResponseDto) {
        SupplierEntity supplierEntity = supplierMapper.toSupplier(supplierResponseDto);

        long newSupplierId = SnowflakeIdGenerator.nextId();
        while (supplierRepository.existsById(newSupplierId)) {
            newSupplierId = SnowflakeIdGenerator.nextId();
        }
        supplierEntity.setId(newSupplierId);

        return supplierMapper.toSupplierResponseDto(supplierRepository.save(supplierEntity));
    }

    public SupplierResponseDto getSupplier(Long supplierId) {
        return supplierMapper.toSupplierResponseDto(supplierRepository.findById(supplierId).orElseThrow(() -> new RuntimeException("Supplier not found")));
    }

    @Transactional
    public SupplierResponseDto updateSupplier(Long supplierId, SupplierResponseDto supplierResponseDto) {
        SupplierEntity supplierEntity = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        supplierMapper.updateSupplier(supplierEntity, supplierResponseDto);
        SupplierEntity afterUpdateSupplier = supplierRepository.save(supplierEntity);

        return supplierMapper.toSupplierResponseDto(afterUpdateSupplier);
    }

    @Transactional
    public void deleteSupplier(Long supplierId) {
        supplierRepository.deleteById(supplierId);
    }

    public Page<SupplierResponseDto> getSearchAllIn4(String search, Boolean status, Pageable pageable) {
        return supplierRepository.getSearchAllIn4(search, status, pageable).map(supplierMapper::fromProjection);
    }

    public Optional<SupplierResponseDto> findRandSupplier() {
        Random random = new Random();
        long count = supplierRepository.count();
        if (count == 0) {
            return Optional.empty();
        }

        int randomIndex = random.nextInt((int) count);
        Page<SupplierEntity> supplierResponseDtoPage = supplierRepository.findAll(PageRequest.of(randomIndex, 1));
        if (supplierResponseDtoPage.hasContent()) {
            SupplierEntity randomSupplier = supplierResponseDtoPage.getContent().get(0);
            return Optional.of(supplierMapper.toSupplierResponseDto(randomSupplier));
        }
        return Optional.empty();
    }

    public List<SupplierResponseDto> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toSupplierResponseDto)
                .collect(Collectors.toList());
    }
}
