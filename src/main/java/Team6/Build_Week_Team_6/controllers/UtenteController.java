package Team6.Build_Week_Team_6.controllers;

import Team6.Build_Week_Team_6.dto.RuoloUtenteDTO;
import Team6.Build_Week_Team_6.dto.UtenteDTO;
import Team6.Build_Week_Team_6.entities.Utente;
import Team6.Build_Week_Team_6.exceptions.BadRequestException;
import Team6.Build_Week_Team_6.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping
    public Page<Utente> getAllUtenti(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "nome") String sortBy) {
        return utenteService.getAllUsers(page, size, sortBy);
    }

    @DeleteMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUtente(@PathVariable UUID utenteId) {
        utenteService.deleteUtente(utenteId);
    }

    @GetMapping("/me")
    public Utente getLoggedUtente(@AuthenticationPrincipal Utente loggato) {
        return loggato;
    }

    @PutMapping("/me")
    public Utente modificaUtenteLoggato(@AuthenticationPrincipal Utente loggato,
                                        @RequestBody @Validated UtenteDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return utenteService.modifiyUtenteAndUpdate(loggato.getUtenteId(), body);
    }

    @PatchMapping("/{utenteId}/ruolo")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente aggiungiRuoloUtente(@PathVariable UUID utenteId, @RequestBody @Validated RuoloUtenteDTO body,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return utenteService.aggiungiRuolo(body, utenteId);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente registraUtente(@RequestBody @Validated UtenteDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return utenteService.salvaUtente(body);
    }

}
