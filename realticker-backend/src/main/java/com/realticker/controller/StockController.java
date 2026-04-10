package com.realticker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realticker.dto.AnalysisResponse;
import com.realticker.model.Stock;
import com.realticker.service.StockService;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin("*")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping("/top10")
    public List<Stock> getTop10Stocks() {
        return service.getTop10Stocks();
    }

    @GetMapping("/{ticker}/history")
    public List<Double> getStockHistory(@PathVariable String ticker) {
        return service.getStockHistory(ticker);
    }

    @PostMapping("/{ticker}/analyze")
    public AnalysisResponse analyzeStock(@PathVariable String ticker) {
        return service.analyzeStock(ticker);
    }
}