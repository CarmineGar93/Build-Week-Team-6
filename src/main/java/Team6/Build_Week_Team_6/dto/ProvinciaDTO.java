package Team6.Build_Week_Team_6.dto;


import lombok.Builder;

import java.util.UUID;

@Builder
public record ProvinciaDTO(
        UUID provinciaId,
        String nome,
        String sigla
) {
}
