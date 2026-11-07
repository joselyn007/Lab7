package com.ejercicios.proveedores.service;

import com.ejercicios.proveedores.dto.CrearProveedorRequest;
import com.ejercicios.proveedores.exception.RecursoNoEncontradoException;
import com.ejercicios.proveedores.model.Proveedor;
import com.ejercicios.proveedores.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorService {
    private final ProveedorRepository repository;

    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

    public Proveedor crear(CrearProveedorRequest request) {
        Proveedor nuevo = new Proveedor(
                null,
                request.nombre().trim(),
                request.apellido().trim(),
                request.correo().trim(),
                request.categoria().trim(),
                request.estado().trim()
        );
        return repository.guardar(nuevo);
    }

    public List<Proveedor> listar(String categoria) {
        List<Proveedor> todos = repository.listar();
        if (categoria == null || categoria.isBlank()) {
            return todos;
        }
        String buscada = categoria.trim();
        return todos.stream()
                .filter(p -> p.categoria().equalsIgnoreCase(buscada))
                .toList();
    }

    public Proveedor obtenerPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Proveedor con id " + id + " no encontrado"));
    }
}
