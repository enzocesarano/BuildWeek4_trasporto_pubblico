package enzocesarano.dao;

import enzocesarano.entities.ENUM.TipoMezzo;
import enzocesarano.entities.Manutenzione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class ManutenzioneDAO {

    private final EntityManager entityManager;

    public ManutenzioneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Metodo che calcola la durata totale della manutenzione e restituire il tipo di mezzo e il motivo della manutenzione
    public String calcolaDurataTipoMotivoManutenzione(UUID idMezzo) {
        try {

            Query querydati = entityManager.createQuery("SELECT m, m.mezzo.tipo_mezzo, m.motivo FROM Manutenzione m WHERE m.mezzo.id = :idMezzo");
            querydati.setParameter("idMezzo", idMezzo);
            List<Object[]> results = querydati.getResultList();

            if (results.isEmpty()) {
                return "Nessun mezzo trovato con ID " + idMezzo;
            }

            long totaleDurata = 0;  //nr. giorni
            TipoMezzo tipoMezzo = null;
            String motivoManutenzione = "";

            for (Object[] result : results) {
                Manutenzione manutenzione = (Manutenzione) result[0];  //oggetto
                tipoMezzo = (TipoMezzo) result[1];  //tipo mezzo presoo da enum
                motivoManutenzione = (String) result[2];  //motivo

                if (manutenzione.getData_inizio() != null && manutenzione.getData_fine() != null) {
                    long durataManutenzione = ChronoUnit.DAYS.between(manutenzione.getData_inizio(), manutenzione.getData_fine());
                    totaleDurata += durataManutenzione;
                }
            }

            return "Tipo mezzo: " + tipoMezzo + ", Motivo: " + motivoManutenzione + ", Durata totale manutenzione: " + totaleDurata + " giorni.";

        } catch (NoResultException e) {
            return "Errore: Nessun mezzo trovato con ID " + idMezzo;
        } catch (Exception e) {
            return "Errore durante il calcolo della manutenzione: " + e.getMessage();
        }
    }
}
