package enzocesarano.dao;

import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.ENUM.TipoMezzo;
import enzocesarano.entities.Manutenzione;
import enzocesarano.entities.Mezzo;
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

    public static void ManutenzioniPerMezzo(DefaultDAO dd, Mezzo mezzo) {
        try {
            List<Manutenzione> manutenzioni = dd.getAllEntities(Manutenzione.class);
            List<Manutenzione> manutenzioniMezzo = manutenzioni.stream()
                    .filter(m -> m.getMezzo().getId_mezzo().equals(mezzo.getId_mezzo()))
                    .toList();

            if (manutenzioniMezzo.isEmpty()) {
                System.out.println("Non ci sono manutenzioni per il mezzo con ID: " + mezzo.getId_mezzo());
            } else {
                System.out.println("Manutenzioni per il mezzo " + mezzo.getTipo_mezzo() + " (ID: " + mezzo.getId_mezzo() + "):");

                for (Manutenzione manutenzione : manutenzioniMezzo) {
                    System.out.println("- Motivo: " + manutenzione.getMotivo());
                    System.out.println("  Data inizio: " + manutenzione.getData_inizio());
                    System.out.println("  Data fine: " + (manutenzione.getData_fine() != null ? manutenzione.getData_fine() : "Non terminata"));
                    System.out.println("----------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Errore durante il recupero delle manutenzioni: " + e.getMessage());
        }
    }

    // Metodo per calcola la durata totale della manutenzione e restituisce il tipo di mezzo e il motivo della manutenzione
    public String calcolaDurataTipoMotivoManutenzione(UUID idMezzo) throws MezzoNotFoundException {
        try {
            Query queryMezzoEsiste = entityManager.createQuery("SELECT COUNT(m.id) FROM Mezzo m WHERE m.id = :idMezzo");
            queryMezzoEsiste.setParameter("idMezzo", idMezzo);
            long mezzoEsiste = (long) queryMezzoEsiste.getSingleResult();

            if (mezzoEsiste == 0) {
                throw new MezzoNotFoundException("Nessun mezzo trovato con ID " + idMezzo);
            }

            Query querydati = entityManager.createQuery("SELECT m, m.mezzo.tipo_mezzo, m.motivo FROM Manutenzione m WHERE m.mezzo.id = :idMezzo");
            querydati.setParameter("idMezzo", idMezzo);
            List<Object[]> results = querydati.getResultList();

            if (results.isEmpty()) {
                return "Il mezzo con ID " + idMezzo + " non ha mai avuto manutenzioni.";
            }

            long totaleDurata = 0;
            TipoMezzo tipoMezzo = null;
            String motivoManutenzione = "";

            for (Object[] result : results) {
                Manutenzione manutenzione = (Manutenzione) result[0];
                tipoMezzo = (TipoMezzo) result[1];
                motivoManutenzione = (String) result[2];

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
