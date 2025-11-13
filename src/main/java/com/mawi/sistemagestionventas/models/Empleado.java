package com.mawi.sistemagestionventas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empleado")
public class Empleado extends Persona {
    private double sueldo;
    private LocalDate fechaContratacion;
    @OneToMany(mappedBy = "empleado")
    @JsonIgnore
    private List<Pedido> pedidos = new ArrayList<>();

    public Empleado() {
    }

    public Empleado(String nombre, String apellido, int documento, String email, String telefono, String direccion, Rol rol, double sueldo, LocalDate fechaContratacion) {
        super(nombre, apellido, documento, email, telefono, direccion, rol);
        this.sueldo = sueldo;
        this.fechaContratacion = fechaContratacion;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
