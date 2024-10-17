package enzocesarano.utils;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.ENUM.TipoMezzo;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.Tratta;

import java.util.List;
import java.util.Scanner;

public class SetMezzo {
    public static void CreaMezzo(Scanner scanner, DefaultDAO dd) {
        TipoMezzo tipo_mezzo = null;
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

        boolean puntoValido = false;
        Tratta tratta = null;
        List<Tratta> tratte = dd.getAllEntities(Tratta.class);

        System.out.println("Seleziona una tratta:");
        tratte.forEach(p -> System.out.println((tratte.indexOf(p) + 1) + ". " + p.getZonaPartenza() + " - " + p.getId_tratta()));

        int scelta = -1;
        while (!puntoValido) {
            System.out.print("Inserisci il numero del punto di emissione: ");
            try {
                scelta = scanner.nextInt();
                scanner.nextLine();
                if (scelta >= 1 && scelta <= tratte.size()) {
                    tratta = tratte.get(scelta - 1);
                    puntoValido = true;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        Mezzo nuovoMezzo = new Mezzo(tipo_mezzo, statoMezzo, tratta);
        dd.save(nuovoMezzo);

        tratta.setMezzo(nuovoMezzo);
        dd.update(tratta);
        
        System.out.println("Nuovo mezzo creato con successo! " + nuovoMezzo.getTipo_mezzo() + " " + nuovoMezzo.getId_mezzo() + ", Tratta: " + nuovoMezzo.getTratta().getZonaPartenza() + " - " + nuovoMezzo.getTratta().getCapolinea());
    }
}
