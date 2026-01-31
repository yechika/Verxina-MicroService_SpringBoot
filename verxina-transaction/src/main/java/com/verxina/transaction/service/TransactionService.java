package com.verxina.transaction.service;

import com.verxina.common.dto.TransactionRequest;
import com.verxina.transaction.domain.Transaction;
import com.verxina.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    @Transactional
    public Transaction createTransaction(TransactionRequest request) {

        if (request.amount().signum() <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        Transaction transaction = Transaction.builder()
                .accountId(request.accountId())
                .amount(request.amount())
                .currency(request.currency())
                .status(Transaction.TransactionStatus.SUCCESS)
                .build();

        return repository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction with ID " + id + " not found"));
    }
}