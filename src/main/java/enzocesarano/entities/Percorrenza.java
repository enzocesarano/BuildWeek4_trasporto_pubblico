package enzocesarano.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "Percorrenze")
public class Percorrenza {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_percorrenza;

    private LocalDate data;
    private LocalTime tempoEffettivo;
    @ManyToOne
    @JoinColumn(name = "id_tratta")
    private Tratta tratta;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    public Percorrenza(LocalDate data, LocalTime tempoEffettivo, Tratta tratta, Mezzo mezzo) {
        this.data = data;
        this.tempoEffettivo = tempoEffettivo;
        this.tratta = tratta;
        this.mezzo = mezzo;
    }

    public Percorrenza() {
    }

    public UUID getId_percorrenza() {
        return id_percorrenza;
    }

    public void setId_percorrenza(UUID id_percorrenza) {
        this.id_percorrenza = id_percorrenza;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getTempoEffettivo() {
        return tempoEffettivo;
    }

    public void setTempoEffettivo(LocalTime tempoEffettivo) {
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
