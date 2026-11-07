package com.ejercicios.profesores.service;

import com.ejercicios.profesores.dto.CrearProfesorRequest;
import com.ejercicios.profesores.exception.RecursoNoEncontradoException;
import com.ejercicios.profesores.model.Profesor;
import com.ejercicios.profesores.repository.ProfesorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProfesorService {
    private final ProfesorRepository repository;

    public ProfesorService(ProfesorRepository repository) {
        this.repository = repository;
    }

    public Profesor crear(CrearProfesorRequest request) {
        Profesor nuevo = new Profesor(
                null,
                request.nombre().trim(),
                request.apellido().trim(),
                request.correo().trim(),
                request.especialidad().trim(),
                request.estado().trim()
        );
        return repository.guardar(nuevo);
    }

    public List<Profesor> listar(String especialidad) {
        List<Profesor> todos = repository.listar();
        if (especialidad == null || especialidad.isBlank()) {
            return todos;
        }
        String buscada = especialidad.trim();
        return todos.stream()
                .filter(p -> p.especialidad().equalsIgnoreCase(buscada))
                .toList();
    }

    public Profesor obtenerPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Profesor con id " + id + " no encontrado"));
    }
}
