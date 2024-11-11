package Team6.Build_Week_Team_6.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "fatture")
public class Fattura {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "fattura_id")
    private UUID fatturaId;
    @Column(nullable = false)
    private long numero;
    @Column(nullable = false)
    private LocalDate data;
    @Column(nullable = false)
    private double importo;
    @ManyToOne
    @JoinColumn(name = "stato_fattura_id")
    private StatoFattura statoFattura;

    public Fattura(long numero, LocalDate data, double importo, StatoFattura statoFattura) {//TODO aggiungere cliente
        // nel costruttore
        this.numero = numero;
        this.data = data;
        this.importo = importo;
        this.statoFattura = statoFattura;
    }
}
