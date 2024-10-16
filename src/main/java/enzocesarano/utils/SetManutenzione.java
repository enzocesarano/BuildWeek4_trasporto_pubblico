package enzocesarano.utils;

import enzocesarano.dao.ManutenzioneDAO;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class SetManutenzione {

    public static void tracciaManutenzione(Scanner scanner, ManutenzioneDAO manutenzioneDAO, boolean isAdmin) {
        try {

            if (!isAdmin) {
                System.out.println("Accesso negato! Accesso solo al personale autorizzato!");
                return;
            }

            System.out.println("Menu Manutenzione: ");
            System.out.println("1. Visualizza dati manutenzione");
            System.out.println("2. Calcola numero di manutenzioni in un periodo per un certo mezzo");
            System.out.println("0. Esci");

            int nrScelto = scanner.nextInt();
            scanner.nextLine();

            if (nrScelto == 1) {
                System.out.println("Inserisci l'id del mezzo: ");
                String idMezzo = scanner.nextLine();

                try {
                    UUID mezzoId = UUID.fromString(idMezzo);
                    String dettagliManutenzione = manutenzioneDAO.calcolaDurataTipoMotivoManutenzione(mezzoId);
                    System.out.println(dettagliManutenzione);
                } catch (IllegalArgumentException e) {
                    System.out.println("Errore! ID del mezzo non valido!");
                }

            } else if (nrScelto == 2) {
                System.out.println("Inserisci l'id del mezzo: ");
                String idMezzo = scanner.nextLine();

                try {
                    UUID mezzoId = UUID.fromString(idMezzo);

                    System.out.println("Inserisci la data di inizio (YYYY-MM-DD): ");
                    String dataInizioStr = scanner.nextLine();
                    System.out.println("Inserisci la data di fine (YYYY-MM-DD): ");
                    String dataFineStr = scanner.nextLine();

                    LocalDate dataInizio;
                    LocalDate dataFine;
                    try {
                        dataInizio = LocalDate.parse(dataInizioStr);  //converto la stringa della data in oggetto tipo LocalDate
                        dataFine = LocalDate.parse(dataFineStr);

                        if (dataInizio.isAfter(dataFine)) {
                            System.out.println("Errore: La data di inizio non può essere successiva alla data di fine.");
                        } else {
                            String risultato = manutenzioneDAO.calcolaNumeroManutenzioniInPeriodo(mezzoId, dataInizio, dataFine);
                            System.out.println(risultato);
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Errore: Formato data non valido.Formato valido: YYYY-MM-DD.");
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println("Errore! ID del mezzo non valido!");
                }

            } else if (nrScelto == 0) {
                System.out.println("Sei uscito dal Menu Manutenzione!");
            } else {
                System.out.println("Numero inserito non valido!");
            }

        } catch (Exception e) {
            System.out.println("Errore durante la gestione della manutenzione: " + e.getMessage());
        }
    }
}
