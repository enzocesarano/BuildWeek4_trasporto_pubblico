Sistema di Gestione del Trasporto Pubblico
Descrizione del Progetto:
Il sistema gestisce il trasporto pubblico, consentendo agli utenti di acquistare biglietti e abbonamenti, convalidare biglietti a bordo e visualizzare i propri dati di tessera e abbonamento.
Gli amministratori (utenti Admin) possono gestire il parco mezzi (tram e bus), monitorare lo stato delle manutenzioni e delle percorrenze, e visualizzare le statistiche sui biglietti venduti e validati.
Il sistema si basa su un'interfaccia testuale interattiva(tramite lo Scanner) e utilizza JPA per la persistenza dei dati su un database PostgreSQL con il nome "trasporto_pubblico".

Funzionalità Principali:
Biglietti e Abbonamenti
Emissione di Biglietti: Gli utenti normali possono acquistare biglietti tramite distributori automatici o rivenditori autorizzati. Ogni biglietto è identificato da un UUID univoco e associato a un punto di emissione.
I biglietti acquistati sono registrati con la data di emissione e possono essere visualizzati in base al punto di emissione e al periodo.
Validazione dei Biglietti: Una volta acquistato, un biglietto può essere validato su un mezzo in servizio. La validazione è registrata con la data e il mezzo su cui è stata effettuata.
Emissione di Abbonamenti: Gli utenti con tessere attive possono acquistare abbonamenti settimanali o mensili. Ogni abbonamento è legato a una tessera e identificato da un ID univoco.
Rinnovo di Tessere e Abbonamenti: Le tessere scadono dopo un anno e possono essere rinnovate. Gli abbonamenti scadono alla fine del periodo e possono essere rinnovati anche essi dall'utente.
Gestione del Parco Mezzi
Stato dei Mezzi: I mezzi possono essere in servizio o in manutenzione. Gli utenti Admin possono modificare lo stato di un mezzo e visualizzare i dati di manutenzione come il motivo, la durata della manutenzione e il numero di manutenzioni di un certo mezzo in un certo periodo. 
Assegnazione alle Tratte: I mezzi possono essere assegnati a una o più tratte. Gli utenti Admin possono visualizzare le tratte associate ai mezzi, comprese informazioni come zona di partenza, capolinea e tempo di percorrenza previsto.
Manutenzione Mezzi: Gli utenti Admin possono cambiare lo stato del mezzo, registrando le manutenzioni, specificando data di inizio, data di fine e motivo della manutenzione. Inoltre il sistema tiene traccia di tutte le manutenzioni per ogni mezzo.
Percorrenze e Statistiche: Il sistema consente di calcolare e monitorare il numero di volte che un mezzo ha percorso una tratta e di confrontare i tempi di percorrenza effettivi con quelli previsti.
Menu Utente
Gli utenti normali hanno accesso a un menu che permette loro di:

Acquistare biglietti
Validare biglietti
Acquistare o rinnovare tessere
Acquistare o rinnovare abbonamenti
Visualizzare i dati della tessera e dell'abbonamento
Menu Amministratore
Gli utenti Admin possono:

Creare e gestire i mezzi (tram e bus)
Visualizzare e gestire le manutenzioni
Monitorare i biglietti venduti e validati
Visualizzare le tratte associate ai mezzi
Calcolare statistiche sui tempi di percorrenza
Visualizzare il proprio profilo
Gli utenti non registrati possono creare un account inserendo i propri dati (nome, cognome, data di nascita) e scegliendo il tipo di utente (normale o admin). L'account viene poi salvato nel sistema con un UUID univoco.

Scelte Tecnologiche
UUID per Chiavi Primarie: Per garantire l'unicità globale. Tutte le entità del sistema (biglietti, abbonamenti, tessere, mezzi, ecc.) utilizzano UUID come chiavi primarie.
Relazioni tra Entità:
Scelte Relazionali nel Sistema

@ManyToOne e @OneToMany: Biglietti, Punti di Emissione e Mezzi
Biglietti e Punti di Emissione: Ogni punto di emissione può emettere più biglietti, ma ogni biglietto è emesso da un solo punto di emissione. Questo permette di tracciare i biglietti in modo efficiente e aggregare le vendite per punto di emissione.
Biglietti e Mezzi: Ogni mezzo può validare più biglietti, ma ogni biglietto è associato a un solo mezzo una volta validato. Questa relazione aiuta a monitorare i biglietti validati su ciascun mezzo.

@OneToOne: Biglietto e Validazione
Biglietto e Validazione: Un biglietto può essere validato una sola volta, e ogni validazione è legata a un solo biglietto. La relazione @OneToOne garantisce che non ci siano validazioni multiple per lo stesso biglietto.

@ManyToOne: Abbonamenti, Utenti e Punti di Emissione
Abbonamenti e Utenti: Ogni utente può avere più abbonamenti, ma ogni abbonamento è legato a un solo utente.
Abbonamenti e Punti di Emissione: Ogni punto di emissione può emettere molti abbonamenti, ma ogni abbonamento è emesso da un solo punto.

@OneToOne e @OneToMany: Mezzi, Tratte e Manutenzioni
Mezzi e Tratte: Ogni mezzo è assegnato a una sola tratta e viceversa.
Mezzi e Manutenzioni: Ogni mezzo può avere diverse manutenzioni nel tempo, ma ogni manutenzione è legata a un solo mezzo.

Enumerazioni:
Stati dei Mezzi: SERVIZIO, MANUTENZIONE
Tipologia di Mezzo: TRAM, BUS
Periodicità degli Abbonamenti: SETTIMANALE, MENSILE
Tipo Punto di Emissione: DISTRIBUTORE_AUTOMATICO, RIVENDITORE_AUTORIZZATO
Tipo utente: NORMALE, ADMIN

Persistenza e Validità: Il sistema controlla automaticamente la validità delle tessere e degli abbonamenti, impedendo l'acquisto di abbonamenti con tessere scadute.
Tecnologie Utilizzate
Java 17: Linguaggio principale utilizzato per lo sviluppo.
JPA (Jakarta Persistence API): Per mappare le entità e gestire le operazioni CRUD con il database.
PostgreSQL: Database relazionale per memorizzare i dati.
Hibernate: Implementazione di JPA per la gestione della persistenza.
Maven: Utilizzato per la gestione delle dipendenze e il build del progetto.
Istruzioni per l'Installazione
Prerequisiti
Java 17+
Maven
PostgreSQL
Configurazione del Database
Creare un database PostgreSQL chiamato trasporto_pubblico.
Configurare le credenziali di accesso nel file persistence.xml.
Compilazione e Esecuzione
Clonare il repository del progetto.
Eseguire mvn clean install per compilare il progetto.
Avviare l'applicazione tramite l'IDE o utilizzando il comando java -jar nomeProgetto.jar.
