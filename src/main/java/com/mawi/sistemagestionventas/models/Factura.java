package com.mawi.sistemagestionventas.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Modelo de Factura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factura")
public class Factura {
    @Schema(description = "ID de la Factura", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFactura;
    @Schema(description = "Pedido Asociado",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "{\"idPedido\": 1}")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPedido")
    private Pedido pedido;
    @Schema(hidden = true)
    @OneToMany(mappedBy = "factura")
    private List<DetalleFactura> detalles = new ArrayList<>();
}
