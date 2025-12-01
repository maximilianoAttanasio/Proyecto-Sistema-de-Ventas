package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.models.Producto;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "Gestión de Productos", description = "Endpoints para Gestionar Productos")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Obtener la Lista de Todos los Productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Productos Obtenida Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping
    public ResponseEntity<?> getAllProductos() {
        try {
            List<Producto> productos = productoService.findAll();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Obtener un Producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto Obtenido Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Producto no Encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{productoId}")
    public ResponseEntity<?> getProductoById(
            @Parameter(description = "Identificador del Producto", example = "1", required = true)
            @PathVariable Integer productoId) {
        try {
            Producto producto = productoService.findById(productoId);
            return ResponseEntity.ok(producto);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Producto no Encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Crear un Producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto Creado Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))}),
            @ApiResponse(responseCode = "409", description = "Error, Conflicto en los Datos Enviados",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/create")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del Producto a Crear", required = true, content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                    name = "Creación del Producto",
                    value = "{\"nombre\":\"Camisa\",\"precio\":\"1000\",\"stock\":\"25\"}"
            )
        )
    )
    public ResponseEntity<?> createProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.save(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (IllegalStateException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Alguno de los Datos Genera Conflictos");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Actualizar un Producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto Actualizado Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Producto no Encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/{productoId}")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del Producto a Actualizar", required = true, content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                    name = "Actualización del Producto",
                    value = "{\"nombre\":\"Camisa\",\"precio\":\"1000\",\"stock\":\"25\"}"
            )
        )
    )
    public ResponseEntity<?> updateProducto(
            @Parameter(description = "Identificador del Producto", example = "1", required = true)
            @PathVariable Integer productoId,
            @RequestBody Producto productoActualizado) {
        try {
            Producto producto = productoService.update(productoId, productoActualizado);
            return ResponseEntity.ok(producto);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Producto no Encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(summary = "Eliminar un Producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto Eliminado Correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))}),
            @ApiResponse(responseCode = "404", description = "Error, Producto no encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/{productoId}")
    public ResponseEntity<?> deleteProducto(
            @Parameter(description = "Identificador del Cliente", example = "1", required = true)
            @PathVariable Integer productoId) {
        try {
            productoService.deleteById(productoId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Producto no Encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Error Interno del Servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
