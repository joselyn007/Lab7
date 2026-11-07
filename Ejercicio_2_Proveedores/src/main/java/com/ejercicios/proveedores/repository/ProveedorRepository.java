package com.ejercicios.proveedores.repository;

import com.ejercicios.proveedores.model.Proveedor;
import java.util.List;
import java.util.Optional;

public interface ProveedorRepository {
    Proveedor guardar(Proveedor proveedor);
    List<Proveedor> listar();
    Optional<Proveedor> buscarPorId(Long id);
}
