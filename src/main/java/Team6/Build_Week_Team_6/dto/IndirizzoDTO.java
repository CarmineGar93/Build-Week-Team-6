package Team6.Build_Week_Team_6.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record IndirizzoDTO(
        @NotEmpty(message = "La via è obbligatoria")
        @Size(min = 2, max = 100, message = "La via deve essere compresa tra 2 e 100 caratteri")
        String via,

        @NotNull(message = "Il civico è obbligatorio")
        @Min(value = 1, message = "Il civico deve essere maggiore di 0")
        int civico,

        @NotEmpty(message = "La località è obbligatoria")
        @Size(min = 2, max = 100, message = "La località deve essere compresa tra 2 e 100 caratteri")
        String localita,

        @NotNull(message = "Il CAP è obbligatorio")
        @Min(value = 0, message = "Il CAP deve essere positivo")
        @Max(value = 99999, message = "Il CAP non può superare 5 cifre")
        int cap,

        @NotNull(message = "L'ID del comune è obbligatorio")
        UUID comuneId
) {
}