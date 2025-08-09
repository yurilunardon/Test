package net.synergy2.logic.aps.tmp.common;

public enum ApsTmpTableKey {
    
    AngJob ("commesse"),
    AngSubJob ("sottocommesse"),
    OrgLvl ("organigrammi"),
    AngApsAttVal ("attributi"),
    AngGrp ("commesse"),
    AngRes ("risorse"),
    AngApsRes ("risorsePrimarie"),
    ApsSceRes ("risorseScenario"),
    AngResGrp ("gruppiRisorsa"),
    AngApsCst ("vincoliSecondari"),
    ApsSceCst ("vincoliSecondariScenario"),
    AngApsCstGrp ("gruppoVincoli"),
    ApsSceResCurCst ("risorsaPrimariaVincoloSecondarioScenarioInUso"),
    ApsSceResCurAttVal ("risorsaPrimariaValoreAttributoInUso"),
    AngCus ("clienti"),
    AngPrdCyc ("cicliLavoro"),
    AngPrdCycOpr ("cicliFaseLavoro"),
    AngBom ("distintaBase"),
    AngPrdVrnCat ("categorieVarianti"),
    AngPrdVrn ("varianti"),
    AngItmVrn ("variantiArticolo"),
    AngItmMdl ("modelliArticolo"),
    AngItmMdlVrn ("modelliVariante"),
    AngPrdCycOprLnk ("legamiCicloFaseLavoro"),
    AngPrdCycOprCst ("vincoliSecondariCicloFaseLavoro"),
    AngPrdCycOprCstGrp ("gruppoVincoliCicloFaseLavoro"),
    AngPrdCycOprAtt ("attributiCicloFaseLavoro"),
    ApsCusOrd ("ordiniCliente"),
    ApsDem ("domande"),
    ApsSupOrd ("ordiniFornitore"),
    ApsSup ("approvvigionamenti"),
    ApsShpOrd ("ordiniProduzione"),
    ApsItmCop ("articoliCoprodotti"),
    ApsOpr ("fasi"),
    ApsMat ("materiali"),
    ApsOprLnk ("legamiFasi"),
    ApsOprCst ("vincoliSecondariPerFase"),
    ApsOprCstGrp ("gruppoVincoliSecondariPerFase"),
    ApsOprAtt ("attributiFase"),
    ApsOprResExp ("eccezioneFasePerRisorsa"), 
    ApsOrdLnk ("legamiOrdini");

    ApsTmpTableKey (String keyLabel) {
        this.keyLabel = keyLabel;
    } // ApsTmpTableKey
    
    private final String keyLabel;
    public String getKeyLabel () { return keyLabel; } // getKeyLabel

} // ApsTmpTableKey
