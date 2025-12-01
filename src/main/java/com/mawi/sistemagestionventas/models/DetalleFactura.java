package com.mawi.sistemagestionventas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Modelo de Detalle Factura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_factura")
public class DetalleFactura {
    @Schema(description = "ID del Detalle", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetalleFactura;
    @Schema(hidden = true)
    @ManyToOne
    @JoinColumn(name = "idFactura")
    @JsonIgnore
    private Factura factura;
    @Schema(description = "Producto Asociado",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "{\"idProducto\": 3}")
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;
    @Schema(description = "Cantidad incluida en los detalles", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private int cantidad;
    @Schema(description = "Precio unitario aplicado en el momento de la facturación", requiredMode = Schema.RequiredMode.REQUIRED, example = "7000.0")
    private double precioUnitario;
}
