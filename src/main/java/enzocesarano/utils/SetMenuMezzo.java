package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.TrattaMezziDAO;

import java.util.Scanner;

public class SetMenuMezzo {
    public static void MenuMezzo(Scanner scanner, DefaultDAO dd, TrattaMezziDAO tmd) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nSeleziona un utente oppure registrati:");
            System.out.println("1. Crea Mezzo");
            System.out.println("2. Crea Tratta");
            System.out.println("3. Cancella Tratta");
            System.out.println("4. Assegna Tratta");
            System.out.println("5. Visualizza Tratta");
            System.out.println("0. Esci");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    SetMezzo.CreaMezzo(scanner, dd);
                    break;
                case 2:
                    SetTrattaMezzo.CreaTratta(scanner, dd);
                    break;
                case 3:
                    SetTrattaMezzo.CancellaTratta(scanner, dd);
                    break;
                case 4:
                    SetMezzo.assegnaTratta(scanner, dd);
                    break;
                case 5:
                    SetTrattaMezzo.InserisciTrattaMezzo(scanner, dd, tmd);
                    break;
                case 0:
                    exit = true;
                    System.out.println("Uscita dalle opzioni.");
                    break;

                default:
                    System.out.println("Opzione non valida.");
                    break;
            }
        }
    }
}
