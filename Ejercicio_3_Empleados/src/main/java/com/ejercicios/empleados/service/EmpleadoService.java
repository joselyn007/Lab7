package com.ejercicios.empleados.service;

import com.ejercicios.empleados.dto.CrearEmpleadoRequest;
import com.ejercicios.empleados.exception.RecursoNoEncontradoException;
import com.ejercicios.empleados.model.Empleado;
import com.ejercicios.empleados.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpleadoService {
    private final EmpleadoRepository repository;

    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    public Empleado crear(CrearEmpleadoRequest request) {
        Empleado nuevo = new Empleado(
                null,
                request.nombre().trim(),
                request.apellido().trim(),
                request.correo().trim(),
                request.especialidad().trim(),
                request.estado().trim()
        );
        return repository.guardar(nuevo);
    }

    public List<Empleado> listar(String especialidad) {
        List<Empleado> todos = repository.listar();
        if (especialidad == null || especialidad.isBlank()) {
            return todos;
        }
        String buscada = especialidad.trim();
        return todos.stream()
                .filter(p -> p.especialidad().equalsIgnoreCase(buscada))
                .toList();
    }

    public Empleado obtenerPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Empleado con id " + id + " no encontrado"));
    }
}
