package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.dto.FatturaDTO;
import Team6.Build_Week_Team_6.entities.Cliente;
import Team6.Build_Week_Team_6.entities.Fattura;
import Team6.Build_Week_Team_6.entities.StatoFattura;
import Team6.Build_Week_Team_6.exceptions.BadRequestException;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.repositories.ClienteRepository;
import Team6.Build_Week_Team_6.repositories.FatturaRepository;
import Team6.Build_Week_Team_6.repositories.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class FatturaService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    private FatturaRepository fatturaRepository;
    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    public Page<Fattura> getFatture(int page, int size, String sortBy, UUID clienteId, UUID statoId,
                                    LocalDate data, Integer anno, Double importoMin, Double importoMax) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        if (clienteId != null) {
            if (!clienteRepository.existsById(clienteId)) {
                throw new NotFoundException("Cliente non trovato");
            }
            return fatturaRepository.findByCliente_ClienteId(clienteId, pageable);
        }

        if (statoId != null) {
            if (!statoFatturaRepository.existsById(statoId)) {
                throw new NotFoundException("Stato Fattura non trovato");
            }
            return fatturaRepository.findByStatoFattura_StatoFatturaId(statoId, pageable);
        }

        if (data != null) {
            return fatturaRepository.findByData(data, pageable);
        }

        if (anno != null) {
            LocalDate inizioAnno = LocalDate.of(anno, 1, 1);
            LocalDate fineAnno = LocalDate.of(anno, 12, 31);
            return fatturaRepository.findByDataBetween(inizioAnno, fineAnno, pageable);
        }

        if (importoMin != null && importoMax != null) {
            if (importoMin > importoMax) {
                throw new BadRequestException("L'importo minimo non può essere maggiore dell'importo massimo");
            }
            return fatturaRepository.findByImportoBetween(importoMin, importoMax, pageable);
        }

        if (importoMin != null) {
            return fatturaRepository.findByImportoGreaterThanEqual(importoMin, pageable);
        }

        if (importoMax != null) {
            return fatturaRepository.findByImportoLessThanEqual(importoMax, pageable);
        }

        return fatturaRepository.findAll(pageable);
    }

    public Fattura save(FatturaDTO body) {
        Cliente cliente = clienteRepository.findById(body.clienteId())
                .orElseThrow(() -> new NotFoundException("Cliente non trovato"));

        Long nuovoNumero = generaNuovoNumeroFattura();

        Fattura fattura = new Fattura();
        fattura.setNumero(nuovoNumero);
        fattura.setData(body.data());
        fattura.setImporto(body.importo());
        fattura.setCliente(cliente);

        return fatturaRepository.save(fattura);
    }

    private Long generaNuovoNumeroFattura() {
        return fatturaRepository.findTopByOrderByNumeroDesc()
                .map(f -> f.getNumero() + 1)
                .orElse(1L);
    }

    public Fattura updateStato(UUID id, UUID statoId) {
        Fattura fattura = fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura non trovata"));

        StatoFattura statoFattura = statoFatturaRepository.findById(statoId)
                .orElseThrow(() -> new NotFoundException("Stato fattura non trovato"));

        fattura.setStatoFattura(statoFattura);
        return fatturaRepository.save(fattura);
    }

    public void findByIdAndDelete(UUID id) {
        Fattura fattura = fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura non trovata"));
        fatturaRepository.delete(fattura);
    }
}