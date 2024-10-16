package enzocesarano.utils.utenti;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.ENUM.TipoUtente;
import enzocesarano.entities.Utenti;

import java.time.LocalDate;
import java.util.Scanner;

public class SetCreaUtente {

    public static void creaUtente(Scanner scanner, DefaultDAO dd) {
        String nome = "";
        String cognome = "";
        LocalDate data_di_nascita = null;
        TipoUtente tipoUtente = null;

        boolean inputValido = false;

        while (!inputValido) {
            try {
                System.out.print("Inserisci il nome: ");
                nome = scanner.nextLine();
                if (nome.trim().isEmpty()) {
                    throw new IllegalArgumentException("Il nome non può essere vuoto.");
                }
                inputValido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        inputValido = false;
        while (!inputValido) {
            try {
                System.out.print("Inserisci il cognome: ");
                cognome = scanner.nextLine();
                if (cognome.trim().isEmpty()) {
                    throw new IllegalArgumentException("Il cognome non può essere vuoto.");
                }
                inputValido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        inputValido = false;
        while (!inputValido) {
            try {
                System.out.print("Inserisci la data di nascita (yyyy-mm-dd): ");
                String dataInput = scanner.nextLine();
                data_di_nascita = LocalDate.parse(dataInput);
                inputValido = true;
            } catch (Exception e) {
                System.out.println("Data non valida. Inserisci una data nel formato corretto.");
            }
        }

        inputValido = false;
        while (!inputValido) {
            try {
                System.out.println("Seleziona il tipo di utente: ");
                System.out.println("1. Normale");
                System.out.println("2. Admin");
                int scelta = scanner.nextInt();
                scanner.nextLine();
                switch (scelta) {
                    case 1:
                        tipoUtente = TipoUtente.NORMALE;
                        inputValido = true;
                        break;
                    case 2:
                        tipoUtente = TipoUtente.ADMIN;
                        inputValido = true;
                        break;
                    default:
                        throw new IllegalArgumentException("Scelta non valida. Seleziona 1 o 2.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        Utenti nuovoUtente = new Utenti(nome, cognome, data_di_nascita, tipoUtente);
        dd.save(nuovoUtente);
        System.out.println("Nuovo utente creato con successo! " + nuovoUtente.getNome() + " " + nuovoUtente.getCognome());
    }

}
