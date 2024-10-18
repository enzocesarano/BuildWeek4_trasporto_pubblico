package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.PercorrenzaDAO;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.Percorrenza;
import enzocesarano.entities.Tratta;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SetPercorrenza {
    public static void CreaPercorrenza(DefaultDAO dd, Scanner scanner) {
        LocalDate data = LocalDate.now();
        int tempoEffettivo = 0;
        Tratta tratta = null;

        List<Tratta> tratte = dd.getAllEntities(Tratta.class);

        List<Tratta> tratteAssegnate = tratte.stream()
                .filter(m -> m.getMezzo() != null).toList();

        if (tratteAssegnate.isEmpty()) {
            System.out.println("Non ci sono tratte non assegnate.");
            return;
        }

        System.out.println("Seleziona la tratta: ");
        tratteAssegnate.forEach(m -> System.out.println((tratteAssegnate.indexOf(m) + 1) + ". " + m.getZonaPartenza() + " - " + m.getCapolinea() + " Id: " + m.getId_tratta()));

        int sceltaTratta = -1;
        boolean trattaValida = false;
        while (!trattaValida) {
            System.out.print("Inserisci il numero della tratta: ");
            try {
                sceltaTratta = scanner.nextInt();
                scanner.nextLine();
                if (sceltaTratta >= 1 && sceltaTratta <= tratteAssegnate.size()) {
                    tratta = tratteAssegnate.get(sceltaTratta - 1);
                    trattaValida = true;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        boolean input = false;

        while (!input) {
            try {
                System.out.print("Inserisci il tempo effettivo di percorrenza (in minuti): ");
                tempoEffettivo = scanner.nextInt();
                scanner.nextLine();
                if (tempoEffettivo <= 0) {
                    throw new IllegalArgumentException("Il tempo effettivo deve essere maggiore di zero.");
                }
                input = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        Percorrenza percorrenza = new Percorrenza(data, tempoEffettivo, tratta);
        dd.save(percorrenza);
        System.out.println("Percorrenza salvata con successo!");
        System.out.println("Data percorrenza: " + percorrenza.getData());
        System.out.println("Tempo effettivo: " + percorrenza.getTempoEffettivo() + " minuti");
        System.out.println("Mezzo: " + percorrenza.getMezzo().getTipo_mezzo() + " - " + percorrenza.getMezzo().getId_mezzo());
        System.out.println("Tratta: " + tratta.getZonaPartenza() + " - " + tratta.getCapolinea());
    }

    public static void ListaPercorrenze(Scanner scanner, DefaultDAO get, PercorrenzaDAO pd) {
        Mezzo mezzo = null;
        List<Mezzo> mezziDisponibili = get.getAllEntities(Mezzo.class);

        System.out.println("Seleziona un mezzo :");
        mezziDisponibili.forEach(m -> System.out.println((mezziDisponibili.indexOf(m) + 1) + ". " + m.getTipo_mezzo() + " - (" + m.getStatoMezzo().toString().toLowerCase() + ") - Tratta: " + (m.getTratta() != null ? (m.getTratta().getZonaPartenza() + " - " + m.getTratta().getCapolinea()) : "Nessuna tratta per questo mezzo")));

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

        Tratta trattaAssociata = mezzo.getTratta();
        if (trattaAssociata != null) {
            List<Percorrenza> percorrenze = pd.listaPercorrenze(mezzo.getId_mezzo(), trattaAssociata.getId_tratta());
            if (!percorrenze.isEmpty()) {
                System.out.println("\nLista delle percorrenze per la tratta del mezzo selezionato:\n");
                System.out.println("Tratta: " + trattaAssociata.getZonaPartenza() + " - " + trattaAssociata.getCapolinea() + "\n");
                percorrenze.forEach(p -> System.out.println("Data: " + p.getData() + ", Tempo Effettivo: " + p.getTempoEffettivo() + " minuti."));
                System.out.println("\n");
            } else {
                System.out.println("\nNon ci sono percorrenze disponibili per questa tratta.\n");
            }
        } else {
            System.out.println("\nErrore: Nessuna tratta associata al mezzo selezionato.\n");
        }
    }

    public static void MediaPercorrenza(Scanner scanner, DefaultDAO get, PercorrenzaDAO pd) {
        Mezzo mezzo = null;
        List<Mezzo> mezziDisponibili = get.getAllEntities(Mezzo.class);

        System.out.println("Seleziona un mezzo :");
        mezziDisponibili.forEach(m -> System.out.println((mezziDisponibili.indexOf(m) + 1) + ". " + m.getTipo_mezzo() + " - (" + m.getStatoMezzo().toString().toLowerCase() + ") - Tratta: " + (m.getTratta() != null ? (m.getTratta().getZonaPartenza() + " - " + m.getTratta().getCapolinea()) : "Nessuna tratta per questo mezzo")));

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

        Tratta trattaAssociata = mezzo.getTratta();
        if (trattaAssociata != null) {
            double mediaTempoPercorrenze = pd.calcolaMediaPercorrenze(trattaAssociata.getId_tratta().toString());
            System.out.println("\nTratta: " + trattaAssociata.getZonaPartenza() + " - " + trattaAssociata.getCapolinea() + "\n");
            System.out.println("\nIl tempo medio delle percorrenze per la tratta del mezzo selezionato è: " + mediaTempoPercorrenze + " minuti.\n");
        } else {
            System.out.println("Errore: Nessuna tratta associata al mezzo selezionato.");
        }
    }
}
