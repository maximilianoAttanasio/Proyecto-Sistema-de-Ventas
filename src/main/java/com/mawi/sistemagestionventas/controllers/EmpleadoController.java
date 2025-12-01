package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.models.Empleado;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.EmpleadoService;
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

@Tag(name = "Gestión de Empleados", description = "Endpoints para Gestionar Empleados")
@RestController
@RequestMapping("api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Operation(summary = "Obtener la Lista de Todos los Empleados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Empleados Obtenida Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Empleado.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))})
    })
    @GetMapping
    public ResponseEntity<?> getAllEmpleados() {
        try {
            List<Empleado> empleados = empleadoService.findAll();
            return ResponseEntity.ok(empleados);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Obtener un Empleado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado Obtenido Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Empleado.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Empleado no Encontrado",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))})
    })
    @GetMapping("/{empleadoId}")
    public ResponseEntity<?> getEmpleadoById(
            @Parameter(description = "Identificador del Empleado", example = "1", required = true)
            @PathVariable Integer empleadoId) {
        try {
            Empleado empleado = empleadoService.findById(empleadoId);
            return ResponseEntity.ok(empleado);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Empleado no Encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Crear un Empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado Creado Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Empleado.class))}),
            @ApiResponse(responseCode = "409", description = "Error, Conflicto en los Datos Enviados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping("/create")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del Empleado a Crear", required = true, content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                    name = "Creación del Empleado",
                    value = "{\"nombre\":\"Clara\",\"apellido\":\"Rosales\",\"documento\":\"40121521\",\"email\":\"clara@correo.com\",\"telefono\":\"2612325236\",\"direccion\":\"1.048596\",\"sueldo\":\"350000\",\"rol\":{\"idRol\":1}}"
            )
        )
    )
    public ResponseEntity<?> createEmpleado(@RequestBody Empleado empleado) {
        try {
            Empleado nuevoEmpleado = empleadoService.save(empleado);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
        } catch (IllegalStateException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Alguno de los Datos Genera Conflictos");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Actualizar un Empleado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado Actualizado Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Empleado.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Empleado no Encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PutMapping("/{empleadoId}")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del Empleado a Actualizar", required = true, content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                    name = "Actualización del Empleado",
                    value = "{\"nombre\":\"Clara\",\"apellido\":\"Rosales\",\"documento\":\"40121521\",\"email\":\"clara@correo.com\",\"telefono\":\"2612325236\",\"direccion\":\"1.048596\",\"sueldo\":\"350000\",\"rol\":{\"idRol\":1}}"
            )
        )
    )
    public ResponseEntity<?> updateEmpleado(
            @Parameter(description = "Identificador del Empleado", example = "1", required = true)
            @PathVariable Integer empleadoId,
            @RequestBody Empleado empleadoActualizado) {
        try {
            Empleado empleado = empleadoService.update(empleadoId, empleadoActualizado);
            return ResponseEntity.ok(empleado);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Empleado no Encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Eliminar un Empleado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empleado Eliminado Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Empleado.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Empleado no encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @DeleteMapping("/{empleadoId}")
    public ResponseEntity<?> deleteEmpleado(
            @Parameter(description = "Identificador del Empleado", example = "1", required = true)
            @PathVariable Integer empleadoId) {
        try {
            empleadoService.deleteById(empleadoId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Empleado no Encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
