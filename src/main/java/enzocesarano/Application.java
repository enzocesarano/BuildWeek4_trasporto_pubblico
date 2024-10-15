package enzocesarano;

import enzocesarano.dao.DefaultDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("trasporto_pubblico");

    public static void main(String[] args) throws Exception {
        EntityManager em = emf.createEntityManager();
        DefaultDAO dd = new DefaultDAO(em);


        /*Utenti utente1 = new Utenti("Mario", "Rossi", LocalDate.of(1990, 5, 20), TipoUtente.NORMALE);
        Utenti utente2 = new Utenti("Luigi", "Verdi", LocalDate.of(1985, 8, 15), TipoUtente.ADMIN);

        dd.save(utente1);
        dd.save(utente2);*/

        /*Utenti utente1 = dd.getEntityById(Utenti.class, "39a67702-c0d7-4550-88af-83cc64e1a8c1");
        Tessera marioTessera = new Tessera(LocalDate.of(2023, 02, 01), utente1);
        dd.save(marioTessera);*/

        em.close();
        emf.close();
    }
}
