package enzocesarano;

import enzocesarano.dao.DefaultDAO;
import enzocesarano.dao.TesseraDAO;
import enzocesarano.dao.ValidazioneBigliettoDAO;
import enzocesarano.entities.Abbonamento;
import enzocesarano.entities.ENUM.TipoUtente;
import enzocesarano.entities.Tessera;
import enzocesarano.entities.Utenti;
import enzocesarano.entities.ValidazioneBiglietto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("trasporto_pubblico");

    public static void main(String[] args) throws Exception {
        EntityManager em = emf.createEntityManager();
        TesseraDAO td = new TesseraDAO(em);
        ValidazioneBigliettoDAO md = new ValidazioneBigliettoDAO(em);

//----------------------------------------------------creazione utenti---------------------------------------------
        DefaultDAO dd = new DefaultDAO(em);
//        Utenti utente1 = new Utenti("Mario", "Rossi", LocalDate.of(1990, 5, 20), TipoUtente.NORMALE);
//        Utenti utente2 = new Utenti("Luigi", "Verdi", LocalDate.of(1985, 8, 15), TipoUtente.ADMIN);
        Utenti utente3 = new Utenti("Aldo", "Baglio", LocalDate.of(1990, 10, 15), TipoUtente.NORMALE);
//        dd.save(utente3);
//
////        dd.save(utente1);
////        dd.save(utente2);
//
//        // --------------------------------creazione di punti di emissione---------------------------------------
//        PuntoDiEmissione punto1 = new PuntoDiEmissione("Stazione Centrale", true, TipoPuntoDiEmissione.RIVENDITORE_AUTORIZZATO, new ArrayList<>(), new ArrayList<>());
//        PuntoDiEmissione punto2 = new PuntoDiEmissione("Piazza Garibaldi", true, TipoPuntoDiEmissione.DISTRIBUTORE_AUTOMATICO, new ArrayList<>(), new ArrayList<>());
////        dd.save(punto1);
////        dd.save(punto2);
//
//        //  -----------------------------------------creazione tessere---------------------------------------
        List<Abbonamento> abbonamenti1 = new ArrayList<>();
        Utenti utenteId1 = dd.getEntityById(Utenti.class, "8b5b8764-3d8e-4c9f-a709-6394517ba4a7");
        Utenti utenteId2 = dd.getEntityById(Utenti.class, "eca82a95-f81b-4577-ac67-64d53f690514");
        Utenti utenteId3 = dd.getEntityById(Utenti.class, "cdf87892-ac7e-4149-acb7-0a7d2dd3d588");
//
//
        Tessera tessera1 = new Tessera(LocalDate.of(2023, 1, 1), utenteId1, abbonamenti1);
        Tessera tessera2 = new Tessera(LocalDate.of(2023, 2, 1), utenteId2, abbonamenti1);
        Tessera tessera3 = new Tessera(LocalDate.of(2024, 7, 10), utenteId3, abbonamenti1);
//        dd.save(tessera3);
//
//
//        dd.save(tessera1);
//        dd.save(tessera2);
//
//        // -------------------------------------------------creazione di abbonamenti---------------------------------------------------
//
//        PuntoDiEmissione puntoId1 = dd.getEntityById(PuntoDiEmissione.class, "28fe5187-94bc-43dc-a99b-17fd8c017108");
//        PuntoDiEmissione puntoId2 = dd.getEntityById(PuntoDiEmissione.class, "a4bf8d2c-6855-4679-876a-725f495796f1");
//        Tessera tesseraId1 = dd.getEntityById(Tessera.class, "0c468e4c-088a-4327-884a-b839d309546e");
        Tessera tesseraId2 = dd.getEntityById(Tessera.class, "a3cb2167-9e32-4102-a810-7feac557d9c1");
        Tessera tesseraId3 = dd.getEntityById(Tessera.class, "15d022a6-da8a-4df7-b8c9-d44dd17c6c30");
//
//        Abbonamento abbonamento1 = new Abbonamento(true, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), utenteId1, puntoId1, tesseraId1);
//        Abbonamento abbonamento2 = new Abbonamento(true, LocalDate.of(2023, 2, 1), LocalDate.of(2024, 1, 31), utenteId2, puntoId2, tesseraId2);
////        dd.save(abbonamento1);
////        dd.save(abbonamento2);
////
////        abbonamenti1.add(abbonamento1);
////        abbonamenti1.add(abbonamento2);
//
//
//        // -------------------------------------------------creazione di biglietti---------------------------------------------------
//        Biglietto biglietto1 = new Biglietto(LocalDate.of(2023, 5, 1), true, puntoId1, null);
//        Biglietto biglietto2 = new Biglietto(LocalDate.of(2023, 4, 1), false, puntoId2, null);
//
////        dd.save(biglietto1);
////        dd.save(biglietto2);
//
//        // -------------------------------------------------creazione di mezzi---------------------------------------------------
//        List<Manutenzione> manutenzioni1 = new ArrayList<>();
//
//
//        Mezzo mezzo1 = new Mezzo(TipoMezzo.BUS, 50, StatoMezzo.SERVIZIO, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//        Mezzo mezzo2 = new Mezzo(TipoMezzo.TRAM, 100, StatoMezzo.MANUTENZIONE, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//
////        dd.save(mezzo1);
////        dd.save(mezzo2);
//
//        // -------------------------------------------------creazione delle manutenzioni-------------------------------------------------
//
//        Mezzo mezzoId1 = dd.getEntityById(Mezzo.class, "3bd5dd17-0d2d-44cb-8968-81d2d2500215");
//        Mezzo mezzoId2 = dd.getEntityById(Mezzo.class, "4acd13fc-c235-4fea-a1ba-6a71b0d434b3");
//        Manutenzione manutenzione1 = new Manutenzione(mezzoId1, LocalDate.of(2023, 5, 10), LocalDate.of(2023, 5, 20), "Sostituzione motore");
//        Manutenzione manutenzione2 = new Manutenzione(mezzoId2, LocalDate.of(2023, 6, 15), LocalDate.of(2023, 6, 25), "Revisione generale");
////        dd.save(manutenzione1);
////        dd.save(manutenzione2);
//
////        manutenzioni1.add(manutenzione1);
////        manutenzioni1.add(manutenzione2);
//
//
//        // -------------------------------------------------creazione di percorrenze---------------------------------------------------
//        Tratta trattaId1 = dd.getEntityById(Tratta.class, "2b3d6c64-3f95-4d97-8c37-b7b1b60f5728");
//        Tratta trattaId2 = dd.getEntityById(Tratta.class, "f9ac7138-1f5d-4c3f-ba02-44372a298752");
//        Percorrenza percorrenza1 = new Percorrenza(LocalDate.of(2023, 7, 1), LocalTime.of(1, 30), trattaId1, mezzoId1);
//        Percorrenza percorrenza2 = new Percorrenza(LocalDate.of(2023, 8, 1), LocalTime.of(2, 0), trattaId2, mezzoId2);
//
//
////        dd.save(percorrenza1);
////        dd.save(percorrenza2);
//
//        // -------------------------------------------------creazione delle tratte-----------------------------------------------
//        Tratta tratta1 = new Tratta("Centro", "Periferia", 45, new ArrayList<>());
//        Tratta tratta2 = new Tratta("Stazione", "Aeroporto", 30, new ArrayList<>());
//
////        dd.save(tratta1);
////        dd.save(tratta2);
//
////        tratta1.getPercorrenze().add(percorrenza1);
////        tratta2.getPercorrenze().add(percorrenza2);
//
//
//        // -------------------------------------------------creazione delle validazioni dei biglietti-------------------------------------------------
//        Biglietto bigliettoId1 = dd.getEntityById(Biglietto.class, "ace2f3d1-1698-4e95-944c-1202922c45d4");
//        Biglietto bigliettoId2 = dd.getEntityById(Biglietto.class, "f2e775f0-6d40-4f22-91f1-79d8c75cfe65");
//
//        ValidazioneBiglietto validazione1 = new ValidazioneBiglietto(bigliettoId1, mezzoId1, LocalDateTime.of(2023, 5, 1, 12, 30));
//        ValidazioneBiglietto validazione2 = new ValidazioneBiglietto(bigliettoId2, mezzoId2, LocalDateTime.of(2023, 6, 1, 14, 0));
//
////        dd.save(validazione1);
////        dd.save(validazione2);


//        List<Tessera> tessereScadute = td.ricercaTessereScadute(LocalDate.now());
//        tessereScadute.forEach(System.out::println);


//        td.rinnovaTessera("0c468e4c-088a-4327-884a-b839d309546e", LocalDate.now());

        List<ValidazioneBiglietto> bigliettiValidatiPerMezzo = md.ricercaValidazioniPerMezzo("3bd5dd17-0d2d-44cb-8968-81d2d2500215", LocalDate.of(2020, 10, 1), LocalDate.now());
        bigliettiValidatiPerMezzo.forEach(System.out::println);

        Long result = md.contaValidazioniTotali(LocalDate.of(2020, 1, 1), LocalDate.now());
        System.out.println("in totale ci sono stati convalidati: " + result + " biglietti");


        em.close();
        emf.close();
    }
}
