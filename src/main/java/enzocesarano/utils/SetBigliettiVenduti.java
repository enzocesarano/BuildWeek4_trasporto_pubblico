package enzocesarano.utils;

import enzocesarano.dao.BigliettoDAO;
import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.Biglietto;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.PuntoDiEmissione;
import exceptions.BigliettoNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class SetBigliettiVenduti {
    public static void BigliettiVendutiPerPunto(DefaultDAO get, Scanner scanner, BigliettoDAO bd) {
        boolean puntoValido = false;
        PuntoDiEmissione puntoDiEmissione = null;
        List<PuntoDiEmissione> puntiDiEmissione = get.getAllEntities(PuntoDiEmissione.class);

        System.out.println("Seleziona un punto di emissione:");
        puntiDiEmissione.forEach(p -> System.out.println((puntiDiEmissione.indexOf(p) + 1) + ". " + p.getNome_punto() + " - " + p.getId_punto_emissione()));

        int scelta = -1;
        while (!puntoValido) {
            System.out.print("Inserisci il numero del punto di emissione: ");
            try {
                scelta = scanner.nextInt();
                scanner.nextLine();
                if (scelta >= 1 && scelta <= puntiDiEmissione.size()) {
                    puntoDiEmissione = puntiDiEmissione.get(scelta - 1);
                    puntoValido = true;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        LocalDate dataInizio = null;
        LocalDate dataFine = null;
        boolean dateValide = false;

        while (!dateValide) {
            try {
                System.out.print("Inserisci la data di inizio (formato yyyy-mm-dd): ");
                String dataInizioStr = scanner.nextLine();
                dataInizio = LocalDate.parse(dataInizioStr);

                System.out.print("Inserisci la data di fine (formato yyyy-mm-dd): ");
                String dataFineStr = scanner.nextLine();
                dataFine = LocalDate.parse(dataFineStr);

                if (dataInizio.isAfter(dataFine)) {
                    System.out.println("Errore: la data di inizio non può essere successiva alla data di fine. Riprova.");
                } else {
                    dateValide = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato data non valido. Riprova.");
            }
        }

        try {
            List<Biglietto> biglietti = bd.bigliettiPerPuntoEPeriodo(puntoDiEmissione, dataInizio, dataFine);
            long biglietti1 = bd.totBigliettiPerPEmissione(puntoDiEmissione, dataInizio, dataFine);

            if (biglietti.isEmpty()) {
                System.out.println("Non ci sono biglietti venduti per il punto di emissione tra " + dataInizio + " e " + dataFine + "\n");
            } else {
                System.out.println("Biglietti venduti dal " + dataInizio + " al " + dataFine + " presso " + puntoDiEmissione.getNome_punto() + ":");
                biglietti.forEach(b -> {
                    System.out.println("- Biglietto ID: " + b.getId_biglietto() + ", Data emissione: " + b.getData_emissione() + " - " + (b.isConvalidato() ? "Validato" : "Non Validato"));
                });
                System.out.println("\nNumero di biglietti venduti presso " + puntoDiEmissione.getNome_punto() + ": " + biglietti1 + "\n");

            }
        } catch (BigliettoNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Errore durante il recupero dei biglietti: " + e.getMessage());
        }
    }

    public static void BigliettiValidati(DefaultDAO get, Scanner scanner, BigliettoDAO bd) {
        boolean mezzoValido = false;
        Mezzo mezzo = null;

        List<Mezzo> mezziList = get.getAllEntities(Mezzo.class);

        if (mezziList.isEmpty()) {
            System.out.println("Non ci sono mezzi disponibili.");
            return;
        }

        System.out.println("Seleziona un mezzo:");
        mezziList.forEach(m -> System.out.println((mezziList.indexOf(m) + 1) + ". " + m.getTipo_mezzo() + " - " + m.getId_mezzo()));

        int scelta = -1;
        while (!mezzoValido) {
            System.out.print("Inserisci il numero del mezzo: ");
            try {
                scelta = scanner.nextInt();
                scanner.nextLine();
                if (scelta >= 1 && scelta <= mezziList.size()) {
                    mezzo = mezziList.get(scelta - 1);
                    mezzoValido = true;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("Errore: inserisci un numero valido.");
                scanner.nextLine();
            }
        }

        try {
            List<Biglietto> biglietti = bd.bigliettiValidatiPerMezzo(mezzo);

            if (biglietti.isEmpty()) {
                System.out.println("Non ci sono biglietti validati per il mezzo " + mezzo.getTipo_mezzo() + ".\n");
            } else {
                System.out.println("Biglietti validati per il mezzo " + mezzo.getTipo_mezzo() + ":");
                biglietti.forEach(b -> {
                    System.out.println("- Biglietto ID: " + b.getId_biglietto() + ", Data emissione: " + b.getData_emissione() + " - " + (b.isConvalidato() ? "Validato" : "Non Validato"));
                });
            }
        } catch (BigliettoNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Errore durante il recupero dei biglietti: " + e.getMessage());
        }
    }


}
