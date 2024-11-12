package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.entities.Provincia;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {
    @Autowired
    private ProvinciaRepository provinciaRepository;

    public List<Provincia> findAllProvince() {
        return provinciaRepository.findAll();
    }

    public Provincia saveProvincia(Provincia provincia) {
        return provinciaRepository.save(provincia);
    }

    public Provincia findProvinciaByNome(String nome) {
        return provinciaRepository.findByNome(nome).orElseThrow(() -> new NotFoundException("Non trovato"));
    }

    public Provincia findProvinciaByNomeStartingWith(String partial) {
        List<Provincia> founded = provinciaRepository.findByNomeStartingWithIgnoreCase(partial);
        if (founded.isEmpty()) throw new NotFoundException("Non trovato");
        return founded.getFirst();
    }


}
