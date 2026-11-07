package com.ejercicios.profesores.repository;

import com.ejercicios.profesores.model.Profesor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProfesorRepositoryEnMemoria implements ProfesorRepository {
    // Lista en memoria, segura para lecturas concurrentes.
    private final List<Profesor> profesores = new CopyOnWriteArrayList<>();
    private final AtomicLong contadorIds = new AtomicLong(0);

    @Override
    public Profesor guardar(Profesor profesor) {
        Profesor creado = new Profesor(
                contadorIds.incrementAndGet(),
                profesor.nombre(),
                profesor.apellido(),
                profesor.correo(),
                profesor.especialidad(),
                profesor.estado()
        );
        profesores.add(creado);
        return creado;
    }

    @Override
    public List<Profesor> listar() {
        return List.copyOf(profesores);
    }

    @Override
    public Optional<Profesor> buscarPorId(Long id) {
        return profesores.stream().filter(p -> p.id().equals(id)).findFirst();
    }
}
