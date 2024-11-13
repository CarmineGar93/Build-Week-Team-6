package Team6.Build_Week_Team_6.controllers;

import Team6.Build_Week_Team_6.dto.RuoloUtenteDTO;
import Team6.Build_Week_Team_6.entities.RuoloUtente;
import Team6.Build_Week_Team_6.exceptions.BadRequestException;
import Team6.Build_Week_Team_6.repositories.RuoloUtenteRepository;
import Team6.Build_Week_Team_6.services.RuoloUtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ruoloUtenti")
public class RuoloUtenteController {
    @Autowired
    private RuoloUtenteService ruoloUtenteService;
    @Autowired
    private RuoloUtenteRepository ruoloUtenteRepository;

    @GetMapping
    public List<RuoloUtente> getAll() {
        return ruoloUtenteService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RuoloUtente createRuoloUtente(@RequestBody @Validated RuoloUtenteDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return ruoloUtenteService.createRuoloUtente(body);
    }

}