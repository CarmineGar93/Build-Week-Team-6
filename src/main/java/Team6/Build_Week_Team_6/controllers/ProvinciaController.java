package Team6.Build_Week_Team_6.controllers;

import Team6.Build_Week_Team_6.entities.Provincia;
import Team6.Build_Week_Team_6.services.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/province")
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping
    public List<Provincia> getAllProvince() {
        return provinciaService.findAllProvince();
    }
}