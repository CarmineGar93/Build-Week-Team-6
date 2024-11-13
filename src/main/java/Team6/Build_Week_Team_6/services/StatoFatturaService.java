package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.entities.Fattura;
import Team6.Build_Week_Team_6.entities.StatoFattura;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.repositories.FatturaRepository;
import Team6.Build_Week_Team_6.repositories.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StatoFatturaService {
    @Autowired
    private StatoFatturaRepository statoFatturaRepository;
    @Autowired
    FatturaRepository fatturaRepository;

    public List<StatoFattura> findAll() {
        return this.statoFatturaRepository.findAll();
    }

    public StatoFattura findById(UUID statoFatturaId) {
        return this.statoFatturaRepository.findById(statoFatturaId)  .orElseThrow(() -> new NotFoundException("err"));
    }
    public StatoFattura createStatoFattura(String nome) {
        StatoFattura statoFattura = new StatoFattura(nome);
        return this.statoFatturaRepository.save(statoFattura);
    }

    public void deleteStatoFattura(UUID statoFatturaId) {
        StatoFattura statoFattura = this.findById(statoFatturaId);
        this.statoFatturaRepository.delete(statoFattura);
    }


    public StatoFattura saveStatoFattura(StatoFattura statoFattura) {
        return this.statoFatturaRepository.save(statoFattura);
    }

}