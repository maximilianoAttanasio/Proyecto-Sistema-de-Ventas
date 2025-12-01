package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.models.Pedido;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.PedidoService;
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

@Tag(name = "Gestión de Pedidos", description = "Endpoints para Gestionar Pedidos")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Obtener la Lista de Todos los Pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Pedidos Obtenida Correctamente",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Pedido.class)))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping
    public ResponseEntity<?> getAllPedidos() {
        try {
            List<Pedido> pedidos = pedidoService.findAll();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Obtener un Pedido por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido Obtenido Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Pedido no Encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{pedidoId}")
    public ResponseEntity<?> getPedidoById(
            @Parameter(description = "Identificador del Pedido", example = "1", required = true)
            @PathVariable Integer pedidoId) {
        try {
            Pedido pedido = pedidoService.findById(pedidoId);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Pedido no Encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Crear un Pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido Creado Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class))}),
            @ApiResponse(responseCode = "409", description = "Error, Conflicto en los Datos Enviados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/create")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del Pedido a Crear", required = true, content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                    name = "Creación del Pedido",
                    value = "{\"cliente\":{\"idPersona\":4}, \"empleado\":{\"idPersona\":8}}"
            )
    )
    )
    public ResponseEntity<?> createPedido(@RequestBody Pedido nuevoPedido) {
        try {
            Pedido pedido = pedidoService.save(nuevoPedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        } catch (IllegalStateException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Alguno de los Datos Genera Conflictos");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}