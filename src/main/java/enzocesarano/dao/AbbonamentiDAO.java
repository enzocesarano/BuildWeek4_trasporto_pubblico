package enzocesarano.dao;

import enzocesarano.entities.Abbonamento;
import enzocesarano.entities.PuntoDiEmissione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
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


}
