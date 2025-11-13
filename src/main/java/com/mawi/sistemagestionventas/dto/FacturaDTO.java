package com.mawi.sistemagestionventas.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FacturaDTO {
    private Integer idFactura;
    private String fechaEmision;
    private Double total;
    private String clienteNombre;
    private String empleadoNombre;
    private List<DetalleFacturaDTO> detalles;
}
