package enzocesarano.entities;

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

    @ManyToOne
    @JoinColumn(name = "id_utenti")
    private Utenti utenti;

    @ManyToOne
    @JoinColumn(name = "id_punto_emissione")
    private PuntoDiEmissione puntoDiEmissione;

    @ManyToOne
    @JoinColumn(name = "id_tessera")
    private Tessera tessera;

    public Abbonamento(boolean stato, LocalDate data_inizio, LocalDate data_fine, Utenti utenti, PuntoDiEmissione puntoDiEmissione, Tessera tessera) {
        this.stato = stato;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.utenti = utenti;
        this.puntoDiEmissione = puntoDiEmissione;
        this.tessera = tessera;
    }

    public Abbonamento() {
    }

    // validità
    public boolean isAbbValido() {
        return data_fine.isAfter(LocalDate.now());
    }

    public UUID getId_abbonamento() {
        return id_abbonamento;
    }

    public void setId_abbonamento(UUID id_abbonamento) {
        this.id_abbonamento = id_abbonamento;
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

    public LocalDate getData_fine() {
        return data_fine;
    }

    public void setData_fine(LocalDate data_fine) {
        this.data_fine = data_fine;
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
}
