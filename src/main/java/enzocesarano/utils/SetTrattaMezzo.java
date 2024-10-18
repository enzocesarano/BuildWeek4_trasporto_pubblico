package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.TrattaMezziDAO;
import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.Tratta;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SetTrattaMezzo {
    public static void InserisciTrattaMezzo(Scanner scanner, DefaultDAO get, TrattaMezziDAO tmD) {
        Mezzo mezzo = null;
        List<Mezzo> mezziDisponibili = get.getAllEntities(Mezzo.class);

        System.out.println("Seleziona un mezzo :");
        mezziDisponibili.forEach(m -> System.out.println((mezziDisponibili.indexOf(m) + 1) + ". " + m.getTipo_mezzo() + " - (" + m.getStatoMezzo().toString().toLowerCase() + ") - " + m.getId_mezzo()));

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

        if (mezzo.getStatoMezzo() == StatoMezzo.MANUTENZIONE) {
            System.out.println("Il mezzo con ID " + mezzo.getId_mezzo() + " è attualmente in manutenzione e le tratte non possono essere visualizzate.");
            return;
        }

        List<Tratta> tratte = tmD.getTrattaMezzi(mezzo.getId_mezzo());

        if (tratte.isEmpty()) {
            System.out.println("Nessuna tratta trovata per il mezzo con ID: " + mezzo.getId_mezzo());
        } else {
            for (Tratta tratta : tratte) {
                System.out.println("\nID Tratta: " + tratta.getId_tratta());
                System.out.println("Zona Partenza: " + tratta.getZonaPartenza());
                System.out.println("Capolinea: " + tratta.getCapolinea());
                System.out.println("Tempo Previsto: " + tratta.getTempoPrevisto() + " minuti\n");
            }
        }
    }


    public static void CreaTratta(Scanner scanner, DefaultDAO dd) {
        String zonaPartenza = "";
        String capolinea = "";
        int tempoPrevisto = 0;

        boolean input = false;

        while (!input) {
            try {
                System.out.print("\nInserisci la zona di partenza: ");
                zonaPartenza = scanner.nextLine();
                if (zonaPartenza.trim().isEmpty()) {
                    throw new IllegalArgumentException("Il campo non può essere vuoto.");
                }
                input = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        input = false;

        while (!input) {
            try {
                System.out.print("Inserisci il capolinea: ");
                capolinea = scanner.nextLine();
                if (capolinea.trim().isEmpty()) {
                    throw new IllegalArgumentException("Il campo non può essere vuoto.");
                }
                input = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        input = false;

        while (!input) {
            try {
                System.out.print("Inserisci il tempo previsto di percorrenza (in minuti): ");
                tempoPrevisto = scanner.nextInt();
                scanner.nextLine();
                if (tempoPrevisto <= 0) {
                    throw new IllegalArgumentException("Il tempo previsto deve essere maggiore di zero.");
                }
                input = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        Mezzo mezzo = null;

        Tratta tratta = new Tratta(zonaPartenza, capolinea, tempoPrevisto, mezzo);
        dd.save(tratta);

        System.out.println("\nTratta: " + tratta.getZonaPartenza() + " - " + tratta.getCapolinea() + " creata con successo!\n");
    }

    public static void CancellaTratta(Scanner scanner, DefaultDAO dd) {
        Tratta tratta = null;
        List<Tratta> tratte = dd.getAllEntities(Tratta.class);

        if (tratte.isEmpty()) {
            System.out.println("Non ci sono tratte non assegnate.");
            return;
        }

        System.out.println("Seleziona la tratta da assegnare al mezzo selezionato: ");
        tratte.forEach(m -> System.out.println((tratte.indexOf(m) + 1) + ". " + m.getZonaPartenza() + " - " + m.getCapolinea() + " Id: " + m.getId_tratta()));

        int sceltaTratta = -1;
        boolean trattaValida = false;
        while (!trattaValida) {
            System.out.print("Inserisci il numero della tratta: ");
            try {
                sceltaTratta = scanner.nextInt();
                scanner.nextLine();
                if (sceltaTratta >= 1 && sceltaTratta <= tratte.size()) {
                    tratta = tratte.get(sceltaTratta - 1);
                    trattaValida = true;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        tratta.getMezzo().setTratta(null);
        dd.update(tratta);
        dd.delete(Tratta.class, tratta.getId_tratta().toString());

        System.out.println("\nLa tratta " + tratta.getZonaPartenza() + " - " + tratta.getCapolinea() + " è stata eliminata con successo!\n");
    }

}