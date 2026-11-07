package com.ejercicios.empleados.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "app.api-key=clave-prueba")
@AutoConfigureMockMvc
class EmpleadoApiIntegrationTest {
    private static final String BASE = "/api/empleados";
    private static final String CLAVE = "clave-prueba";

    @Autowired
    private MockMvc mvc;

    @Test
    void crear_regresa201YLocation() throws Exception {
        mvc.perform(post(BASE)
                        .header("X-API-KEY", CLAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValido()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nombre").value("María"));
    }

    @Test
    void obtenerExistente_regresa200() throws Exception {
        MvcResult creado = mvc.perform(post(BASE)
                        .header("X-API-KEY", CLAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValido()))
                .andExpect(status().isCreated())
                .andReturn();
        String ubicacion = creado.getResponse().getHeader("Location");
        org.junit.jupiter.api.Assertions.assertNotNull(ubicacion);

        mvc.perform(get(ubicacion).header("X-API-KEY", CLAVE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("maria.garcia@ejemplo.com"));
    }

    @Test
    void obtenerInexistente_regresa404() throws Exception {
        mvc.perform(get(BASE + "/999999").header("X-API-KEY", CLAVE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value(404));
    }

    @Test
    void sinApiKey_regresa401() throws Exception {
        mvc.perform(get(BASE))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.codigo").value(401));
    }

    @Test
    void apiKeyIncorrecta_regresa401() throws Exception {
        mvc.perform(get(BASE).header("X-API-KEY", "incorrecta"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void datosInvalidos_regresa400() throws Exception {
        String json = "{\"nombre\":\" \",\"apellido\":\"\",\"correo\":\"sin-arroba\",\"especialidad\":\"Desarrollo\",\"estado\":\"ACTIVO\"}";
        mvc.perform(post(BASE)
                        .header("X-API-KEY", CLAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.nombre").exists())
                .andExpect(jsonPath("$.errores.apellido").exists())
                .andExpect(jsonPath("$.errores.correo").exists());
    }

    @Test
    void filtroPorEspecialidad_soloRegresaCoincidencias() throws Exception {
        mvc.perform(post(BASE)
                        .header("X-API-KEY", CLAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValido()))
                .andExpect(status().isCreated());

        mvc.perform(get(BASE)
                        .header("X-API-KEY", CLAVE)
                        .param("especialidad", "DESARROLLO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.especialidad == 'Desarrollo')]").isNotEmpty());
    }

    private String jsonValido() {
        return "{\"nombre\":\"María\",\"apellido\":\"García\",\"correo\":\"maria.garcia@ejemplo.com\",\"especialidad\":\"Desarrollo\",\"estado\":\"ACTIVO\"}";
    }
}
