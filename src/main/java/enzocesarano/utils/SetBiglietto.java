package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.Biglietto;
import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.PuntoDiEmissione;
import enzocesarano.entities.ValidazioneBiglietto;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class SetBiglietto {

    public static void AcquistaBiglietto(Scanner scanner, DefaultDAO get) {
        LocalDate dataEmissione = LocalDate.now();
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

        Biglietto biglietto1 = new Biglietto(dataEmissione, false, puntoDiEmissione, null);
        get.save(biglietto1);
        System.out.println("\nIl biglietto " + biglietto1.getId_biglietto() + " acquistato con successo!\n");
    }

    public static void ValidareBiglietto(Scanner scanner, DefaultDAO get) {
        Biglietto biglietto = null;
        boolean bigliettoValido = false;

        while (!bigliettoValido) {
            System.out.println("\nInserisci l'ID del biglietto da validare: ");
            try {
                String idBiglietto = scanner.nextLine();
                biglietto = get.getEntityById(Biglietto.class, idBiglietto);

                if (biglietto != null && !biglietto.isConvalidato()) {
                    biglietto.setConvalidato(true);
                    bigliettoValido = true;
                } else if (biglietto == null) {
                    System.out.println("Biglietto non trovato. Riprova.");
                } else {
                    System.out.println("Il biglietto è già stato convalidato.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("ID non valido. Inserisci un UUID corretto.");
            }
        }

        Mezzo mezzo = null;
        List<Mezzo> mezziDisponibili = get.getAllEntities(Mezzo.class);

        List<Mezzo> mezziInServizio = mezziDisponibili.stream()
                .filter(m -> m.getStatoMezzo() == StatoMezzo.SERVIZIO && m.getTratta() != null)
                .toList();

        if (mezziInServizio.isEmpty()) {
            System.out.println("Non ci sono mezzi attualmente in servizio.");
            return;
        }

        System.out.println("\nSeleziona un mezzo in servizio:");
        mezziInServizio.forEach(m -> System.out.println((mezziInServizio.indexOf(m) + 1) + ". " + m.getTipo_mezzo() + "  (Tratta: " + m.getTratta().getZonaPartenza() + " - " + m.getTratta().getCapolinea() + ")"));

        int sceltaMezzo = -1;
        boolean mezzoValido = false;
        while (!mezzoValido) {
            System.out.print("Inserisci il numero del mezzo: ");
            try {
                sceltaMezzo = scanner.nextInt();
                scanner.nextLine();
                if (sceltaMezzo >= 1 && sceltaMezzo <= mezziInServizio.size()) {
                    mezzo = mezziInServizio.get(sceltaMezzo - 1);
                    mezzoValido = true;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        LocalDate dataValidazione = LocalDate.now();

        ValidazioneBiglietto validazioneBiglietto = new ValidazioneBiglietto(biglietto, mezzo, dataValidazione);
        get.save(validazioneBiglietto);
        System.out.println("\nIl biglietto " + biglietto.getId_biglietto() + " è stato validato con successo!\n");
    }
}
