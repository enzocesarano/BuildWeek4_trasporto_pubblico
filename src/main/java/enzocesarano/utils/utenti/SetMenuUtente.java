package enzocesarano.utils.utenti;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.TrattaMezziDAO;
import enzocesarano.entities.ENUM.TipoUtente;
import enzocesarano.entities.Utenti;
import enzocesarano.utils.SetAbbonamento;
import enzocesarano.utils.SetBiglietto;
import enzocesarano.utils.SetTessera;
import enzocesarano.utils.SetTrattaMezzo;
import jakarta.persistence.EntityManager;

import java.util.Scanner;

public class SetMenuUtente {
    public static void gestisciUtente(Scanner scanner, DefaultDAO dd, TrattaMezziDAO tmd) {
        EntityManager em = dd.getEntityManager();
        Utenti utente1 = null;
        boolean idValido = false;
        while (!idValido) {
            System.out.println("\nInserisci il tuo id: ");
            String idUtente = scanner.nextLine();
            utente1 = dd.getEntityById(Utenti.class, idUtente);
            try {
                if (utente1 != null && utente1.getTipoUtente() == TipoUtente.NORMALE) {
                    System.out.println("\nCiao " + utente1.getNome() + " " + utente1.getCognome() + " :)\n");
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
            System.out.println("3. Acquista o rinnova Tessera");
            System.out.println("4. Acquista o rinnova Abbonamento");
            System.out.println("5. Visualizza Tratta Mezzo");
            System.out.println("9. Visualizza Profilo Utente");
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
                    } else if (utente1.getTessera() != null && utente1.getTessera().isStato()) {
                        System.out.println("\nHai già una tessera attiva.\n");
                    } else if (!utente1.getTessera().isStato()) {
                        boolean inputValido = false;
                        while (!inputValido) {
                            System.out.println("\nLa tua tessera è scaduta. Vuoi rinnovarla? (S/N)");
                            String risposta = scanner.nextLine().trim().toUpperCase();

                            switch (risposta) {
                                case "S":
                                    SetTessera.RinnovaTessera(scanner, dd, utente1);
                                    inputValido = true;
                                    break;

                                case "N":
                                    System.out.println("\nHai scelto di non rinnovare la tessera.");
                                    inputValido = true;
                                    break;

                                default:
                                    System.out.println("\nScelta non valida. Inserisci 'S' per Sì o 'N' per No.\n");
                                    break;
                            }
                        }
                    }
                    break;
                case 4:
                    em.refresh(utente1);
                    if (utente1.getTessera() != null && utente1.getTessera().getAbbonamenti() == null) {
                        SetAbbonamento.AcquistaAbbonamento(scanner, dd, utente1);
                    } else if (utente1.getTessera() == null) {
                        System.out.println("\nNon hai una tessera. Acquista una tessera prima di fare l'abbonamento.\n");
                    } else if (utente1.getTessera().getAbbonamenti().isStato()) {
                        System.out.println("\nHai già un abbonamento attivo.\n");
                    } else if (!utente1.getTessera().getAbbonamenti().isStato()) {

                        boolean inputValido = false;

                        while (!inputValido) {
                            System.out.println("\nIl tuo abbonamento è scaduto. Vuoi rinnovarlo? (S/N)");
                            String risposta = scanner.nextLine().trim().toUpperCase();

                            switch (risposta) {
                                case "S":
                                    SetAbbonamento.RinnovaAbbonamento(scanner, dd, utente1);
                                    inputValido = true;
                                    break;

                                case "N":
                                    System.out.println("\nHai scelto di non rinnovare l'abbonamento.\n");
                                    inputValido = true;
                                    break;

                                default:
                                    System.out.println("\nScelta non valida. Inserisci 'S' per Sì o 'N' per No.\n");
                                    break;
                            }
                        }
                        break;
                    }
                    break;
                case 5:
                    SetTrattaMezzo.InserisciTrattaMezzo(scanner, dd, tmd);
                    break;
                case 9:
                    em.refresh(utente1);
                    SetUtente.VisualizzaProfilo(utente1);
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
