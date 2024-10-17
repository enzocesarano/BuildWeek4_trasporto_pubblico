package enzocesarano.dao;

import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.Percorrenza;
import enzocesarano.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TrattaMezziDAO {

    private EntityManager entityManager;

    public TrattaMezziDAO(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    public List<Tratta> getTrattaMezzi(UUID idMezzo) {
        Mezzo mezzo = entityManager.find(Mezzo.class, idMezzo);

        if (mezzo.getStatoMezzo() == StatoMezzo.MANUTENZIONE) {
            System.out.println("Il mezzo con ID " + idMezzo +
                    " è attualmente in manutenzione e le tratte non possono essere visualizzate.");
            return Collections.emptyList();
        }
        TypedQuery<Tratta> query = entityManager.createQuery(" " +
                "SELECT p FROM Tratta p WHERE" +
                " p.mezzo.id_mezzo = :idMezzo", Tratta.class);
        query.setParameter("idMezzo", idMezzo);
        return query.getResultList();
    }


    public int contaPercorrenze(UUID idMezzo, UUID idTratta) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(p) FROM Percorrenza p WHERE p.mezzo.id = :idMezzo AND p.tratta.id = :idTratta",
                Long.class
        );
        query.setParameter("idMezzo", idMezzo);
        query.setParameter("idTratta", idTratta);
        return query.getSingleResult().intValue();
    }

    public List<Percorrenza> trovaPercorrenze(UUID idMezzo, UUID idTratta) {
        TypedQuery<Percorrenza> query = entityManager.createQuery(
                "SELECT p FROM Percorrenza p WHERE " +
                        "p.mezzo.id = :idMezzo AND" +
                        " p.tratta.id = :idTratta",
                Percorrenza.class
        );
        query.setParameter("idMezzo", idMezzo);
        query.setParameter("idTratta", idTratta);
        return query.getResultList();
    }


    public double calcolaMediaPercorrenze(String trattaId) {
        UUID id = UUID.fromString(trattaId);
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT SUM(EXTRACT(EPOCH FROM v.tempoEffettivo)) / 60 FROM Percorrenza v WHERE v.tratta.id_tratta = :id", Long.class);
        query.setParameter("id", id);
        Long totaleMinuti = query.getSingleResult();

        TypedQuery<Long> countQuery = entityManager.createQuery(
                "SELECT COUNT(v) FROM Percorrenza v WHERE v.tratta.id_tratta = :id", Long.class);
        countQuery.setParameter("id", id);
        Long conteggioPercorrenze = countQuery.getSingleResult();

        return (double) (totaleMinuti / conteggioPercorrenze) / 60;
    }
}



