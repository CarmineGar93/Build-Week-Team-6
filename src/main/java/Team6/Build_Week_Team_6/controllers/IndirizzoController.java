package Team6.Build_Week_Team_6.controllers;

import Team6.Build_Week_Team_6.dto.IndirizzoDTO;
import Team6.Build_Week_Team_6.dto.IndirizzoResponseDTO;
import Team6.Build_Week_Team_6.exceptions.BadRequestException;
import Team6.Build_Week_Team_6.services.IndirizzoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/indirizzi")
public class IndirizzoController {
    @Autowired
    private IndirizzoService indirizzoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IndirizzoResponseDTO save(@RequestBody @Validated IndirizzoDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return indirizzoService.save(body);
    }

    @GetMapping("/{indirizzoId}")
    public IndirizzoResponseDTO findById(@PathVariable UUID indirizzoId) {
        return indirizzoService.findById(indirizzoId);
    }
}
