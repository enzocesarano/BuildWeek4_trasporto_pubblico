package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.ManutenzioneDAO;
import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.Mezzo;
import exceptions.InvalidIDException;
import exceptions.MezzoNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class SetManutenzione {

    public static void tracciaManutenzione(Scanner scanner, DefaultDAO defaultDAO, ManutenzioneDAO manutenzioneDAO, boolean isAdmin) {
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
                System.out.println("3. Cambia stato del mezzo");
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

                            // While per le date finche non vengono inserite correttamente
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

                } else if (nrScelto == 3) {
                    cambiaStatoMezzo(scanner, defaultDAO); // Chiamo il metodo per cambiare stato del mezzo

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

    private static void cambiaStatoMezzo(Scanner scanner, DefaultDAO defaultDAO) {
        boolean idValido = false;
        while (!idValido) {
            System.out.println("Inserisci l'ID del mezzo: ");
            String idMezzo = scanner.nextLine();

            try {
                Mezzo mezzo = defaultDAO.getEntityById(Mezzo.class, idMezzo);  // Recupero il mezzo

                if (mezzo == null) {
                    throw new MezzoNotFoundException("Errore! Nessun mezzo trovato con questo ID!");
                }
                StatoMezzo statoAttualeMezzo = mezzo.getStatoMezzo();  // Recupero lo stato attuale
                boolean sceltaValida = false;

                while (!sceltaValida) {
                    if (statoAttualeMezzo == StatoMezzo.SERVIZIO) {
                        System.out.println("Lo stato attuale del mezzo è in SERVIZIO.");
                        System.out.println("1. Cambia stato a MANUTENZIONE");
                        System.out.println("0. Torna indietro");

                        int scelta = scanner.nextInt();
                        scanner.nextLine();

                        if (scelta == 1) {
                            mezzo.setStatoMezzo(StatoMezzo.MANUTENZIONE);  // Aggiorno lo stato
                            defaultDAO.save(mezzo);
                            System.out.println("Lo stato del mezzo è stato cambiato da SERVIZIO a MANUTENZIONE.");
                            sceltaValida = true;
                        } else if (scelta == 0) {
                            System.out.println("Ritorno al menu precedente.");
                            return;
                        } else {
                            System.out.println("Opzione non valida. Riprova!");
                        }

                    } else if (statoAttualeMezzo == StatoMezzo.MANUTENZIONE) {
                        System.out.println("Lo stato attuale del mezzo è in MANUTENZIONE.");
                        System.out.println("1. Cambia stato a SERVIZIO");
                        System.out.println("0. Torna indietro");

                        int scelta = scanner.nextInt();
                        scanner.nextLine();

                        if (scelta == 1) {
                            mezzo.setStatoMezzo(StatoMezzo.SERVIZIO);
                            defaultDAO.save(mezzo);
                            System.out.println("Lo stato del mezzo è stato cambiato da MANUTENZIONE a SERVIZIO.");
                            sceltaValida = true;
                        } else if (scelta == 0) {
                            System.out.println("Ritorno al menu precedente.");
                            return;
                        } else {
                            System.out.println("Opzione non valida. Riprova!");
                        }
                    }
                }

                idValido = true;
            } catch (IllegalArgumentException e) {
                try {
                    throw new InvalidIDException("Errore! ID del mezzo non valido: " + idMezzo);
                } catch (InvalidIDException ex) {
                    System.out.println(ex.getMessage());
                }
            } catch (MezzoNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}