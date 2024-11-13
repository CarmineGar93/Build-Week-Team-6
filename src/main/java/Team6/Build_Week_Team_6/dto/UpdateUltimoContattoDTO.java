package Team6.Build_Week_Team_6.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateUltimoContattoDTO(
        @NotNull(message = "La data deve essere inserita")
        String ultimoContatto) {
}
