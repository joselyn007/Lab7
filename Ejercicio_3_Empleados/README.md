# Ejercicio 3: API de empleados

        Proyecto independiente en **Java 17 + Spring Boot 3.5.16 + Maven**. No utiliza base de datos: los registros viven en una lista en memoria y se pierden al reiniciar.

        ## Estructura por capas

        - `model/`: modelo `Empleado` (con `id`, `nombre`, `apellido`, `correo`, `especialidad` y `estado`).
        - `dto/`: entrada `CrearEmpleadoRequest` y validaciones Jakarta.
        - `controller/`: endpoints REST.
        - `service/`: lógica de negocio y filtro por especialidad.
        - `repository/`: lista `CopyOnWriteArrayList` e IDs `AtomicLong`.
        - `exception/`: respuestas 400 y 404.
        - `security/`: filtro de API Key (401 cuando falta o es incorrecta).
        - `src/test/`: tres pruebas unitarias con Mockito y siete pruebas de integración con MockMvc.

        ## Cómo ejecutarlo

        Requisitos: **JDK 17 o superior** y **Maven 3.6.3 o superior**.

        Dentro de esta carpeta ejecutá:

        ```bash
        mvn clean test
        mvn spring-boot:run
        ```

        El servidor queda en `http://localhost:8083`.

        Para desarrollo, la clave predeterminada es **`clave-local-demo`**. Cambiala mediante la variable de entorno `API_KEY` antes de usar la API fuera de un entorno local.

        Ejemplo de configuración (PowerShell):

        ```powershell
        $env:API_KEY="mi-clave-personal"
        mvn spring-boot:run
        ```

        ## Endpoints

        | Método | Ruta | Acción | HTTP |
        |---|---|---|---|
        | POST | `/api/empleados` | Crear registro | 201 Created |
        | GET | `/api/empleados` | Obtener todos | 200 OK |
        | GET | `/api/empleados?especialidad=Desarrollo` | Filtrar por especialidad (ignora mayúsculas) | 200 OK |
        | GET | `/api/empleados/1` | Buscar por ID | 200 OK / 404 Not Found |

        Todos los endpoints necesitan el encabezado `X-API-KEY`.

        ## Cuerpo para el POST

        ```json
        {
  "nombre": "María",
  "apellido": "García",
  "correo": "maria.garcia@ejemplo.com",
  "especialidad": "Desarrollo",
  "estado": "ACTIVO"
}
        ```

        El ID es autogenerado; **no** se manda en el POST.

        ## Probar con curl

        ```bash
        curl -i -X POST http://localhost:8083/api/empleados \
          -H 'Content-Type: application/json' \
          -H 'X-API-KEY: clave-local-demo' \
          -d '{"nombre": "María", "apellido": "García", "correo": "maria.garcia@ejemplo.com", "especialidad": "Desarrollo", "estado": "ACTIVO"}'

        curl -i -H 'X-API-KEY: clave-local-demo' http://localhost:8083/api/empleados
        curl -i -H 'X-API-KEY: clave-local-demo' 'http://localhost:8083/api/empleados?especialidad=Desarrollo'
        curl -i -H 'X-API-KEY: clave-local-demo' http://localhost:8083/api/empleados/1
        curl -i -H 'X-API-KEY: clave-local-demo' http://localhost:8083/api/empleados/9999
        ```

        ## Respuestas de error

        - `400 Bad Request`: nombre/apellido en blanco, correo inválido u otros campos obligatorios vacíos.
        - `401 Unauthorized`: falta el encabezado `X-API-KEY` o su valor no coincide.
        - `404 Not Found`: se consulta un ID no registrado.

        ## Pruebas

        `mvn test` ejecuta tanto pruebas unitarias del servicio (crear, encontrar y no encontrar) como pruebas de endpoints, validaciones y seguridad.
