package enzocesarano.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "Percorrenze")
public class Percorrenza {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_percorrenza;
    private int nr_percorrenze;
    private LocalTime tempoEffettivo;
    @ManyToOne
    @JoinColumn(name = "id_tratta")
    private Tratta id_tratta;

    public Percorrenza(UUID id_percorrenza, int nr_percorrenze, LocalTime tempoEffettivo, Tratta id_tratta) {
        this.id_percorrenza = id_percorrenza;
        this.nr_percorrenze = nr_percorrenze;
        this.tempoEffettivo = tempoEffettivo;
        this.id_tratta = id_tratta;
    }

    public Percorrenza() {
    }

    public UUID getId_percorrenza() {
        return id_percorrenza;
    }

    public int getNrPercorrenze() {
        return nr_percorrenze;
    }

    public void setNrPercorrenze(int nr_percorrenze) {
        this.nr_percorrenze = nr_percorrenze;
    }

    public LocalTime getTempoEffettivo() {
        return tempoEffettivo;
    }

    public void setTempoEffettivo(LocalTime tempoEffettivo) {
        this.tempoEffettivo = tempoEffettivo;
    }

    public Tratta getId_tratta() {
        return id_tratta;
    }

    public void setId_tratta(Tratta id_tratta) {
        this.id_tratta = id_tratta;
    }
}
