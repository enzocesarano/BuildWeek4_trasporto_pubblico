package enzocesarano.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Tessere")
public class Tessera {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_tessera;
    private LocalDate data_aquisto;
    private LocalDate data_scadenza;
    private boolean stato;

    @OneToOne
    @JoinColumn(name = "id_utente")
    private Utenti utenti;

    @OneToOne(mappedBy = "tessera")
    private Abbonamento abbonamenti;

    public Tessera(LocalDate data_aquisto, Utenti utenti, Abbonamento abbonamenti) {
        this.data_aquisto = data_aquisto;
        this.data_scadenza = data_aquisto.plusYears(1);
        this.utenti = utenti;
        this.abbonamenti = abbonamenti;
        this.stato = this.data_scadenza.isAfter(LocalDate.now()) || this.data_scadenza.isEqual(LocalDate.now());
    }

    public Tessera() {
    }

    public UUID getId_tessera() {
        return id_tessera;
    }

    public boolean isStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public LocalDate getData_aquisto() {
        return data_aquisto;
    }

    public void setData_aquisto(LocalDate data_aquisto) {
        this.data_aquisto = data_aquisto;
    }

    public LocalDate getData_scadenza() {
        return data_scadenza;
    }

    public void setData_scadenza(LocalDate data_scadenza) {
        this.data_scadenza = data_scadenza;
    }

    public Utenti getUtenti() {
        return utenti;
    }

    public void setUtenti(Utenti utenti) {
        this.utenti = utenti;
    }

    public Abbonamento getAbbonamenti() {
        return abbonamenti;
    }

    public void setAbbonamenti(Abbonamento abbonamenti) {
        this.abbonamenti = abbonamenti;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id_tessera=" + id_tessera +
                ", data_aquisto=" + data_aquisto +
                ", data_scadenza=" + data_scadenza +
                '}';
    }
}