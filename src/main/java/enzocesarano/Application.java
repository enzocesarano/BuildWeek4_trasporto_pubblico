package enzocesarano;

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
        DefaultDAO dd = new DefaultDAO(em);
        Utenti utente1 = new Utenti("Mario", "Rossi", LocalDate.of(1990, 5, 20), TipoUtente.NORMALE);
        Utenti utente2 = new Utenti("Luigi", "Verdi", LocalDate.of(1985, 8, 15), TipoUtente.ADMIN);

//        dd.save(utente1);
//        dd.save(utente2);

        // --------------------------------creazione di punti di emissione---------------------------------------
        PuntoDiEmissione punto1 = new PuntoDiEmissione("Stazione Centrale", true, TipoPuntoDiEmissione.RIVENDITORE_AUTORIZZATO, new ArrayList<>(), new ArrayList<>());
        PuntoDiEmissione punto2 = new PuntoDiEmissione("Piazza Garibaldi", true, TipoPuntoDiEmissione.DISTRIBUTORE_AUTOMATICO, new ArrayList<>(), new ArrayList<>());
//        dd.save(punto1);
//        dd.save(punto2);

        //  -----------------------------------------creazione tessere---------------------------------------
        List<Abbonamento> abbonamenti1 = new ArrayList<>();
        Utenti utenteId1 = dd.getEntityById(Utenti.class, "4b8c9802-6a02-427e-a069-3178d6acd80f");
        Utenti utenteId2 = dd.getEntityById(Utenti.class, "777097b2-fa1d-405f-b899-fd1dd24e945e");


        Tessera tessera1 = new Tessera(LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), utenteId1, abbonamenti1);
        Tessera tessera2 = new Tessera(LocalDate.of(2023, 2, 1), LocalDate.of(2024, 2, 1), utenteId2, abbonamenti1);


//        dd.save(tessera1);
//        dd.save(tessera2);

        // -------------------------------------------------creazione di abbonamenti---------------------------------------------------

        PuntoDiEmissione puntoId1 = dd.getEntityById(PuntoDiEmissione.class, "58fe169b-46fc-43e1-b62f-bb6699358e8d");
        PuntoDiEmissione puntoId2 = dd.getEntityById(PuntoDiEmissione.class, "ffa5e99f-9d03-4cb2-9ede-cab690fa2d08");
        Tessera tesseraId1 = dd.getEntityById(Tessera.class, "6fcd5a67-d5cb-4d7c-a208-60faa6078359");
        Tessera tesseraId2 = dd.getEntityById(Tessera.class, "78768ae3-1542-4a80-a8a5-0cdbbf725b0c");

        Abbonamento abbonamento1 = new Abbonamento(true, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), utenteId1, puntoId1, tesseraId1);
        Abbonamento abbonamento2 = new Abbonamento(true, LocalDate.of(2023, 2, 1), LocalDate.of(2024, 1, 31), utenteId2, puntoId2, tesseraId2);
//        dd.save(abbonamento1);
//        dd.save(abbonamento2);
//
//        abbonamenti1.add(abbonamento1);
//        abbonamenti1.add(abbonamento2);


        // -------------------------------------------------creazione di biglietti---------------------------------------------------
        Biglietto biglietto1 = new Biglietto(LocalDate.of(2023, 5, 1), true, puntoId1, null);
        Biglietto biglietto2 = new Biglietto(LocalDate.of(2023, 4, 1), false, puntoId2, null);

//        dd.save(biglietto1);
//        dd.save(biglietto2);

        // -------------------------------------------------creazione di mezzi---------------------------------------------------
        List<Manutenzione> manutenzioni1 = new ArrayList<>();


        Mezzo mezzo1 = new Mezzo(TipoMezzo.BUS, 50, StatoMezzo.SERVIZIO, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Mezzo mezzo2 = new Mezzo(TipoMezzo.TRAM, 100, StatoMezzo.MANUTENZIONE, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

//        dd.save(mezzo1);
//        dd.save(mezzo2);

        // -------------------------------------------------creazione delle manutenzioni-------------------------------------------------

        Mezzo mezzoId1 = dd.getEntityById(Mezzo.class, "939378e6-57c2-4c27-a77f-b1fb01b4ecb2");
        Mezzo mezzoId2 = dd.getEntityById(Mezzo.class, "d97a2807-594e-42a0-b855-ce5f6611d643");
        Manutenzione manutenzione1 = new Manutenzione(mezzoId1, LocalDate.of(2023, 5, 10), LocalDate.of(2023, 5, 20), "Sostituzione motore");
       Manutenzione manutenzione2 = new Manutenzione(mezzoId2, LocalDate.of(2023, 6, 15), LocalDate.of(2023, 6, 25), "Revisione generale");
//        dd.save(manutenzione1);
//        dd.save(manutenzione2);

//        manutenzioni1.add(manutenzione1);
//        manutenzioni1.add(manutenzione2);


        // -------------------------------------------------creazione di percorrenze---------------------------------------------------
        Tratta trattaId1 = dd.getEntityById(Tratta.class, "a1c35f74-e70b-441f-a3d8-ad4b881c1334");
        Tratta trattaId2 = dd.getEntityById(Tratta.class, "cf9276d5-4d37-4736-bde5-1d9d061fa8c5");
        Percorrenza percorrenza1 = new Percorrenza(LocalDate.of(2023, 7, 1), LocalTime.of(1, 30), trattaId1, mezzoId1);
        Percorrenza percorrenza2 = new Percorrenza(LocalDate.of(2023, 8, 1), LocalTime.of(2, 0), trattaId2, mezzoId2);


//        dd.save(percorrenza1);
//        dd.save(percorrenza2);

        // -------------------------------------------------creazione delle tratte-----------------------------------------------
        Tratta tratta1 = new Tratta("Centro", "Periferia", 45, new ArrayList<>());
        Tratta tratta2 = new Tratta("Stazione", "Aeroporto", 30, new ArrayList<>());

//        dd.save(tratta1);
//        dd.save(tratta2);

//        tratta1.getPercorrenze().add(percorrenza1);
//        tratta2.getPercorrenze().add(percorrenza2);


        // -------------------------------------------------creazione delle validazioni dei biglietti-------------------------------------------------

        Biglietto bigliettoId1 = dd.getEntityById(Biglietto.class, "187bed48-6809-42c0-9528-e405714ae3db");
        Biglietto bigliettoId2 = dd.getEntityById(Biglietto.class, "c840c872-2aa0-4c69-816a-63a2cb13f25d");

        ValidazioneBiglietto validazione1 = new ValidazioneBiglietto(bigliettoId1, mezzoId1, LocalDateTime.of(2023, 5, 1, 12, 30));
        ValidazioneBiglietto validazione2 = new ValidazioneBiglietto(bigliettoId2, mezzoId1, LocalDateTime.of(2023, 6, 1, 14, 0));

//        dd.save(validazione1);
//        dd.save(validazione2);

        em.close();
        emf.close();
    }
}
