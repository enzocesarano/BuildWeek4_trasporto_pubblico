package enzocesarano;

import enzocesarano.dao.BigliettoDAO;
import enzocesarano.dao.DefaultDAO;
import enzocesarano.entities.*;
import enzocesarano.entities.ENUM.StatoMezzo;
import enzocesarano.entities.ENUM.TipoMezzo;
import enzocesarano.entities.ENUM.TipoPuntoDiEmissione;
import enzocesarano.entities.ENUM.TipoUtente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("trasporto_pubblico");

    public static void main(String[] args) throws Exception {
        EntityManager em = emf.createEntityManager();

//----------------------------------------------------creazione utenti---------------------------------------------
        //DAO
        DefaultDAO dd = new DefaultDAO(em);
        BigliettoDAO bd = new BigliettoDAO(em);


        Utenti utente1 = new Utenti("Mario", "Rossi", LocalDate.of(1990, 5, 20), TipoUtente.NORMALE);
        Utenti utente2 = new Utenti("Luigi", "Verdi", LocalDate.of(1985, 8, 15), TipoUtente.ADMIN);

        // dd.save(utente1);
        // dd.save(utente2);

        // --------------------------------creazione di punti di emissione---------------------------------------
        PuntoDiEmissione punto1 = new PuntoDiEmissione("Stazione Centrale", true, TipoPuntoDiEmissione.RIVENDITORE_AUTORIZZATO, new ArrayList<>(), new ArrayList<>());
        PuntoDiEmissione punto2 = new PuntoDiEmissione("Piazza Garibaldi", true, TipoPuntoDiEmissione.DISTRIBUTORE_AUTOMATICO, new ArrayList<>(), new ArrayList<>());
        // dd.save(punto1);
        // dd.save(punto2);

        //  -----------------------------------------creazione tessere---------------------------------------
        List<Abbonamento> abbonamenti1 = new ArrayList<>();
        Utenti utenteId1 = dd.getEntityById(Utenti.class, "12dae18c-7e71-4fbf-98a0-05cd612405c2");
        Utenti utenteId2 = dd.getEntityById(Utenti.class, "365a2e8a-1046-4b62-9e35-3a860245b4c0");


        Tessera tessera1 = new Tessera(LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), utenteId1, abbonamenti1);
        Tessera tessera2 = new Tessera(LocalDate.of(2023, 2, 1), LocalDate.of(2024, 2, 1), utenteId2, abbonamenti1);


        // dd.save(tessera1);
        // dd.save(tessera2);

        // -------------------------------------------------creazione di abbonamenti---------------------------------------------------

        PuntoDiEmissione puntoId1 = dd.getEntityById(PuntoDiEmissione.class, "0df5d660-6108-40e3-9660-5508a495640c");
        PuntoDiEmissione puntoId2 = dd.getEntityById(PuntoDiEmissione.class, "386bede9-35d1-4b7b-a972-817592747baa");
        Tessera tesseraId1 = dd.getEntityById(Tessera.class, "8578d721-3e33-4c1c-97d5-e52c9bd289a6");
        Tessera tesseraId2 = dd.getEntityById(Tessera.class, "fd0214ff-d815-4fc6-b9dd-b084574e2a98");

        Abbonamento abbonamento1 = new Abbonamento(true, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), utenteId1, puntoId1, tesseraId1);
        Abbonamento abbonamento2 = new Abbonamento(true, LocalDate.of(2023, 2, 1), LocalDate.of(2024, 1, 31), utenteId2, puntoId2, tesseraId2);
        //  dd.save(abbonamento1);
        // dd.save(abbonamento2);

        //  abbonamenti1.add(abbonamento1);
        //  abbonamenti1.add(abbonamento2);


        // -------------------------------------------------creazione di biglietti---------------------------------------------------
        Biglietto biglietto1 = new Biglietto(LocalDate.of(2023, 5, 1), LocalDate.of(2024, 1, 10), true, puntoId1, null);
        Biglietto biglietto2 = new Biglietto(LocalDate.of(2023, 4, 1), LocalDate.of(2024, 3, 10), false, puntoId2, null);

        // dd.save(biglietto1);
        // dd.save(biglietto2);

        // -------------------------------------------------creazione di mezzi---------------------------------------------------
        List<Manutenzione> manutenzioni1 = new ArrayList<>();


        Mezzo mezzo1 = new Mezzo(TipoMezzo.BUS, 50, StatoMezzo.SERVIZIO, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Mezzo mezzo2 = new Mezzo(TipoMezzo.TRAM, 100, StatoMezzo.MANUTENZIONE, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        //  dd.save(mezzo1);
        //  dd.save(mezzo2);

        // -------------------------------------------------creazione delle manutenzioni-------------------------------------------------


        Mezzo mezzoId1 = dd.getEntityById(Mezzo.class, "070e541d-b325-4db2-bc9e-e629cb61622e");
        Mezzo mezzoId2 = dd.getEntityById(Mezzo.class, "da5af318-aeb5-40d0-8385-7d6f252bdbff");
        Manutenzione manutenzione1 = new Manutenzione(mezzoId1, LocalDate.of(2023, 5, 10), LocalDate.of(2023, 5, 20), "Sostituzione motore");
        Manutenzione manutenzione2 = new Manutenzione(mezzoId2, LocalDate.of(2023, 6, 15), LocalDate.of(2023, 6, 25), "Revisione generale");


        // dd.save(manutenzione1);
        // dd.save(manutenzione2);

        // manutenzioni1.add(manutenzione1);
        // manutenzioni1.add(manutenzione2);


        // -------------------------------------------------creazione di percorrenze---------------------------------------------------
        Tratta trattaId1 = dd.getEntityById(Tratta.class, "623df35e-90de-4721-8875-decea06a8a8c");
        Tratta trattaId2 = dd.getEntityById(Tratta.class, "ef98705f-8841-43fe-9570-9be2937dbba7");

        Percorrenza percorrenza1 = new Percorrenza(LocalDate.of(2023, 7, 1), LocalTime.of(1, 30), trattaId1, mezzoId1);
        Percorrenza percorrenza2 = new Percorrenza(LocalDate.of(2023, 8, 1), LocalTime.of(2, 0), trattaId2, mezzoId2);


        //  dd.save(percorrenza1);
        // dd.save(percorrenza2);

        // -------------------------------------------------creazione delle tratte-----------------------------------------------
        Tratta tratta1 = new Tratta("Centro", "Periferia", 45, new ArrayList<>());
        Tratta tratta2 = new Tratta("Stazione", "Aeroporto", 30, new ArrayList<>());

        //  dd.save(tratta1);
        // dd.save(tratta2);

        //   tratta1.getPercorrenze().add(percorrenza1);
        //   tratta2.getPercorrenze().add(percorrenza2);


        // -------------------------------------------------creazione delle validazioni dei biglietti-------------------------------------------------
        Biglietto bigliettoId1 = dd.getEntityById(Biglietto.class, "21b023f5-65b1-45bd-a60e-d2e7c989ca5b");
        Biglietto bigliettoId2 = dd.getEntityById(Biglietto.class, "9eda278b-9f5c-49a2-a56c-15780da188f7");

        ValidazioneBiglietto validazione1 = new ValidazioneBiglietto(bigliettoId1, mezzoId1, LocalDateTime.of(2023, 5, 1, 12, 30));
        ValidazioneBiglietto validazione2 = new ValidazioneBiglietto(bigliettoId2, mezzoId2, LocalDateTime.of(2023, 6, 1, 14, 0));

        //   dd.save(validazione1);
        // dd.save(validazione2);


        //*********************** 1. Ottengo i biglietti emmessi in un certo periodo **********************************
        LocalDate inizio = LocalDate.of(2023, 2, 1);
        LocalDate fine = LocalDate.of(2024, 1, 1);

        List<Biglietto> bigliettiPerPeriodo = bd.bigliettiPerPeriodo(inizio, fine);

        //*********************** 2. Ottengo i biglietti emmessi in un certo periodo e uncerto punto di emissione **********************************

        PuntoDiEmissione puntoDiEmissioneId1 = dd.getEntityById(PuntoDiEmissione.class, "0df5d660-6108-40e3-9660-5508a495640c");

        List<Biglietto> bigliettiPerPeriodoEPEmissione = bd.bigliettiPerPuntoEPeriodo(puntoDiEmissioneId1, inizio, fine);


        //*********************** 3. Totale biglietti emmessi in un certo periodo **********************************
        long totaleBigliettiPerPeriodo = bd.totBigliettiPerPeriodo(inizio, fine);

        //*********************** 4. Totale biglietti emmessi da un punto di emissione in un certo periodo **********************************
        long totaleBigliettiPerPunto = bd.totBigliettiPerPEmissione(puntoDiEmissioneId1, inizio, fine);


        em.close();
        emf.close();


    }
}
