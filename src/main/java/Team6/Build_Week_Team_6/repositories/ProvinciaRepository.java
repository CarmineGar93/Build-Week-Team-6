package Team6.Build_Week_Team_6.repositories;

import Team6.Build_Week_Team_6.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProvinciaRepository extends JpaRepository<Provincia, UUID> {
    Optional<Provincia> findByNome(String nome);

    @Query("SELECT p FROM Provincia p WHERE SIZE(p.comuni) = 0")
    List<Provincia> cercaPerProvinceNonAssociate();
}
