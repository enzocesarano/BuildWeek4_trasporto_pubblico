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

    private Utenti id_utente;
    @OneToMany
    @JoinColumn(name = "id_abbonamento")
    private Abbonamento id_abbonamento;

    private Emissione id_emissione;

    public Tessera(UUID id_tessera, LocalDate data_aquisto, LocalDate data_scadenza, Utenti id_utente, Abbonamento id_abbonamento, Emissione id_emissione) {
        this.id_tessera = id_tessera;
        this.data_aquisto = data_aquisto;
        this.data_scadenza = data_scadenza;
        this.id_utente = id_utente;
        this.id_abbonamento = id_abbonamento;
        this.id_emissione = id_emissione;
    }

    public Tessera() {
    }

    public UUID getId_tessera() {
        return id_tessera;
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

    public Utenti getId_utente() {
        return id_utente;
    }

    public void setId_utente(Utenti id_utente) {
        this.id_utente = id_utente;
    }

    public Abbonamento getId_abbonamento() {
        return id_abbonamento;
    }

    public void setId_abbonamento(Abbonamento id_abbonamento) {
        this.id_abbonamento = id_abbonamento;
    }

    public Emissione getId_emissione() {
        return id_emissione;
    }

    public void setId_emissione(Emissione id_emissione) {
        this.id_emissione = id_emissione;
    }
}
