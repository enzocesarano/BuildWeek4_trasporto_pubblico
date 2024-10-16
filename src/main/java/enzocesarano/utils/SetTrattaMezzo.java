package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.TrattaMezziDAO;
import enzocesarano.entities.Percorrenza;
import enzocesarano.entities.Tratta;

import java.time.LocalTime;
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


                boolean tempoEffettivoValido = false;
                while (!tempoEffettivoValido) {

                    System.out.println("Inserisci il tempo effettivo di percorrenza (HH:mm): ");
                    String tempoEffettivoInput = scanner.nextLine();

                    try {
                        LocalTime tempoEffettivo = LocalTime.parse(tempoEffettivoInput);

                        tmD.registraPercorrenza(mezzoId, tratta.getId_tratta(), tempoEffettivo);
                        tempoEffettivoValido = true;

                        int numeroDiPercorrenze = tmD.contaPercorrenze(mezzoId, tratta.getId_tratta());
                        System.out.println("Numero di percorrenze totali per questa tratta: " + numeroDiPercorrenze);

                        List<Percorrenza> percorrenze = tmD.trovaPercorrenze(mezzoId, tratta.getId_tratta());
                        System.out.println("Dettagli delle percorrenze trovate per questa tratta: ");
                        for (Percorrenza percorrenza : percorrenze) {
                            System.out.println("UUID percorrenza: " + percorrenza.getId_percorrenza());
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Formato tempo non valido. Usa il formato HH:mm. Riprova.");
                    }
                }
            }
        }
    }
}
