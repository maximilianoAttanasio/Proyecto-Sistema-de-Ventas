package com.mawi.sistemagestionventas.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Response Personalizado para Errores en General")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @Schema(description = "Mensaje de Error", example = "Error", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;
    @Schema(description = "Detalle de Error", example = "Descripción del Error")
    private String details;
}
