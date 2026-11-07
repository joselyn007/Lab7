package com.ejercicios.proveedores.controller;

import com.ejercicios.proveedores.dto.CrearProveedorRequest;
import com.ejercicios.proveedores.model.Proveedor;
import com.ejercicios.proveedores.service.ProveedorService;
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
@RequestMapping("/api/proveedores")
public class ProveedorController {
    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Proveedor> crear(@Valid @RequestBody CrearProveedorRequest request) {
        Proveedor creado = service.crear(request);
        URI ubicacion = URI.create("/api/proveedores/" + creado.id());
        return ResponseEntity.created(ubicacion).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> listar(
            @RequestParam(required = false) String categoria) {
        return ResponseEntity.ok(service.listar(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
