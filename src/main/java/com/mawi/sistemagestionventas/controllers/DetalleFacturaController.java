package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.dto.CrearDetalleDTO;
import com.mawi.sistemagestionventas.models.DetalleFactura;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.DetalleFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
public class DetalleFacturaController {

    @Autowired
    private DetalleFacturaService detalleService;

    @GetMapping
    public ResponseEntity<List<DetalleFactura>> getAllDetalles() {
        try {
            List<DetalleFactura> detalles = detalleService.findAll();
            return ResponseEntity.ok(detalles);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{detalleId}")
    public ResponseEntity<DetalleFactura> getDetalleById(@PathVariable Integer detalleId) {
        try {
            DetalleFactura detalle = detalleService.findById(detalleId);
            return ResponseEntity.ok(detalle);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDetalle(@RequestBody DetalleFactura detalle) {
        try {
            DetalleFactura nuevoDetalle = detalleService.save(detalle);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDetalle);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Detalle");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (IllegalStateException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Detalle");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/agregar-detalle")
    public ResponseEntity<DetalleFactura> agregarDetalle(@RequestBody CrearDetalleDTO dto) {
        DetalleFactura detalle = detalleService.agregarDetalle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalle);
    }

}
