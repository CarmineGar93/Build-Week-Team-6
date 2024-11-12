package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.dto.ProvinciaDTO;
import Team6.Build_Week_Team_6.entities.Provincia;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinciaService {
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

    public List<Provincia> cercaProvinceNonAssociate() {
        return provinciaRepository.cercaPerProvinceNonAssociate();
    }

    public List<ProvinciaDTO> getAllProvince() {
        List<Provincia> province = provinciaRepository.findAll();
        return province.stream()
                .map(provincia -> ProvinciaDTO.builder()
                        .provinciaId(provincia.getProvinciaId())
                        .nome(provincia.getNome())
                        .sigla(provincia.getSigla())
                        .build())
                .collect(Collectors.toList());
    }


}
