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
@Table(name = "stati_fattura")
@JsonIgnoreProperties({"fatture"})
public class StatoFattura {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "stato_fattura_id")
    private UUID statoFatturaId;
    @Column(nullable = false)
    private String nome;
    @OneToMany(mappedBy = "statoFattura")
    private List<Fattura> fatture;


    public StatoFattura(String nome) {
        this.nome = nome;
    }
}
