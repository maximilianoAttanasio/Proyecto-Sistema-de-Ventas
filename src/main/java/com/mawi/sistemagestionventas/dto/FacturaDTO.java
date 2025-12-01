package com.mawi.sistemagestionventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(description = "DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {
    @Schema(description = "ID de la Factura", accessMode = Schema.AccessMode.READ_ONLY, example = "2")
    private Integer idFactura;
    @Schema(description = "Fecha de Emisión de la Factura", example = "2025-10-29")
    private String fechaEmision;
    @Schema(description = "Total Calculado Sumando Totales de Todos los Detalles", example = "14000.0")
    private Double total;
    @Schema(description = "Nombre del Cliente Asociado", example = "Diego Altamirano")
    private String clienteNombre;
    @Schema(description = "Nombre del Empelado Asociado", example = "Mauricio Lucero")
    private String empleadoNombre;
    @Schema(description = "Lista de Detalles de Productos Incluidos en la Facutura")
    private List<DetalleFacturaDTO> detalles;
}
