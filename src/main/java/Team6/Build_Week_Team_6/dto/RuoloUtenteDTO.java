package Team6.Build_Week_Team_6.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RuoloUtenteDTO(@NotEmpty(message = "Il nome del ruolo è obbligatorio")
                             @Size(min = 2, max = 50, message = "Il nome del ruolo deve essere compreso tra 2 e 50 " +
                                     "caratteri")
                             String nome) {
}