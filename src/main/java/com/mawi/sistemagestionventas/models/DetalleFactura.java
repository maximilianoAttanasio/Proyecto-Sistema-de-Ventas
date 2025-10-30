package com.mawi.sistemagestionventas.models;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_factura")
public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetalleFactura;
    @ManyToOne
    @JoinColumn(name = "idFactura")
    private Factura factura;
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public DetalleFactura() {
    }

    public DetalleFactura(Factura factura, Producto producto, int cantidad, double precioUnitario) {
        this.factura = factura;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getIdDetalleFactura() {
        return idDetalleFactura;
    }

    public void setIdDetalleFactura(int idDetalleFactura) {
        this.idDetalleFactura = idDetalleFactura;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
