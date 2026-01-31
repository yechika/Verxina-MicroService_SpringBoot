package com.verxina.transaction.service;

import com.verxina.common.dto.TransactionRequest;
import com.verxina.common.exception.ResourceNotFoundException;
import com.verxina.transaction.domain.Transaction;
import com.verxina.transaction.producer.TransactionProducer;
import com.verxina.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final TransactionProducer producer;
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
        Transaction savedTransaction = repository.save(transaction);
        com.verxina.common.event.TransactionCreatedEvent event = new  com.verxina.common.event.TransactionCreatedEvent(
                savedTransaction.getId(),
                savedTransaction.getAccountId(),
                savedTransaction.getCurrency(),
                savedTransaction.getAmount(),
                savedTransaction.getStatus().name()
        );
        producer.sendTransactionEvent(event);
        return savedTransaction;
    }
    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction with ID " + id + " not found"));
    }

}