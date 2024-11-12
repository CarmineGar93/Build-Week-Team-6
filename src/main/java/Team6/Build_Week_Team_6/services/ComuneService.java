package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.entities.Comune;
import Team6.Build_Week_Team_6.repositories.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComuneService {
    @Autowired
    private ComuneRepository comuneRepository;

    public List<Comune> findAllComuni() {
        return comuneRepository.findAll();
    }

    public Comune saveComune(Comune comune) {
        return comuneRepository.save(comune);
    }
}
