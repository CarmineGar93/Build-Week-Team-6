package Team6.Build_Week_Team_6.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UtenteLoginDTO(@NotEmpty(message = "Lo username deve essere fornito")
                             @Size(min = 4, max = 16, message = "Lo username deve essere tra 4 caratteri e 16 " +
                                     "caratteri")
                             String username,
                             @NotEmpty(message = "La password deve essere fornita")
                             @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&+=]).*$",
                                     message = "La password deve contenere almeno 8 caratteri, almeno un numero , " +
                                             "almeno " +
                                             "un carattere minuscolo " +
                                             " almeno un carattere maiuscolo e un carattere speciale @#!$%^&+=")
                             String password) {
}
