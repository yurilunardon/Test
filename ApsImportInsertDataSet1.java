package net.synergy2.tester.aps.tmp;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.synergy2.db.aps.sce.ApsSce;
import net.synergy2.db.sys.bas.AngSup;
import net.synergy2.db.sys.itm.AngBom;
import net.synergy2.db.sys.itm.AngItm;
import net.synergy2.db.sys.itm.AngItmMdl;
import net.synergy2.db.sys.itm.AngItmMdlVrn;
import net.synergy2.db.sys.itm.AngItmVrn;
import net.synergy2.db.sys.itm.AngPrdCyc;
import net.synergy2.db.sys.itm.AngPrdCycOpr;
import net.synergy2.db.sys.itm.AngPrdCycOprLnk;
import net.synergy2.db.sys.itm.AngPrdCycTyp;
import net.synergy2.db.sys.itm.AngPrdVrn;
import net.synergy2.db.sys.itm.AngPrdVrnCat;
import net.synergy2.db.sys.itm.AngUniMea;
import net.synergy2.logic.aps.tmp.impl.AngApsItmTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsSupTmp;
import net.synergy2.logic.aps.tmp.impl.AngBomTmp;
import net.synergy2.logic.aps.tmp.impl.AngItmMdlTmp;
import net.synergy2.logic.aps.tmp.impl.AngItmMdlVrnTmp;
import net.synergy2.logic.aps.tmp.impl.AngItmVrnTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdCycOprLnkTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdCycOprTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdCycTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdVrnCatTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdVrnTmp;
import net.synergy2.tester.aps.sce.ApsSceTestService;
import net.synergy2.tester.sys.bas.SysBasTestService;
import net.synergy2.tester.sys.itm.SysItmTestService;

public class ApsImportInsertDataSet1 extends ApsImportDataSet {

    private final ApsSce scenario;
    private final SysItmTestService sysItm;
    private final SysBasTestService sysBas;
    private final ApsSceTestService apsSce;
    private final ApsTmpTestService apsTmp;

    public ApsImportInsertDataSet1 (
        ApsSce scenario,
        SysItmTestService sysItm,
        SysBasTestService sysBas,
        ApsSceTestService apsSce,
        ApsTmpTestService apsTmp
    ) {
        this.scenario = scenario;
        this.sysItm = sysItm;
        this.sysBas = sysBas;
        this.apsSce = apsSce;
        this.apsTmp = apsTmp;
    }

    private AngSup T0sup;
    public AngSup getT0sup() { return T0sup; }
    
    private AngUniMea T0uniMea;
    public AngUniMea getT0uniMea() { return T0uniMea; }
    
    private AngItm T0item;
    public AngItm getT0item() { return T0item; }

    private AngPrdVrnCat T0vrnCat;
    public AngPrdVrnCat getT0vrnCat() { return T0vrnCat; }
    
    private AngPrdVrn T0vrn;
    public AngPrdVrn getT0vrn() { return T0vrn; }
    
    private AngItmVrn T0itmVrn;
    public AngItmVrn getT0itmVrn() { return T0itmVrn; }

    private AngItmMdl T0itmMdl;
    public AngItmMdl getT0itmMdl() { return T0itmMdl; }
    
    private AngItmMdlVrn T0itmMdlVrn;
    public AngItmMdlVrn getT0itmMdlVrn() { return T0itmMdlVrn; }

    private AngItm T0item1;
    private AngBom T0bom;
    public AngBom getT0bom() { return T0bom; }

    private AngPrdCyc T0prdCyc;
    public AngPrdCyc getT0prdCyc() { return T0prdCyc; }
    
    private AngPrdCycOpr T0prdCycOpr1;
    public AngPrdCycOpr getT0prdCycOpr1() { return T0prdCycOpr1; }
    
    private AngPrdCycOpr T0prdCycOpr2;
    public AngPrdCycOpr getT0prdCycOpr2() { return T0prdCycOpr2; }
    
    private AngPrdCycOprLnk T0prdCycOprLnk;
    public AngPrdCycOprLnk getT0prdCycOprLnk() { return T0prdCycOprLnk; }
    
        
    private AngPrdVrn T1vrn;
    
    private AngItmVrn T1itmVrn;

    private AngItmMdl T1itmMdl;

    private AngItmMdlVrn T1itmMdlVrn;

    private AngPrdCyc T1prdCyc;
    
    private AngPrdCycOpr T1prdCycOpr;
    private AngPrdCycOpr T1prdCycOpr2;
    
    private AngPrdCycOprLnk T1prdCycOprLnk;
    
    private AngBom T1bom;
    

    private AngPrdVrn T2vrn;
    private AngPrdVrn T2vrnTMP;
    private AngPrdVrnCat T2vrnCat;
    private AngItm T2itm;
    private AngItmVrn T2itmVrnTMP;
    private AngItmMdl T2itmMdl;
    private AngItmMdl T2itmMdlTMP;
    private AngItmMdlVrn T2itmMdlVrnTMP;
    private AngItmVrn T2itmVrn;
    private AngPrdCyc T2prdCyc;
    private AngPrdCyc T2prdCycTMP;
    private AngPrdCycOpr T2prdCycOpr;
    private AngPrdCycOpr T2prdCycOpr2;
    private AngPrdCycOpr T2prdCycOprTMP;
    private AngPrdCycOprLnk T2prdCycOprLnk;
    private AngPrdCycOprLnk T2prdCycOprLnkTMP;
    private AngItm T2itm2;
    private AngBom T2bom;
    private AngBom T2bomTMP;
    
    
    /************************************************************************************/
    /** MEMO ****************************************************************************/
    /************************************************************************************/
    // 1. Allinearsi con Develop per gestione di LogDel e LngDsc in AngUniMea [FATTO]
    // 2. Verificare fattibilità rimozione ridondanza metodi .setCat(), .setItm() [NON FUNZIONA]
    // 3. Provare ad utilizzare in tutti i .setX () di riferimento .getCopy () [NON FUNZIONA] 
    // 4. Sistemare AngBom, fare check [FATTO]
    // 5. Check di tutti i "componenti" (run1()) [FATTO]
    // 6. In run1() verificare prdCycOpr con itemVariant settato [FATTO]
    // 7. Su .setSqlSetter fare controllo solo sui facoltativi [TODO]
    // 8. Aggiungere .setReferenceMerger [FATTO]
    // 9. Aggiungere .getComparatorByCode [FATTO]
    // 10. Sistemare join in ApsTmpTestService [FATTO]
    // 11. Sistemare LogDel sotto-entità in ApsTmpTestService [FATTO]
    // 12. Sistemare align() in *Tmp [FATTO]
    // 13. Sistemare getDummy() in *Tmp [TODO]
    // 14. Riguardare LngDsc di AngUniMeaTmp [TODO]
    // 15. Cambiare messaggi warning in ApsTmpMessage ???? [TODO ????]
    // 16. Spostare file in "common" [TODO]
    // 17. Perchè in DbmTmpLogic c'era toRemoveAngItm.size() in tutti?
    
//    if (importBaseData) {
//        // Rimozione dati di base.
//        LOGGER.info (String.format ("AngItm toRemove size: %d", toRemoveAngItm.size ()));
//        for (var r : toRemoveAngItm) { AngItmDao.get ().delete (r, importContext, wCon); }
//        start = FctTmpUtil.get ().infoProcessingEnd (LOGGER, start, "End DbmSku toRemove in");
//        LOGGER.info (String.format ("AngSup toRemove size: %d", toRemoveAngItm.size ()));
//        for (var r : toRemoveAngSup) { AngSupDao.get ().delete (r, importContext, wCon); }
//        start = FctTmpUtil.get ().infoProcessingEnd (LOGGER, start, "End DbmSku toRemove in");
//        LOGGER.info (String.format ("AngUniMea toRemove size: %d", toRemoveAngItm.size ()));
//        for (var r : toRemoveAngUniMea) { AngUniMeaDao.get ().delete (r, importContext, wCon); }
//        start = FctTmpUtil.get ().infoProcessingEnd (LOGGER, start, "End DbmSku toRemove in");
//        LOGGER.info (String.format ("AngWhs toRemove size: %d", toRemoveAngItm.size ()));
//        for (var r : toRemoveAngWhs) { AngWhsDao.get ().delete (r, importContext, wCon); }
//        start = FctTmpUtil.get ().infoProcessingEnd (LOGGER, start, "End DbmSku toRemove in");
//    } // if
    
    // 18. Controllare clearApsOpr* controllo == null [TODO]
    // 19. Controllare EntitiesHandler [FATTO]
    // 20. In run2() provare a creare nel secondo caso un ItmVrn con stesso Item della prima ItmVrn ma con una nuova PrdVrn [TODO]
    // 21. Dichiarazione oggetti utili solo come riferimenti mancanti con newIstance() [FATTO]
    // 22. Sistemare AngPrdCycOprLnk in run2() [TODO]
    // 23. Capire perchè per i PrdCyc devo fare .setItm (AngItm.newInstance ().setItmCod (T0item.getItmCod ())) e non posso fare .setItm(itm già inizializzato) [FATTO] --> Usa PrdCycLst (non usato per errore nelle altre etità)
    // 24. Risolvere problema creazione di parecchie entità AngUniMea [TODO]
    // 25. Usare il getCopy ovunque quando si fa il .setRef [FATTO]
    // 26. Sistemare AngPrdCycOprLnk e AngBom in run2() [TODO]
    // 27. Aggiungere riferimenti non obbligatori mancanti in run2() [TODO]
    // 28. Sistemare descrizione sbagliata fra le due entità confrontate in checkEQ di AngPrdCycOpr run3() [TODO]
    // 29. Controllare i fare .setComparator dataset9 perchè in alcuni manca il && !value2 == LogDel. [TODO]
    // 30. Sistemare / fare il prdCycOprLnkField setSqlSetter in ApsOprLnkTmp [TODO]
    
    @Override
    protected String title () { return "Inserimento dati di base e verifica"; }
        
    // Test con tutto impostato correttamente
    private void run1 () {
        T0sup = apsTmp.post (sysBas.supplier ("Fornitore Metalmeccanico srl"));

        T0uniMea = apsTmp.post (sysItm.measureUnit ("Pezzi"));
   
        T0item = apsTmp.post (sysItm.item ("Vite M8")
            .setItmLngDsc ("Vite M8")
            .setItmIco ("mdi mdi-access-point") // "icon.d.ts"
            .setItmClr ("#9FA8DA")
            .setIgnMis (false)
            .setDlvBufDay (new BigDecimal ("5"))
            .setMinStkLvl (new BigDecimal ("100"))
            .setMinBtc (new BigDecimal ("500"))
            .setMaxBtc (new BigDecimal ("2000"))
            .setSupOnl (true)
            .setItmCst (new BigDecimal ("0.15"))
            .setItmAcr ("VITM8")
            .setCnvRat (new BigDecimal ("1"))
            .setMinBomQty (new BigDecimal ("1"))
            .setSrtQty (new BigDecimal ("100"))
            .setAdvDay (new BigDecimal ("7"))
            .setCovDay (new BigDecimal ("30"))
            .setJobPln (false)
            .setMulBat (false)
            .setUniMea (T0uniMea.getCopy ())
            .setSup (T0sup.getCopy ())
            );

        T0vrnCat = apsTmp.post (sysItm.variantCategory ("Materiale"));
       
        T0vrn = apsTmp.post (sysItm.variant ("Acciaio Inox", T0vrnCat).setCat (T0vrnCat.getCopy ()));
    
        T0itmVrn = apsTmp.post (sysItm.itemVariant (T0item, T0vrn, true).setItm (T0item.getCopy ().setUniMea (T0uniMea).setSup (T0sup)).setVrn (T0vrn.getCopy ().setCat (T0vrnCat)));

        T0itmMdl = apsTmp.post (sysItm.itemModel (T0item, "Vite Test M8x20", false).setItm (T0item.getCopy ().setUniMea (T0uniMea).setSup (T0sup)));

        T0itmMdlVrn = apsTmp.post (sysItm.itemModelVariant (T0itmVrn, T0itmMdl).setItmVrn (T0itmVrn.getCopy ().setItm (T0item).setVrn (T0vrn)).setMdl (T0itmMdl.getCopy ().setItm (T0item)));

        AngPrdCycTyp typLinked = AngPrdCycTyp.newInstance ().setUid (AngPrdCycTyp.LINKED_CYCLE);
        T0prdCyc = apsTmp.post (sysItm.cycle (T0item, "Ciclo produttivo VITE M8").setItm (T0item.getCopy ().setUniMea (T0uniMea).setSup (T0sup)).setTyp (typLinked).setPrdCycStd (false));

        T0prdCycOpr1 = apsTmp.post (sysItm.cycleOperation (T0prdCyc, T0itmVrn, "1", "Tornitura").setPrdCyc (T0prdCyc.getCopy ().setItm (T0item)).setItmVrn (T0itmVrn.getCopy ().setItm (T0item).setVrn (T0vrn)).setOprNum ("1").setOprEff (new BigDecimal ("100")));

        T0prdCycOpr2 = apsTmp.post (sysItm.cycleOperation (T0prdCyc, T0itmVrn, "2", "Filettatura").setPrdCyc (T0prdCyc.getCopy ().setItm (T0item)).setItmVrn (T0itmVrn.getCopy ().setItm (T0item).setVrn (T0vrn)).setOprNum ("2").setOprEff (new BigDecimal ("100")));

        T0prdCycOprLnk = apsTmp.post (sysItm.cycleOperationLink (T0prdCycOpr1, T0prdCycOpr2).setPrePrdCycOpr (T0prdCycOpr1.getCopy ().setPrdCyc (T0prdCyc).setItmVrn (T0itmVrn)).setNxtPrdCycOpr (T0prdCycOpr2.getCopy ().setPrdCyc (T0prdCyc).setItmVrn (T0itmVrn)));

        T0item1 = apsTmp.post (sysItm.item ("Componente grezzo"));

        T0bom = apsTmp.post (sysItm.billOfMaterial (T0item, T0item1, T0itmVrn, new BigDecimal ("10")).setPrdItm (T0item.getCopy ().setUniMea (T0uniMea).setSup (T0sup)).setItm (T0item1.getCopy ()).setItmVrn (T0itmVrn.getCopy ().setItm (T0item).setVrn (T0vrn)));

    }
    

    // Test con i riferimenti obbligatori inesistenti -> dovrebbe associare un record dummy in automatico per andare in errore di mancanza del "padre"
    // Test con i riferimenti facoltativi inesistenti -> non dovrebbe settare nulla quando viene importato in aps, quindi il riferimento a null
    private void run2 () {

        AngItm T1emptyItem = sysItm.item ("T1emptyItem");
        AngPrdVrnCat T1emptyVrnCat = sysItm.variantCategory("T1emptyVrnCat");
        AngPrdVrn T1emptyVrn = sysItm.variant ("T1emptyVrn", T1emptyVrnCat).setCat (T1emptyVrnCat.getCopy ());
        AngItmVrn T1emptyItmVrn = sysItm.itemVariant (T1emptyItem, T1emptyVrn, false).setItm (T1emptyItem.getCopy ()).setVrn (T1emptyVrn.getCopy ().setCat (T1emptyVrnCat));
        AngItmMdl T1emptyItmMdl = sysItm.itemModel (T1emptyItem, "T1emptyItmMdl", false).setItm (T1emptyItem.getCopy ());
        AngItmMdlVrn T1emptyItmMdlVrn = sysItm.itemModelVariant (T1emptyItmVrn, T1emptyItmMdl).setItmVrn (T1emptyItmVrn.getCopy ().setItm (T1emptyItem).setVrn (T1emptyVrn)).setMdl (T1emptyItmMdl.getCopy ().setItm (T1emptyItem));
        AngPrdCycTyp T1prdCycTyp = AngPrdCycTyp.newInstance ().setUid (AngPrdCycTyp.ALTERNATIVE_CYCLE);
        AngPrdCyc T1emptyPrdCyc = sysItm.cycle (T1emptyItem, "T1emptyCycle").setItm (T1emptyItem.getCopy ()).setTyp (T1prdCycTyp).setPrdCycStd (false);
        AngPrdCycOpr T1emptyPrdCycOpr1 = sysItm.cycleOperation (T1emptyPrdCyc, null, "5", "T1emptyPrdCycOpr1").setPrdCyc (T1emptyPrdCyc.getCopy ().setItm (T1emptyItem)).setOprNum ("5");
        AngPrdCycOpr T1emptyPrdCycOpr2 = sysItm.cycleOperation (T1emptyPrdCyc, null, "6", "T1emptyPrdCycOpr2").setPrdCyc (T1emptyPrdCyc.getCopy ().setItm (T1emptyItem)).setOprNum ("6");
        AngPrdCycOprLnk T1emptyPrdCycOprLnk = sysItm.cycleOperationLink (T1emptyPrdCycOpr1, T1emptyPrdCycOpr2).setPrePrdCycOpr (T1emptyPrdCycOpr1.getCopy ().setPrdCyc (T1emptyPrdCyc).setOprNum ("5")).setNxtPrdCycOpr (T1emptyPrdCycOpr2.getCopy ().setPrdCyc (T1emptyPrdCyc).setOprNum ("6"));
        AngItm T1emptyItem2 = sysItm.item ("T1emptyItem2");
        AngBom T1emptyBom = sysItm.billOfMaterial (T1emptyItem, T1emptyItem2, null, new BigDecimal("10")).setPrdItm (T1emptyItem).setItm (T1emptyItem2).setItmVrn (null);
        
        
        // AngPrdVrn --/--> AngPrdVrnCat
        T1vrn = apsTmp.post (T1emptyVrn);

        // AngItmVrn --/--> AngItm
        // AngItmVrn --/--> AngPrdVrn
        T1itmVrn = apsTmp.post (T1emptyItmVrn);

        // AngItmMdl --/--> AngItm
        T1itmMdl = apsTmp.post (T1emptyItmMdl);

        // AngItmMdlVrn --/--> AngItmMdl
        // AngItmMdlVrn --/--> AngItmVrn
        T1itmMdlVrn = apsTmp.post (T1emptyItmMdlVrn);
         
        // AngPrdCyc --/--> AngItm
        T1prdCyc = apsTmp.post (T1emptyPrdCyc);

        // AngPrdCycOpr --/--> AngPrdCyc
        T1prdCycOpr = apsTmp.post (T1emptyPrdCycOpr1);
//        T1prdCycOpr2 = apsTmp.post (T1emptyPrdCycOpr2);

        // AngPrdCycOprLnk --/--> AngPrdCycOpr
//        T1prdCycOprLnk = apsTmp.post (T1emptyPrdCycOprLnk);      
        
        // AngBom --/--> AngPrdItm
        // AngBom --/--> AngItm
        T1bom = apsTmp.post (T1emptyBom);
    }
    
    
    // Test di inserimento di entità con riferimenti già esistenti in aps ma non nel database temporaneo.
    private void run3 () {
        
        // POST ENTITA' IN APS
        // AngPrdVrnCat
        T2vrnCat = sysItm.post (sysItm.variantCategory ("T2vrnCat"));

        // AngPrdVrn
        T2vrn = sysItm.post (sysItm.variant ("T2vrn", T2vrnCat).setCat (T2vrnCat.getCopy ()));
        
        // AngItm
        T2itm = sysItm.post (sysItm.item ("T2itm"));
        
        // AngItmMdl
        T2itmMdl = sysItm.post (sysItm.itemModel (T2itm, "T2itmMdl", false).setItm (T2itm.getCopy ()));
        
        // AngItmVrn
        T2itmVrn = sysItm.post (sysItm.itemVariant (T2itm, T2vrn, false).setItm (T2itm.getCopy ()).setVrn (T2vrn.getCopy ().setCat (T2vrnCat)));
        
        // AngPrdCyc
        T2prdCyc = sysItm.post (sysItm.cycle (T2itm, "T2prdCyc").setItm (T2itm.getCopy ()).setPrdCycStd (false));
        
        // AngPrdCycOpr
        T2prdCycOpr = sysItm.post (sysItm.cycleOperation (T2prdCyc, null, "1", "T2prdCycOpr").setPrdCyc (T2prdCyc.getCopy ().setItm (T2itm)));
        T2prdCycOpr2 = sysItm.post (sysItm.cycleOperation (T2prdCyc, null, "2", "T2prdCycOpr2").setPrdCyc (T2prdCyc.getCopy ().setItm (T2itm)));
        
        // AngPrdCycOprLnk
        T2prdCycOprLnk = sysItm.post (sysItm.cycleOperationLink (T2prdCycOpr, T2prdCycOpr2).setPrePrdCycOpr (T2prdCycOpr.getCopy ().setPrdCyc (T2prdCyc)).setNxtPrdCycOpr (T2prdCycOpr2.getCopy ().setPrdCyc (T2prdCyc)));
        
        // AngBom
        T2itm2 = sysItm.post (sysItm.item ("T2itm2"));
        T2bom = sysItm.post (sysItm.billOfMaterial (T2itm, T2itm2, null, new BigDecimal("10")).setPrdItm (T2itm.getCopy ()).setItm (T2itm2.getCopy ()));
        
        
        
        
        // POST ENTITA' (CON RIFERIMENTI) IN TMP
        // AngPrdVrn
        T2vrnTMP = apsTmp.post (sysItm.variant ("T2vrnTMP", T2vrnCat).setCat (T2vrnCat.getCopy ()));
       
        // AngItmVrn
        T2itmVrnTMP = apsTmp.post (sysItm.itemVariant (T2itm, T2vrn, false).setItm (T2itm.getCopy ()).setVrn (T2vrn.getCopy ().setCat (T2vrnCat)));
        
        // AngItmMdl
        T2itmMdlTMP = apsTmp.post (sysItm.itemModel (T2itm, "T2itmMdlTMP", false).setItm (T2itm.getCopy ()));
        
        // AngItmMdlVrn 
        T2itmMdlVrnTMP = apsTmp.post (sysItm.itemModelVariant (T2itmVrn, T2itmMdl).setItmVrn (T2itmVrn.getCopy ().setItm (T2itm).setVrn (T2vrn)).setMdl (T2itmMdl.getCopy ().setItm (T2itm)));
        
        // AngPrdCyc 
        T2prdCycTMP = apsTmp.post (sysItm.cycle (T2itm, "T2prdCycTMP").setItm (T2itm.getCopy ()).setPrdCycStd (false));
        
        // AngPrdCycOpr
        T2prdCycOprTMP = apsTmp.post (sysItm.cycleOperation (T2prdCyc, null, "3", "T2prdCycOprTMP").setPrdCyc (T2prdCyc.getCopy ().setItm (T2itm)));
        
        // AngPrdCycOprLnk
        T2prdCycOprLnkTMP = apsTmp.post (sysItm.cycleOperationLink (T2prdCycOpr, T2prdCycOpr2).setPrePrdCycOpr (T2prdCycOpr.getCopy ().setPrdCyc (T2prdCyc)).setNxtPrdCycOpr (T2prdCycOpr2.getCopy ().setPrdCyc (T2prdCyc)));
       
        // AngBom
        T2bomTMP = apsTmp.post (sysItm.billOfMaterial (T2itm, T2itm2, null, new BigDecimal("10")).setPrdItm (T2itm.getCopy ()).setItm (T2itm2.getCopy ()));
    
    }

    
    private void checkRun1 () {

        List<AngItm> T0items = apsTmp.getActiveAngApsItm ();
        Map<String, AngItm> T0itemByCode = T0items.stream ().collect (Collectors.toMap (e -> AngApsItmTmp.get ().getCode (e), e -> e));
        AngItm T0apsItem = T0itemByCode.get (AngApsItmTmp.get ().getCode (T0item));
        apsTmp.checkEq (T0item, T0apsItem);

        
        List<AngSup> T0suppliers = apsTmp.getActiveAngApsSup ();
        Map<String, AngSup> T0supplierByCode = T0suppliers.stream ().collect (Collectors.toMap (e -> AngApsSupTmp.get ().getCode (e), e -> e));
        AngSup T0apsSup = T0supplierByCode.get (AngApsSupTmp.get ().getCode (T0sup));
        apsTmp.checkEq (T0sup, T0apsSup);


        List<AngPrdVrnCat> T0prdVrnCats = apsTmp.getActiveAngPrdVrnCat ();
        Map<String, AngPrdVrnCat> T0prdVrnCatByCode = T0prdVrnCats.stream ().collect (Collectors.toMap (e -> AngPrdVrnCatTmp.get ().getCode (e), e -> e));
        AngPrdVrnCat T0apsVrnCat = T0prdVrnCatByCode.get (AngPrdVrnCatTmp.get ().getCode (T0vrnCat));
        apsTmp.checkEq (T0vrnCat, T0apsVrnCat);


        List<AngPrdVrn> T0prdVrns = apsTmp.getActiveAngPrdVrn ();
        Map<String, AngPrdVrn> T0prdVrnByCode = T0prdVrns.stream ().collect (Collectors.toMap (e -> AngPrdVrnTmp.get ().getCode (e), e -> e));
        AngPrdVrn T0apsVrn = T0prdVrnByCode.get (AngPrdVrnTmp.get ().getCode (T0vrn));
        apsTmp.checkEq (T0vrn, T0apsVrn);


        List<AngItmVrn> T0itmVrns = apsTmp.getActiveAngItmVrn ();
        Map<String, AngItmVrn> T0itmVrnByCode = T0itmVrns.stream ().collect (Collectors.toMap (e -> AngItmVrnTmp.get ().getCode (e), e -> e));
        AngItmVrn T0apsItmVrn = T0itmVrnByCode.get(AngItmVrnTmp.get ().getCode (T0itmVrn));
        apsTmp.checkEq (T0itmVrn, T0apsItmVrn);


        List<AngItmMdl> T0itmMdls = apsTmp.getActiveAngItmMdl ();
        Map<String, AngItmMdl> T0itmMdlByCode = T0itmMdls.stream ().collect (Collectors.toMap (e -> AngItmMdlTmp.get ().getCode (e), e -> e));
        AngItmMdl T0apsItmMdl = T0itmMdlByCode.get (AngItmMdlTmp.get ().getCode (T0itmMdl));
        apsTmp.checkEq (T0itmMdl, T0apsItmMdl);


        List<AngItmMdlVrn> T0itmMdlVrns = apsTmp.getActiveAngItmMdlVrn ();
        Map<String, AngItmMdlVrn> T0itmMdlVrnByCode = T0itmMdlVrns.stream ().collect (Collectors.toMap (e -> AngItmMdlVrnTmp.get ().getCode (e), e -> e));
        AngItmMdlVrn T0apsItmMdlVrn = T0itmMdlVrnByCode.get (AngItmMdlVrnTmp.get ().getCode (T0itmMdlVrn));
        apsTmp.checkEq (T0itmMdlVrn, T0apsItmMdlVrn);


        List<AngPrdCyc> T0prdCycs = apsTmp.getActiveAngPrdCyc ();
        Map<String, AngPrdCyc> T0prdCycByCode = T0prdCycs.stream ().collect (Collectors.toMap (e -> AngPrdCycTmp.get ().getCode (e), e -> e));
        AngPrdCyc T0apsPrdCyc = T0prdCycByCode.get (AngPrdCycTmp.get ().getCode (T0prdCyc));
        apsTmp.checkEq (T0prdCyc, T0apsPrdCyc);


        List<AngPrdCycOpr> T0prdCycOprs = apsTmp.getActiveAngPrdCycOpr ();
        Map<String, AngPrdCycOpr> T0prdCycOprByCode = T0prdCycOprs.stream ().collect (Collectors.toMap (e -> AngPrdCycOprTmp.get ().getCode (e), e -> e));
        AngPrdCycOpr T0apsPrdCycOpr1 = T0prdCycOprByCode.get (AngPrdCycOprTmp.get ().getCode (T0prdCycOpr1));
        apsTmp.checkEq (T0prdCycOpr1, T0apsPrdCycOpr1);

        AngPrdCycOpr T0apsPrdCycOpr2 = T0prdCycOprByCode.get (AngPrdCycOprTmp.get ().getCode (T0prdCycOpr2));
        apsTmp.checkEq (T0prdCycOpr2, T0apsPrdCycOpr2);


        List<AngPrdCycOprLnk> T0prdCycOprLnks = apsTmp.getActiveAngPrdCycOprLnk ();
        Map<String, AngPrdCycOprLnk> T0prdCycOprLnkByCode = T0prdCycOprLnks.stream ().collect (Collectors.toMap (e -> AngPrdCycOprLnkTmp.get ().getCode (e), e -> e));
        AngPrdCycOprLnk T0apsPrdCycOprLnk = T0prdCycOprLnkByCode.get (AngPrdCycOprLnkTmp.get ().getCode (T0prdCycOprLnk));
        apsTmp.checkEq (T0prdCycOprLnk, T0apsPrdCycOprLnk);


        List<AngBom> T0bills = apsTmp.getActiveAngBom ();
        Map<String, AngBom> T0billByCode = T0bills.stream ().collect (Collectors.toMap (e -> AngBomTmp.get ().getCode (e), e -> e));
        AngBom T0apsBill = T0billByCode.get (AngBomTmp.get ().getCode (T0bom));
        apsTmp.checkEq (T0bom, T0apsBill);
    }
    
    
    private void checkRun2 () {
 
         AngItm dummyItm = AngItm.newInstance ().setItmCod ("@dummy");
         AngItm dummyItm2 = AngItm.newInstance ().setItmCod ("@dummy");
         AngPrdVrnCat dummyPrdVrnCat = AngPrdVrnCat.newInstance ().setCatCod ("@dummy");
         AngPrdVrn dummyPrdVrn = AngPrdVrn.newInstance ().setVrnCod ("@dummy").setCat (dummyPrdVrnCat.getCopy ());
         AngItmVrn dummyItmVrn = AngItmVrn.newInstance ().setItm (dummyItm.getCopy ()).setVrn (dummyPrdVrn.getCopy ().setCat (dummyPrdVrnCat));
         AngItmMdl dummyItmMdl = AngItmMdl.newInstance ().setMdlCod ("@dummy").setItm (dummyItm.getCopy ());
         AngPrdCycTyp dummyPrdCycTyp = AngPrdCycTyp.newInstance ().setUid (AngPrdCycTyp.ALTERNATIVE_CYCLE);
         AngPrdCyc dummyPrdCyc = AngPrdCyc.newInstance ().setPrdCycCod ("@dummy").setItm (dummyItm.getCopy ()).setTyp (dummyPrdCycTyp).setPrdCycTypUid (2);
         AngPrdCycOpr dummyPrdCycOpr1 = AngPrdCycOpr.newInstance ().setPrdCyc (dummyPrdCyc.getCopy ().setItm (dummyItm).setTyp (dummyPrdCycTyp)).setOprNum ("5");
         AngPrdCycOpr dummyPrdCycOpr2 = AngPrdCycOpr.newInstance ().setPrdCyc (dummyPrdCyc.getCopy ().setItm (dummyItm).setTyp (dummyPrdCycTyp)).setOprNum ("6");
         
         // AngPrdVrn --/--> AngPrdVrnCat
         List<AngPrdVrn> T1prdVrns = apsTmp.getActiveAngPrdVrn ();
         Map<String, AngPrdVrn> T1prdVrnByCode = T1prdVrns.stream ().collect (Collectors.toMap (e -> AngPrdVrnTmp.get ().getCode (e), e -> e));
        
         T1vrn.setCat (dummyPrdVrnCat.getCopy ());
        
         AngPrdVrn T1apsVrn = T1prdVrnByCode.get (AngPrdVrnTmp.get ().getCode (T1vrn));
         apsTmp.checkEq (T1vrn, T1apsVrn);
        
        
         // AngItmVrn --/--> AngItm
         // AngItmVrn --/--> AngPrdVrn
         List<AngItmVrn> T1itmVrns = apsTmp.getActiveAngItmVrn ();
         Map<String, AngItmVrn> T1itmVrnByCode = T1itmVrns.stream ().collect (Collectors.toMap (e -> AngItmVrnTmp.get ().getCode (e), e -> e));
        
         T1itmVrn.setItm (dummyItm.getCopy ());
         T1itmVrn.setVrn (dummyPrdVrn.getCopy ().setCat (dummyPrdVrnCat));
        
         AngItmVrn T1apsItmVrn = T1itmVrnByCode.get(AngItmVrnTmp.get ().getCode (T1itmVrn));
         apsTmp.checkEq (T1itmVrn, T1apsItmVrn);
        
        
         // AngItmMdl --/--> AngItm
         List<AngItmMdl> T1itmMdls = apsTmp.getActiveAngItmMdl ();
         Map<String, AngItmMdl> T1itmMdlByCode = T1itmMdls.stream ().collect (Collectors.toMap (e -> AngItmMdlTmp.get ().getCode (e), e -> e));
         T1itmMdl.setItm (dummyItm.getCopy ());
        
         AngItmMdl T1apsItmMdl = T1itmMdlByCode.get (AngItmMdlTmp.get ().getCode (T1itmMdl));
         apsTmp.checkEq (T1itmMdl, T1apsItmMdl);
        
        
         // AngItmMdlVrn --/--> AngItmMdl
         // AngItmMdlVrn --/--> AngItmVrn
         List<AngItmMdlVrn> T1itmMdlVrns = apsTmp.getActiveAngItmMdlVrn ();
         Map<String, AngItmMdlVrn> T1itmMdlVrnByCode = T1itmMdlVrns.stream ().collect (Collectors.toMap (e -> AngItmMdlVrnTmp.get().getCode(e), e -> e));
         T1itmMdlVrn.setItmVrn (dummyItmVrn.getCopy ().setItm (dummyItm).setVrn (dummyPrdVrn));
         T1itmMdlVrn.setMdl (dummyItmMdl.getCopy ().setItm (dummyItm));
        
         AngItmMdlVrn T1apsItmMdlVrn = T1itmMdlVrnByCode.get (AngItmMdlVrnTmp.get ().getCode (T1itmMdlVrn));
         apsTmp.checkEq (T1itmMdlVrn, T1apsItmMdlVrn);
        
        
          // AngPrdCyc --/--> AngItm
          List<AngPrdCyc> T1prdCycs = apsTmp.getActiveAngPrdCyc ();
          Map<String, AngPrdCyc> T1prdCycByCode = T1prdCycs.stream ().collect (Collectors.toMap (e -> AngPrdCycTmp.get ().getCode (e), e -> e));
          T1prdCyc.setItm (dummyItm.getCopy ());
        
          AngPrdCyc T1apsPrdCyc = T1prdCycByCode.get (AngPrdCycTmp.get ().getCode (T1prdCyc));
          apsTmp.checkEq (T1prdCyc, T1apsPrdCyc);
         
         
             // AngPrdCycOpr --/--> AngPrdCyc
             List<AngPrdCycOpr> T1prdCycOprs = apsTmp.getActiveAngPrdCycOpr ();
             Map<String, AngPrdCycOpr> T1prdCycOprByCode = T1prdCycOprs.stream ().collect (Collectors.toMap (e -> AngPrdCycOprTmp.get ().getCode (e), e -> e));       
             T1prdCycOpr.setPrdCyc (dummyPrdCyc.getCopy ().setItm (dummyItm));
    
             AngPrdCycOpr T1apsPrdCycOpr = T1prdCycOprByCode.get (AngPrdCycOprTmp.get ().getCode (T1prdCycOpr));
             apsTmp.checkEq (T1prdCycOpr, T1apsPrdCycOpr);
        
        
//          // AngPrdCycOprLnk --/--> AngPrdCycOpr DA COMPELTARE:
//          List<AngPrdCycOprLnk> T1prdCycOprLnks = apsTmp.getActiveAngPrdCycOprLnk ();
//          Map<String, AngPrdCycOprLnk> T1prdCycOprLnkByCode = T1prdCycOprLnks.stream ().collect (Collectors.toMap (e -> AngPrdCycOprLnkTmp.get ().getCode (e), e -> e));
//          T1prdCycOprLnk.setPrePrdCycOpr (dummyPrdCycOpr1.getCopy ().setPrdCyc (dummyPrdCyc).setOprNum ("5"));
//          T1prdCycOprLnk.setNxtPrdCycOpr (dummyPrdCycOpr2.getCopy ().setPrdCyc (dummyPrdCyc).setOprNum ("6"));
//          
//          AngPrdCycOprLnk T1apsPrdCycOprLnk = T1prdCycOprLnkByCode.get (AngPrdCycOprLnkTmp.get ().getCode (T1prdCycOprLnk));
//          apsTmp.checkEq (T1prdCycOprLnk, T1apsPrdCycOprLnk);
             
            
             // AngBom --/--> AngPrdItm
             // AngBom --/--> AngItm
             List<AngBom> T1bills = apsTmp.getActiveAngBom ();
             Map<String, AngBom> T1billByCode = T1bills.stream ().collect (Collectors.toMap (e -> AngBomTmp.get ().getCode (e), e -> e));
             T1bom.setPrdItm (dummyItm.getCopy ());
             T1bom.setItm (dummyItm2.getCopy ());
             T1bom.setItmVrn (null);
             
             AngBom T1apsBill = T1billByCode.get (AngBomTmp.get ().getCode (T1bom));
             apsTmp.checkEq (T1bom, T1apsBill);     
    }
    
    
    private void checkRun3() {

        List<AngPrdVrn> T2prdVrns = apsTmp.getActiveAngPrdVrn ();
        Map<String, AngPrdVrn> T2prdVrnByCode = T2prdVrns.stream ().collect (Collectors.toMap (e -> AngPrdVrnTmp.get ().getCode (e), e -> e));
        AngPrdVrn T2apsVrn = T2prdVrnByCode.get (AngPrdVrnTmp.get ().getCode (T2vrnTMP));
        apsTmp.checkEq (T2vrnTMP, T2apsVrn);
        
        
        List<AngItmVrn> T2itmVrns = apsTmp.getActiveAngItmVrn ();
        Map<String, AngItmVrn> T2itmVrnByCode = T2itmVrns.stream ().collect (Collectors.toMap (e -> AngItmVrnTmp.get ().getCode (e), e -> e));
        AngItmVrn T2apsItmVrn = T2itmVrnByCode.get(AngItmVrnTmp.get ().getCode (T2itmVrnTMP));
        apsTmp.checkEq (T2itmVrnTMP, T2apsItmVrn);
        
        
        List<AngItmMdl> T2itmMdls = apsTmp.getActiveAngItmMdl ();
        Map<String, AngItmMdl> T2itmMdlByCode = T2itmMdls.stream ().collect (Collectors.toMap (e -> AngItmMdlTmp.get ().getCode (e), e -> e));
        AngItmMdl T2apsItmMdl = T2itmMdlByCode.get (AngItmMdlTmp.get ().getCode (T2itmMdlTMP));
        apsTmp.checkEq (T2itmMdlTMP, T2apsItmMdl);


        List<AngItmMdlVrn> T2itmMdlVrns = apsTmp.getActiveAngItmMdlVrn ();
        Map<String, AngItmMdlVrn> T2itmMdlVrnByCode = T2itmMdlVrns.stream ().collect (Collectors.toMap (e -> AngItmMdlVrnTmp.get ().getCode (e), e -> e));
        AngItmMdlVrn T2apsItmMdlVrn = T2itmMdlVrnByCode.get (AngItmMdlVrnTmp.get ().getCode (T2itmMdlVrnTMP));
        apsTmp.checkEq (T2itmMdlVrnTMP, T2apsItmMdlVrn);

        
        List<AngPrdCyc> T2prdCycs = apsTmp.getActiveAngPrdCyc ();
        Map<String, AngPrdCyc> T2prdCycByCode = T2prdCycs.stream ().collect (Collectors.toMap (e -> AngPrdCycTmp.get ().getCode (e), e -> e));
        AngPrdCyc T2apsPrdCyc = T2prdCycByCode.get (AngPrdCycTmp.get ().getCode (T2prdCycTMP));
        apsTmp.checkEq (T2prdCycTMP, T2apsPrdCyc);


        List<AngPrdCycOpr> T2prdCycOprs = apsTmp.getActiveAngPrdCycOpr ();
        Map<String, AngPrdCycOpr> T2prdCycOprByCode = T2prdCycOprs.stream ().collect (Collectors.toMap (e -> AngPrdCycOprTmp.get ().getCode (e), e -> e));
        AngPrdCycOpr T2apsPrdCycOpr = T2prdCycOprByCode.get (AngPrdCycOprTmp.get ().getCode (T2prdCycOprTMP));
        apsTmp.checkEq (T2prdCycOprTMP, T2apsPrdCycOpr);


        List<AngPrdCycOprLnk> T2prdCycOprLnks = apsTmp.getActiveAngPrdCycOprLnk ();
        Map<String, AngPrdCycOprLnk> T2prdCycOprLnkByCode = T2prdCycOprLnks.stream ().collect (Collectors.toMap (e -> AngPrdCycOprLnkTmp.get ().getCode (e), e -> e));
        AngPrdCycOprLnk T2apsPrdCycOprLnk = T2prdCycOprLnkByCode.get (AngPrdCycOprLnkTmp.get ().getCode (T2prdCycOprLnkTMP));
        apsTmp.checkEq (T2prdCycOprLnkTMP, T2apsPrdCycOprLnk);


        List<AngBom> T2bills = apsTmp.getActiveAngBom ();
        Map<String, AngBom> T2billByCode = T2bills.stream ().collect (Collectors.toMap (e -> AngBomTmp.get ().getCode (e), e -> e));
        AngBom T2apsBill = T2billByCode.get (AngBomTmp.get ().getCode (T2bomTMP));
        apsTmp.checkEq (T2bomTMP, T2apsBill);
    }

    @Override
    protected void preRun () {
    }

    @Override
    protected void run () {
       run1 ();
       run2 ();
       run3();
    }
    
    
    @Override
    protected void check () {
        checkRun1();
        checkRun2();
        checkRun3();
    }

}
