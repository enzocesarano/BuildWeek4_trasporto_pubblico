package enzocesarano.dao;

import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.Mezzo;
import enzocesarano.entities.Percorrenza;
import enzocesarano.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


import java.time.LocalDate;
import java.time.LocalTime;
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
                "SELECT p.tratta FROM Percorrenza p WHERE" +
                " p.mezzo.id_mezzo = :idMezzo", Tratta.class);
        query.setParameter("idMezzo", idMezzo);
        return query.getResultList();
    }

    public void registraPercorrenza(UUID idMezzo, UUID idTratta, LocalTime tempoEffettivo) {
        Mezzo mezzo = entityManager.find(Mezzo.class, idMezzo);
        Tratta tratta = entityManager.find(Tratta.class, idTratta);

        if (mezzo != null && tratta != null) {
            Percorrenza percorrenza = new Percorrenza(LocalDate.now(), tempoEffettivo, tratta, mezzo);

            entityManager.getTransaction().begin();
            entityManager.persist(percorrenza);
            entityManager.getTransaction().commit();
        }
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
}

