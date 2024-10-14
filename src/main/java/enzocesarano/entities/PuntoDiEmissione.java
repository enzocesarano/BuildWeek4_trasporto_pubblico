package enzocesarano.entities;

import java.util.UUID;

public class PuntoDiEmissione {
    private UUID id_punto_emissione;
    private String nome_punto;
    private PuntoDiEmissione puntoDiEmissione;

    public PuntoDiEmissione(UUID id_punto_emissione, String nome_punto, PuntoDiEmissione puntoDiEmissione) {
        this.id_punto_emissione = id_punto_emissione;
        this.nome_punto = nome_punto;
        this.puntoDiEmissione = puntoDiEmissione;
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

    public PuntoDiEmissione getPuntoDiEmissione() {
        return puntoDiEmissione;
    }

    public void setPuntoDiEmissione(PuntoDiEmissione puntoDiEmissione) {
        this.puntoDiEmissione = puntoDiEmissione;
    }
}
