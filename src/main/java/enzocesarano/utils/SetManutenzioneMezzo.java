package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.Manutenzione;
import enzocesarano.entities.Mezzo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class SetManutenzioneMezzo {
    public static void SetManutenzione(Scanner scanner, DefaultDAO dd, Mezzo mezzo1) {
        LocalDate dataInizio = LocalDate.now();
        LocalDate dataFine = null;
        String motivo = null;
        boolean inputValido = false;

        while (!inputValido) {
            try {

                System.out.print("Inserisci la data di fine manutenzione (yyyy-mm-dd): ");
                String dataFineStr = scanner.nextLine();
                dataFine = LocalDate.parse(dataFineStr);

                if (dataFine.isBefore(dataInizio)) {
                    System.out.println("Errore: la data di fine non può essere precedente alla data di inizio. Riprova.");
                    continue;
                }

                System.out.print("Inserisci il motivo della manutenzione: ");
                motivo = scanner.nextLine();

                if (motivo.trim().isEmpty()) {
                    System.out.println("Errore: il motivo non può essere vuoto. Riprova.");
                    continue;
                }

                inputValido = true;

            } catch (DateTimeParseException e) {
                System.out.println("Errore: data non valida. Usa il formato yyyy-mm-dd.");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }

        Manutenzione manutenzione = new Manutenzione(mezzo1, dataInizio, dataFine, motivo);
        dd.save(manutenzione);
    }

}
