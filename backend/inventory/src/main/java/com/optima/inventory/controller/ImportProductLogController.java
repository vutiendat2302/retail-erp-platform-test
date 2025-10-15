package com.optima.inventory.controller;

import com.optima.inventory.dto.response.*;
import com.optima.inventory.service.ImportProductLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/import-logs")
    public ResponseEntity<Page<ImportLogResponseDto>> getSearchAllIn4(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            Pageable pageable) {
        Boolean statusBool = null;
        if ("active".equalsIgnoreCase(status)) {
            statusBool = true;
        } else if ("inactive".equalsIgnoreCase(status)) {
            statusBool = false;
        }
        return ResponseEntity.ok(importProductLogService.getSearchAllIn4(search, statusBool, pageable));
    }

    @DeleteMapping("/deleteImport/{importId}")
    public String deleteImport(@PathVariable("importId") Long importId) {
        importProductLogService.deleteImport(importId);
        return "Import Log has been deleted";
    }


}
