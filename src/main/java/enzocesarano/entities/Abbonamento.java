package enzocesarano.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Abbonamento {
    private UUID id_abbonamento;
    private boolean stato;
    private LocalDate data_inizio;
    private LocalDate data_fine;
    private Tessera id_tessera;
    private Emissione id_emissione;

}
