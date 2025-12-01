package com.mawi.sistemagestionventas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Modelo de Empleado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleado")
public class Empleado extends Persona {
    @Schema(description = "Sueldo del Empleado", requiredMode = Schema.RequiredMode.REQUIRED, example = "150000.0")
    private double sueldo;
    @Schema(description = "Fecha Contrato del Empleado", accessMode = Schema.AccessMode.READ_ONLY, example = "2024-05-18")
    private LocalDate fechaContratacion;
    @Schema(hidden = true)
    @OneToMany(mappedBy = "empleado")
    @JsonIgnore
    private List<Pedido> pedidos = new ArrayList<>();
}
