package com.example.InvesmentPortfolio.Controller;

import com.example.InvesmentPortfolio.Model.Portfolio;
import com.example.InvesmentPortfolio.Service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @GetMapping("/get")
    public ResponseEntity getPortfolio() {
        return ResponseEntity.status(200).body(portfolioService.getAllPortfolio());
    }
    @PostMapping("/add")
    public ResponseEntity addPortfolio(@Valid @RequestBody Portfolio portfolio) {
        portfolioService.add(portfolio);
        return ResponseEntity.status(201).body("portfolio added successfully");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updatePortfolio(@PathVariable Integer id,@Valid @RequestBody Portfolio portfolio) {
portfolioService.updatePortfolio(id, portfolio);
        return ResponseEntity.status(201).body("portfolio updated successfully");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePortfolio(@PathVariable Integer id) {
        portfolioService.deletePortfolio(id);
        return ResponseEntity.status(201).body("portfolio deleted successfully");
    }
    @GetMapping("/{portfolioId}/asset-ratios")
    public ResponseEntity getAssetRatios(@PathVariable Integer portfolioId) {
        Map<String, Double> assetRatios = portfolioService.calculateAssetRatios(portfolioId);
        return ResponseEntity.ok(assetRatios);
    }


}
