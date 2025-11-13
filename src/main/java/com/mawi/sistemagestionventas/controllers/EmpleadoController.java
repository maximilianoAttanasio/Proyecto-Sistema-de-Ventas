package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.models.Empleado;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<Empleado>> getAllEmpleados() {
        try {
            List<Empleado> empleados = empleadoService.findAll();
            return ResponseEntity.ok(empleados);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{empleadoId}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Integer empleadoId) {
        try {
            Empleado empleado = empleadoService.findById(empleadoId);
            return ResponseEntity.ok(empleado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEmpleado(@RequestBody Empleado empleado) {
        try {
            Empleado nuevoEmpleado = empleadoService.save(empleado);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
        } catch (IllegalStateException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Detalle");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{empleadoId}")
    public ResponseEntity<?> updateEmpleado(@PathVariable Integer empleadoId, @RequestBody Empleado empleadoActualizado) {
        try {
            Empleado empleado = empleadoService.update(empleadoId, empleadoActualizado);
            return ResponseEntity.ok(empleado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{empleadoId}")
    public ResponseEntity<?> deleteEmpleado(@PathVariable Integer empleadoId) {
        try {
            empleadoService.deleteById(empleadoId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
