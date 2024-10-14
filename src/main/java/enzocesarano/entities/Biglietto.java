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
    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utenti id_utente;
    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo id_mezzo;
    private boolean convalidato;

    public Biglietto(UUID id_biglietto, LocalDate data_emissione, Utenti id_utente, Mezzo id_mezzo, boolean convalidato) {
        this.id_biglietto = id_biglietto;
        this.data_emissione = data_emissione;
        this.id_utente = id_utente;
        this.id_mezzo = id_mezzo;
        this.convalidato = convalidato;
    }

    public Biglietto() {
    }

    public UUID getId_biglietto() {
        return id_biglietto;
    }


    public LocalDate getData_emissione() {
        return data_emissione;
    }

    public void setData_emissione(LocalDate data_emissione) {
        this.data_emissione = data_emissione;
    }

    public Utenti getId_utente() {
        return id_utente;
    }

    public void setId_utente(Utenti id_utente) {
        this.id_utente = id_utente;
    }

    public Mezzo getId_mezzo() {
        return id_mezzo;
    }

    public void setId_mezzo(Mezzo id_mezzo) {
        this.id_mezzo = id_mezzo;
    }

    public boolean isConvalidato() {
        return convalidato;
    }

    public void setConvalidato(boolean convalidato) {
        this.convalidato = convalidato;
    }
}
