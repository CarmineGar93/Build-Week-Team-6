package Team6.Build_Week_Team_6.repositories;

import Team6.Build_Week_Team_6.entities.RuoloUtente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RuoloUtenteRepository extends JpaRepository<RuoloUtente, UUID> {
    Optional<RuoloUtente> findByNome(String nome);
}
