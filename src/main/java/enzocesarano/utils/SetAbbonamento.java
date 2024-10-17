package enzocesarano.utils;

import enzocesarano.dao.AbbonamentiDAO;
import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.Abbonamento;
import enzocesarano.entities.ENUM.Periodicità;
import enzocesarano.entities.PuntoDiEmissione;
import enzocesarano.entities.Tessera;
import enzocesarano.entities.Utenti;
import exceptions.BigliettoNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class SetAbbonamento {
    public static void AcquistaAbbonamento(Scanner scanner, DefaultDAO get, Utenti utente) {
        LocalDate dataInizio = LocalDate.now();
        boolean puntoValido = false;
        PuntoDiEmissione puntoDiEmissione = null;
        List<PuntoDiEmissione> puntiDiEmissione = get.getAllEntities(PuntoDiEmissione.class);

        List<PuntoDiEmissione> puntiAttivi = puntiDiEmissione.stream()
                .filter(PuntoDiEmissione::isAttivo)
                .toList();

        if (puntiAttivi.isEmpty()) {
            System.out.println("Non ci sono punti di emissione attivi disponibili.");
            return;
        }

        System.out.println("Seleziona un punto di emissione:");
        puntiAttivi.forEach(p -> System.out.println((puntiAttivi.indexOf(p) + 1) + ". " + p.getNome_punto()));

        int scelta = -1;
        while (!puntoValido) {
            System.out.print("Inserisci il numero del punto di emissione: ");
            try {
                scelta = scanner.nextInt();
                scanner.nextLine();
                if (scelta >= 1 && scelta <= puntiAttivi.size()) {
                    puntoDiEmissione = puntiAttivi.get(scelta - 1);
                    puntoValido = true;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        Periodicità periodicità = null;
        boolean periodicitàValida = false;
        while (!periodicitàValida) {
            System.out.println("Seleziona la periodicità dell'abbonamento: ");
            System.out.println("1. Settimanale");
            System.out.println("2. Mensile");

            scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    periodicità = Periodicità.SETTIMANALE;
                    periodicitàValida = true;
                    break;
                case 2:
                    periodicità = Periodicità.MENSILE;
                    periodicitàValida = true;
                    break;
                default:
                    System.out.println("Scelta non valida. Seleziona un numero tra 1 e 2.");
            }
        }

        Tessera tessera = utente.getTessera();

        Abbonamento abbonamento = new Abbonamento(dataInizio, utente, puntoDiEmissione, tessera, periodicità);
        get.save(abbonamento);
        System.out.println("\nL'abbonamento per la tessera " + utente.getTessera().getId_tessera() + " è stato acquistato con successo!\n");
    }

    public static void RinnovaAbbonamento(Scanner scanner, DefaultDAO get, Utenti utente) {
        Tessera tessera = utente.getTessera();
        Abbonamento abbonamento = tessera.getAbbonamenti();

        if (abbonamento != null && !abbonamento.isStato()) {
            LocalDate nuovaDataInizio = LocalDate.now();

            Periodicità periodicità = abbonamento.getPeriodicità();
            boolean periodicitàValida = false;

            while (!periodicitàValida) {
                System.out.println("Vuoi cambiare la periodicità dell'abbonamento?");
                System.out.println("1. Mantieni " + periodicità);
                System.out.println("2. Cambia periodicità");

                int scelta = scanner.nextInt();
                scanner.nextLine();

                switch (scelta) {
                    case 1:
                        periodicitàValida = true;
                        break;
                    case 2:
                        System.out.println("Seleziona la nuova periodicità dell'abbonamento:");
                        System.out.println("1. Settimanale");
                        System.out.println("2. Mensile");

                        int nuovaScelta = scanner.nextInt();
                        scanner.nextLine();

                        switch (nuovaScelta) {
                            case 1:
                                periodicità = Periodicità.SETTIMANALE;
                                periodicitàValida = true;
                                break;
                            case 2:
                                periodicità = Periodicità.MENSILE;
                                periodicitàValida = true;
                                break;
                            default:
                                System.out.println("Scelta non valida. Seleziona un numero tra 1 e 2.");
                        }
                        break;
                    default:
                        System.out.println("Scelta non valida.");
                }
            }

            abbonamento.setData_inizio(nuovaDataInizio);

            if (periodicità == Periodicità.SETTIMANALE) {
                abbonamento.setData_fine(nuovaDataInizio.plusWeeks(1));
            } else if (periodicità == Periodicità.MENSILE) {
                abbonamento.setData_fine(nuovaDataInizio.plusMonths(1));
            }

            abbonamento.setPeriodicità(periodicità);

            abbonamento.setStato(abbonamento.getData_fine().isAfter(LocalDate.now()) || abbonamento.getData_fine().isEqual(LocalDate.now()));
            get.update(abbonamento);

            System.out.println("L'abbonamento è stato rinnovato con successo!");
        }
    }

    public static void AbbonamentiPerPunto(DefaultDAO get, Scanner scanner, AbbonamentiDAO ad) {
        boolean puntoValido = false;
        PuntoDiEmissione puntoDiEmissione = null;
        List<PuntoDiEmissione> puntiDiEmissione = get.getAllEntities(PuntoDiEmissione.class);

        System.out.println("Seleziona un punto di emissione:");
        puntiDiEmissione.forEach(p -> System.out.println((puntiDiEmissione.indexOf(p) + 1) + ". " + p.getNome_punto() + " - " + p.getId_punto_emissione()));

        int scelta = -1;
        while (!puntoValido) {
            System.out.print("Inserisci il numero del punto di emissione: ");
            try {
                scelta = scanner.nextInt();
                scanner.nextLine();
                if (scelta >= 1 && scelta <= puntiDiEmissione.size()) {
                    puntoDiEmissione = puntiDiEmissione.get(scelta - 1);
                    puntoValido = true;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        LocalDate dataInizio = null;
        LocalDate dataFine = null;
        boolean dateValide = false;

        while (!dateValide) {
            try {
                System.out.print("Inserisci la data di inizio (formato yyyy-mm-dd): ");
                String dataInizioStr = scanner.nextLine();
                dataInizio = LocalDate.parse(dataInizioStr);

                System.out.print("Inserisci la data di fine (formato yyyy-mm-dd): ");
                String dataFineStr = scanner.nextLine();
                dataFine = LocalDate.parse(dataFineStr);

                if (dataInizio.isAfter(dataFine)) {
                    System.out.println("Errore: la data di inizio non può essere successiva alla data di fine. Riprova.");
                } else {
                    dateValide = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato data non valido. Riprova.");
            }
        }

        try {
            List<Abbonamento> abbonamenti = ad.abbonamentoPerPuntoDiEmissione(puntoDiEmissione, dataInizio, dataFine);

            if (abbonamenti.isEmpty()) {
                System.out.println("Non ci sono abbonamenti venduti per il punto di emissione tra " + dataInizio + " e " + dataFine + "\n");
            } else {
                System.out.println("Abbonamenti dal " + dataInizio + " al " + dataFine + " presso " + puntoDiEmissione.getNome_punto() + ":");
                abbonamenti.forEach(b -> {
                    System.out.println("- Abbonamento ID: " + b.getId_abbonamento() + ", Data emissione: " + b.getData_inizio() + " - " + b.getPeriodicità());
                });
            }
        } catch (BigliettoNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Errore durante il recupero degli abbonamenti: " + e.getMessage());
        }
    }
}
