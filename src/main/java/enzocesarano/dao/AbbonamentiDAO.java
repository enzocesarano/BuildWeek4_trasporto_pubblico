package enzocesarano.dao;

import enzocesarano.entities.Abbonamento;
import enzocesarano.entities.PuntoDiEmissione;
import exceptions.BigliettoNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AbbonamentiDAO {
    private EntityManager entityManager;

    public AbbonamentiDAO(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    public boolean AbbonamentoValido(UUID id) {
        Abbonamento abbonamento = entityManager.find(Abbonamento.class, id);
        return (abbonamento != null);
    }

    public long getAbbonamentoStats(LocalDate startDate, LocalDate endDate, PuntoDiEmissione puntoEmissione) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(a) FROM Abbonamento a WHERE a.data_inizio" +
                        " >= :startDate AND a.data_fine <= :endDate AND" +
                        " a.puntoDiEmissione = :puntoEmissione",
                Long.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("puntoEmissione", puntoEmissione);

        return query.getSingleResult();
    }

    public List<Abbonamento> abbonamentoPerPuntoDiEmissione(PuntoDiEmissione puntoDiEmissione, LocalDate inizio, LocalDate fine) {
        TypedQuery<Abbonamento> query = entityManager.createQuery(
                "SELECT b FROM Abbonamento b WHERE b.puntoDiEmissione = :puntoDiEmissione AND b.data_inizio BETWEEN :inizio AND :fine", Abbonamento.class);
        query.setParameter("puntoDiEmissione", puntoDiEmissione);
        query.setParameter("inizio", inizio);
        query.setParameter("fine", fine);

        List<Abbonamento> abbonamenti = query.getResultList();
        if (abbonamenti.isEmpty()) {
            throw new BigliettoNotFoundException("Non ci sono abbonamenti per questo periodo e punto di emissione!");
        }
        return abbonamenti;
    }

}
