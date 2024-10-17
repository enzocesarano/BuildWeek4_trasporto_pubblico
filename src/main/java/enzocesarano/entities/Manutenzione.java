package enzocesarano.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "manutenzioni")
public class Manutenzione {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_manutenzione;
    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;
    private LocalDate data_inizio;
    private LocalDate data_fine;
    private String motivo;

    public Manutenzione(Mezzo mezzo, LocalDate data_inizio, LocalDate data_fine, String motivo) {
        this.mezzo = mezzo;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.motivo = motivo;
    }

    public Manutenzione() {
    }

    public UUID getId_manutenzione() {
        return id_manutenzione;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public LocalDate getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(LocalDate data_inizio) {
        this.data_inizio = data_inizio;
    }

    public LocalDate getData_fine() {
        return data_fine;
    }

    public void setData_fine(LocalDate data_fine) {
        this.data_fine = data_fine;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
