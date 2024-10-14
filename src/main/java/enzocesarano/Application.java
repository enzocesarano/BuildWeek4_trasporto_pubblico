package enzocesarano;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.ENUM.TipoUtente;
import enzocesarano.entities.Utenti;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("trasporto_pubblico");

    public static void main(String[] args) throws Exception {
        EntityManager em = emf.createEntityManager();


        DefaultDAO dd = new DefaultDAO(em);
        Utenti utente1 = new Utenti("Mario", "Rossi", LocalDate.of(1990, 5, 20), TipoUtente.NORMALE);
        Utenti utente2 = new Utenti("Luigi", "Verdi", LocalDate.of(1985, 8, 15), TipoUtente.ADMIN);

        dd.save(utente1);
        dd.save(utente2);

        em.close();
        emf.close();
    }
}
