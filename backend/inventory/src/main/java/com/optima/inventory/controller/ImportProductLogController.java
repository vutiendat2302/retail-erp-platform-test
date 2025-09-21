package com.optima.inventory.controller;

import com.optima.inventory.dto.response.HistoryPayResponseDto;
import com.optima.inventory.dto.response.ImportLogResponseDto;
import com.optima.inventory.dto.response.ImportProductLogResponseDto;
import com.optima.inventory.dto.response.ImportProductResponseDto;
import com.optima.inventory.entity.ImportLogEntity;
import com.optima.inventory.service.ImportProductLogService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/importProductLog")
public class ImportProductLogController {
    @Autowired
    private ImportProductLogService importProductLogService;


    @GetMapping("/getHistoryPay/{logId}")
    public HistoryPayResponseDto getHistoryPay(@PathVariable Long logId) {
        return importProductLogService.getHistoryPayRepository(logId);
    }

    @GetMapping("/getImportLog/{id}")
    public ImportLogResponseDto getImportLog(@PathVariable Long id) {
        return importProductLogService.getImportLog(id);
    }

    @PostMapping("/createImportLog")
    public ImportLogResponseDto createImportLogDto(@RequestBody ImportProductLogResponseDto importProductLogResponseDto) {
        return importProductLogService.createImportLogDto(importProductLogResponseDto);
    }

    @PutMapping("/updateImportLog/{importLogId}")
    public ImportLogResponseDto updateImportLog(@PathVariable Long importLogId,
                                                @RequestParam String status) {
        boolean statusFinal = "active".equalsIgnoreCase(status.trim());
        return importProductLogService.updateImportLog(importLogId, statusFinal);
    }

    @GetMapping("/getImportProductList/{logId}")
    public List<ImportProductResponseDto> getImportProduct(@PathVariable Long logId) {
        return importProductLogService.getImportProduct(logId);
    }

    @PostMapping("/createHistoryPay/{logId}")
    public HistoryPayResponseDto createHistoryPay(@PathVariable Long logId, @RequestBody @Valid HistoryPayResponseDto historyPayResponseDto) {
        return importProductLogService.createHistoryPay(logId, historyPayResponseDto);
    }
}
