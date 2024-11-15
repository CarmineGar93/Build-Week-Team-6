package Team6.Build_Week_Team_6.repositories;

import Team6.Build_Week_Team_6.entities.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, UUID> {
    boolean existsByViaAndCivicoAndCap(String via, int civico, int cap);
}
