package enzocesarano.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Tessere")
public class Tessera {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_tessera;
    private LocalDate data_aquisto;
    private LocalDate data_scadenza;

    @OneToOne
    @JoinColumn(name = "id_utente")
    private Utenti utenti;

    @OneToMany(mappedBy = "tessera", cascade = CascadeType.ALL)
    private List<Abbonamento> abbonamenti;

    public Tessera(LocalDate data_aquisto, LocalDate data_scadenza, Utenti utenti, List<Abbonamento> abbonamenti) {
        this.data_aquisto = data_aquisto;
        this.data_scadenza = data_scadenza;
        this.utenti = utenti;
        this.abbonamenti = abbonamenti;
    }

    public Tessera() {
    }

    // rinnovo tessera
    public boolean isValida() {
        return data_scadenza.isAfter(LocalDate.now());
    }

    public UUID getId_tessera() {
        return id_tessera;
    }

    public void setId_tessera(UUID id_tessera) {
        this.id_tessera = id_tessera;
    }

    public LocalDate getData_aquisto() {
        return data_aquisto;
    }

    public void setData_aquisto(LocalDate data_aquisto) {
        this.data_aquisto = data_aquisto;
    }

    public LocalDate getData_scadenza() {
        return data_scadenza;
    }

    public void setData_scadenza(LocalDate data_scadenza) {
        this.data_scadenza = data_scadenza;
    }

    public Utenti getUtenti() {
        return utenti;
    }

    public void setUtenti(Utenti utenti) {
        this.utenti = utenti;
    }

    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }

    public void setAbbonamenti(List<Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }
}