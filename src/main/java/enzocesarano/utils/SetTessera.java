package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.PuntoDiEmissione;
import enzocesarano.entities.Tessera;
import enzocesarano.entities.Utenti;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class SetTessera {

    public static void AcquistaTessera(Scanner scanner, DefaultDAO get, Utenti utente1) {
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

        Tessera nuovaTessera = new Tessera(dataEmissione, utente1, null);
        get.save(nuovaTessera);
        System.out.println("Tessera " + nuovaTessera.getId_tessera() + " acquistata con successo!");
    }

    public static void RinnovaTessera(Scanner scanner, DefaultDAO get, Utenti utente1) {
        Tessera tessera = utente1.getTessera();

        if (tessera != null && !tessera.isStato()) {
            LocalDate nuovaDataScadenza = LocalDate.now().plusYears(1);
            tessera.setData_scadenza(nuovaDataScadenza);
            tessera.setStato(true);
            get.update(tessera);
            System.out.println("Tessera " + utente1.getTessera().getId_tessera() + " rinnovata con successo! Nuova data di scadenza: " + tessera.getData_scadenza());
        }
    }

}
