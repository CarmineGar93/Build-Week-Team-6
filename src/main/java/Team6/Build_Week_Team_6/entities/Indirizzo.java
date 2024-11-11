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
@Table(name = "indirizzi")
@JsonIgnoreProperties({"clienti"})
public class Indirizzo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "indirizzo_id")
    private UUID indirizzoId;
    @Column(nullable = false)
    private String via, localita;
    @Column(nullable = false)
    private int civico, cap;
    @ManyToOne
    @JoinColumn(name = "comune_id", nullable = false)
    private Comune comune;
    @ManyToMany(mappedBy = "indirizzi")
    private List<Cliente> clienti;

    public Indirizzo(String via, int civico, String localita, int cap, Comune comune) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
    }
}
