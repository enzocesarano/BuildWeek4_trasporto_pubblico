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
    @JoinColumn(name = "id_punto_emissione")
    private PuntoDiEmissione puntoDiEmissione;
    @OneToOne(mappedBy = "biglietto")
    private ValidazioneBiglietto validazioneBiglietto;

    public Biglietto(LocalDate data_emissione, boolean convalidato, PuntoDiEmissione puntoDiEmissione, ValidazioneBiglietto validazioneBiglietto) {
        this.data_emissione = data_emissione;
        this.convalidato = convalidato;
        this.puntoDiEmissione = puntoDiEmissione;
        this.validazioneBiglietto = validazioneBiglietto;
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

    @Override
    public String toString() {
        return "Biglietto{" +
                "id_biglietto=" + id_biglietto +
                ", data_emissione=" + data_emissione +
                ", convalidato=" + convalidato +
                ", puntoDiEmissione=" + (puntoDiEmissione != null ? puntoDiEmissione.getNome_punto() : "N/A") +
                '}';
    }
}