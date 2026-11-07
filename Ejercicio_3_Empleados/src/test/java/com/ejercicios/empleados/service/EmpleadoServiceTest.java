package com.ejercicios.empleados.service;

import com.ejercicios.empleados.dto.CrearEmpleadoRequest;
import com.ejercicios.empleados.exception.RecursoNoEncontradoException;
import com.ejercicios.empleados.model.Empleado;
import com.ejercicios.empleados.repository.EmpleadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {
    @Mock
    private EmpleadoRepository repository;

    @InjectMocks
    private EmpleadoService service;

    @Test
    void crearEmpleado_guardaYDevuelveElRegistro() {
        CrearEmpleadoRequest request = new CrearEmpleadoRequest(
                "María", "García", "maria.garcia@ejemplo.com",
                "Desarrollo", "ACTIVO");
        Empleado guardado = new Empleado(1L, "María", "García",
                "maria.garcia@ejemplo.com", "Desarrollo", "ACTIVO");
        when(repository.guardar(any(Empleado.class))).thenReturn(guardado);

        Empleado resultado = service.crear(request);

        assertEquals(1L, resultado.id());
        assertEquals("María", resultado.nombre());
        assertEquals("Desarrollo", resultado.especialidad());
        verify(repository, times(1)).guardar(any(Empleado.class));
    }

    @Test
    void obtenerEmpleadoExistente_devuelveElRegistro() {
        Empleado existente = new Empleado(1L, "María", "García",
                "maria.garcia@ejemplo.com", "Desarrollo", "ACTIVO");
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(existente));

        Empleado resultado = service.obtenerPorId(1L);

        assertEquals(existente, resultado);
        verify(repository).buscarPorId(1L);
    }

    @Test
    void obtenerEmpleadoInexistente_lanzaExcepcion() {
        when(repository.buscarPorId(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> service.obtenerPorId(999L));
        verify(repository).buscarPorId(999L);
    }
}
