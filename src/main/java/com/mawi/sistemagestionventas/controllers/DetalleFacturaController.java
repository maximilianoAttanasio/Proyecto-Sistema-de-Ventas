package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.dto.CrearDetalleDTO;
import com.mawi.sistemagestionventas.models.DetalleFactura;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.DetalleFacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gestión de Detalles de Facturas", description = "Endpoints para Gestionar los Detalles de las Facturas")
@RestController
@RequestMapping("/api/detalles")
public class DetalleFacturaController {

    @Autowired
    private DetalleFacturaService detalleService;

    @Operation(summary = "Agregar un producto a una factura como detalle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Detalle de Factura Agregado Correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DetalleFactura.class))),
            @ApiResponse(responseCode = "400", description = "Error, Datos Inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Error, Conflicto en los Datos Enviados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/agregar-detalle")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Producto a Agregar como Detalle", required = true, content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                    name = "Agregar Detalle a Factura",
                    value = "{\"idFactura\":2, \"idProducto\":3, \"cantidad\":1}"
            )
        )
    )
    public ResponseEntity<?> agregarDetalle(@RequestBody CrearDetalleDTO dto) {
        try {
            DetalleFactura detalle = detalleService.agregarDetalle(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(detalle);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Datos Inválidos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (IllegalStateException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Alguno de los Datos Genera Conflictos");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
