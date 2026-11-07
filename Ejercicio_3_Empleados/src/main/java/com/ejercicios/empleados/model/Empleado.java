package com.ejercicios.empleados.model;

// Modelo de respuesta: el ID se asigna al guardar en memoria.
public record Empleado(
        Long id,
        String nombre,
        String apellido,
        String correo,
        String especialidad,
        String estado
) { }
