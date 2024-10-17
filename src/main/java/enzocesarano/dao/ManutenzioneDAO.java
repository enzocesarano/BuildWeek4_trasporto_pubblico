package enzocesarano.dao;

import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.ENUM.TipoMezzo;
import enzocesarano.entities.Manutenzione;
import exceptions.InvalidIDException;
import exceptions.MezzoNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class ManutenzioneDAO {

    private final EntityManager entityManager;

    public ManutenzioneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Metodo per calcola la durata totale della manutenzione e restituisce il tipo di mezzo e il motivo della manutenzione
    public String calcolaDurataTipoMotivoManutenzione(UUID idMezzo) throws MezzoNotFoundException {
        try {
            Query querydati = entityManager.createQuery("SELECT m, m.mezzo.tipo_mezzo, m.motivo FROM Manutenzione m WHERE m.mezzo.id = :idMezzo");
            querydati.setParameter("idMezzo", idMezzo);
            List<Object[]> results = querydati.getResultList();

            if (results.isEmpty()) {
                throw new MezzoNotFoundException("Nessun mezzo trovato con ID " + idMezzo);
            }

            long totaleDurata = 0;  //nr. giorni
            TipoMezzo tipoMezzo = null;
            String motivoManutenzione = "";

            for (Object[] result : results) {
                Manutenzione manutenzione = (Manutenzione) result[0];  //oggetto
                tipoMezzo = (TipoMezzo) result[1];  //tipo mezzo preso da enum
                motivoManutenzione = (String) result[2];  //motivo

                if (manutenzione.getData_inizio() != null && manutenzione.getData_fine() != null) {
                    long durataManutenzione = ChronoUnit.DAYS.between(manutenzione.getData_inizio(), manutenzione.getData_fine());
                    totaleDurata += durataManutenzione;
                }
            }

            return "Tipo mezzo: " + tipoMezzo + ", Motivo: " + motivoManutenzione + ", Durata totale manutenzione: " + totaleDurata + " giorni.";

        } catch (NoResultException e) {
            throw new MezzoNotFoundException("Errore: Nessun mezzo trovato con ID " + idMezzo);
        } catch (Exception e) {
            return "Errore durante il calcolo della manutenzione: " + e.getMessage();
        }
    }

    // Metodo che calcola quante volte un mezzo è andato in manutenzione in un determinato periodo e restituisce anche lo stato
    public String calcolaNumeroManutenzioniInPeriodo(UUID idMezzo, LocalDate dataInizio, LocalDate dataFine) throws MezzoNotFoundException {
        try {

            LocalDate today = LocalDate.now();
            if (dataInizio.isAfter(today) || dataFine.isAfter(today)) {
                throw new InvalidIDException("Errore: Le date inserite non possono essere future. Reinserisci un intervallo di date valido:");
            }

            if (dataInizio.isAfter(dataFine)) {
                throw new InvalidIDException("Errore: La data di inizio non può essere successiva alla data di fine.");
            }

            Query queryCount = entityManager.createQuery(
                    "SELECT COUNT(m) FROM Manutenzione m " +
                            "WHERE m.mezzo.id = :idMezzo " +
                            "AND m.data_inizio >= :dataInizio " +
                            "AND m.data_fine <= :dataFine"
            );
            queryCount.setParameter("idMezzo", idMezzo);
            queryCount.setParameter("dataInizio", dataInizio);
            queryCount.setParameter("dataFine", dataFine);

            Long count = (Long) queryCount.getSingleResult();

            if (count == 0) {
                throw new MezzoNotFoundException("Nessuna manutenzione trovata per il mezzo con ID " + idMezzo + " nel periodo specificato.");
            }

            // Lo stato del mezzo
            Query queryStato = entityManager.createQuery("SELECT m.statoMezzo FROM Mezzo m WHERE m.id_mezzo = :idMezzo");
            queryStato.setParameter("idMezzo", idMezzo);
            StatoMezzo statoMezzo = (StatoMezzo) queryStato.getSingleResult();

            String statoMezzoDescrizione = statoMezzo == StatoMezzo.SERVIZIO
                    ? "attualmente in servizio."
                    : "attualmente in manutenzione.";

            return "Il mezzo con ID " + idMezzo + " è andato in manutenzione " + count + " volta/e nel periodo dal " + dataInizio + " al "
                    + dataFine + ". Lo stato del mezzo è " + statoMezzoDescrizione;

        } catch (NoResultException e) {
            throw new MezzoNotFoundException("Errore: Nessun mezzo trovato con ID " + idMezzo);
        } catch (InvalidIDException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Errore durante il calcolo: " + e.getMessage();
        }
    }
}
