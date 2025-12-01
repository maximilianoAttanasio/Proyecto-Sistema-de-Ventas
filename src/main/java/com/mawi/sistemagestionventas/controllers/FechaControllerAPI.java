package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.dto.TimeResponseDTO;
import com.mawi.sistemagestionventas.services.FechaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Fecha API", description = "Obtiene Fecha Actual, con o sin hora, desde Servicio Externo")
@RestController
@RequestMapping("/api/fecha")
public class FechaControllerAPI {
    @Autowired
    private FechaService fechaService;

    @Operation(summary = "Obtener Solo la Fecha Actual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fecha Obtenida Correctamente",
            content = {@Content(mediaType = "text/plain", examples = @ExampleObject("2025-11-29"))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
    })
    @GetMapping("/solo-fecha")
    public ResponseEntity<String> getFecha() {
        TimeResponseDTO fecha = fechaService.obtenerFechaYHoraActual();
        String message = String.format(
                "%d-%02d-%02d",
                fecha.getYear(),
                fecha.getMonth(),
                fecha.getDay()
        );
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Obtener Fecha y Hora Actual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fecha y Hora Obtenidas Correctamente",
            content = {@Content(mediaType = "text/plain", examples = @ExampleObject("2025-11-29 11:47"))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
    })
    @GetMapping("/fecha-hora")
    public ResponseEntity<String> getFechaYHora() {
        TimeResponseDTO fechaHora = fechaService.obtenerFechaYHoraActual();
        String message = String.format(
                "%d-%02d-%02d %02d:%02d",
                fechaHora.getYear(),
                fechaHora.getMonth(),
                fechaHora.getDay(),
                fechaHora.getHour(),
                fechaHora.getMinute()
        );
        return ResponseEntity.ok(message);
    }


}
