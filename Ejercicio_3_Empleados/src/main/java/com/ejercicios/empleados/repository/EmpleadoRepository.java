package com.ejercicios.empleados.repository;

import com.ejercicios.empleados.model.Empleado;
import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository {
    Empleado guardar(Empleado empleado);
    List<Empleado> listar();
    Optional<Empleado> buscarPorId(Long id);
}
