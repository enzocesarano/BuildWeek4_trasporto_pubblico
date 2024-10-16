package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.PuntoDiEmissione;
import enzocesarano.entities.Tessera;
import enzocesarano.entities.Utenti;

import java.time.LocalDate;
import java.util.Scanner;

public class SetTessera {

    public static void AcquistaTessera(Scanner scanner, DefaultDAO get, Utenti utente1) {
        LocalDate dataEmissione = LocalDate.now();
        boolean puntoValido = false;

        PuntoDiEmissione puntoDiEmissione = null;
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

        Tessera nuovaTessera = new Tessera(dataEmissione, utente1, null);
        get.save(nuovaTessera);
        System.out.println("Tessera " + nuovaTessera.getId_tessera() + " acquistata con successo!");
    }
}
