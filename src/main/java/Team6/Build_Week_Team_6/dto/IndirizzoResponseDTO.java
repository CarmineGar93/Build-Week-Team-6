package Team6.Build_Week_Team_6.dto;

import java.util.UUID;

public record IndirizzoResponseDTO(
        UUID id,
        String via,
        int civico,
        String localita,
        int cap,
        String comune,
        String provincia
) {
}
