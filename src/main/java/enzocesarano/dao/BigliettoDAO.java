package enzocesarano.dao;

import enzocesarano.entities.Biglietto;
import enzocesarano.entities.PuntoDiEmissione;
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

    //-------------------Nr. biglietti emessi in un punto di emissione in un determinato periodo
    public List<Biglietto> bigliettiPerPuntoEPeriodo(PuntoDiEmissione puntoDiEmissione, LocalDate inizio, LocalDate fine) {
        TypedQuery<Biglietto> query = getEntityManager().createQuery(
                "SELECT b FROM Biglietto b WHERE b.puntoDiEmissione = :puntoDiEmissione AND b.data_emissione BETWEEN :inizio AND :fine", Biglietto.class);
        query.setParameter("puntoDiEmissione", puntoDiEmissione);
        query.setParameter("inizio", inizio);
        query.setParameter("fine", fine);

        List<Biglietto> biglietti = query.getResultList();
        if (biglietti.isEmpty()) {
            System.out.println("Non ci sono biglietti per questo periodo!");
        } else {
            System.out.println("I biglietti trovati per questo periodo e punto di emissione: ");
            for (Biglietto biglietto : biglietti) {
                System.out.println(biglietto);
            }
        }
        return biglietti;
    }


    //-------------------Nr.totale biglietti emessi in un determinato periodo
    public long totBigliettiPerPeriodo(LocalDate inizio, LocalDate fine) {
        TypedQuery<Long> countBQuery = getEntityManager().createQuery(
                "SELECT COUNT(b) FROM Biglietto b WHERE b.data_emissione BETWEEN :inizio AND :fine", Long.class);
        countBQuery.setParameter("inizio", inizio);
        countBQuery.setParameter("fine", fine);
        long totaleBiglietti = countBQuery.getSingleResult();
        if (totaleBiglietti == 0) {
            System.out.println("Il totale dei biglietti per il periodo selezionato è zero.");
        } else {
            System.out.println("Il totale dei biglietti emessi nel periodo selezionato è: " + totaleBiglietti);
        }
        return totaleBiglietti;
    }

    //-------------------Nr.totale biglietti emessi in un certo punto di emissione
    public long totBigliettiPerPEmissione(PuntoDiEmissione puntoDiEmissione, LocalDate inizio, LocalDate fine) {
        TypedQuery<Long> countQueryPEmissione = getEntityManager().createQuery(
                "SELECT COUNT(b) FROM Biglietto b WHERE b.puntoDiEmissione = :puntoDiEmissione " +
                        "AND b.data_emissione BETWEEN :inizio AND :fine", Long.class);
        countQueryPEmissione.setParameter("puntoDiEmissione", puntoDiEmissione);
        countQueryPEmissione.setParameter("inizio", inizio);
        countQueryPEmissione.setParameter("fine", fine);
        long totaleBigliettiPEm = countQueryPEmissione.getSingleResult();
        if (totaleBigliettiPEm == 0) {
            System.out.println("Il totale dei biglietti per il " + puntoDiEmissione.getNome_punto() + " è zero.");
        } else {
            System.out.println("Il totale dei biglietti emessi dal " + puntoDiEmissione.getNome_punto() + " è: " + totaleBigliettiPEm);
        }
        return totaleBigliettiPEm;


    }
}
