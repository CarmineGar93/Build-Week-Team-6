package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.dto.IndirizzoDTO;
import Team6.Build_Week_Team_6.dto.IndirizzoResponseDTO;
import Team6.Build_Week_Team_6.entities.Comune;
import Team6.Build_Week_Team_6.entities.Indirizzo;
import Team6.Build_Week_Team_6.exceptions.BadRequestException;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.repositories.ComuneRepository;
import Team6.Build_Week_Team_6.repositories.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository indirizzoRepository;

    @Autowired
    private ComuneRepository comuneRepository;

    public IndirizzoResponseDTO save(IndirizzoDTO body) {
        if (existsByViaAndCivicoAndCap(body.via(), body.civico(), body.cap())) {
            throw new BadRequestException("Indirizzo già esistenze ");
        }
        Comune comune = comuneRepository.findById(body.comuneId())
                .orElseThrow(() -> new NotFoundException("Comune con id " + body.comuneId() + " non trovato"));

        Indirizzo newIndirizzo = new Indirizzo();
        newIndirizzo.setVia(body.via());
        newIndirizzo.setCivico(body.civico());
        newIndirizzo.setLocalita(body.localita());
        newIndirizzo.setCap(body.cap());
        newIndirizzo.setComune(comune);

        Indirizzo savedIndirizzo = indirizzoRepository.save(newIndirizzo);

        return new IndirizzoResponseDTO(
                savedIndirizzo.getIndirizzoId(),
                savedIndirizzo.getVia(),
                savedIndirizzo.getCivico(),
                savedIndirizzo.getLocalita(),
                savedIndirizzo.getCap(),
                savedIndirizzo.getComune().getNome(),
                savedIndirizzo.getComune().getProvincia().getNome()
        );
    }

    public IndirizzoResponseDTO findById(UUID id) {
        Indirizzo indirizzo = indirizzoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Indirizzo con id " + id + " non trovato"));

        return new IndirizzoResponseDTO(
                indirizzo.getIndirizzoId(),
                indirizzo.getVia(),
                indirizzo.getCivico(),
                indirizzo.getLocalita(),
                indirizzo.getCap(),
                indirizzo.getComune().getNome(),
                indirizzo.getComune().getProvincia().getNome()
        );
    }

    private boolean existsByViaAndCivicoAndCap(String via, int civico, int cap) {
        return indirizzoRepository.existsByViaAndCivicoAndCap(via, civico, cap);
    }
}
