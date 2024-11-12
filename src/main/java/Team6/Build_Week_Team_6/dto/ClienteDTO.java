package Team6.Build_Week_Team_6.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record ClienteDTO(
        @NotEmpty(message = "La ragione sociale deve essere fornita")
        String ragioneSociale,
        @NotNull(message = "Partita Iva obbligatoria")
        @Pattern(regexp = "^[A-Za-z]{2,4}(?=.{2,12}$)[-_\s0-9]*(?:[a-zA-Z][-_\s0-9]*){0,2}$")
        String partitaIva,
        @NotNull(message = "L'email deve essere fornita")
        @Email(message = "Email non valida")
        String email,
        @NotNull(message = "La pec deve essere fornita")
        @Email(message = "Email non valida")
        String pec,
        @NotNull(message = "Telefono obbligatorio")
        @Pattern(regexp = "(0{1}[1-9]{1,3})[\\s|\\.|\\-]?(\\d{4,})")
        String telefono,
        @NotNull(message = "L'email contatto deve essere fornita")
        @Email(message = "Email non valida")
        String emailContatto,
        @NotEmpty(message = "Nome contatto obbligatorio")
        @Size(min = 2, message = "Il nome deve avere almeno di due caratteri")
        String nomeContatto,
        @NotEmpty(message = "Nome contatto obbligatorio")
        @Size(min = 2, message = "Il nome deve avere almeno di due caratteri")
        String cognomeContatto,
        @NotNull(message = "Telefono contatto obbligatorio")
        @Pattern(regexp = "(0{1}[1-9]{1,3})[\\s|\\.|\\-]?(\\d{4,})")
        String telefonoContatto,
        @NotNull(message = "Indirizzo sede legale obbligatorio")
        UUID indirizzoSedeLegale,
        @NotNull(message = "Indirizzo sede organizzativa obbligatorio")
        UUID indirizzoSedeOperativa,
        @NotNull(message = "Il tipo cliente deve essere fornito")
        @Pattern(regexp = "SPA|SRL|PA|SAS|SNC|SSR", message = "Tipo cliente non valido")
        String tipoCliente

) {
}
