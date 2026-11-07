package com.ejercicios.empleados.repository;

import com.ejercicios.empleados.model.Empleado;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EmpleadoRepositoryEnMemoria implements EmpleadoRepository {
    // Lista en memoria, segura para lecturas concurrentes.
    private final List<Empleado> empleados = new CopyOnWriteArrayList<>();
    private final AtomicLong contadorIds = new AtomicLong(0);

    @Override
    public Empleado guardar(Empleado empleado) {
        Empleado creado = new Empleado(
                contadorIds.incrementAndGet(),
                empleado.nombre(),
                empleado.apellido(),
                empleado.correo(),
                empleado.especialidad(),
                empleado.estado()
        );
        empleados.add(creado);
        return creado;
    }

    @Override
    public List<Empleado> listar() {
        return List.copyOf(empleados);
    }

    @Override
    public Optional<Empleado> buscarPorId(Long id) {
        return empleados.stream().filter(p -> p.id().equals(id)).findFirst();
    }
}
