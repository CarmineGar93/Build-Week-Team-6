// FatturaService.java
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class FatturaService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private FatturaRepository fatturaRepository;
    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    public Page<Fattura> getFatture(int page, int size, String sortBy, String sortDir,
                                    UUID clienteId, UUID statoId, LocalDate data,
                                    Integer anno, Double importoMin, Double importoMax) {

        if (importoMin != null && importoMax != null && importoMin > importoMax) {
            throw new BadRequestException("L'importo minimo non può essere maggiore dell'importo massimo");
        }

        Specification<Fattura> spec = Specification.where(null);

        if (clienteId != null) {
            if (!clienteRepository.existsById(clienteId)) {
                throw new NotFoundException("Cliente non trovato");
            }
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("cliente").get("clienteId"), clienteId));
        }

        if (statoId != null) {
            if (!statoFatturaRepository.existsById(statoId)) {
                throw new NotFoundException("Stato Fattura non trovato");
            }
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("statoFattura").get("statoFatturaId"), statoId));
        }

        if (data != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("data"), data));
        }

        if (anno != null) {
            LocalDate inizioAnno = LocalDate.of(anno, 1, 1);
            LocalDate fineAnno = LocalDate.of(anno, 12, 31);
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("data"), inizioAnno, fineAnno));
        }

        if (importoMin != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("importo"), importoMin));
        }

        if (importoMax != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("importo"), importoMax));
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return fatturaRepository.findAll(spec, pageable);
    }

    public Fattura save(FatturaDTO body) {

        Cliente cliente = clienteRepository.findById(body.clienteId())
                .orElseThrow(() -> new NotFoundException("Cliente non trovato"));

        StatoFattura statoEmessa = statoFatturaRepository.findByNome("EMESSA")
                .orElseThrow(() -> new NotFoundException("Stato fattura EMESSA non trovato"));
        Long nuovoNumero = generaNuovoNumeroFattura();
        cliente.setFatturatoAnnuale(cliente.getFatturatoAnnuale() + body.importo());

        Fattura fattura = new Fattura(nuovoNumero, body.importo(), statoEmessa, cliente);

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