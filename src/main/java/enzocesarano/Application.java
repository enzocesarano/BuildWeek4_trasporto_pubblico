package enzocesarano;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.ManutenzioneDAO;
import enzocesarano.entities.ENUM.TipoUtente;
import enzocesarano.entities.Utenti;
import enzocesarano.utils.SetAbbonamento;
import enzocesarano.utils.SetBiglietto;
import enzocesarano.utils.SetManutenzione;
import enzocesarano.utils.SetTessera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("trasporto_pubblico");

    public static void main(String[] args) throws Exception {
        EntityManager em = emf.createEntityManager();
        DefaultDAO dd = new DefaultDAO(em);
        ManutenzioneDAO md = new ManutenzioneDAO(em);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nSeleziona un utente oppure registrati:");
            System.out.println("1. Utente");
            System.out.println("2. Admin");
            System.out.println("3. Registrati");
            System.out.println("0. Esci");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    boolean idValido = false;
                    while (!idValido) {
                        System.out.println("Inserisci il tuo id: ");
                        try {
                            String utente = scanner.nextLine();
                            Utenti utente1 = dd.getEntityById(Utenti.class, utente);
                            System.out.println(utente1.getNome());
                            idValido = true;
                            while (!exit) {
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
                                        SetTessera.AcquistaTessera(scanner, dd, utente1);
                                        break;
                                    case 4:
                                        SetAbbonamento.AcquistaAbbonamento(scanner, dd, utente1);
                                        break;
                                    case 0:
                                        System.out.println("Uscita dal menu.");
                                        exit = true;
                                        break;
                                    default:
                                        System.out.println("Opzione non valida. Riprova.");
                                        break;
                                }
                            }

                        } catch (Exception e) {
                            System.out.println("Input non valido. Assicurati di inserire un id valido.");
                        }
                    }
                    break;
                case 2:
                    // Admin
                    System.out.println("Accesso Admin");
                    System.out.println("Inserisci il tuo ID admin:");
                    String adminUtenteId = scanner.nextLine();

                    try {
                        Utenti adminUtente = dd.getEntityById(Utenti.class, adminUtenteId);


                        if (adminUtente != null && adminUtente.getTipoUtente() == TipoUtente.ADMIN) {
                            SetManutenzione.tracciaManutenzione(scanner, md, true);
                        } else {
                            System.out.println("Accesso negato! Solo per utenti autorizzati.");
                        }
                    } catch (Exception e) {
                        System.out.println("Errore! Utente non trovato.");
                    }
                    break;

                case 3:
                    System.out.println("sono il caso 3");
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
        
        em.close();
        emf.close();
    }
}

