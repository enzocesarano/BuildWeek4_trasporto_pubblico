package enzocesarano.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "Tratte")
public class Tratta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_tratta;

    private String zonaPartenza;
    private String capolinea;
    private int tempoPrevisto;

    @OneToMany(mappedBy = "tratta", cascade = CascadeType.ALL)
    private List<Percorrenza> percorrenze;


    public Tratta(String zonaPartenza, String capolinea, int tempoPrevisto, List<Percorrenza> percorrenze) {
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoPrevisto = tempoPrevisto;
        this.percorrenze = percorrenze;
    }

    public Tratta() {
    }

    public UUID getId_tratta() {
        return id_tratta;
    }

    public void setId_tratta(UUID id_tratta) {
        this.id_tratta = id_tratta;
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public void setZonaPartenza(String zonaPartenza) {
        this.zonaPartenza = zonaPartenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public int getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(int tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }

    public List<Percorrenza> getPercorrenze() {
        return percorrenze;
    }

    public void setPercorrenze(List<Percorrenza> percorrenze) {
        this.percorrenze = percorrenze;
    }
}