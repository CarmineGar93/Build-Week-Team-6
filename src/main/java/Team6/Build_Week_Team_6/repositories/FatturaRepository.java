// FatturaRepository.java
package Team6.Build_Week_Team_6.repositories;

import Team6.Build_Week_Team_6.entities.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface FatturaRepository extends JpaRepository<Fattura, UUID>,
        JpaSpecificationExecutor<Fattura> {
    Optional<Fattura> findTopByOrderByNumeroDesc();

}