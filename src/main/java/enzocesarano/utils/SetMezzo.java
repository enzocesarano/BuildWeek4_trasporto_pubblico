package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.ENUM.TipoMezzo;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.Tratta;

import java.util.List;
import java.util.Scanner;

public class SetMezzo {
    public static void CreaMezzo(Scanner scanner, DefaultDAO dd) {
        TipoMezzo tipo_mezzo = null;
        StatoMezzo statoMezzo = StatoMezzo.SERVIZIO;

        boolean inputValido = false;

        while (!inputValido) {
            System.out.print("Inserisci il tipo di mezzo:\n");
            System.out.println("1. TRAM");
            System.out.println("2. BUS");
            try {
                int nuovaScelta = scanner.nextInt();
                scanner.nextLine();

                switch (nuovaScelta) {
                    case 1:
                        tipo_mezzo = TipoMezzo.TRAM;
                        break;
                    case 2:
                        tipo_mezzo = TipoMezzo.BUS;
                        break;
                    default:
                        System.out.println("Scelta non valida. Seleziona un numero tra 1 e 2.");
                        continue;
                }
                inputValido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }

        boolean puntoValido = false;
        Tratta tratta = null;
        List<Tratta> tratte = dd.getAllEntities(Tratta.class);

        System.out.println("Seleziona una tratta (oppure digita '0' per Nessuna):");
        System.out.println("0. Nessuna");
        tratte.forEach(p -> System.out.println((tratte.indexOf(p) + 1) + ". " + p.getZonaPartenza() + " - " + p.getCapolinea()));

        int scelta = -1;
        while (!puntoValido) {
            try {
                scelta = scanner.nextInt();
                scanner.nextLine();
                if (scelta == 0) {
                    tratta = null;
                    puntoValido = true;
                } else if (scelta >= 1 && scelta <= tratte.size()) {
                    tratta = tratte.get(scelta - 1);
                    puntoValido = true;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        Mezzo nuovoMezzo = new Mezzo(tipo_mezzo, statoMezzo, tratta);
        dd.save(nuovoMezzo);

        if (tratta != null) {
            tratta.setMezzo(nuovoMezzo);
            dd.update(tratta);
        }

        String trattaInfo = (nuovoMezzo.getTratta() != null)
                ? nuovoMezzo.getTratta().getZonaPartenza() + " - " + nuovoMezzo.getTratta().getCapolinea()
                : "Nessuna tratta associata";
        System.out.println("Nuovo mezzo creato con successo! " + nuovoMezzo.getTipo_mezzo() + " " + nuovoMezzo.getId_mezzo() + ", Tratta: " + trattaInfo);
    }

    public static void assegnaTratta(Scanner scanner, DefaultDAO dd) {
        Mezzo mezzo = null;
        List<Mezzo> mezziDisponibili = dd.getAllEntities(Mezzo.class);

        List<Mezzo> mezziSenzaTratta = mezziDisponibili.stream()
                .filter(m -> m.getTratta() == null)
                .toList();

        if (mezziSenzaTratta.isEmpty()) {
            System.out.println("Non ci sono mezzi senza Tratta.");
            return;
        }

        System.out.println("Seleziona un mezzo senza Tratta:");
        mezziSenzaTratta.forEach(m -> System.out.println((mezziSenzaTratta.indexOf(m) + 1) + ". " + m.getTipo_mezzo() + " - (" + m.getId_mezzo() + ")"));

        int sceltaMezzo = -1;
        boolean mezzoValido = false;
        while (!mezzoValido) {
            System.out.print("Inserisci il numero del mezzo: ");
            try {
                sceltaMezzo = scanner.nextInt();
                scanner.nextLine();
                if (sceltaMezzo >= 1 && sceltaMezzo <= mezziSenzaTratta.size()) {
                    mezzo = mezziSenzaTratta.get(sceltaMezzo - 1);
                    mezzoValido = true;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        Tratta tratta = null;
        List<Tratta> tratte = dd.getAllEntities(Tratta.class);
        List<Tratta> tratteNonAssegnate = tratte.stream()
                .filter(m -> m.getMezzo() == null).toList();

        if (tratteNonAssegnate.isEmpty()) {
            System.out.println("Non ci sono tratte non assegnate.");
            return;
        }

        System.out.println("Seleziona la tratta da assegnare al mezzo selezionato: ");
        tratteNonAssegnate.forEach(m -> System.out.println((tratteNonAssegnate.indexOf(m) + 1) + ". " + m.getZonaPartenza() + " - " + m.getCapolinea() + " Id: " + m.getId_tratta()));

        int sceltaTratta = -1;
        boolean trattaValida = false;
        while (!trattaValida) {
            System.out.print("Inserisci il numero della tratta: ");
            try {
                sceltaTratta = scanner.nextInt();
                scanner.nextLine();
                if (sceltaTratta >= 1 && sceltaTratta <= mezziSenzaTratta.size()) {
                    tratta = tratteNonAssegnate.get(sceltaTratta - 1);
                    trattaValida = true;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        mezzo.setTratta(tratta);
        dd.update(mezzo);

        tratta.setMezzo(mezzo);
        dd.update(tratta);

        System.out.println("Hai assegnato la tratta con successo! " + mezzo.getTipo_mezzo() + " " + mezzo.getId_mezzo() + ", Tratta: " + tratta.getZonaPartenza() + " - " + tratta.getCapolinea());

    }
}
