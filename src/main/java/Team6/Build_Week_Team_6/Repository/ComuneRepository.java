package Team6.Build_Week_Team_6.Repository;

import Team6.Build_Week_Team_6.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComuneRepository extends JpaRepository<Comune, UUID> {
}
