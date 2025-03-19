package com.technical.paylink.validators.responses;


import lombok.Data;

@Data
public class TransactionResponse {
    private Long id;
    private double value;
    private String cellPhone;
    private String message;
    private String transactionalID;
    private String supplierId;

}
