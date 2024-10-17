package enzocesarano.utils.utenti;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.ManutenzioneDAO;
import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.ENUM.TipoUtente;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.Utenti;
import enzocesarano.utils.SetMezzo;
import exceptions.InvalidIDException;
import exceptions.MezzoNotFoundException;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class SetAdmin {

    public static void MenuAdmin(Scanner scanner, DefaultDAO dd, ManutenzioneDAO manutenzioneDAO, boolean isAdmin) {
        EntityManager em = dd.getEntityManager();
        Utenti utente1 = null;
        boolean idValido = false;
        while (!idValido) {
            System.out.println("Inserisci il tuo id Admin: ");
            String idUtente = scanner.nextLine();
            utente1 = dd.getEntityById(Utenti.class, idUtente);
            try {
                if (utente1 != null && utente1.getTipoUtente() == TipoUtente.ADMIN) {
                    System.out.println("Benvenuto " + utente1.getNome() + " " + utente1.getCognome());
                    idValido = true;
                } else {
                    System.out.println("Utente non trovato. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("ID non valido. Assicurati di inserire un ID valido.");
            }
        }
        boolean exitUtente = false;
        while (!exitUtente) {
            System.out.println("Scegli una delle seguenti opzioni:");
            System.out.println("1. Manutenzione");
            System.out.println("2. Crea Mezzo");
            /*System.out.println("3. Acquista o rinnova Tessera");
            System.out.println("4. Acquista o rinnova Abbonamento");
            System.out.println("5. Visualizza Tratta Mezzo");
            System.out.println("9. Visualizza Profilo Admin");*/
            System.out.println("0. Esci");
            int subScelta = scanner.nextInt();
            scanner.nextLine();
            switch (subScelta) {
                case 1:
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
                            idValido = false;
                            while (!idValido) {
                                Mezzo mezzo = null;
                                List<Mezzo> mezziDisponibili = dd.getAllEntities(Mezzo.class);

                                System.out.println("Seleziona un mezzo in servizio:");
                                mezziDisponibili.forEach(m -> System.out.println((mezziDisponibili.indexOf(m) + 1) + ". " + m.getTipo_mezzo()));

                                int sceltaMezzo = -1;
                                boolean mezzoValido = false;
                                while (!mezzoValido) {
                                    System.out.print("Inserisci il numero del mezzo: ");
                                    try {
                                        sceltaMezzo = scanner.nextInt();
                                        scanner.nextLine();
                                        if (sceltaMezzo >= 1 && sceltaMezzo <= mezziDisponibili.size()) {
                                            mezzo = mezziDisponibili.get(sceltaMezzo - 1);
                                            mezzoValido = true;
                                        } else {
                                            System.out.println("Scelta non valida. Riprova.");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Errore: inserisci un numero valido.");
                                        scanner.nextLine();
                                    }
                                }

                                try {
                                    String dettagliManutenzione = manutenzioneDAO.calcolaDurataTipoMotivoManutenzione(mezzo.getId_mezzo());
                                    System.out.println(dettagliManutenzione);
                                    idValido = true;
                                } catch (IllegalArgumentException | MezzoNotFoundException e) {
                                    System.out.println("Errore! ID del mezzo non valido! Reinserisci un ID valido.");
                                }
                            }

                        } else if (nrScelto == 2) {
                            idValido = false;
                            while (!idValido) {
                                Mezzo mezzo = null;
                                List<Mezzo> mezziDisponibili = dd.getAllEntities(Mezzo.class);

                                System.out.println("Seleziona un mezzo in servizio:");
                                mezziDisponibili.forEach(m -> System.out.println((mezziDisponibili.indexOf(m) + 1) + ". " + m.getTipo_mezzo()));

                                int sceltaMezzo = -1;
                                boolean mezzoValido = false;
                                while (!mezzoValido) {
                                    System.out.print("Inserisci il numero del mezzo: ");
                                    try {
                                        sceltaMezzo = scanner.nextInt();
                                        scanner.nextLine();
                                        if (sceltaMezzo >= 1 && sceltaMezzo <= mezziDisponibili.size()) {
                                            mezzo = mezziDisponibili.get(sceltaMezzo - 1);
                                            mezzoValido = true;
                                        } else {
                                            System.out.println("Scelta non valida. Riprova.");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Errore: inserisci un numero valido.");
                                        scanner.nextLine();
                                    }
                                }
                                try {
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
                                                String risultato = manutenzioneDAO.calcolaNumeroManutenzioniInPeriodo(mezzo.getId_mezzo(), dataInizio, dataFine);
                                                System.out.println(risultato);
                                                dateValide = true;  // Se le date sono valide esce dal ciclo
                                            }
                                        } catch (DateTimeParseException e) {
                                            System.out.println("Errore: Formato data non valido. Formato valido: YYYY-MM-DD. Reinserisci le date.");
                                        } catch (MezzoNotFoundException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }

                                } catch (IllegalArgumentException e) {
                                    System.out.println("Errore! ID del mezzo non valido! Reinserisci un ID valido.");
                                }
                            }

                        } else if (nrScelto == 3) {
                            cambiaStatoMezzo(scanner, dd); // Chiamo il metodo per cambiare stato del mezzo

                        } else if (nrScelto == 0) {
                            System.out.println("Sei uscito dal Menu Manutenzione!");
                            exit = true;
                        } else {
                            System.out.println("Numero inserito non valido! Riprova.");
                        }
                    }
                    break;
                case 2:
                    SetMezzo.CreaMezzo(scanner, dd);
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 9:
                    em.refresh(utente1);
                    SetUtente.VisualizzaProfilo(utente1);
                    break;
                case 0:
                    exitUtente = true;
                    System.out.println("Uscita dal menu utente.");
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
                    break;
            }
        }
    }

    private static void cambiaStatoMezzo(Scanner scanner, DefaultDAO dd) {
        boolean idValido = false;
        while (!idValido) {
            Mezzo mezzo = null;
            List<Mezzo> mezziDisponibili = dd.getAllEntities(Mezzo.class);

            System.out.println("Seleziona un mezzo in servizio:");
            mezziDisponibili.forEach(m -> System.out.println((mezziDisponibili.indexOf(m) + 1) + ". " + m.getTipo_mezzo()));

            int sceltaMezzo = -1;
            boolean mezzoValido = false;
            while (!mezzoValido) {
                System.out.print("Inserisci il numero del mezzo: ");
                try {
                    sceltaMezzo = scanner.nextInt();
                    scanner.nextLine();
                    if (sceltaMezzo >= 1 && sceltaMezzo <= mezziDisponibili.size()) {
                        mezzo = mezziDisponibili.get(sceltaMezzo - 1);
                        mezzoValido = true;
                    } else {
                        System.out.println("Scelta non valida. Riprova.");
                    }
                } catch (Exception e) {
                    System.out.println("Errore: inserisci un numero valido.");
                    scanner.nextLine();
                }
            }

            try {
                Mezzo mezzo1 = dd.getEntityById(Mezzo.class, mezzo.getId_mezzo().toString());  // Recupero il mezzo

                if (mezzo == null) {
                    throw new MezzoNotFoundException("Errore! Nessun mezzo trovato con questo ID!");
                }
                StatoMezzo statoAttualeMezzo = mezzo.getStatoMezzo();
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
                            dd.save(mezzo);
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
                            dd.save(mezzo);
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
                    throw new InvalidIDException("Errore! ID del mezzo non valido: " + mezzo.getId_mezzo());
                } catch (InvalidIDException ex) {
                    System.out.println(ex.getMessage());
                }
            } catch (MezzoNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}


