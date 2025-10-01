package com.ejemplo.banco.banco2025.service;

import com.ejemplo.banco.banco2025.DTO.TransactionDTO;
import com.ejemplo.banco.banco2025.entity.Customer;
import com.ejemplo.banco.banco2025.entity.Transaction;
import com.ejemplo.banco.banco2025.repository.CustomerRepository;
import com.ejemplo.banco.banco2025.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public TransactionDTO transferMoney(TransactionDTO transactionDTO) {
        // Validar que los numeros de cuenta no sean nulos
        if (transactionDTO.getSenderAccountNumber() == null || transactionDTO.getReceiverAccountNumber() == null) {
            throw new IllegalArgumentException("Los números de cuenta del remitente y receptor son obligatorios");
        }

        Customer sender = customerRepository.findByAccountNumber(transactionDTO.getSenderAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("La cuenta del remitente no existe."));
        Customer receiver = customerRepository.findByAccountNumber(transactionDTO.getReceiverAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("La cuenta del receptor no existe."));

        // Validar que el remitente tenga saldo suficiente
        if (sender.getBalance() < transactionDTO.getAmount()) {
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta del remitente.");
        }

        // Realizar la transferencia
        sender.setBalance(sender.getBalance() - transactionDTO.getAmount());
        receiver.setBalance(receiver.getBalance() + transactionDTO.getAmount());

        // Guardar los cambios en las cuentas
        customerRepository.save(sender);
        customerRepository.save(receiver);

        // Crear y guardar la transacción
        Transaction transaction = new Transaction();
        transaction.setSenderAccountNumber(sender.getAccountNumber());
        transaction.setReceiverAccountNumber(receiver.getAccountNumber());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());

        transaction = transactionRepository.save(transaction);

        // Devolver la transacción creada como DTO
        return mapToDTO(transaction);
    }

    // ✅ Obtener todas las transacciones
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Obtener transacción por ID
    public TransactionDTO getTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.map(this::mapToDTO).orElse(null);
    }

    // ✅ Obtener transacciones de una cuenta (envía o recibe)
    public List<TransactionDTO> getTransactionsForAccount(String accountNumber) {
        List<Transaction> transactions;

        // Si accountNumber es null → devolver todas
        if (accountNumber == null) {
            transactions = transactionRepository.findAll();
        } else {
            transactions = transactionRepository.findBySenderAccountNumberOrReceiverAccountNumber(accountNumber, accountNumber);
        }

        return transactions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // 🔹 Método privado para mapear Transaction → TransactionDTO
    private TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setSenderAccountNumber(transaction.getSenderAccountNumber());
        dto.setReceiverAccountNumber(transaction.getReceiverAccountNumber());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        return dto;
    }
}
