package enzocesarano.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Emissioni")
public class Emissione {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_emissione;
    @ManyToOne
    @JoinColumn(name = "id_punto_emissione")
    private PuntoDiEmissione id_punto_emissione;
    @ManyToOne
    @JoinColumn(name = "id_biglietto")
    private Biglietto id_biglietto;
    @ManyToOne
    @JoinColumn(name = "id_tessera")
    private Tessera id_tessera;
    @ManyToOne
    @JoinColumn(name = "id_abbonamento")
    private Abbonamento id_abbonamento;

    public Emissione(UUID id_emissione, PuntoDiEmissione id_punto_emissione, Biglietto id_biglietto, Tessera id_tessera, Abbonamento id_abbonamento) {
        this.id_emissione = id_emissione;
        this.id_punto_emissione = id_punto_emissione;
        this.id_biglietto = id_biglietto;
        this.id_tessera = id_tessera;
        this.id_abbonamento = id_abbonamento;
    }

    public Emissione() {
    }

    public UUID getId_emissione() {
        return id_emissione;
    }


    public PuntoDiEmissione getId_punto_emissione() {
        return id_punto_emissione;
    }

    public void setId_punto_emissione(PuntoDiEmissione id_punto_emissione) {
        this.id_punto_emissione = id_punto_emissione;
    }

    public Biglietto getId_biglietto() {
        return id_biglietto;
    }

    public void setId_biglietto(Biglietto id_biglietto) {
        this.id_biglietto = id_biglietto;
    }

    public Tessera getId_tessera() {
        return id_tessera;
    }

    public void setId_tessera(Tessera id_tessera) {
        this.id_tessera = id_tessera;
    }

    public Abbonamento getId_abbonamento() {
        return id_abbonamento;
    }

    public void setId_abbonamento(Abbonamento id_abbonamento) {
        this.id_abbonamento = id_abbonamento;
    }
}
