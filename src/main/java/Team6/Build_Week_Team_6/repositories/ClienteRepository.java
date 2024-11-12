package Team6.Build_Week_Team_6.repositories;

import Team6.Build_Week_Team_6.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    boolean existsByPartitaIva(String partitaIva);

    boolean existsByRagioneSociale(String ragioneSociale);

    boolean existsByEmail(String email);

    boolean existsByPec(String pec);

    boolean existsByTelefono(String telefono);

    @Query("SELECT c FROM Cliente c WHERE " +
            "(:fatturatoAnnuale IS NULL) OR (c.fatturatoAnnuale > :fatturatoAnnuale) AND " +
            "(:dataInserimento IS NULL) OR (c.dataInserimento = :dataInserimento) AND " +
            "(:dataUltimoContatto IS NULL) OR (c.dataUltimoContatto = :dataUltimoContatto) AND " +
            "(:ragioneSociale IS NULL) OR (c.ragioneSociale LIKE :ragioneSociale)")
    List<Cliente> filtriCustom(Double fatturatoAnnuale, LocalDate dataInserimento, LocalDate dataUltimoContatto,
                               String ragioneSociale);

}
