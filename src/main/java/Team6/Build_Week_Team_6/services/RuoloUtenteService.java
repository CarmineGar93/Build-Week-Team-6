package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.dto.RuoloUtenteDTO;
import Team6.Build_Week_Team_6.entities.RuoloUtente;
import Team6.Build_Week_Team_6.entities.Utente;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.repositories.RuoloUtenteRepository;
import Team6.Build_Week_Team_6.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RuoloUtenteService {
    @Autowired
    UtenteRepository utenteRepository;
    @Autowired
    private RuoloUtenteRepository ruoloUtenteRepository;

    public List<RuoloUtente> findAll() {
        return this.ruoloUtenteRepository.findAll();
    }

    public RuoloUtente findById(UUID ruoloUtenteId) {
        return this.ruoloUtenteRepository.findById(ruoloUtenteId).orElseThrow(() -> new NotFoundException("Ruolo " +
                "utente " + ruoloUtenteId + " non trovato"));
    }

    public RuoloUtente createRuoloUtente(RuoloUtenteDTO ruoloUtenteDTO) {
        RuoloUtente ruoloUtente = new RuoloUtente(ruoloUtenteDTO.nome());
        return this.ruoloUtenteRepository.save(ruoloUtente);
    }

    public RuoloUtente findRuoloUtenteByNome(String nome) {
        return ruoloUtenteRepository.findByNome(nome).orElseThrow(() -> new NotFoundException("Ruolo " + nome + " non" +
                " trovato"));
    }

    public void deleteRuoloUtente(UUID ruoloUtenteId) {
        RuoloUtente ruoloUtente = this.findById(ruoloUtenteId);
        this.ruoloUtenteRepository.delete(ruoloUtente);
    }

    public RuoloUtente saveRuoloUtente(RuoloUtente ruoloUtente) {
        return this.ruoloUtenteRepository.save(ruoloUtente);
    }


    public Utente assegnaRuoloAUtente(UUID utenteId, UUID ruoloUtenteId) {

        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException("Utente con ID " + utenteId + " non trovato"));

        RuoloUtente ruoloUtente = ruoloUtenteRepository.findById(ruoloUtenteId)
                .orElseThrow(() -> new NotFoundException("Ruolo utente con ID " + ruoloUtenteId + " non trovato"));

        utente.getRuoli().add(ruoloUtente);
        return utenteRepository.save(utente);
    }

    public Utente rimuoviRuoloUtente(UUID utenteId, UUID ruoloUtenteId) {
        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException("Utente con ID " + utenteId + " non trovato"));

        RuoloUtente ruoloUtente = ruoloUtenteRepository.findById(ruoloUtenteId)
                .orElseThrow(() -> new NotFoundException("Ruolo utente con ID " + ruoloUtenteId + " non trovato"));

        utente.getRuoli().remove(ruoloUtente);
        return utenteRepository.save(utente);
    }
}
