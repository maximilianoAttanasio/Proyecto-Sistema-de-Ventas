package com.mawi.sistemagestionventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "DTO de Rol, usado para exponer solo la información necesaria sin incluir las personas asociadas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolDTO {
    @Schema(description = "ID del Rol", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Integer idRol;
    @Schema(description = "Nombre del Rol", example = "Empleado")
    private String nombreRol;

}
