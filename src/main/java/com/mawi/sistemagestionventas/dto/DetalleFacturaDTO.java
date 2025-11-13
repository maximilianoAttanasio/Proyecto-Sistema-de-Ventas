package com.mawi.sistemagestionventas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleFacturaDTO {
    private String productoNombre;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}
