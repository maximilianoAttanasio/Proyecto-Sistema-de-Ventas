package com.mawi.sistemagestionventas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente extends Persona {
    private LocalDate fechaRegistro;
    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, int documento, String email, String telefono, String direccion, Rol rol, LocalDate fechaRegistro) {
        super(nombre, apellido, documento, email, telefono, direccion, rol);
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
