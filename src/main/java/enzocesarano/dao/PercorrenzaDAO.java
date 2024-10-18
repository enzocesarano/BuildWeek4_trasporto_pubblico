package enzocesarano.dao;

import enzocesarano.entities.Percorrenza;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class PercorrenzaDAO {
    private EntityManager entityManager;

    public PercorrenzaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Percorrenza> listaPercorrenze(UUID idMezzo, UUID idTratta) {
        TypedQuery<Percorrenza> query = entityManager.createQuery(
                "SELECT p FROM Percorrenza p WHERE p.mezzo.id = :idMezzo", Percorrenza.class);
        query.setParameter("idMezzo", idMezzo);
        ;
        return query.getResultList();
    }

    public double calcolaMediaPercorrenze(String trattaId) {
        UUID id = UUID.fromString(trattaId);
        TypedQuery<Double> query = entityManager.createQuery(
                "SELECT AVG(v.tempoEffettivo) " +
                        "FROM Percorrenza v WHERE v.tratta.id_tratta = :id", Double.class);

        query.setParameter("id", id);
        Double mediaOre = query.getSingleResult();

        return mediaOre != null ? mediaOre : 0.0;
    }
}
