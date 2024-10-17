package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.TrattaMezziDAO;
import enzocesarano.entities.Tratta;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class SetTrattaMezzo {
    public static void InserisciTrattaMezzo(Scanner scanner, DefaultDAO get, TrattaMezziDAO tmD) {
        UUID mezzoId = null;
        boolean mezzoIdValido = false;

        while (!mezzoIdValido) {
            System.out.println("Inserisci l'ID del mezzo:");
            String mezzoIdInput = scanner.nextLine();

            try {
                mezzoId = UUID.fromString(mezzoIdInput);
                mezzoIdValido = true;
            } catch (IllegalArgumentException e) {
                System.out.println("ID del mezzo non valido. Inserisci un UUID corretto.");
            }
        }

        List<Tratta> tratte = tmD.getTrattaMezzi(mezzoId);

        if (tratte.isEmpty()) {
            System.out.println("Nessuna tratta trovata per il mezzo con ID: " + mezzoId);
        } else {
            for (Tratta tratta : tratte) {
                System.out.println("ID Tratta: " + tratta.getId_tratta());
                System.out.println("Zona Partenza: " + tratta.getZonaPartenza());
                System.out.println("Capolinea: " + tratta.getCapolinea());
                System.out.println("Tempo Previsto: " + tratta.getTempoPrevisto() + " minuti");


            }
        }
    }
}
