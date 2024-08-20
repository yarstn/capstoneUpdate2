package com.example.InvesmentPortfolio.Controller;
import com.example.InvesmentPortfolio.Model.Assest;
import com.example.InvesmentPortfolio.Model.Transactions;

import com.example.InvesmentPortfolio.Service.TransactionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionsService transactionsService;

    @GetMapping("/get")
    public ResponseEntity getAllTransactions() {
        return ResponseEntity.ok(transactionsService.getAllTransactions());
    }

    @PostMapping("/add")
    public ResponseEntity addTransaction(@Valid @RequestBody Transactions transactions) {

        try {
            transactionsService.addTransaction(transactions);
            return ResponseEntity.status(201).body("Transaction added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/transactions/charge-or-complete")
    public ResponseEntity<List<Transactions>> getChargeOrCompleteTransactions(@PathVariable int userId) {
        List<Transactions> transactions = transactionsService.getChargeOrCompleteTransactions(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{userId}/transactions/report")
    public ResponseEntity getTransactionReport(@PathVariable Integer userId) {
        Map<String, Object> report = transactionsService.getTransactionReport(userId);
        return ResponseEntity.ok(report);
    }
}

//        @GetMapping("/user/{userId}/assets")
//        public ResponseEntity getUserAssets(@PathVariable Integer userId) {
//            List<Assest> assets = transactionsService.getAllAssetsByUserId(userId);
//            if (assets.isEmpty()) {
//                return ResponseEntity.noContent().build();
//            }
//            return ResponseEntity.ok(assets);
//        }
//    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity updateTransaction(@PathVariable int id, @Valid @RequestBody Transactions transactions,Errors errors) {
//        if (errors.hasErrors()) {
//            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
//            return ResponseEntity.badRequest().body(errorMessage);
//        }
//        transactionsService.updateTransaction(id, transactions);
//        return ResponseEntity.status(200).body("transaction successfully updated");
//    }
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity deleteTransaction(@PathVariable int id) {
//        transactionsService.deleteTransaction(id);
//        return ResponseEntity.status(200).body("transaction successfully deleted");
//    }

