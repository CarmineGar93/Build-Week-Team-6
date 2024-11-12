package Team6.Build_Week_Team_6.controllers;

import Team6.Build_Week_Team_6.entities.Comune;
import Team6.Build_Week_Team_6.services.ComuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comuni")
public class ComuneController {

    @Autowired
    private ComuneService comuneService;

    public ComuneController(ComuneService comuneService) {
        this.comuneService = comuneService;
    }

    @GetMapping
    public List<Comune> getAllComune(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "asc") String sortBy) {
        return comuneService.findAllComuni();
    }
}