package com.technical.paylink.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Auth {
    @NotNull(message = "El usuario no puede ser nulo")
    @Size(min = 1, message = "El usuario no puede estar vacío")
    private String user;

    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
}
