package Team6.Build_Week_Team_6.repositories;

import Team6.Build_Week_Team_6.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatoFatturaRepository extends JpaRepository<StatoFattura, UUID> {
    Optional<StatoFattura> findByNome(String nome);

}
