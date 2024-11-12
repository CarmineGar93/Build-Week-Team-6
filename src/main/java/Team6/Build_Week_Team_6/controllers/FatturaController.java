package Team6.Build_Week_Team_6.controllers;

import Team6.Build_Week_Team_6.dto.FatturaDTO;
import Team6.Build_Week_Team_6.dto.StatoFatturaUpdateDTO;
import Team6.Build_Week_Team_6.entities.Fattura;
import Team6.Build_Week_Team_6.exceptions.BadRequestException;
import Team6.Build_Week_Team_6.services.FatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fatture")
public class FatturaController {
    @Autowired
    private FatturaService fatturaService;

    @GetMapping
    public Page<Fattura> getFatture(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "data") String sortBy,
            @RequestParam(required = false) UUID clienteId,
            @RequestParam(required = false) UUID statoId,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) Integer anno,
            @RequestParam(required = false) Double importoMin,
            @RequestParam(required = false) Double importoMax) {
        return fatturaService.getFatture(page, size, sortBy, clienteId, statoId, data, anno, importoMin, importoMax);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fattura save(@RequestBody @Validated FatturaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Errori nel payload: " + message);
        }
        return fatturaService.save(body);
    }

    @PatchMapping("/stato/{fatturaId}")
    public Fattura updateStato(@PathVariable UUID fatturaId,
                               @RequestBody @Validated StatoFatturaUpdateDTO body,
                               BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Errori nel payload: " + message);
        }
        return fatturaService.updateStato(fatturaId, body.statoFatturaId());
    }

    @DeleteMapping("/{fatturaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID fatturaId) {
        fatturaService.findByIdAndDelete(fatturaId);
    }
}

