package com.example.InvesmentPortfolio.Controller;
import com.example.InvesmentPortfolio.Model.Assest;
import com.example.InvesmentPortfolio.Service.AssestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assest")
public class AssestController {
    private final AssestService assestService;
    @GetMapping("/get")
    public ResponseEntity getAllAssests() {
        return ResponseEntity.status(200).body(assestService.getAllAssest());
    }

    @PostMapping("/add/{id}")
    public ResponseEntity addAssest(@PathVariable int id,@Valid @RequestBody Assest assest) {
        assestService.addAssest(id,assest);
        return ResponseEntity.status(201).body("assest added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateAssest(@Valid @RequestBody Assest assest, @PathVariable int id) {
        assestService.updateAssest(id, assest);
        return ResponseEntity.status(200).body("assest updated successfully");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAssest(@PathVariable int id) {
        assestService.deleteAssest(id);
        return ResponseEntity.status(200).body("assest deleted successfully");
    }
    @GetMapping("/compare/{asset_id1}/{asset_id2}")
    public ResponseEntity compareAssets(@PathVariable Integer asset_id1, @PathVariable Integer asset_id2) {
        String comparisonResult = assestService.compareAssets(asset_id1, asset_id2);
        return ResponseEntity.ok(comparisonResult);
    }

    @PostMapping("/trends/{userId}")
    public ResponseEntity postMarketTrends(@PathVariable int userId, @RequestBody List<Map<String, String>> trends) {
        if (userId != 1) { // Only allow admin (user with id = 1) to post trends
            return ResponseEntity.status(403).body("Access denied. Only admin can post market trends.");
        }
        assestService.postMarketTrends(trends);
        return ResponseEntity.ok("Market trends posted successfully");
    }

    @GetMapping("/trends")
    public ResponseEntity getMarketTrends() {
        List<Map<String, String>> trends = assestService.getMarketTrends();
        Map<String, List<Map<String, String>>> response = new HashMap<>();
        response.put("trends", trends);
        return ResponseEntity.ok(response);
    }


}
