package enzocesarano.utils.utenti;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.ENUM.TipoUtente;
import enzocesarano.entities.Utenti;
import enzocesarano.utils.SetAbbonamento;
import enzocesarano.utils.SetBiglietto;
import enzocesarano.utils.SetTessera;
import jakarta.persistence.EntityManager;

import java.util.Scanner;

public class SetMenuUtente {
    public static void gestisciUtente(Scanner scanner, DefaultDAO dd) {
        EntityManager em = dd.getEntityManager();
        Utenti utente1 = null;
        boolean idValido = false;
        while (!idValido) {
            System.out.println("Inserisci il tuo id: ");
            String idUtente = scanner.nextLine();
            utente1 = dd.getEntityById(Utenti.class, idUtente);
            try {
                if (utente1 != null && utente1.getTipoUtente() == TipoUtente.NORMALE) {
                    System.out.println("Benvenuto " + utente1.getNome() + " " + utente1.getCognome());
                    idValido = true;
                } else {
                    System.out.println("Utente non trovato. Riprova.");
                }
            } catch (Exception e) {
                System.out.println("ID non valido. Assicurati di inserire un ID valido.");
            }
        }

        boolean exitUtente = false;
        while (!exitUtente) {
            System.out.println("Scegli una delle seguenti opzioni:");
            System.out.println("1. Acquista Biglietto");
            System.out.println("2. Convalida Biglietto");
            System.out.println("3. Acquista Tessera");
            System.out.println("4. Abbonamento");
            System.out.println("0. Esci");

            int subScelta = scanner.nextInt();
            scanner.nextLine();

            switch (subScelta) {
                case 1:
                    SetBiglietto.AcquistaBiglietto(scanner, dd);
                    break;
                case 2:
                    SetBiglietto.ValidareBiglietto(scanner, dd);
                    break;
                case 3:
                    em.refresh(utente1);
                    if (utente1.getTessera() == null) {
                        SetTessera.AcquistaTessera(scanner, dd, utente1);
                    } else {
                        System.out.println("Hai già una tessera.");
                    }
                    break;
                case 4:
                    em.refresh(utente1);
                    if (utente1.getTessera() != null && utente1.getTessera().getAbbonamenti().isEmpty()) {
                        SetAbbonamento.AcquistaAbbonamento(scanner, dd, utente1);
                    } else if (utente1.getTessera() == null) {
                        System.out.println("Non hai una tessera. Acquista una tessera prima di fare l'abbonamento.");
                    } else if (!utente1.getTessera().getAbbonamenti().isEmpty()) {
                        System.out.println("Hai già un abbonamento.");
                    }
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
