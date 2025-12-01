package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.dto.FacturaDTO;
import com.mawi.sistemagestionventas.models.Factura;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.List;

@Tag(name = "Gestión de Facturas", description = "Endpoints para Gestionar Facturas")
@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Operation(summary = "Obtener la Lista de Todas las Facturas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Facturas Obtenida Correctamente",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Factura.class)))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping
    public ResponseEntity<?> getAllFacturas() {
        try {
            List<Factura> facturas = facturaService.findAll();
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Obtener una Factura por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura Obtenida Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Factura.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Factura no Encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{facturaId}")
    public ResponseEntity<?> getFacturaById(
            @Parameter(description = "Identificador de la Factura", example = "1", required = true)
            @PathVariable Integer facturaId) {
        try {
            Factura factura = facturaService.findById(facturaId);
            return ResponseEntity.ok(factura);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Factura no Encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Crear una Factura Asociada a un Pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Factura Creada Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Factura.class))}),
            @ApiResponse(responseCode = "409", description = "Error, Conflicto en los Datos Enviados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/create")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la Factura a Crear", required = true, content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                    name = "Creación de la Factura",
                    value = "{\"pedido\": {\"idPedido\": 1}}"
            )
    )
    )
    public ResponseEntity<?> createFactura(@RequestBody Factura nuevaFactura) {
        try {
            Factura factura = facturaService.save(nuevaFactura);
            return ResponseEntity.status(HttpStatus.CREATED).body(factura);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Alguno de los Datos Genera Conflictos");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Obtener una Factura por su ID con Detalles Reducidos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Factura obtenida correctamente con sus detalles",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacturaDTO.class),
                            examples = @ExampleObject(
                                    name = "Factura con Detalles",
                                    value = "{ \"idFactura\": 1, \"fechaEmision\": \"2025-10-29\", \"total\": 12000.0, \"clienteNombre\": \"Diego Altamirano\", \"empleadoNombre\": \"Mauricio Lucero\", \"detalles\": [ { \"productoNombre\": \"Camisa\", \"cantidad\": 2, \"precioUnitario\": 3000.0, \"subtotal\": 6000.0 }, { \"productoNombre\": \"Pantalón\", \"cantidad\": 1, \"precioUnitario\": 5000.0, \"subtotal\": 5000.0 } ] }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Error, Factura no Encontrada",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{facturaId}/detalles")
    public ResponseEntity<?> getFacturaConDetalles(@PathVariable Integer facturaId) {
        try {
            var facturaDTO = facturaService.obtenerFacturaConDetalles(facturaId);
            return ResponseEntity.ok(facturaDTO);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Factura no Encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
