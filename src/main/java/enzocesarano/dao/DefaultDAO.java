package enzocesarano.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.UUID;

public class DefaultDAO {

    private final EntityManager entityManager;

    public DefaultDAO(EntityManager em) {
        this.entityManager = em;
    }

    public <T> void save(T obj) {
        EntityTransaction t = entityManager.getTransaction();
        try {
            t.begin();
            entityManager.persist(obj);
            t.commit();
            System.out.println(obj + " SALVATO!");
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            System.out.println("Error durante il salvataggio: " + e.getMessage());
        }
    }

    public <T> T getEntityById(Class<T> entityClass, String id) {
        try {
            T entity = entityManager.find(entityClass, UUID.fromString(id));
            if (entity == null) {
                System.out.println("Nessun elemento trovato con l'ID: " + id);
            }
            return entity;
        } catch (Exception e) {
            System.out.println("Errore nella ricerca: " + e.getMessage());
            return null;
        }
    }

    public <T> void delete(Class<T> entityClass, String id) {
        EntityTransaction t = entityManager.getTransaction();
        try {
            T obj = this.getEntityById(entityClass, id);
            if (obj != null) {
                t.begin();
                entityManager.remove(obj);
                t.commit();
                System.out.println(obj + " Eliminato!");
            } else {
                System.out.println("Entity non trovata.");
            }
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            System.out.println("Errore durante l'eliminazione: " + e.getMessage());
        }
    }
}