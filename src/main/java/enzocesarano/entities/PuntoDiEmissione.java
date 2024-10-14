package enzocesarano.entities;

import enzocesarano.entities.ENUM.TipoPuntoDiEmissione;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Punti_Di_Emissione")
public class PuntoDiEmissione {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_punto_emissione;
    private String nome_punto;
    @Enumerated(EnumType.STRING)
    private TipoPuntoDiEmissione tipo_puntoDiEmissione;

    public PuntoDiEmissione(UUID id_punto_emissione, String nome_punto, TipoPuntoDiEmissione tipo_puntoDiEmissione) {
        this.id_punto_emissione = id_punto_emissione;
        this.nome_punto = nome_punto;
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

    public TipoPuntoDiEmissione getTipo_puntoDiEmissione() {
        return tipo_puntoDiEmissione;
    }

    public void setTipo_puntoDiEmissione(TipoPuntoDiEmissione tipo_puntoDiEmissione) {
        this.tipo_puntoDiEmissione = tipo_puntoDiEmissione;
    }
}
