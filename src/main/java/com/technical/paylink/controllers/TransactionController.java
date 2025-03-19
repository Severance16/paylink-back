
package com.technical.paylink.controllers;

import com.technical.paylink.models.TransactionHistory;
import com.technical.paylink.respositories.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;
    TransactionHistory transactionHistory = new TransactionHistory();

    @GetMapping()
    public List<TransactionHistory> findAll() {
        return transactionHistoryRepository.findAll().reversed();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionHistory> get(@PathVariable Long id) {
        TransactionHistory transaction = transactionHistoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transacción no encontrada"));
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionHistory> put(@PathVariable Long id, @RequestBody TransactionHistory input) {
        return transactionHistoryRepository.findById(id)
                .map(existingTransaction -> {

                    if (input.getValue() != null) {
                        transactionHistory.validateValue(input.getValue());
                        existingTransaction.setValue(input.getValue());
                    }
                    if (input.getCellPhone() != null) {
                        transactionHistory.validateCellPhone(input.getCellPhone());
                        existingTransaction.setCellPhone(input.getCellPhone());
                    }
                    if (input.getMessage() != null) {
                        existingTransaction.setMessage(input.getMessage());
                    }
                    if (input.getSupplierId() != null) {
                        existingTransaction.setSupplierId(input.getSupplierId());
                    }

                    return ResponseEntity.ok(transactionHistoryRepository.save(existingTransaction));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transacción no encontrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!transactionHistoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transacción no encontrada");
        }
        transactionHistoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
