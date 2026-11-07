package com.ejercicios.profesores.repository;

import com.ejercicios.profesores.model.Profesor;
import java.util.List;
import java.util.Optional;

public interface ProfesorRepository {
    Profesor guardar(Profesor profesor);
    List<Profesor> listar();
    Optional<Profesor> buscarPorId(Long id);
}
