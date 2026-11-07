package com.ejercicios.proveedores.service;

import com.ejercicios.proveedores.dto.CrearProveedorRequest;
import com.ejercicios.proveedores.exception.RecursoNoEncontradoException;
import com.ejercicios.proveedores.model.Proveedor;
import com.ejercicios.proveedores.repository.ProveedorRepository;
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
class ProveedorServiceTest {
    @Mock
    private ProveedorRepository repository;

    @InjectMocks
    private ProveedorService service;

    @Test
    void crearProveedor_guardaYDevuelveElRegistro() {
        CrearProveedorRequest request = new CrearProveedorRequest(
                "Carlos", "Pérez", "carlos.perez@ejemplo.com",
                "Tecnología", "ACTIVO");
        Proveedor guardado = new Proveedor(1L, "Carlos", "Pérez",
                "carlos.perez@ejemplo.com", "Tecnología", "ACTIVO");
        when(repository.guardar(any(Proveedor.class))).thenReturn(guardado);

        Proveedor resultado = service.crear(request);

        assertEquals(1L, resultado.id());
        assertEquals("Carlos", resultado.nombre());
        assertEquals("Tecnología", resultado.categoria());
        verify(repository, times(1)).guardar(any(Proveedor.class));
    }

    @Test
    void obtenerProveedorExistente_devuelveElRegistro() {
        Proveedor existente = new Proveedor(1L, "Carlos", "Pérez",
                "carlos.perez@ejemplo.com", "Tecnología", "ACTIVO");
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(existente));

        Proveedor resultado = service.obtenerPorId(1L);

        assertEquals(existente, resultado);
        verify(repository).buscarPorId(1L);
    }

    @Test
    void obtenerProveedorInexistente_lanzaExcepcion() {
        when(repository.buscarPorId(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> service.obtenerPorId(999L));
        verify(repository).buscarPorId(999L);
    }
}
