package com.ejercicios.profesores.service;

import com.ejercicios.profesores.dto.CrearProfesorRequest;
import com.ejercicios.profesores.exception.RecursoNoEncontradoException;
import com.ejercicios.profesores.model.Profesor;
import com.ejercicios.profesores.repository.ProfesorRepository;
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
class ProfesorServiceTest {
    @Mock
    private ProfesorRepository repository;

    @InjectMocks
    private ProfesorService service;

    @Test
    void crearProfesor_guardaYDevuelveElRegistro() {
        CrearProfesorRequest request = new CrearProfesorRequest(
                "Ana", "López", "ana.lopez@ejemplo.com",
                "Matemáticas", "ACTIVO");
        Profesor guardado = new Profesor(1L, "Ana", "López",
                "ana.lopez@ejemplo.com", "Matemáticas", "ACTIVO");
        when(repository.guardar(any(Profesor.class))).thenReturn(guardado);

        Profesor resultado = service.crear(request);

        assertEquals(1L, resultado.id());
        assertEquals("Ana", resultado.nombre());
        assertEquals("Matemáticas", resultado.especialidad());
        verify(repository, times(1)).guardar(any(Profesor.class));
    }

    @Test
    void obtenerProfesorExistente_devuelveElRegistro() {
        Profesor existente = new Profesor(1L, "Ana", "López",
                "ana.lopez@ejemplo.com", "Matemáticas", "ACTIVO");
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(existente));

        Profesor resultado = service.obtenerPorId(1L);

        assertEquals(existente, resultado);
        verify(repository).buscarPorId(1L);
    }

    @Test
    void obtenerProfesorInexistente_lanzaExcepcion() {
        when(repository.buscarPorId(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> service.obtenerPorId(999L));
        verify(repository).buscarPorId(999L);
    }
}
