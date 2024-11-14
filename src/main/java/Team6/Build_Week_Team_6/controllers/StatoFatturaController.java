package Team6.Build_Week_Team_6.controllers;

import Team6.Build_Week_Team_6.dto.StatoFatturaDTO;
import Team6.Build_Week_Team_6.entities.StatoFattura;
import Team6.Build_Week_Team_6.exceptions.BadRequestException;
import Team6.Build_Week_Team_6.repositories.StatoFatturaRepository;
import Team6.Build_Week_Team_6.services.StatoFatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statofatture")
public class StatoFatturaController {
    @Autowired
    private StatoFatturaService statoFatturaService;
    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    @GetMapping
    public List<StatoFattura> getAll() {
        return statoFatturaService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public StatoFattura createStatoFattura(@RequestBody @Validated StatoFatturaDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return statoFatturaService.createStatoFattura(body);
    }
}