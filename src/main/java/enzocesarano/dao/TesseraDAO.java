package enzocesarano.dao;

import enzocesarano.entities.Tessera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TesseraDAO {
    private EntityManager entityManager;

    public TesseraDAO(EntityManager em) {
        this.entityManager = em;
    }

    public List<Tessera> ricercaTessereScadute(LocalDate dataCorrente) {
        TypedQuery<Tessera> query = entityManager.createQuery(
                "SELECT t FROM Tessera t WHERE t.data_scadenza < :dataCorrente", Tessera.class);
        query.setParameter("dataCorrente", dataCorrente);
        return query.getResultList();
    }

    public void aggiornaTessera(Tessera tessera) {
        entityManager.getTransaction().begin();
        entityManager.merge(tessera);
        entityManager.getTransaction().commit();
    }

    public void rinnovaTessera(String id, LocalDate nuovaDataAquisto) {
        UUID uuid = UUID.fromString(id);
        Tessera tessera = entityManager.find(Tessera.class, uuid);
        if (tessera != null) {
            tessera.setData_aquisto(nuovaDataAquisto);
            tessera.setData_scadenza(nuovaDataAquisto.plusYears(1));
            aggiornaTessera(tessera);
        }
    }
}
