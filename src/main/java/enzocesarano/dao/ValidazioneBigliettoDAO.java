package enzocesarano.dao;

import enzocesarano.entities.ValidazioneBiglietto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ValidazioneBigliettoDAO {
    private EntityManager entityManager;

    public ValidazioneBigliettoDAO(EntityManager em) {
        this.entityManager = em;
    }

    public List<ValidazioneBiglietto> ricercaValidazioniPerMezzo(String idMezzo, LocalDate inizio, LocalDate fine) {
        UUID uuid = UUID.fromString(idMezzo);
        TypedQuery<ValidazioneBiglietto> query = entityManager.createQuery(
                "SELECT v FROM ValidazioneBiglietto v WHERE v.mezzo.id_mezzo = :idMezzo AND v.dataValidazione BETWEEN :inizio AND :fine",
                ValidazioneBiglietto.class);
        query.setParameter("idMezzo", uuid);
        query.setParameter("inizio", inizio);
        query.setParameter("fine", fine);
        return query.getResultList();
    }


    public long contaValidazioniTotali(LocalDate inizio, LocalDate fine) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(v) FROM ValidazioneBiglietto v WHERE v.dataValidazione BETWEEN :inizio AND :fine",
                Long.class);
        query.setParameter("inizio", inizio);
        query.setParameter("fine", fine);
        return query.getSingleResult();
    }

}
