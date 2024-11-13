package Team6.Build_Week_Team_6.repositories;

import Team6.Build_Week_Team_6.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID>, JpaSpecificationExecutor<Cliente> {
    boolean existsByPartitaIva(String partitaIva);

    boolean existsByRagioneSociale(String ragioneSociale);

    boolean existsByEmail(String email);

    boolean existsByPec(String pec);

    boolean existsByTelefono(String telefono);

}
