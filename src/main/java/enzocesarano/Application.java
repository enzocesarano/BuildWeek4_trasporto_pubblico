package enzocesarano;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.TrattaMezziDAO;
import enzocesarano.entities.Utenti;
import enzocesarano.utils.SetAbbonamento;
import enzocesarano.utils.SetBiglietto;
import enzocesarano.utils.SetTessera;
import enzocesarano.utils.SetTrattaMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("trasporto_pubblico");

    public static void main(String[] args) throws Exception {
        EntityManager em = emf.createEntityManager();
        DefaultDAO dd = new DefaultDAO(em);
        TrattaMezziDAO tmD = new TrattaMezziDAO(em);

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
                                System.out.println("5. Visualizza Tratte Mezzo");
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
                                    case 5:
                                        SetTrattaMezzo.InserisciTrattaMezzo(scanner, dd, tmD);
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
                    System.out.println("sono il caso 2");
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


//        Percorrenza percorrenza1 = dd.getEntityById(Percorrenza.class, "199e76ca-c36b-49ed-bbd3-5198dee2f4d9");
//        Percorrenza percorrenza2 = dd.getEntityById(Percorrenza.class, "79e98328-8650-4c5a-9b51-c84fd807b787");
//        Percorrenza percorrenza3 = dd.getEntityById(Percorrenza.class, "b555b081-7cc8-405e-82e0-9b970c5e3159");
//        List<Percorrenza> percorrenze = new ArrayList<>();
//        percorrenze.add(percorrenza1);
//        percorrenze.add(percorrenza2);
//        percorrenze.add(percorrenza3);
//        Tratta trattaI = dd.getEntityById(Tratta.class, "f9ac7138-1f5d-4c3f-ba02-44372a298752");
//        String trattaId = trattaI.getId_tratta().toString();
//        double mediaPercorrenze = tmD.calcolaMediaPercorrenze(trattaId);
//        System.out.println("La media delle percorrenze è di ore " + mediaPercorrenze + " minuti.");
//        ;

            em.close();
            emf.close();
        }

    }
}
