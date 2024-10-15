package enzocesarano.entities;

import enzocesarano.entities.ENUM.TipoPuntoDiEmissione;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Punti_Di_Emissione")
public class PuntoDiEmissione {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_punto_emissione;

    private String nome_punto;
    private boolean attivo;

    @Enumerated(EnumType.STRING)
    private TipoPuntoDiEmissione tipo_puntoDiEmissione;

    @OneToMany(mappedBy = "puntoDiEmissione", cascade = CascadeType.ALL)
    private List<Biglietto> bigliettiEmessi;

    @OneToMany(mappedBy = "puntoDiEmissione", cascade = CascadeType.ALL)
    private List<Abbonamento> abbonamentiEmessi;

    public PuntoDiEmissione(String nome_punto, boolean attivo, TipoPuntoDiEmissione tipo_puntoDiEmissione) {
        this.nome_punto = nome_punto;
        this.attivo = attivo;
        this.tipo_puntoDiEmissione = tipo_puntoDiEmissione;
    }

    public PuntoDiEmissione() {
    }

    public UUID getId_punto_emissione() {
        return id_punto_emissione;
    }


    public String getNome_punto() {
        return nome_punto;
    }

    public void setNome_punto(String nome_punto) {
        this.nome_punto = nome_punto;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    public TipoPuntoDiEmissione getTipo_puntoDiEmissione() {
        return tipo_puntoDiEmissione;
    }

    public void setTipo_puntoDiEmissione(TipoPuntoDiEmissione tipo_puntoDiEmissione) {
        this.tipo_puntoDiEmissione = tipo_puntoDiEmissione;
    }

    public List<Biglietto> getBigliettiEmessi() {
        return bigliettiEmessi;
    }

    public void setBigliettiEmessi(List<Biglietto> bigliettiEmessi) {
        this.bigliettiEmessi = bigliettiEmessi;
    }

    public List<Abbonamento> getAbbonamentiEmessi() {
        return abbonamentiEmessi;
    }

    public void setAbbonamentiEmessi(List<Abbonamento> abbonamentiEmessi) {
        this.abbonamentiEmessi = abbonamentiEmessi;
    }
}
