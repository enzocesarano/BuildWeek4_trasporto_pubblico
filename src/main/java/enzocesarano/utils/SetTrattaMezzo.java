package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.PercorrenzaDAO;
import enzocesarano.dao.TrattaMezziDAO;
import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.Percorrenza;
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


    public static void CreaTratta(Scanner scanner, DefaultDAO dd) {
        String zonaPartenza = "";
        String capolinea = "";
        int tempoPrevisto = 0;

        boolean input = false;

        while (!input) {
            try {
                System.out.print("Inserisci la zona di partenza: ");
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


}