package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.dto.UtenteDTO;
import Team6.Build_Week_Team_6.entities.RuoloUtente;
import Team6.Build_Week_Team_6.entities.Utente;
import Team6.Build_Week_Team_6.exceptions.BadRequestException;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private RuoloUtenteService ruoloUtenteService;

    public Utente findUtenteById(UUID utenteId) {
        return utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException("Utente con id " + utenteId + " non trovato"));
    }

    public Page<Utente> getAllUsers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return utenteRepository.findAll(pageable);
    }

    public Utente salvaUtente(UtenteDTO body) {
        if (utenteRepository.existsByEmail(body.email()))
            throw new BadRequestException("Email già in uso");
        if (utenteRepository.existsByUsername(body.username()))
            throw new BadRequestException("Username già in uso");
        Utente utente = new Utente(body.username(), body.email(), bcrypt.encode(body.password()), body.nome(),
                body.cognome());
        RuoloUtente ruoloUser = ruoloUtenteService.findRuoloUtenteByNome("USER");
        utente.addRuolo(ruoloUser);
        return utenteRepository.save(utente);
    }

    public Utente cercaUtentePerUsername(String username) {
        return utenteRepository.findUtenteByUsername(username).orElseThrow(() -> new NotFoundException("Utente con " +
                "username " + username + " non trovato"));
    }
}
