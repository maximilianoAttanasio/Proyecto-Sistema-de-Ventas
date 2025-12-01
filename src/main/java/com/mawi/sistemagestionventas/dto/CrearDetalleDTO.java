package com.mawi.sistemagestionventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "DTO para Crear los Detalles de una Factura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearDetalleDTO {
    @Schema(description = "ID de la Factura", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer idFactura;
    @Schema(description = "ID del Producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer idProducto;
    @Schema(description = "Cantidad de Artículos", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private int cantidad;
}
