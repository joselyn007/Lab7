package com.ejercicios.profesores.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    private static final String CABECERA = "X-API-KEY";
    private final String apiKeyEsperada;

    public ApiKeyFilter(@Value("${app.api-key}") String apiKeyEsperada) {
        this.apiKeyEsperada = apiKeyEsperada;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String ruta = request.getRequestURI()
            .substring(request.getContextPath().length());

    return !ruta.startsWith("/api/");
}

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        String recibida = request.getHeader(CABECERA);
        if (recibida == null || apiKeyEsperada.isBlank()
                || !MessageDigest.isEqual(
                        apiKeyEsperada.getBytes(StandardCharsets.UTF_8),
                        recibida.getBytes(StandardCharsets.UTF_8))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                    "{\"codigo\":401,\"mensaje\":\"API Key inválida o ausente\"}");
            return;
        }
        chain.doFilter(request, response);
    }
}
