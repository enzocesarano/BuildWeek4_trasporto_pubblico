package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.Abbonamento;
import enzocesarano.entities.ENUM.Periodicità;
import enzocesarano.entities.PuntoDiEmissione;
import enzocesarano.entities.Tessera;
import enzocesarano.entities.Utenti;

import java.time.LocalDate;
import java.util.Scanner;

public class SetAbbonamento {
    public static void AcquistaAbbonamento(Scanner scanner, DefaultDAO get, Utenti utente) {
        LocalDate dataInizio = LocalDate.now();

        PuntoDiEmissione puntoDiEmissione = null;
        boolean puntoValido = false;

        while (!puntoValido) {
            System.out.println("Inserisci l'ID del punto di emissione: ");
            try {
                String idPunto = scanner.nextLine();
                puntoDiEmissione = get.getEntityById(PuntoDiEmissione.class, idPunto);

                if (puntoDiEmissione != null && puntoDiEmissione.isAttivo()) {
                    puntoValido = true;
                } else {
                    System.out.println("Punto di emissione non trovato o non attivo. Riprova.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("ID non valido. Inserisci un UUID corretto.");
            }
        }

        Periodicità periodicità = null;
        boolean periodicitàValida = false;
        while (!periodicitàValida) {
            System.out.println("Seleziona la periodicità dell'abbonamento: ");
            System.out.println("1. Settimanale");
            System.out.println("2. Mensile");

            int scelta = scanner.nextInt();
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

        Abbonamento abbonamento = new Abbonamento(false, dataInizio, utente, puntoDiEmissione, tessera, periodicità);
        get.save(abbonamento);
        System.out.println("L'abbonamento per la tessera " + utente.getTessera() + " è stato acquistato con successo!\n");

    }
}
