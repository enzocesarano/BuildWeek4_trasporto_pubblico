package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.Abbonamento;
import enzocesarano.entities.ENUM.Periodicità;
import enzocesarano.entities.PuntoDiEmissione;
import enzocesarano.entities.Tessera;
import enzocesarano.entities.Utenti;

import java.time.LocalDate;
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
        tessera.getAbbonamenti().add(abbonamento);
        get.save(abbonamento);
        System.out.println("L'abbonamento per la tessera " + utente.getTessera() + " è stato acquistato con successo!\n");

    }
}
