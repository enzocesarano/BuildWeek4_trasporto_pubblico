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
    private Tessera id_tessera;
    private Emissione id_emissione;

    public Abbonamento(UUID id_abbonamento, boolean stato, LocalDate data_inizio, LocalDate data_fine, Tessera id_tessera, Emissione id_emissione) {
        this.id_abbonamento = id_abbonamento;
        this.stato = stato;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.id_tessera = id_tessera;
        this.id_emissione = id_emissione;
    }

    public Abbonamento() {
    }

    public UUID getId_abbonamento() {
        return id_abbonamento;
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

    public Tessera getId_tessera() {
        return id_tessera;
    }

    public void setId_tessera(Tessera id_tessera) {
        this.id_tessera = id_tessera;
    }

    public Emissione getId_emissione() {
        return id_emissione;
    }

    public void setId_emissione(Emissione id_emissione) {
        this.id_emissione = id_emissione;
    }
}
