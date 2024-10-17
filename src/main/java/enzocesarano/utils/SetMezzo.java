package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.ENUM.TipoMezzo;
import enzocesarano.entities.Mezzo;

import java.util.Scanner;

public class SetMezzo {
    public static void CreaMezzo(Scanner scanner, DefaultDAO dd) {
        TipoMezzo tipo_mezzo = null;
        int capienza = 0;
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
                }
                inputValido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        inputValido = false;
        while (!inputValido) {
            System.out.println("Inserisci la capienza del mezzo: ");
            try {
                capienza = scanner.nextInt();
                inputValido = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Inserisci un numero intero.");
            }
        }

        Mezzo nuovoMezzo = new Mezzo(tipo_mezzo, capienza, statoMezzo);
        dd.save(nuovoMezzo);
        System.out.println("Nuovo utente creato con successo! " + nuovoMezzo.getTipo_mezzo() + " " + nuovoMezzo.getId_mezzo());
    }
}
