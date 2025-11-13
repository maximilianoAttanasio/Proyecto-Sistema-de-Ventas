package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.models.Cliente;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        try {
            List<Cliente> clientes = clienteService.findAll();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer clienteId) {
        try {
            Cliente cliente = clienteService.findById(clienteId);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = clienteService.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        } catch (IllegalStateException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Detalle");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<?> updateCliente(@PathVariable Integer clienteId, @RequestBody Cliente clienteActualizado) {
        try {
            Cliente cliente = clienteService.update(clienteId, clienteActualizado);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<?> deleteById(@PathVariable Integer clienteId) {
        try {
            clienteService.deleteById(clienteId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
