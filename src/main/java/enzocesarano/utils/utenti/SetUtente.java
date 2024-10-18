package enzocesarano.utils.utenti;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.Abbonamento;
import enzocesarano.entities.ENUM.TipoUtente;
import enzocesarano.entities.Tessera;
import enzocesarano.entities.Utenti;

import java.time.LocalDate;
import java.util.Scanner;

public class SetUtente {

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
        System.out.println("Nuovo utente creato con successo! " + nuovoUtente.getNome() + " " + nuovoUtente.getCognome() + " - " + nuovoUtente.getId_utente());
    }


    public static void VisualizzaProfilo(Utenti utente1) {
        if (utente1 != null) {
            System.out.println("\n---- Profilo Utente ----");
            System.out.println("Nome: " + utente1.getNome());
            System.out.println("Cognome: " + utente1.getCognome());
            System.out.println("Data di Nascita: " + utente1.getData_di_nascita());

            Tessera tessera = utente1.getTessera();
            if (tessera != null) {
                System.out.println("\n---- Tessera ----");
                System.out.println("ID Tessera: " + tessera.getId_tessera());
                System.out.println("Data di Acquisto: " + tessera.getData_aquisto());
                System.out.println("Data di Scadenza: " + tessera.getData_scadenza());
                System.out.println("Stato Tessera: " + (tessera.isStato() ? "Attiva" : "Scaduta"));

                Abbonamento abbonamento = tessera.getAbbonamenti();
                if (abbonamento != null) {
                    System.out.println("\n---- Abbonamento ----");
                    System.out.println("ID Abbonamento: " + abbonamento.getId_abbonamento());
                    System.out.println("Data Inizio Abbonamento: " + abbonamento.getData_inizio());
                    System.out.println("Data Fine Abbonamento: " + abbonamento.getData_fine());
                    System.out.println("Periodicità: " + abbonamento.getPeriodicità());
                    System.out.println("Stato Abbonamento: " + (abbonamento.isStato() ? "Attivo\n" : "Scaduto\n"));
                } else {
                    System.out.println("\nNessun abbonamento attivo o registrato.\n");
                }
            } else {
                System.out.println("\nL'utente non ha una tessera associata.\n");
            }
        }
    }

}
