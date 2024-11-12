package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.dto.ComuneDTO;
import Team6.Build_Week_Team_6.entities.Comune;
import Team6.Build_Week_Team_6.entities.Provincia;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.repositories.ComuneRepository;
import Team6.Build_Week_Team_6.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComuneService {
    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public List<Comune> findAllComuni() {
        return comuneRepository.findAll();
    }

    public Comune saveComune(Comune comune) {
        return comuneRepository.save(comune);
    }

    public List<ComuneDTO> getAllComuni() {
        List<Comune> comuni = comuneRepository.findAll();
        return comuni.stream()
                .map(comune -> ComuneDTO.builder()
                        .comuneId(comune.getComuneId())
                        .nome(comune.getNome())
                        .provinciaNome(comune.getProvincia().getNome())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ComuneDTO> getComuniByProvincia(String provinciaNome) {
        Provincia provincia = provinciaRepository.findByNome(provinciaNome)
                .orElseThrow(() -> new NotFoundException("Provincia non trovata"));
        List<Comune> comuni = comuneRepository.findByProvincia(provincia);
        return comuni.stream()
                .map(comune -> ComuneDTO.builder()
                        .comuneId(comune.getComuneId())
                        .nome(comune.getNome())
                        .provinciaNome(provincia.getNome())
                        .build())
                .collect(Collectors.toList());
    }
}
