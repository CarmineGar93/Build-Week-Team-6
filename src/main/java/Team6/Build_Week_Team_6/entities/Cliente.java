package Team6.Build_Week_Team_6.entities;

import Team6.Build_Week_Team_6.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "clienti")
@JsonIgnoreProperties({"fatture"})
public class Cliente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "cliente_id")
    private UUID clienteId;
    @Column(name = "ragione_sociale", nullable = false)
    private String ragioneSociale;
    @Column(name = "partiva_iva", nullable = false, length = 11)
    private String partivaIva;
    @Column(nullable = false)
    private String email, pec, telefono;
    @Column(name = "data_inserimento", nullable = false)
    private LocalDate dataInserimento;
    @Column(name = "data_ultimo_contatto", nullable = false)
    private LocalDate dataUltimoContatto;
    @Column(name = "fatturato_annuale", nullable = false)
    private double fatturatoAnnuale;
    @Column(name = "email_contatto", nullable = false)
    private String emailContatto;
    @Column(name = "nome_contatto", nullable = false)
    private String nomeContatto;
    @Column(name = "cognome_contatto", nullable = false)
    private String cognomeContatto;
    @Column(name = "telefono_contatto", nullable = false)
    private String telefonoContatto;
    @Column(name = "logo_aziendale", nullable = false)
    private String logoAziendale;
    @Column(name = "tipo_cliente", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;
    @ManyToOne
    @JoinColumn(name = "indirizzo_sede_legale_id")
    private Indirizzo indirizzoSedeLegale;
    @ManyToOne
    @JoinColumn(name = "indirizzo_sede_operativa_id")
    private Indirizzo indirizzoSedeOperativa;
    @OneToMany(mappedBy = "cliente")
    private List<Fattura> fatture;

    public Cliente(String ragioneSociale, String partivaIva, String email, String pec, String telefono,
                   LocalDate dataUltimoContatto, String emailContatto, String nomeContatto, String cognomeContatto,
                   String telefonoContatto, TipoCliente tipoCliente, Indirizzo indirizzoSedeLegale,
                   Indirizzo indirizzoSedeOperativa) {
        this.ragioneSociale = ragioneSociale;
        this.partivaIva = partivaIva;
        this.email = email;
        this.pec = pec;
        this.telefono = telefono;
        this.dataUltimoContatto = dataUltimoContatto;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.tipoCliente = tipoCliente;
        this.indirizzoSedeLegale = indirizzoSedeLegale;
        this.indirizzoSedeOperativa = indirizzoSedeOperativa;
        this.dataInserimento = LocalDate.now();
        this.logoAziendale = "https://it.freepik.com/vettori-gratuito/misterioso-uomo-della-mafia-che-indossa-un" +
                "-cappello_7074313.htm#fromView=keyword&page=1&position=6&uuid=623bc9a6-559f-476c-bd8a-b12a5fe6a5e7";
    }

}
