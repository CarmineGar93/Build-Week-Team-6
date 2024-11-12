package Team6.Build_Week_Team_6.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record FatturaDTO(
        @NotNull(message = "La data è obbligatoria")
        LocalDate data,

        @NotNull(message = "L'importo è obbligatorio")
        @DecimalMin(value = "0.0", message = "L'importo deve essere maggiore di 0")
        double importo,

        @NotNull(message = "L'ID del cliente è obbligatorio")
        UUID clienteId
) {
}