package Team6.Build_Week_Team_6.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "comuni")
@JsonIgnoreProperties({"indirizzi"})
public class Comune {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "comune_id")
    private UUID comuneId;
    @Column(nullable = false)
    private String nome;
    @ManyToOne
    @JoinColumn(name = "provincia_id", nullable = false)
    private Provincia provincia;
    @OneToMany(mappedBy = "comune")
    private List<Indirizzo> indirizzi;

    public Comune(String nome, Provincia provincia) {
        this.nome = nome;
        this.provincia = provincia;
    }
}
