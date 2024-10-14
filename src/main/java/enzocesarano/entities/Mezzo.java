package enzocesarano.entities;

import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.ENUM.TipoMezzo;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Mezzi")
public class Mezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_mezzo;
    @Enumerated(EnumType.STRING)
    private TipoMezzo tipo_mezzo;
    private int capienza;
    @Enumerated(EnumType.STRING)
    private StatoMezzo statoMezzo;
    private LocalDate periodo;

    public Mezzo(UUID id_mezzo, TipoMezzo tipo_mezzo, int capienza, StatoMezzo statoMezzo, LocalDate periodo) {
        this.id_mezzo = id_mezzo;
        this.tipo_mezzo = tipo_mezzo;
        this.capienza = capienza;
        this.statoMezzo = statoMezzo;
        this.periodo = periodo;
    }

    public Mezzo() {
    }

    public UUID getId_mezzo() {
        return id_mezzo;
    }


    public TipoMezzo getTipo_mezzo() {
        return tipo_mezzo;
    }

    public void setTipo_mezzo(TipoMezzo tipo_mezzo) {
        this.tipo_mezzo = tipo_mezzo;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public StatoMezzo getStatoMezzo() {
        return statoMezzo;
    }

    public void setStatoMezzo(StatoMezzo statoMezzo) {
        this.statoMezzo = statoMezzo;
    }

    public LocalDate getPeriodo() {
        return periodo;
    }

    public void setPeriodo(LocalDate periodo) {
        this.periodo = periodo;
    }
}
