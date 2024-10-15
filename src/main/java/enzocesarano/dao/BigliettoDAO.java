package enzocesarano.dao;

import enzocesarano.entities.Biglietto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class BigliettoDAO extends DefaultDAO {

    public BigliettoDAO(EntityManager em) {
        super(em);
    }

    //-------------------Nr. biglietti emessi in un determinato periodo
    public List<Biglietto> bigliettiPerPeriodo(LocalDate inizio, LocalDate fine) {
        TypedQuery<Biglietto> query = getEntityManager().createQuery(
                "SELECT b FROM Biglietto b WHERE b.data_emissione BETWEEN : inizio AND :fine", Biglietto.class);
        query.setParameter("inizio", inizio);
        query.setParameter("fine", fine);

        List<Biglietto> biglietti = query.getResultList();

        if (biglietti.isEmpty()) {
            System.out.println("Non ci sono biglietti per questo periodo!");
        } else {
            System.out.println("I biglietti trovati per questo periodo: ");
            for (Biglietto biglietto : biglietti) {
                System.out.println(biglietto);
            }
        }

        return biglietti;
    }
}
