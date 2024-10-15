package enzocesarano.entities;

import enzocesarano.entities.ENUM.TipoUtente;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Utenti")
public class Utenti {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_utente;
    private String nome;
    private String cognome;
    private LocalDate data_di_nascita;
    @Enumerated(EnumType.STRING)
    private TipoUtente tipoUtente;
    @OneToOne(mappedBy = "utenti", cascade = CascadeType.ALL)
    private Tessera tessera;

    @OneToMany(mappedBy = "utenti", cascade = CascadeType.ALL)
    private List<Abbonamento> abbonamenti;

    public Utenti() {
    }

    public Utenti(String nome, String cognome, LocalDate data_di_nascita, TipoUtente tipoUtente
    ) {
        this.nome = nome;
        this.cognome = cognome;
        this.data_di_nascita = data_di_nascita;
        this.tipoUtente = tipoUtente;

    }

    public UUID getId_utente() {
        return id_utente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getData_di_nascita() {
        return data_di_nascita;
    }

    public void setData_di_nascita(LocalDate data_di_nascita) {
        this.data_di_nascita = data_di_nascita;
    }

    public TipoUtente getTipoUtente() {
        return tipoUtente;
    }

    public void setTipoUtente(TipoUtente tipoUtente) {
        this.tipoUtente = tipoUtente;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }

    public void setAbbonamenti(List<Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }

    @Override
    public String toString() {
        return "Utenti{" +
                "id_utente=" + id_utente +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", data_di_nascita=" + data_di_nascita +
                ", tipoUtente=" + tipoUtente +
                '}';
    }
}