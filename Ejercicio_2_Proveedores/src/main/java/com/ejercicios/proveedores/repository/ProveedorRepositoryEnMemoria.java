package com.ejercicios.proveedores.repository;

import com.ejercicios.proveedores.model.Proveedor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProveedorRepositoryEnMemoria implements ProveedorRepository {
    // Lista en memoria, segura para lecturas concurrentes.
    private final List<Proveedor> proveedores = new CopyOnWriteArrayList<>();
    private final AtomicLong contadorIds = new AtomicLong(0);

    @Override
    public Proveedor guardar(Proveedor proveedor) {
        Proveedor creado = new Proveedor(
                contadorIds.incrementAndGet(),
                proveedor.nombre(),
                proveedor.apellido(),
                proveedor.correo(),
                proveedor.categoria(),
                proveedor.estado()
        );
        proveedores.add(creado);
        return creado;
    }

    @Override
    public List<Proveedor> listar() {
        return List.copyOf(proveedores);
    }

    @Override
    public Optional<Proveedor> buscarPorId(Long id) {
        return proveedores.stream().filter(p -> p.id().equals(id)).findFirst();
    }
}
