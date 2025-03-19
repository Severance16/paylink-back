package com.technical.paylink.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Data
@Entity
@DynamicUpdate
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "El id no puede ser nula")
    private Long id;
    @NotNull(message = "El id de la transaccion no puede ser nula")
    private String transactionalID;

    @Column(name = "\"value\"")
    @NotNull(message = "El valor no puede ser nulo")
    private Double value;

    @NotNull(message = "El telefono no puede ser nulo")
    private String cellPhone;

    @NotNull(message = "El id del proveedor no puede ser nulo")
    private String supplierId;

    @NotNull(message = "El mensage no puede ser nulo")
    private String message;

    public void validateCellPhone(String cellPhone) {
        if (!cellPhone.startsWith("3")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El número de teléfono debe iniciar con '3'.");
        }
        if (cellPhone.length() != 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El número de teléfono debe tener una longitud de 10 caracteres.");
        }
        if (!cellPhone.matches("\\d+")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El número de teléfono solo puede contener caracteres numéricos.");
        }
    }

    public void validateValue(double value) {
        if (value < 1000 || value > 100000) {
            throw new IllegalArgumentException("El valor de la recarga debe estar entre 1000 y 100000.");
        }
    }
}

