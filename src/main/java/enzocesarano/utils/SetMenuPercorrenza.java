package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.PercorrenzaDAO;

import java.util.Scanner;

public class SetMenuPercorrenza {

    public static void MenuPercorrenza(Scanner scanner, DefaultDAO dd, PercorrenzaDAO pd) {
        boolean exitUtente = false;
        while (!exitUtente) {
            System.out.println("Scegli una delle seguenti opzioni:");
            System.out.println("1. Lista Percorrenze");
            System.out.println("2. Media Percorrenze per tratta");
            System.out.println("0. Esci");
            int subScelta = scanner.nextInt();
            scanner.nextLine();
            switch (subScelta) {
                case 1:
                    SetTrattaMezzo.ListaPercorrenze(scanner, dd, pd);
                    break;
                case 2:
                    SetTrattaMezzo.MediaPercorrenza(scanner, dd, pd);
                    break;
                case 0:
                    exitUtente = true;
                    System.out.println("Uscita dal menu utente.");
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
                    break;
            }
        }
    }


}
