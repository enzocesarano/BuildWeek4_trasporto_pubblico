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
    private UUID numero_tessera;

    private String nome;
    private String cognome;
    private LocalDate data_di_nascita;
    private TipoUtente tipoUtente;
    private Tessera id_tessera;

    public Utenti() {
    }

    public Utenti(String nome, String cognome, LocalDate data_di_nascita, TipoUtente tipoUtente) {
        this.nome = nome;
        this.cognome = cognome;
        this.data_di_nascita = data_di_nascita;
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
        return numero_tessera;
    }


    @Override
    public String toString() {
        return "Utenti{" +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", data_di_nascita=" + data_di_nascita +
                ", numero_tessera=" + numero_tessera +
                '}';
    }
}