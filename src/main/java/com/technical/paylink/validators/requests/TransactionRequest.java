package com.technical.paylink.validators.requests;

import com.technical.paylink.models.TransactionHistory;
import lombok.Data;

@Data
public class TransactionRequest {
    private double value;
    private String cellPhone;
    private String supplierId;


    public void updateFrom(TransactionHistory other) {
        this.cellPhone = other.getCellPhone();
        this.supplierId = other.getSupplierId();
        this.value = other.getValue();
    }
}
