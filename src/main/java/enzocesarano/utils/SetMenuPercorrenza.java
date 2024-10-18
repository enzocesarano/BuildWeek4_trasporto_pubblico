package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.PercorrenzaDAO;

import java.util.Scanner;

public class SetMenuPercorrenza {

    public static void MenuPercorrenza(Scanner scanner, DefaultDAO dd, PercorrenzaDAO pd) {
        boolean exitUtente = false;
        while (!exitUtente) {
            System.out.println("\nScegli una delle seguenti opzioni:");
            System.out.println("1. Lista Percorrenze");
            System.out.println("2. Media Percorrenze per tratta");
            System.out.println("3. Aggiungi Percorrenza");
            System.out.println("0. Esci\n");
            int subScelta = scanner.nextInt();
            scanner.nextLine();
            switch (subScelta) {
                case 1:
                    SetPercorrenza.ListaPercorrenze(scanner, dd, pd);
                    break;
                case 2:
                    SetPercorrenza.MediaPercorrenza(scanner, dd, pd);
                    break;
                case 3:
                    SetPercorrenza.CreaPercorrenza(dd, scanner);
                    break;
                case 0:
                    exitUtente = true;
                    System.out.println("\nUscita dal menu utente.");
                    break;
                default:
                    System.out.println("\nOpzione non valida. Riprova.\n");
                    break;
            }
        }
    }


}
