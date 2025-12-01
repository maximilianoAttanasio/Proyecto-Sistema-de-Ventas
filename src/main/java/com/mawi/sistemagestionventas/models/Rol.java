package com.mawi.sistemagestionventas.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Modelo de Rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRol;
    @Schema(description = "Nombre del rol", accessMode = Schema.AccessMode.READ_ONLY, example = "Empleado")
    private String nombreRol;
    @Schema(hidden = true)
    @OneToMany(mappedBy = "rol")
    @JsonManagedReference
    private List<Persona> personas = new ArrayList<>();
}
