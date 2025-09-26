package com.optima.inventory.controller;

import com.optima.inventory.dto.response.*;
import com.optima.inventory.service.ReturnProductLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/returnProductLog")
public class ReturnProductLogController {
    @Autowired
    private ReturnProductLogService returnProductLogService;

    @GetMapping("/getHistoryPay/{logId}")
    public HistoryPayResponseDto getHistoryPay(@PathVariable Long logId) {
        return returnProductLogService.getHistoryPayRepository(logId);
    }

    @GetMapping("/getReturnLog/{id}")
    public ReturnLogResponseDto getReturnLog(@PathVariable Long id) {
        return returnProductLogService.getReturnLog(id);
    }

    @PostMapping("/createReturnLog")
    public ReturnLogResponseDto createReturnProductLogDto(@RequestBody ReturnProductLogResponseDto returnProductLogResponseDto) {
        return returnProductLogService.createReturnLogDto(returnProductLogResponseDto);
    }

    @PutMapping("/updateReturnLog/{returnLogId}")
    public ReturnLogResponseDto updateReturnLog(@PathVariable Long returnLogId,
                                                @RequestParam String status) {
        boolean statusFinal = "active".equalsIgnoreCase(status.trim());
        return returnProductLogService.updateReturnLog(returnLogId, statusFinal);
    }

    @GetMapping("/getReturnProductList/{logId}")
    public List<ReturnProductResponseDto> getReturnProduct(@PathVariable Long logId) {
        return returnProductLogService.getReturnProduct(logId);
    }

    @PostMapping("/createHistoryPay/{logId}")
    public HistoryPayResponseDto createHistoryPay(@PathVariable Long logId, @RequestBody @Valid HistoryPayResponseDto historyPayResponseDto) {
        return returnProductLogService.createHistoryPay(logId, historyPayResponseDto);
    }

    @GetMapping("/checkReturnProduct")
    public boolean checkReturnProduct(@RequestBody @Valid ReturnProductResponseDto returnProductResponseDtoList) {
        return returnProductLogService.checkReturnProduct(returnProductResponseDtoList);
    }

    @PutMapping("/updateInventory/{logId}")
    public void updateInventory(@PathVariable Long logId) {
        returnProductLogService.updateInventory(logId);
    }
}
