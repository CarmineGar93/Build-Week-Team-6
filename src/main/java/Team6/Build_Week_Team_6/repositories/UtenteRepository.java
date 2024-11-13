package Team6.Build_Week_Team_6.repositories;

import Team6.Build_Week_Team_6.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UtenteRepository extends JpaRepository<Utente, UUID> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
