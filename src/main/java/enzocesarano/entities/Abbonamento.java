package enzocesarano.entities;

import enzocesarano.entities.ENUM.Periodicità;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Abbonamenti")
public class Abbonamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_abbonamento;
    private boolean stato;
    private LocalDate data_inizio;
    private LocalDate data_fine;
    @Enumerated(EnumType.STRING)
    private Periodicità periodicità;

    @ManyToOne
    @JoinColumn(name = "id_utenti")
    private Utenti utenti;

    @ManyToOne
    @JoinColumn(name = "id_punto_emissione")
    private PuntoDiEmissione puntoDiEmissione;

    @ManyToOne
    @JoinColumn(name = "id_tessera")
    private Tessera tessera;

    public Abbonamento(LocalDate data_inizio, Utenti utenti, PuntoDiEmissione puntoDiEmissione, Tessera tessera, Periodicità periodicità) {
        this.data_inizio = data_inizio;
        this.utenti = utenti;
        this.puntoDiEmissione = puntoDiEmissione;
        this.tessera = tessera;
        this.periodicità = periodicità;
        if (this.periodicità == Periodicità.SETTIMANALE) {
            data_fine = data_inizio.plusWeeks(1);
        } else if (this.periodicità == Periodicità.MENSILE) {
            data_fine = data_inizio.plusMonths(1);
        }

        this.stato = this.data_fine.isAfter(LocalDate.now()) || this.data_fine.isEqual(LocalDate.now());
    }

    public Abbonamento() {
    }

    public Periodicità getPeriodicità() {
        return periodicità;
    }

    public void setPeriodicità(Periodicità periodicità) {
        this.periodicità = periodicità;
    }

    public UUID getId_abbonamento() {
        return id_abbonamento;
    }

    public LocalDate getData_fine() {
        return data_fine;
    }

    public void setData_fine(LocalDate data_fine) {
        this.data_fine = data_fine;
    }

    public boolean isStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public LocalDate getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(LocalDate data_inizio) {
        this.data_inizio = data_inizio;
    }

    public Utenti getUtenti() {
        return utenti;
    }

    public void setUtenti(Utenti utenti) {
        this.utenti = utenti;
    }

    public PuntoDiEmissione getPuntoDiEmissione() {
        return puntoDiEmissione;
    }

    public void setPuntoDiEmissione(PuntoDiEmissione puntoDiEmissione) {
        this.puntoDiEmissione = puntoDiEmissione;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "id_abbonamento=" + id_abbonamento +
                ", stato=" + stato +
                ", data_inizio=" + data_inizio +
                ", utenti=" + utenti +
                ", puntoDiEmissione=" + puntoDiEmissione +
                ", tessera=" + tessera +
                '}';
    }
}
