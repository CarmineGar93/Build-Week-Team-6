package Team6.Build_Week_Team_6.controllers;

import Team6.Build_Week_Team_6.dto.ComuneDTO;
import Team6.Build_Week_Team_6.services.ComuneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comuni")
public class ComuneController {

    private final ComuneService comuneService;

    public ComuneController(ComuneService comuneService) {
        this.comuneService = comuneService;
    }

    @GetMapping
    public ResponseEntity<List<ComuneDTO>> getAllComuni(
            @RequestParam(value = "provincia", required = false) String provincia) {
        List<ComuneDTO> comuni = provincia != null
                ? comuneService.getComuniByProvincia(provincia)
                : comuneService.getAllComuni();
        return ResponseEntity.ok(comuni);
    }
}