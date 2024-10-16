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

            boolean exit = false;
            while (!exit) {
                System.out.println("\nMenu Manutenzione: ");
                System.out.println("1. Visualizza dati manutenzione");
                System.out.println("2. Calcola numero di manutenzioni in un periodo per un certo mezzo");
                System.out.println("0. Vai al menu principale");

                int nrScelto = scanner.nextInt();
                scanner.nextLine();

                if (nrScelto == 1) {
                    boolean idValido = false;
                    while (!idValido) {
                        System.out.println("Inserisci l'id del mezzo: ");
                        String idMezzo = scanner.nextLine();

                        try {
                            UUID mezzoId = UUID.fromString(idMezzo);
                            String dettagliManutenzione = manutenzioneDAO.calcolaDurataTipoMotivoManutenzione(mezzoId);
                            System.out.println(dettagliManutenzione);
                            idValido = true;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Errore! ID del mezzo non valido! Reinserisci un ID valido.");
                        }
                    }

                } else if (nrScelto == 2) {
                    boolean idValido = false;
                    while (!idValido) {
                        System.out.println("Inserisci l'id del mezzo: ");
                        String idMezzo = scanner.nextLine();

                        try {
                            UUID mezzoId = UUID.fromString(idMezzo);
                            idValido = true;

                            //While per le date finche non vengono inserite correttamente
                            boolean dateValide = false;
                            while (!dateValide) {
                                System.out.println("Inserisci la data di inizio (YYYY-MM-DD): ");
                                String dataInizioStr = scanner.nextLine();
                                System.out.println("Inserisci la data di fine (YYYY-MM-DD): ");
                                String dataFineStr = scanner.nextLine();

                                LocalDate dataInizio;
                                LocalDate dataFine;
                                try {
                                    dataInizio = LocalDate.parse(dataInizioStr);  // Converti la stringa della data in oggetto tipo LocalDate
                                    dataFine = LocalDate.parse(dataFineStr);

                                    if (dataInizio.isAfter(dataFine)) {
                                        System.out.println("Errore: La data di inizio non può essere successiva alla data di fine.");
                                    } else if (dataInizio.isAfter(LocalDate.now()) || dataFine.isAfter(LocalDate.now())) {
                                        System.out.println("Errore: Le date inserite non possono essere nel futuro.");
                                    } else {
                                        String risultato = manutenzioneDAO.calcolaNumeroManutenzioniInPeriodo(mezzoId, dataInizio, dataFine);
                                        System.out.println(risultato);
                                        dateValide = true;  // Se le date sono valide esce dal ciclo
                                    }
                                } catch (DateTimeParseException e) {
                                    System.out.println("Errore: Formato data non valido. Formato valido: YYYY-MM-DD. Reinserisci le date.");
                                }
                            }

                        } catch (IllegalArgumentException e) {
                            System.out.println("Errore! ID del mezzo non valido! Reinserisci un ID valido.");
                        }
                    }

                } else if (nrScelto == 0) {
                    System.out.println("Sei uscito dal Menu Manutenzione!");
                    exit = true;
                } else {
                    System.out.println("Numero inserito non valido! Riprova.");
                }
            }

        } catch (Exception e) {
            System.out.println("Errore durante la gestione della manutenzione: " + e.getMessage());
        }
    }
}
