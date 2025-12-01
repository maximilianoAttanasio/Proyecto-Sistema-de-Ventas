package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.dto.RolDTO;
import com.mawi.sistemagestionventas.models.Rol;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gestión de Roles", description = "Endpoints para Gestionar Roles")
@RestController
@RequestMapping("api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @Operation(summary = "Obtener la Lista de Todos los Roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Roles Obtenida Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))})
    })
    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        try {
            List<RolDTO> rolesDTO = rolService.obtenerRolesDTO();
            return ResponseEntity.ok(rolesDTO);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Obtener un Rol por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol Obtenido Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Rol no Encontrado",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))})
    })
    @GetMapping("/{rolId}")
    public ResponseEntity<?> getRolById(
            @Parameter(description = "Identificador del Rol", example = "1", required = true)
            @PathVariable Integer rolId) {
        try {
            Rol rol = rolService.findById(rolId);
            return ResponseEntity.ok(rol);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Rol no Encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Crear un Rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol Creado Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))}),
            @ApiResponse(responseCode = "409", description = "Error, Datos Inválidos",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping("/create")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del Rol a Crear", required = true, content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                    name = "Creación del Rol",
                    value = "{\"nombreRol\":\"Admin\"}"
            )
    ))
    public ResponseEntity<?> createRol(@RequestBody Rol rol) {
        try {
            Rol nuevoRol = rolService.save(rol);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRol);
        } catch (IllegalStateException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Datos Inválidos");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Actualizar un Rol por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol Actualizado Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Rol no Encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PutMapping("/{rolId}")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del Rol a Actualizar", required = true, content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                    name = "Actualización del Rol",
                    value = "{\"nombreRol\":\"VENDEDOR\"}"
            )
    ))
    public ResponseEntity<?> updateRol(
            @Parameter(description = "Identificador del Rol", example = "1", required = true)
            @PathVariable Integer rolId,
            @RequestBody Rol rolActualizado) {
        try {
            Rol rol = rolService.update(rolId, rolActualizado);
            return ResponseEntity.ok(rol);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Rol no Encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
