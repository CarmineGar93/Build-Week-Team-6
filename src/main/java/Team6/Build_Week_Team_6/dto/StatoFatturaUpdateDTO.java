package Team6.Build_Week_Team_6.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StatoFatturaUpdateDTO(
        @NotNull(message = "L'ID dello stato fattura è obbligatorio")
        UUID statoFatturaId
) {
}