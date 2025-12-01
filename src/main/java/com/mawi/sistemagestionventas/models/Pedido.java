package com.mawi.sistemagestionventas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {
    @Schema(description = "ID del Pedido", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPedido;
    @Schema(description = "Cliente Asociado",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "{\"idPersona\": 2}\"")
    @ManyToOne
    @JoinColumn(name = "id_cliente_persona")
    private Cliente cliente;
    @Schema(description = "Empleado Asociado",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "{\"idPersona\": 1}\"")
    @ManyToOne
    @JoinColumn(name = "id_empleado_persona")
    private Empleado empleado;
    @Schema(description = "Fecha del Pedido", accessMode = Schema.AccessMode.READ_ONLY, example = "2025-11-13")
    private LocalDateTime fechaPedido;
    @OneToOne(mappedBy = "pedido")
    @JsonIgnore
    private Factura factura;
}
