package enzocesarano.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Validazione_Biglietto")
public class ValidazioneBiglietto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idValidazioneBiglietto;

    @OneToOne
    @JoinColumn(name = "id_biglietto")
    private Biglietto biglietto;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    private LocalDate dataValidazione;

    public ValidazioneBiglietto(Biglietto biglietto, Mezzo mezzo, LocalDate dataValidazione) {
        this.biglietto = biglietto;
        this.mezzo = mezzo;
        this.dataValidazione = dataValidazione;
    }

    public ValidazioneBiglietto() {
    }

    public UUID getIdValidazioneBiglietto() {
        return idValidazioneBiglietto;
    }

    public Biglietto getBiglietto() {
        return biglietto;
    }

    public void setBiglietto(Biglietto biglietto) {
        this.biglietto = biglietto;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public LocalDate getDataValidazione() {
        return dataValidazione;
    }

    public void setDataValidazione(LocalDate dataValidazione) {
        this.dataValidazione = dataValidazione;
    }
}
