package Team6.Build_Week_Team_6.Repository;

import Team6.Build_Week_Team_6.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatoFatturaRepository extends JpaRepository<StatoFattura, UUID> {
}
