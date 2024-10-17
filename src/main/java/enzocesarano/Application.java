package enzocesarano;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.ManutenzioneDAO;
import enzocesarano.dao.TrattaMezziDAO;
import enzocesarano.entities.ENUM.TipoUtente;
import enzocesarano.entities.Utenti;
import enzocesarano.utils.SetManutenzione;
import enzocesarano.utils.utenti.SetMenuUtente;
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
        TrattaMezziDAO tmd = new TrattaMezziDAO(em);

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
                    SetMenuUtente.gestisciUtente(scanner, dd, tmd);
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

