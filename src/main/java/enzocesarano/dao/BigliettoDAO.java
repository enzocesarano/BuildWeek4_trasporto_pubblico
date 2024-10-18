package enzocesarano.dao;

import enzocesarano.entities.Biglietto;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.PuntoDiEmissione;
import exceptions.BigliettoNotFoundException;
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
            throw new BigliettoNotFoundException("Non ci sono biglietti per questo periodo!");
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
            throw new BigliettoNotFoundException("Non ci sono biglietti per questo periodo e punto di emissione!");
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
            throw new BigliettoNotFoundException("Il totale dei biglietti per il " + puntoDiEmissione.getNome_punto() + " è zero.");
        }
        return totaleBigliettiPEm;
    }

    public List<Biglietto> bigliettiValidatiPerMezzo(Mezzo mezzo) {
        TypedQuery<Biglietto> query = getEntityManager().createQuery(
                "SELECT b FROM Biglietto b JOIN ValidazioneBiglietto v ON b.id_biglietto = v.biglietto.id_biglietto WHERE v.mezzo = :mezzo", Biglietto.class);
        query.setParameter("mezzo", mezzo);

        List<Biglietto> biglietti = query.getResultList();

        if (biglietti.isEmpty()) {
            throw new BigliettoNotFoundException("Non ci sono biglietti validati per il mezzo selezionato!");
        }

        return biglietti;
    }
}

