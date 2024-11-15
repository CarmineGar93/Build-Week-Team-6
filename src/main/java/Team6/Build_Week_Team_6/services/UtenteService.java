package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.dto.RuoloUtenteDTO;
import Team6.Build_Week_Team_6.dto.UtenteDTO;
import Team6.Build_Week_Team_6.entities.RuoloUtente;
import Team6.Build_Week_Team_6.entities.Utente;
import Team6.Build_Week_Team_6.exceptions.BadRequestException;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.repositories.UtenteRepository;
import Team6.Build_Week_Team_6.tools.MailgunSender;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private RuoloUtenteService ruoloUtenteService;
    @Autowired
    private Cloudinary cloudinaryUploader;
    @Autowired
    private MailgunSender mailgunSender;

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
        mailgunSender.sendRegistrationEmail(utente);
        return utenteRepository.save(utente);
    }

    public Utente cercaUtentePerUsername(String username) {
        return utenteRepository.findUtenteByUsername(username).orElseThrow(() -> new NotFoundException("Utente con " +
                "username " + username + " non trovato"));
    }

    public Utente creaAdmin(Utente utente) {
        return utenteRepository.save(utente);
    }

    public void deleteUtente(UUID utenteId) {
        Utente utente = findUtenteById(utenteId);
        utenteRepository.delete(utente);
    }

    public Utente modifiyUtenteAndUpdate(UUID utenteId, UtenteDTO body) {
        Utente searched = findUtenteById(utenteId);
        if (!searched.getCognome().equals(body.cognome()) || !searched.getNome().equals(body.nome())) {
            if (searched.getAvatarUrl().equals("https://ui-avatars.com/api/?name=" + searched.getNome() + "+" + searched.getCognome()))
                searched.setAvatarUrl("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        }
        if (!body.username().equals(searched.getUsername())) {
            if (utenteRepository.existsByUsername(body.username()))
                throw new BadRequestException("Username already in use");
            searched.setUsername(body.username());
        }
        if (!body.email().equals(searched.getEmail())) {
            if (utenteRepository.existsByEmail(body.email())) throw new BadRequestException("Email already in use");
            searched.setEmail(body.email());
        }
        searched.setPassword(bcrypt.encode(body.password()));
        searched.setNome(body.nome());
        searched.setCognome(body.cognome());
        return utenteRepository.save(searched);
    }

    public Utente aggiungiRuolo(RuoloUtenteDTO body, UUID utenteId) {
        Utente cercato = findUtenteById(utenteId);
        RuoloUtente ruoloDaAggiungere = ruoloUtenteService.findRuoloUtenteByNome(body.nome());
        if (cercato.getRuoli().stream().anyMatch(ruoloUtente -> ruoloUtente.getRuoloUtenteId().equals(ruoloDaAggiungere.getRuoloUtenteId())))
            throw new BadRequestException("L'utente ha già il ruolo scelto");
        cercato.addRuolo(ruoloDaAggiungere);
        return utenteRepository.save(cercato);
    }

    public String uploadFotoProfilo(MultipartFile file, UUID utenteId) {
        try {
            String url = (String) cloudinaryUploader.uploader()
                    .upload(file.getBytes(), ObjectUtils.emptyMap())
                    .get("url");
            Utente trovato = this.findUtenteById(utenteId);
            trovato.setAvatarUrl(url);
            utenteRepository.save(trovato);
            return url;
        } catch (IOException e) {
            throw new BadRequestException("Errore durante l'upload dell'immagine!");
        }
    }

    public Utente rimuoviRuolo(RuoloUtenteDTO body, UUID utenteId) {
        Utente cercato = findUtenteById(utenteId);
        RuoloUtente ruoloDaRimuovere = ruoloUtenteService.findRuoloUtenteByNome(body.nome());
        boolean rimosso =
                cercato.getRuoli().removeIf(ruoloUtente -> ruoloUtente.getRuoloUtenteId().equals(ruoloDaRimuovere.getRuoloUtenteId()));
        if (rimosso) return utenteRepository.save(cercato);
        throw new BadRequestException("L'utente non possiede il ruolo fornito");
    }
}
