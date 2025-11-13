package com.mawi.sistemagestionventas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearDetalleDTO {
    private Integer idFactura;
    private Integer idProducto;
    private int cantidad;
}
