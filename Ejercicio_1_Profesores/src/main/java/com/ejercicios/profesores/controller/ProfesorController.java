package com.ejercicios.profesores.controller;

import com.ejercicios.profesores.dto.CrearProfesorRequest;
import com.ejercicios.profesores.model.Profesor;
import com.ejercicios.profesores.service.ProfesorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {
    private final ProfesorService service;

    public ProfesorController(ProfesorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Profesor> crear(@Valid @RequestBody CrearProfesorRequest request) {
        Profesor creado = service.crear(request);
        URI ubicacion = URI.create("/api/profesores/" + creado.id());
        return ResponseEntity.created(ubicacion).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<Profesor>> listar(
            @RequestParam(required = false) String especialidad) {
        return ResponseEntity.ok(service.listar(especialidad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
