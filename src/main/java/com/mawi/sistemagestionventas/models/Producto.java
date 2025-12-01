package com.mawi.sistemagestionventas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Modelo de Producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {
    @Schema(description = "ID del Producto", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProducto;
    @Schema(description = "Nombre del Producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "Camisa")
    private String nombre;
    @Schema(description = "Precio del Producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "10.99")
    private double precio;
    @Schema(description = "Stock del Producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "15")
    private int stock;
    @Schema(hidden = true)
    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private List<DetalleFactura> detalles = new ArrayList<>();
}
