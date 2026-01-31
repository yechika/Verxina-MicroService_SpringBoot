package com.verxina.transaction.controller;

import com.verxina.common.dto.TransactionRequest;
import com.verxina.transaction.domain.Transaction;
import com.verxina.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    // ini buat POST
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionRequest request){
        Transaction savedTransaction = service.createTransaction(request);
        return ResponseEntity.ok(savedTransaction);
    }
    // ini GET yang all
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(service.getAllTransactions());
    }
    // ini GET per id
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        return ResponseEntity.ok(service.getTransactionById(id));
    }
}