package com.optima.inventory.controller;

import com.optima.inventory.dto.response.*;
import com.optima.inventory.repository.ExportProductRepository;
import com.optima.inventory.service.ExportProductLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/exportProductLog")
public class ExportProductLogController {
    @Autowired
    private ExportProductLogService exportProductLogService;

    @GetMapping("/getExportLog/{id}")
    public ExportLogResponseDto getExportLog(@PathVariable Long id) {
        return exportProductLogService.getExportLog(id);
    }

    @PostMapping("/createExportLog")
    public ExportLogResponseDto createExportLogDto(@RequestBody ExportProductLogResponseDto exportProductLogResponseDto) {
        return exportProductLogService.createExportLogDto(exportProductLogResponseDto);
    }

    @PutMapping("/updateExportLog/{exportLogId}")
    public ExportLogResponseDto updateExportLog(@PathVariable Long exportLogId,
                                                @RequestParam String status) {
        boolean statusFinal = "active".equalsIgnoreCase(status.trim());
        return exportProductLogService.updateExportLog(exportLogId, statusFinal);
    }

    @GetMapping("/getExportProductList/{logId}")
    public List<ExportProductResponseDto> getExportProduct(@PathVariable Long logId) {
        return exportProductLogService.getExportProduct(logId);
    }

    @GetMapping("/checkExportProduct/{warehouseId}")
    public boolean checkExportProduct(@PathVariable Long warehouseId, @RequestBody @Valid ExportProductResponseDto exportProductResponseDto) {
        return exportProductLogService.checkExportProduct(warehouseId, exportProductResponseDto);
    }

    @PutMapping("/updateInventory/{logId}")
    public void updateInventory(@PathVariable Long logId) {
        exportProductLogService.updateInventory(logId);
    }

    @PutMapping("/updateStoreProduct/{logId}")
    public void updateStoreProduct(@PathVariable Long logId) {
        exportProductLogService.updateStoreProduct(logId);
    }
}
