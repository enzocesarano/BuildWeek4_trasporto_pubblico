package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.TrattaMezziDAO;
import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.Tratta;

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
}