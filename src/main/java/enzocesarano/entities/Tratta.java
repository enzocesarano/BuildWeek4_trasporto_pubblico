package enzocesarano.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.UUID;


@Entity
@Table(name = "Tratte")
public class Tratta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_tratta;
    private String zonaPartenza;
    private String capolinea;
    private LocalTime tempoPrevisto;
    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    public Tratta(UUID id_tratta, String zonaPartenza, String capolinea, LocalTime tempoPrevisto, Mezzo mezzo) {
        this.id_tratta = id_tratta;
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoPrevisto = tempoPrevisto;
        this.mezzo = mezzo;
    }

    public Tratta() {
    }

    public UUID getId_tratta() {
        return id_tratta;
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

    public LocalTime getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(LocalTime tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }
}
