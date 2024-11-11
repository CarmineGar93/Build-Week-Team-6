package Team6.Build_Week_Team_6.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@Entity
@Table(name = "province")
@JsonIgnoreProperties({"comuni"})
public class Provincia {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "provincia_id")
    private UUID provinciaId;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true, length = 2)
    private String sigla;
    @OneToMany(mappedBy = "provincia")
    private List<Comune> comuni;

    public Provincia(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }
}
