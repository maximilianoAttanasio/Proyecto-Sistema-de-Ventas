package com.mawi.sistemagestionventas.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Modelo de Persona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
    @Schema(description = "ID de Persona", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPersona;
    @Schema(description = "Nombre de la Persona", requiredMode = Schema.RequiredMode.REQUIRED, example = "Max")
    private String nombre;
    @Schema(description = "Apellido de la Persona", requiredMode = Schema.RequiredMode.REQUIRED, example = "A")
    private String apellido;
    @Schema(description = "Documento de la Persona", requiredMode = Schema.RequiredMode.REQUIRED, example = "40972817")
    private int documento;
    @Schema(description = "Email de la Persona", requiredMode = Schema.RequiredMode.REQUIRED, example = "max@test.com")
    private String email;
    @Schema(description = "Teléfono de la Persona", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234567890")
    private String telefono;
    @Schema(description = "Dirección de la Persona", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.048596 World Line")
    private String direccion;
    @Schema(description = "Rol de la Persona", requiredMode = Schema.RequiredMode.REQUIRED, example = "{\"idRol\": 1}")
    @ManyToOne
    @JoinColumn(name = "idRol")
    @JsonBackReference
    private Rol rol;
}
