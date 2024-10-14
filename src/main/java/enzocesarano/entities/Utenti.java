package enzocesarano.entities;

import enzocesarano.entities.ENUM.TipoUtente;
import jakarta.persistence.*;

import java.time.LocalDate;
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
    @OneToOne
    private Tessera id_tessera;

    public Utenti() {
    }

    public Utenti(UUID id_utente, String nome, String cognome, LocalDate data_di_nascita, TipoUtente tipoUtente, Tessera id_tessera) {
        this.id_utente = id_utente;
        this.nome = nome;
        this.cognome = cognome;
        this.data_di_nascita = data_di_nascita;
        this.tipoUtente = tipoUtente;
        this.id_tessera = id_tessera;
    }

    public Tessera getId_tessera() {
        return id_tessera;
    }

    public void setId_tessera(Tessera id_tessera) {
        this.id_tessera = id_tessera;
    }

    public TipoUtente getTipoUtente() {
        return tipoUtente;
    }

    public void setTipoUtente(TipoUtente tipoUtente) {
        this.tipoUtente = tipoUtente;
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

    public UUID getNumero_tessera() {
        return id_utente;
    }


    @Override
    public String toString() {
        return "Utenti{" +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", data_di_nascita=" + data_di_nascita +
                ", numero_tessera=" + id_utente +
                '}';
    }
}