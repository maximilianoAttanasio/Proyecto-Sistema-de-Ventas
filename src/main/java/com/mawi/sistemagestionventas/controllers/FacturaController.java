package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.models.Factura;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<Factura>> getAllFacturas() {
        try {
            List<Factura> facturas = facturaService.findAll();
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{facturaId}")
    public ResponseEntity<Factura> getFacturaById(@PathVariable Integer facturaId) {
        try {
            Factura factura = facturaService.findById(facturaId);
            return ResponseEntity.ok(factura);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFactura(@RequestBody Factura nuevaFactura) {
        try {
            Factura factura = facturaService.save(nuevaFactura);
            return ResponseEntity.status(HttpStatus.CREATED).body(factura);
        } catch (IllegalArgumentException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Detalle");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{facturaId}/detalles")
    public ResponseEntity<?> getFacturaConDetalles(@PathVariable Integer facturaId) {
        try {
            var facturaDTO = facturaService.obtenerFacturaConDetalles(facturaId);
            return ResponseEntity.ok(facturaDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage(), "No se encontró la factura"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("Error interno", e.getMessage()));
        }
    }

}
