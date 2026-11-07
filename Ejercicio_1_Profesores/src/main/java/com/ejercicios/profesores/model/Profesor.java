package com.ejercicios.profesores.model;

// Modelo de respuesta: el ID se asigna al guardar en memoria.
public record Profesor(
        Long id,
        String nombre,
        String apellido,
        String correo,
        String especialidad,
        String estado
) { }
