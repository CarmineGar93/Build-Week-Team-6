package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.entities.Comune;
import Team6.Build_Week_Team_6.entities.Provincia;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.repositories.ComuneRepository;
import Team6.Build_Week_Team_6.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComuneService {
    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public List<Comune> findAllComuni() {
        return comuneRepository.findAll();
    }

    public Comune saveComune(Comune comune) {
        return comuneRepository.save(comune);
    }

    public Iterable<Comune> getAllComuni(int page, int size, String sortBy, String provinciaNome) {
        if (size > 100) size = 100;
        if (provinciaNome != null) {
            Provincia provincia = provinciaRepository.findByNome(provinciaNome)
                    .orElseThrow(() -> new NotFoundException("Provincia non trovata"));
            return comuneRepository.findByProvincia(provincia);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return comuneRepository.findAll(pageable);

    }
}
