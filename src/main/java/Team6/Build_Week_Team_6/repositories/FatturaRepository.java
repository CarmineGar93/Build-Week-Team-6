package Team6.Build_Week_Team_6.repositories;

import Team6.Build_Week_Team_6.entities.Fattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface FatturaRepository extends JpaRepository<Fattura, UUID> {
    Page<Fattura> findByCliente_ClienteId(UUID clienteId, Pageable pageable);

    Page<Fattura> findByStatoFattura_StatoFatturaId(UUID statoId, Pageable pageable);

    Page<Fattura> findByData(LocalDate data, Pageable pageable);

    Page<Fattura> findByDataBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Fattura> findByImportoBetween(double minImporto, double maxImporto, Pageable pageable);

    Page<Fattura> findByImportoGreaterThanEqual(double minImporto, Pageable pageable);

    Page<Fattura> findByImportoLessThanEqual(double maxImporto, Pageable pageable);

    Optional<Fattura> findTopByOrderByNumeroDesc();
}