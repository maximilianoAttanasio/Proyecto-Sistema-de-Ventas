package com.mawi.sistemagestionventas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPedido;
    @ManyToOne
    @JoinColumn(name = "id_cliente_persona")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "id_empleado_persona")
    private Empleado empleado;
    private LocalDate fechaPedido;

    @OneToOne(mappedBy = "pedido")
    @JsonIgnore
    private Factura factura;

    public Pedido() {
    }

    public Pedido(Cliente cliente, Empleado empleado, LocalDate fechaPedido) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.fechaPedido = fechaPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
}
