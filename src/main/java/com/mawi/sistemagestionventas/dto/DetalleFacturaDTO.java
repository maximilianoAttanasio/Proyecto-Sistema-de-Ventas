package com.mawi.sistemagestionventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "DTO para Asignar los Detalles a una Factura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFacturaDTO {
    @Schema(description = "Nombre del Producto", example = "Zapatos")
    private String productoNombre;
    @Schema(description = "Cantidad Facturada del Producto", example = "2")
    private Integer cantidad;
    @Schema(description = "Precio Unitario del Producto al Momento de Facturar", example = "7000.0")
    private Double precioUnitario;
    @Schema(description = "Subtotal Calculado", example = "14000.0")
    private Double subtotal;
}
