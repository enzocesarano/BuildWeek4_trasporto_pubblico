package enzocesarano.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Biglietti")
public class Biglietto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_biglietto;
    private LocalDate data_emissione;
    private boolean convalidato;
    @ManyToOne
    @JoinColumn(name = "id_utenti")
    private Utenti utenti;
    @ManyToOne
    @JoinColumn(name = "id_punto_emissione")
    private PuntoDiEmissione puntoDiEmissione;


    public Biglietto(LocalDate data_emissione, boolean convalidato, Utenti utenti, PuntoDiEmissione puntoDiEmissione) {
        this.data_emissione = data_emissione;
        this.convalidato = convalidato;
        this.utenti = utenti;
        this.puntoDiEmissione = puntoDiEmissione;
    }

    public Biglietto() {
    }

    public UUID getId_biglietto() {
        return id_biglietto;
    }

    public void setId_biglietto(UUID id_biglietto) {
        this.id_biglietto = id_biglietto;
    }

    public LocalDate getData_emissione() {
        return data_emissione;
    }

    public void setData_emissione(LocalDate data_emissione) {
        this.data_emissione = data_emissione;
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

    public boolean isConvalidato() {
        return convalidato;
    }

    public void setConvalidato(boolean convalidato) {
        this.convalidato = convalidato;
    }
}