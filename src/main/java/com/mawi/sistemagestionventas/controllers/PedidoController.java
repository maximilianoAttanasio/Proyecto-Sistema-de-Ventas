package com.mawi.sistemagestionventas.controllers;

import com.mawi.sistemagestionventas.models.Pedido;
import com.mawi.sistemagestionventas.responses.ErrorResponse;
import com.mawi.sistemagestionventas.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        try {
            List<Pedido> pedidos = pedidoService.findAll();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Integer pedidoId) {
        try {
            Pedido pedido = pedidoService.findById(pedidoId);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPedido(@RequestBody Pedido nuevoPedido) {
        try {
            Pedido pedido = pedidoService.save(nuevoPedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        } catch (IllegalStateException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage(), "Detalle");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}