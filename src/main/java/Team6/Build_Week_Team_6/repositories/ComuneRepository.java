package Team6.Build_Week_Team_6.repositories;

import Team6.Build_Week_Team_6.entities.Comune;
import Team6.Build_Week_Team_6.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ComuneRepository extends JpaRepository<Comune, UUID> {
    List<Comune> findByProvincia(Provincia provincia);
}
