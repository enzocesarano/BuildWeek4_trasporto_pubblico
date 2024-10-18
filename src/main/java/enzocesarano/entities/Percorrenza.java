package enzocesarano.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Percorrenze")
public class Percorrenza {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_percorrenza;

    private LocalDate data;
    private int tempoEffettivo;
    @ManyToOne
    @JoinColumn(name = "id_tratta")
    private Tratta tratta;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    public Percorrenza(LocalDate data, int tempoEffettivo, Tratta tratta) {
        this.data = data;
        this.tempoEffettivo = tempoEffettivo;
        this.tratta = tratta;
        this.mezzo = tratta.getMezzo();
    }

    public Percorrenza() {
    }

    public UUID getId_percorrenza() {
        return id_percorrenza;
    }


    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getTempoEffettivo() {
        return tempoEffettivo;
    }

    public void setTempoEffettivo(int tempoEffettivo) {
        this.tempoEffettivo = tempoEffettivo;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }
}
