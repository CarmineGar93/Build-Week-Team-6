package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.dto.IndirizzoDTO;
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

    public Indirizzo save(IndirizzoDTO body) {
        // Verifica se l'indirizzo esiste già
        if (existsByViaAndCivicoAndCap(body.via(), body.civico(), body.cap())) {
            throw new BadRequestException("Indirizzo già esistente con via: " + body.via() +
                    ", civico: " + body.civico() +
                    ", cap: " + body.cap());
        }

        // Verifica esistenza comune
        Comune comune = comuneRepository.findById(body.comuneId())
                .orElseThrow(() -> new NotFoundException("Comune con id " + body.comuneId() + " non trovato"));

        // Crea nuovo indirizzo
        Indirizzo newIndirizzo = new Indirizzo();
        newIndirizzo.setVia(body.via());
        newIndirizzo.setCivico(body.civico());
        newIndirizzo.setLocalita(body.localita());
        newIndirizzo.setCap(body.cap());
        newIndirizzo.setComune(comune);

        // Salva e ritorna l'indirizzo
        return indirizzoRepository.save(newIndirizzo);
    }

    public Indirizzo findById(UUID id) {
        return indirizzoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Indirizzo con id " + id + " non trovato"));
    }

    private boolean existsByViaAndCivicoAndCap(String via, int civico, int cap) {
        return indirizzoRepository.existsByViaAndCivicoAndCap(via, civico, cap);
    }
}