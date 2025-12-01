package com.mawi.sistemagestionventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO que Representa la Respuesta de la API Externa")
@Data
public class TimeResponseDTO {

    @Schema(description = "Año Actual Devuelto", accessMode = Schema.AccessMode.READ_ONLY, example = "2025")
    private int year;
    @Schema(description = "Mes Actual (1-12) Devuelto", accessMode = Schema.AccessMode.READ_ONLY, example = "11")
    private int month;
    @Schema(description = "Día Actual (1-31) Devuelto", accessMode = Schema.AccessMode.READ_ONLY, example = "29")
    private int day;
    @Schema(description = "Hora Actual (0-23) Devuelto", accessMode = Schema.AccessMode.READ_ONLY, example = "11")
    private int hour;
    @Schema(description = "Minuto Actual (0-59) Devuelto", accessMode = Schema.AccessMode.READ_ONLY, example = "47")
    private int minute;
    @Schema(description = "Segundo Actual (0-59) Devuelto", accessMode = Schema.AccessMode.READ_ONLY, example = "32")
    private int seconds;
}
