package com.mawi.sistemagestionventas.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFactura;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPedido")
    private Pedido pedido;
    @OneToMany(mappedBy = "factura")
    private List<DetalleFactura> detalles = new ArrayList<>();

    public Factura() {
    }

    public Factura(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }
}
