package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.models.Cliente;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.ClienteService;
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

@Tag(name = "Gestión de Clientes", description = "Endpoints para Gestionar Clientes")
@RestController
@RequestMapping("api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Obtener la Lista de Todos los Clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Clientes Obtenida Correctamente",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Cliente.class)))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping
    public ResponseEntity<?> getAllClientes() {
        try {
            List<Cliente> clientes = clienteService.findAll();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Obtener un Cliente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente Obtenido Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Cliente no Encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{clienteId}")
    public ResponseEntity<?> getClienteById(
            @Parameter(description = "Identificador del Cliente", example = "1", required = true)
            @PathVariable Integer clienteId) {
        try {
            Cliente cliente = clienteService.findById(clienteId);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Cliente no Encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Crear un Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente Creado Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "409", description = "Error, Conflicto en los Datos Enviados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/create")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del Cliente a Crear", required = true, content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                    name = "Creación del Cliente",
                    value = "{\"nombre\":\"Clara\",\"apellido\":\"Rosales\",\"documento\":40121521,\"email\":\"clara@correo.com\",\"telefono\":\"2612325236\",\"direccion\":\"1.048596\",\"rol\":{\"idRol\":2}}"
            )
        )
    )
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = clienteService.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        } catch (IllegalStateException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Alguno de los Datos Genera Conflictos");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Actualizar un Cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente Actualizado Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Cliente no Encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/{clienteId}")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del Cliente a Actualizar", required = true, content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                    name = "Actualización del Cliente",
                    value = "{\"nombre\":\"Clara\",\"apellido\":\"Rosales\",\"documento\":40121521,\"email\":\"clara@correo.com\",\"telefono\":\"2612325236\",\"direccion\":\"1.048596\",\"rol\":{\"idRol\":2}}"
            )
        )
    )

    public ResponseEntity<?> updateCliente(
            @Parameter(description = "Identificador del Cliente", example = "1", required = true)
            @PathVariable Integer clienteId,
            @RequestBody Cliente clienteActualizado) {
        try {
            Cliente cliente = clienteService.update(clienteId, clienteActualizado);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Cliente no Encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Eliminar un Cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente Eliminado Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Cliente no encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/{clienteId}")
    public ResponseEntity<?> deleteById(
            @Parameter(description = "Identificador del Cliente", example = "1", required = true)
            @PathVariable Integer clienteId) {
        try {
            clienteService.deleteById(clienteId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Cliente no Encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
