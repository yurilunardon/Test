package net.synergy2.logic.aps.tmp;

import net.synergy2.base.types.IMessage;
import net.synergy2.base.types.IMessageInstaller;

//@formatter:off
public enum ApsTmpMessage implements IMessage, IMessageInstaller {

    // {1} Sottoentità {2] Inserimento/Modifica {3} Entità principale
    // Se codice/descrizione/numeroOrdine non presente viene usato l'uid per entità/sottoentità
    
    // Sottocommesse
    /** Commessa [{1}] non presente in {2} sottocommessa [{3}]. Associato un record generico. */
    tmpImpJobUidSubJob ("Commessa [{1}] non presente in {2} sottocommessa [{3}]. Associato un record generico."),

    // Risorse aps
    /** Risorsa synergy [{1}] non presente in {2} risorsa Aps [{3}]. Associato un record generico. */
    tmpImpResUidApsRes ("Risorsa synergy [{1}] non presente in {2} risorsa Aps [{3}]. Associato un record generico."),

    // Risorse scenario
    /** Risorsa aps [{1}] non presente in {2} risorsa di scenario [{3}]. Associato un record generico. */
    tmpImpApsResUidSceRes ("Risorsa aps [{1}] non presente in {2} risorsa di scenario [{3}]. Associato un record generico."),

    // Associazione risorse gruppi
    /** Risorsa [{1}] non presente in {2} assoc. gruppo - risorsa [Uid: {3}]. Associato un record generico. */
    tmpImpResUidResGrp ("Risorsa [{1}] non presente in {2} assoc. gruppo - risorsa [Uid: {3}]. Associato un record generico."),
    /** Gruppo [{1}] non presente in {2} assoc. gruppo - risorsa [Uid: {3}]. Associato un record generico. */
    tmpImpGrpUidResGrp ("Gruppo [{1}] non presente in {2} assoc. gruppo - risorsa [Uid: {3}]. Associato un record generico."),

    // Vincoli di scenario
    /** Vincolo [{1}] non presente in {2} vincolo di scenario [{3}]. Associato un record generico. */
    tmpImpCstUidSceCst ("Vincolo [{1}] non presente in {2} vincolo di scenario [{3}]. Associato un record generico."),
    
    // Risorse di scenario - Vincoli secondari di scenario in uso
    /** Risorsa di scenario [{1}] non presente in {2} assoc. risorse - vincoli in uso [Uid: {3}]. Associato un record generico. */
    tmpImpSceResUidSceResCurCst ("Risorsa di scenario [{1}] non presente in {2} assoc. risorse - vincoli in uso [Uid: {3}]. Associato un record generico."),
    /** Vincolo secondario di scenario [{1}] non presente in {2} assoc. risorse - vincoli in uso [Uid: {3}]. Associato un record generico. */
    tmpImpSceCstUidSceResCurCst ("Vincolo secondario di scenario [{1}] non presente in {2} assoc. risorse - vincoli in uso [Uid: {3}]. Associato un record generico."),
    
    // Risorse di scenario - Valori attributi in uso
    /** Risorsa di scenario [{1}] non presente in {2} assoc. risorse - vincoli in uso [Uid: {3}]. Associato un record generico. */
    tmpImpSceResUidSceResCurAttVal ("Risorsa di scenario [{1}] non presente in {2} assoc. risorse - vincoli in uso [Uid: {3}]. Associato un record generico."),
    /** Valore attributo [{1}] non presente in {2} assoc. risorse - vincoli in uso [Uid: {3}]. Associato un record generico. */
    tmpImpValUidSceResCurAttVal ("Valore attributo [{1}] non presente in {2} assoc. risorse - vincoli in uso [Uid: {3}]. Associato un record generico."),

    // Cicli
    /** Articolo [{1}] non presente in {2} ciclo produzione [{3}]. Associato un record generico. */
    tmpImpItmUidPrdCyc ("Articolo [{1}] non presente in {2} ciclo produzione [{3}]. Associato un record generico."),

    // Cicli fase
    /** Ciclo produzione [{1}] non presente in {2} del ciclo fase [{3}]. Associato un record generico. */
    tmpImpPrdCycUidCycOpr ("Ciclo produzione [{1}] non presente in {2} del ciclo fase [{3}]. Associato un record generico."),
    /** Variante articolo [{1}] non presente in {2} del ciclo fase [{3}]. Associato un record generico. */
    tmpImpItmVrnUidCycOpr ("Variante articolo [{1}] non presente in {2} del ciclo fase [{3}]. Associato un record generico."),
    /** Gruppo pianificato [{1}] non presente in {2} del ciclo fase [{3}] */
    tmpImpPlnGrpUidCycOpr ("Gruppo pianificato [{1}] non presente in {2} del ciclo fase [{3}]"),

    // Distinte basi
    /** Ciclo fase [{1}] non presente in {2} distinta base [Uid: {3}]. Associato un record generico. */
    tmpImpPrdItmUidBom ("Articolo prodotto [{1}] non presente in {2} distinta base [Uid: {3}]. Associato un record generico."),
    /** Articolo [{1}] non presente in {2} distinta base [Uid: {3}]. Associato un record generico. */
    tmpImpItmUidBom ("Articolo componente [{1}] non presente in {2} distinta base [Uid: {3}]. Associato un record generico."),
    /** Variante articolo [{1}] non presente in {2} distinta base [Uid: {3}]. Associato un record generico. */
    tmpImpItmVrnUidBom ("Variante articolo [{1}] non presente in {2} distinta base [Uid: {3}]. Associato un record generico."),
    
    // Varianti
    /** Categoria variante [{1}] non presente in {2} variante [{3}]. Associato un record generico. */
    tmpImpCatUidPrdVrn ("Categoria variante [{1}] non presente in {2} variante [{3}]. Associato un record generico."),
    
    // Varianti articolo
    /** Articolo [{1}] non presente in {2} variante articolo [Uid: {3}]. Associato un record generico. */
    tmpImpItmUidItmVrn ("Articolo [{1}] non presente in {2} variante articolo [Uid: {3}]. Associato un record generico."),
    /** Variante [{1}] non presente in {2} variante articolo [Uid: {3}]. Associato un record generico. */
    tmpImpVrnUidItmVrn ("Variante [{1}] non presente in {2} variante articolo [Uid: {3}]. Associato un record generico."),

    // Modelli articolo
    /** Articolo [{1}] non presente in {2} variante articolo [Uid: {3}]. Associato un record generico. */
    tmpImpItmUidItmMdl ("Articolo [{1}] non presente in {2} modello articolo [{3}]. Associato un record generico."),

    // Varianti modello articolo
    /** Modello [{1}] non presente in {2} variante modello articolo [Uid: {3}]. Associato un record generico. */
    tmpImpMdlUidItmMdlVrn ("Modello [{1}] non presente in {2} variante modello articolo [Uid: {3}]. Associato un record generico."),
    /** Variante [{1}] non presente in {2} variante modello articolo [Uid: {3}]. Associato un record generico. */
    tmpImpItmVrnUidItmMdlVrn ("Variante [{1}] non presente in {2} variante modello articolo [Uid: {3}]. Associato un record generico."),

    // Associazione cicli legami di fase
    /** Ciclo fase [{1}] non presente in {2} ciclo legame fase [Uid: {3}]. Associato un record generico. */
    tmpImpCycOprUidCycOprLnk ("Ciclo fase [{1}] non presente in {2} ciclo legame fase [Uid: {3}]. Associato un record generico."),

    // Associazione cicli vincoli di fase
    /** Ciclo fase [{1}] non presente in {2} ciclo vincolo fase [Uid: {3}]. Associato un record generico. */
    tmpImpPrdCycOprUidCycOprCst ("Ciclo fase [{1}] non presente in {2} ciclo vincolo fase [Uid: {3}]. Associato un record generico."),
    /** Vincolo [{1}] non presente in {2} ciclo vincolo fase [Uid: {3}]. Associato un record generico. */
    tmpImpCstUidCycOprCst ("Vincolo [{1}] non presente in {2} ciclo vincolo fase [Uid: {3}]. Associato un record generico."),

    // Associazione cicli - gruppi vincoli di fase
    tmpImpPrdCycOprUidCycOprCstGrp ("Ciclo fase [{1}] non presente in {2} ciclo gruppo vincoli fase [Uid: {3}]. Associato un record generico."),
    tmpImpCstGrpUidCycOprCstGrp ("Gruppo vincolo [{1}] non presente in {2} ciclo gruppo vincoli fase [Uid: {3}]. Associato un record generico."),

    // Associazione cicli attributi di fase
    /** Ciclo fase [{1}] non presente in {2} ciclo attributo fase [Uid: {3}]. Associato un record generico. */
    tmpImpPrdCycOprUidCycOprAtt ("Ciclo fase [{1}] non presente in {2} ciclo attributo fase [Uid: {3}]. Associato un record generico."),
    /** Valore attributo [{1}] non presente in {2} ciclo attributo fase [Uid: {3}]. Associato un record generico. */
    tmpImpValUidCycOprAtt ("Valore attributo [{1}] non presente in {2} ciclo attributo fase [Uid: {3}]. Associato un record generico."),

    // Ordini cliente
    /** Cliente [{1}] non presente in {2} dell'ordine cliente [{3}]. Associato un record generico. */
    tmpImpCusUidCusOrd ("Cliente [{1}] non presente in {2} dell'ordine cliente [{3}]. Associato un record generico."),
    /** Offerta [{1}] non presente in {2} dell'ordine cliente [{3}]. */
    tmpImpQuoUidCusOrd ("Offerta [{1}] non presente in {2} dell'ordine cliente [{3}]."),
    /** L'Offerta [{1}] in {2} è stata collegata a più ordini cliente. */
    tmpImpQuoAlrMtc ("L'Offerta [{1}] in {2} è stata collegata a più ordini cliente."),

    // Domande
    /** Ordine cliente [{1}] non presente in {2} domanda [{3}]. Associato un record generico. */
    tmpImpCusOrdUidDem ("Ordine cliente [{1}] non presente in {2} domanda [{3}]. Associato un record generico."),
    /** Articolo [{1}] non presente in {2} domanda [{3}]. Associato un record generico. */
    tmpImpItmUidDem ("Articolo [{1}] non presente in {2} domanda [{3}]. Associato un record generico."),
    /** Stabilimento [{1}] non presente in {2} domanda [{3}] */
    tmpImpOrgLvlItmUidDem ("Stabilimento [{1}] non presente in {2} domanda [{3}]"),
    /** Magazzino [{1}] non presente in {2} domanda [{3}] */
    tmpImpWhsUidDem ("Magazzino [{1}] non presente in {2} domanda [{3}]"),
    /** Commessa [{1}] non presente in {2} domanda [{3}] */
    tmpImpJobUidDem ("Commessa [{1}] non presente in {2} domanda [{3}]"),
    /** Commessa [{1}] non presente in {2} domanda [{3}] */
    tmpImpSubJobUidDem ("Commessa [{1}] non presente in {2} domanda [{3}]"),
    
    // Ordini fornitore
    /** Fornitore [{1}] non presente in {2} ordine fornitore [{3}]. Associato un record generico. */
    tmpImpSupUidSupOrd ("Fornitore [{1}] non presente in {2} ordine fornitore [{3}]. Associato un record generico."),

    // Approvvigionamenti
    /** Ordine fornitore [{1}] non presente in {2} approvvigionamento [{3}] */
    tmpImpSupOrdUidSup ("Ordine fornitore [{1}] non presente in {2} approvvigionamento [{3}]"),
    /** Articolo [{1}] non presente in {2} approvvigionamento [{3}] */
    tmpImpItmUidSup ("Articolo [{1}] non presente in {2} approvvigionamento [{3}]"),
    /** Stabilimento [{1}] non presente in {2} approvvigionamento [{3}] */
    tmpImpOrgLvlItmUidSup ("Stabilimento [{1}] non presente in {2} approvvigionamento [{3}]"),
    /** Magazzino [{1}] non presente in {2} approvvigionamento [{3}] */
    tmpImpWhsUidSup ("Magazzino [{1}] non presente in {2} approvvigionamento [{3}]"),
    /** Commessa [{1}] non presente in {2} approvvigionamento [{3}] */
    tmpImpJobUidSup ("Commessa [{1}] non presente in {2} approvvigionamento [{3}]"),
    /** Sottocommessa [{1}] non presente in {2} approvvigionamento [{3}] */
    tmpImpSubJobUidSup ("Sottocommessa [{1}] non presente in {2} approvvigionamento [{3}]"),

    // Ordini produzione
    /** Articolo [{1}] non presente in {2} ordine produzione [{3}]. Associato un record generico. */
    tmpImpItmUidShpOrd ("Articolo [{1}] non presente in {2} ordine produzione [{3}]. Associato un record generico."),
    /** Stabilimento [{1}] non presente in {2} ordine produzione [{3}] */
    tmpImpOrgLvlUidShpOrd ("Stabilimento [{1}] non presente in {2} ordine produzione [{3}]"),
    /** Magazzino [{1}] non presente in {2} ordine produzione [{3}] */
    tmpImpWhsUidShpOrd ("Magazzino [{1}] non presente in {2} ordine produzione [{3}]"),
    /** Commessa [{1}] non presente in {2} ordine produzione [{3}] */
    tmpImpJobUidShpOrd ("Commessa [{1}] non presente in {2} ordine produzione [{3}]"),
    /** Sottocommessa [{1}] non presente in {2} ordine produzione [{3}] */
    tmpImpSubJobUidShpOrd ("Sottocommessa [{1}] non presente in {2} ordine produzione [{3}]"),
    /** Cliente [{1}] non presente in {2} ordine produzione [{3}] */
    tmpImpCusUidShpOrd ("Cliente [{1}] non presente in {2} ordine produzione [{3}]"),
    /** Ciclo produttivo [{1}] non presente in {2} ordine produzione [{3}] */
    tmpImpPrdCycUidShpOrd ("Ciclo produttivo [{1}] non presente in {2} ordine produzione [{3}]"),
    /** Icona [{1}] non presente in {2} ordine produzione [{3}] */
    tmpImpIcoUidShpOrd ("Icona [{1}] non presente in {2} ordine produzione [{3}]"),
    /** Modello [{1}] non presente in {2} ordine produzione [{3}] */
    tmpImpMdlUidShpOrd ("Modello [{1}] non presente in {2} ordine produzione [{3}]"),

    // Coprodotti
    /** Ordine produzione [{1}] non presente in {2} coprodotto [Uid: {3}]. Associato un record generico. */
    tmpImpShpOrdUidItmCop ("Ordine produzione [{1}] non presente in {2} coprodotto [Uid: {3}]. Associato un record generico."),
    /** Articolo [{1}] non presente in {2} coprodotto [Uid: {3}]. Associato un record generico. */
    tmpImpItmUidItmCop ("Articolo [{1}] non presente in {2} coprodotto [Uid: {3}]. Associato un record generico."),

    // Fasi
    /** Ordine produzione [{1}] non presente in {2} fase [{3}]. Associato un record generico. */
    tmpImpShpOrdUidOpr ("Ordine produzione [{1}] non presente in {2} fase [{3}]. Associato un record generico."),
    /** Ciclo fase [{1}] non presente in {2} fase [{3}] */
    tmpImpPrdCycOprUidOpr ("Ciclo fase [{1}] non presente in {2} fase [{3}]"),
    /** Colore [{1}] non presente in {2} fase [{3}] */
    tmpImpClrUidOpr ("Colore [{1}] non presente in {2} fase [{3}]"),
    /** Gruppo [{1}] non presente in {2} fase [{3}] */
    tmpImpGrpUidOpr ("Gruppo [{1}] non presente in {2} fase [{3}]"),
    /** Risorsa schedulata [{1}] non presente in {2} fase [{3}] */
    tmpImpSchResUidOpr ("Risorsa schedulata [{1}] non presente in {2} fase [{3}]"),
    /** Fase aggregata [{1}] non presente in {2} fase [{3}] */
    tmpImpOprAggUidOpr ("Fase aggregata [{1}] non presente in {2} fase [{3}]"),
    /** Fase multipallet [{1}] non presente in {2} fase [{3}] */
    tmpImpMulPalUidOpr ("Fase multipallet [{1}] non presente in {2} fase [{3}]"),

    // Materiali
    /** Fase [{1}] non presente in {2} materiale [Uid: {3}]. Associato un record generico. */
    tmpImpOprUidMat ("Fase [{1}] non presente in {2} materiale [Uid: {3}]. Associato un record generico."),
    /** Distinta base [{1}] non presente in {2} materiale [Uid: {3}] */
    tmpImpWhsUidMat ("Magazzino [{1}] non presente in {2} materiale [Uid: {3}]"),
    /** Distinta base [{1}] non presente in {2} materiale [Uid: {3}] */
    tmpImpBomUidMat ("Distinta base [{1}] non presente in {2} materiale [Uid: {3}]"),
    /** Articolo [{1}] non presente in {2} materiale [Uid: {3}]. Associato un record generico. */
    tmpImpItmUidMat ("Articolo [{1}] non presente in {2} materiale [Uid: {3}]. Associato un record generico."),

    // Legami fasi
    /** Fase precedente [{1}] non presente in {2} legame fase [Uid: {3}]. Dipendenza non importata. */
    tmpImpPreOprUidOprLnk ("Fase precedente [{1}] non presente in {2} legame fase [Uid: {3}]. Dipendenza non importata."),
    /** Fase successiva [{1}] non presente in {2} legame fase [Uid: {3}]. Dipendenza non importata. */
    tmpImpNxtOprUidOprLnk ("Fase successiva [{1}] non presente in {2} legame fase [Uid: {3}]. Dipendenza non importata."),
    /** Legame di cicli fase non presente in legame fase */
    tmpImpCycOprLnkUidOprLnk ("Legame di cicli fase non presente in legame fase"),

    // Vincoli fase
    /** Fase [{1}] non presente in {2} vincolo - fase [Uid: {3}]. Associato un record generico. */
    tmpImpOprUidOprCst ("Fase [{1}] non presente in {2} vincolo - fase [Uid: {3}]. Associato un record generico."),
    /** Vincolo di scenario [{1}] non presente in {2} assoc. vincolo - fase [Uid: {3}]. Associato un record generico. */
    tmpImpSceCstUidOprCst ("Vincolo di scenario [{1}] non presente in {2} assoc. vincolo - fase [Uid: {3}]. Associato un record generico."),
    /** Vincolo ciclo fase non presente in assoc. vincolo - fase */ // Non usato, se in futuro si aggiungerà collegamento messaggio già pronto.
    tmpImpCycOprCstUidOprCst ("Vincolo ciclo fase [{1}] non presente {2} in assoc. vincolo - fase [Uid: {3}]"),

    // Gruppo vincoli fase
    /** Fase [{1}] non presente in {2} gruppo vincolo - fase [Uid: {3}]. Associato un record generico. */
    tmpImpOprUidOprCstGrp ("Fase [{1}] non presente in {2} gruppo vincolo - fase [Uid: {3}]. Associato un record generico."),
    /** Gruppo vincolo [{1}] non presente in {2} assoc. gruppo vincolo - fase [Uid: {3}]. Associato un record generico. */
    tmpImpCstGrpUidOprCstGrp ("Gruppo vincolo [{1}] non presente in {2} assoc. gruppo vincolo - fase [Uid: {3}]. Associato un record generico."),
    /** Vincolo ciclo fase [{1}] non presente {2} in assoc. gruppo vincolo - fase [Uid: {3}] */ // Non usato, se in futuro si aggiungerà collegamento messaggio già pronto.
    tmpImpCycOprCstGrpUidOprCstGrp ("Vincolo ciclo fase [{1}] non presente {2} in assoc. gruppo vincolo - fase [Uid: {3}]"),

    // Attributi fase
    /** Fase [{1}] non presente in {2} attributo - fase [Uid: {3}]. Associato un record generico. */
    tmpImpOprUidOprAtt ("Fase [{1}] non presente in {2} attributo - fase [Uid: {3}]. Associato un record generico."),
    /** Valore attributo [{1}] non presente in {2} assoc. attributo - fase [Uid: {3}]. Associato un record generico. */
    tmpImpValUidOprAtt ("Valore attributo [{1}] non presente in {2} assoc. attributo - fase [Uid: {3}]. Associato un record generico."),
    /** Attributo ciclo fase non presente in assoc. attributo - fase */ // Non si capisce come mai qui il collegamento è rimasto.
    tmpImpCycOprAttUidOprAtt ("Attributo ciclo fase [{1}] non presente in {2} assoc. attributo - fase [Uid: {3}]"),
    
    // Eccezioni fase per risorsa
    /** Fase [{1}] non presente in {2} eccezione fase risorsa [Uid: {3}]. Associato un record generico. */
    tmpImpOprUidOprResExp ("Fase [{1}] non presente in {2} eccezione fase risorsa [Uid: {3}]. Associato un record generico."),
    /** Risorsa primaria [{1}] non presente in {2} eccezione fase risorsa [Uid: {3}]. Associato un record generico. */
    tmpImpSceResUidOprResExp ("Risorsa primaria [{1}] non presente in {2} eccezione fase risorsa [Uid: {3}]. Associato un record generico."),
    /** Eccezione ciclo fase risorsa non presente in eccezione fase risorsa */ // Non usato, se in futuro si aggiungerà collegamento messaggio già pronto.
    tmpImpCycOprResExpUidOprResExp ("Eccezione ciclo fase risorsa [{1}] non presente in {2} eccezione fase risorsa [Uid: {3}]"),
    
    // Legami
    /** Domanda [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato. */
    tmpImpDemUidOrdLnkExp ("Domanda [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato."),
    /** Articolo domanda [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato. */
    tmpImpDemItmUidOrdLnkExp ("Articolo domanda [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato."),
    /** Materiale [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato. */
    tmpImpMatUidOrdLnkExp ("Materiale [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato."),
    /** Articolo materiale [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato. */
    tmpImpMatItmUidOrdLnkExp ("Articolo materiale [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato."),
    /** Fornitore [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato. */
    tmpImpSupUidOrdLnkExp ("Fornitore [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato."),
    /** Articolo fornitore [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato. */
    tmpImpSupItmUidOrdLnkExp ("Articolo fornitore [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato."),
    /** Ordine [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato. */
    tmpImpShpOrdUidOrdLnkExp ("Ordine [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato."),
    /** Articolo ordine [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato. */
    tmpImpShpOrdItmUidOrdLnkExp ("Articolo ordine [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato."),
    /** Articolo [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato. */
    tmpImpItmUidOrdLnkExp ("Articolo [{1}] non presente in {2} legami [Uid: {3}]. Legame non importato."),
    
    /** Extra imports **/

    /** Risorsa [{1}] non presente in {2} assenza risorsa [Uid: {3}] */
    tmpImpResUidCalResAbs ("Risorsa [{1}] non presente in {2} assenza risorsa [Uid: {3}]");
    
    final private String message;
    final private String code;
    ApsTmpMessage (String message) {
        this.message = message;
        this.code = this.name ();
    } // ApsVrtMessage
    
    @Override
    public String getMessage () { return this.message; }
    @Override
    public String getCode () { return this.code; }
} // ApsVrtMessage
