package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.models.Producto;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        try {
            List<Producto> productos = productoService.findAll();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{productoId}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Integer productoId) {
        try {
            Producto producto = productoService.findById(productoId);
            return ResponseEntity.ok(producto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.save(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (IllegalStateException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Conflicto");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<?> updateProducto(@PathVariable Integer productoId, @RequestBody Producto productoActualizado) {
        try {
            Producto producto = productoService.update(productoId, productoActualizado);
            return ResponseEntity.ok(producto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{productoId}")
    public ResponseEntity<?> deleteProducto(@PathVariable Integer productoId) {
        try {
            productoService.deleteById(productoId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
