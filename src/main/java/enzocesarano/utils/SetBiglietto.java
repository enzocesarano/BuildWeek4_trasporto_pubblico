package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.Biglietto;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.PuntoDiEmissione;
import enzocesarano.entities.ValidazioneBiglietto;

import java.time.LocalDate;
import java.util.Scanner;


public class SetBiglietto {

    public static void AcquistaBiglietto(Scanner scanner, DefaultDAO get) {
        LocalDate dataEmissione = LocalDate.now();

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

        Biglietto biglietto1 = new Biglietto(dataEmissione, false, puntoDiEmissione, null);
        get.save(biglietto1);
        System.out.println("Il biglietto " + biglietto1.getId_biglietto() + " acquistato con successo!\n");
    }

    public static void ValidareBiglietto(Scanner scanner, DefaultDAO get) {
        Biglietto biglietto = null;
        boolean bigliettoValido = false;

        while (!bigliettoValido) {
            System.out.println("Inserisci l'ID del biglietto da validare: ");
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
        boolean mezzoValido = false;

        while (!mezzoValido) {
            System.out.println("Inserisci l'ID del mezzo: ");
            try {
                String idMezzo = scanner.nextLine();
                mezzo = get.getEntityById(Mezzo.class, idMezzo);

                if (mezzo != null) {
                    mezzoValido = true;
                } else {
                    System.out.println("Mezzo non trovato. Riprova.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("ID non valido. Inserisci un UUID corretto.");
            }
        }

        LocalDate dataValidazione = LocalDate.now();

        ValidazioneBiglietto validazioneBiglietto = new ValidazioneBiglietto(biglietto, mezzo, dataValidazione);
        get.save(validazioneBiglietto);
        System.out.println("Il biglietto " + biglietto.getId_biglietto() + " è stato validato con successo!\n");
    }
}
