package com.ejercicios.proveedores.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// DTO: datos enviados por el cliente (sin ID, porque se genera automáticamente).
public record CrearProveedorRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "El apellido es obligatorio") String apellido,
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe tener un formato válido") String correo,
        @NotBlank(message = "La categoria es obligatoria") String categoria,
        @NotBlank(message = "El estado es obligatorio") String estado
) { }
