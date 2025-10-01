package com.ejemplo.banco.banco2025.controller;

import com.ejemplo.banco.banco2025.DTO.TransactionDTO;
import com.ejemplo.banco.banco2025.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Obtener todas las transacciones
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    // Obtener transacción por ID
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        TransactionDTO transaction = transactionService.getTransactionById(id);
        return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.notFound().build();
    }

    // Obtener historial de transacciones por número de cuenta
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccount(@PathVariable String accountNumber) {
        List<TransactionDTO> transactions = transactionService.getTransactionsForAccount(accountNumber);
        return ResponseEntity.ok(transactions);
    }

    // Crear una nueva transacción (transferencia de dinero)
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            TransactionDTO savedTransaction = transactionService.transferMoney(transactionDTO);
            return ResponseEntity.ok(savedTransaction);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

