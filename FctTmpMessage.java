package net.synergy2.logic.fct.tmp;

import net.synergy2.base.types.IMessage;
import net.synergy2.base.types.IMessageInstaller;

//@formatter:off
public enum FctTmpMessage implements IMessage, IMessageInstaller {

    // {1} Sottoentità {2] Inserimento/Modifica {3} Entità principale
    // Se codice/descrizione/numeroOrdine non presente viene usato l'uid per entità/sottoentità
    /** Ordine di importazione entità errato [{1}] */
    tmpImpEtyOrdNotVld ("Ordine di importazione entità errato [{1}]"),
    
    // Articoli
    /** Fornitore [{1}] non presente in {2} articolo [{3}] */
    tmpImpSupUidItm ("Fornitore [{1}] non presente in {2} articolo [{3}]"),
    /** Unità di misura alternativa [{1}] non presente in {2} articolo [{3}] */
    tmpImpUniMeaAltUidItm ("Unità di misura alternativa [{1}] non presente in {2} articolo [{3}]"),
    /** Unità di misura [{1}] non presente in {2} articolo [{3}] */
    tmpImpUniMeaUidItm ("Unità di misura [{1}] non presente in {2} articolo [{3}]");
    
    final private String message;
    final private String code;
    FctTmpMessage (String message) {
        this.message = message;
        this.code = this.name ();
    } // FctVrtMessage
    
    @Override
    public String getMessage () { return this.message; }
    @Override
    public String getCode () { return this.code; }
} // FctTmpMessage
