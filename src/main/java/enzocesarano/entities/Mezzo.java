package enzocesarano.entities;

import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.ENUM.TipoMezzo;
import jakarta.persistence.*;

import java.util.List;
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

    @OneToOne(mappedBy = "mezzo")
    private Tratta tratta;

    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL)
    private List<ValidazioneBiglietto> bigliettiValidi;

    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL)
    private List<Percorrenza> percorrenze;

    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL)
    private List<Manutenzione> manutenzioni;

    public Mezzo() {
    }

    public Mezzo(TipoMezzo tipo_mezzo, StatoMezzo statoMezzo, Tratta tratta) {
        this.tipo_mezzo = tipo_mezzo;
        if (this.tipo_mezzo == TipoMezzo.TRAM) {
            this.capienza = 100;
        } else if (this.tipo_mezzo == TipoMezzo.BUS) {
            this.capienza = 50;
        }
        this.statoMezzo = statoMezzo;
        this.tratta = tratta;
    }

    public UUID getId_mezzo() {
        return id_mezzo;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
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

    public List<ValidazioneBiglietto> getBigliettiValidi() {
        return bigliettiValidi;
    }

    public void setBigliettiValidi(List<ValidazioneBiglietto> bigliettiValidi) {
        this.bigliettiValidi = bigliettiValidi;
    }

    public List<Percorrenza> getPercorrenze() {
        return percorrenze;
    }

    public void setPercorrenze(List<Percorrenza> percorrenze) {
        this.percorrenze = percorrenze;
    }
}
