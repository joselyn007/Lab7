package com.ejercicios.proveedores.model;

// Modelo de respuesta: el ID se asigna al guardar en memoria.
public record Proveedor(
        Long id,
        String nombre,
        String apellido,
        String correo,
        String categoria,
        String estado
) { }
