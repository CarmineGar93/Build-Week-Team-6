package Team6.Build_Week_Team_6.controllers;

import Team6.Build_Week_Team_6.dto.IndirizzoDTO;
import Team6.Build_Week_Team_6.dto.IndirizzoResponseDTO;
import Team6.Build_Week_Team_6.services.IndirizzoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/indirizzi")
public class IndirizzoController {
    @Autowired
    private IndirizzoService indirizzoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IndirizzoResponseDTO save(@RequestBody @Valid IndirizzoDTO body) {
        return indirizzoService.save(body);
    }

    @GetMapping("/{indirizzoId}")
    public IndirizzoResponseDTO findById(@PathVariable UUID indirizzoId) {
        return indirizzoService.findById(indirizzoId);
    }
}
