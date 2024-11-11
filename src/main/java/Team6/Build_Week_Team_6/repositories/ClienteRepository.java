package Team6.Build_Week_Team_6.repositories;

import Team6.Build_Week_Team_6.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
}
