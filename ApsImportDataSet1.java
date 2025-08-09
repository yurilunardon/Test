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

public class ApsImportDataSet1 extends ApsImportDataSet {

    private final ApsSce scenario;
    private final SysItmTestService sysItm;
    private final SysBasTestService sysBas;
    private final ApsSceTestService apsSce;
    private final ApsTmpTestService apsTmp;

    public ApsImportDataSet1 (
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
    private AngUniMea T0uniMea;
    private AngItm T0item;

    private AngPrdVrnCat T0vrnCat;
    private AngPrdVrn T0vrn;
    
    private AngItmVrn T0itmVrn;

    private AngItmMdl T0itmMdl;
    private AngItmMdlVrn T0itmMdlVrn;

    
    private AngItm T0item1;
    private AngBom T0bom;

    private AngPrdCycOpr T0prdCycOpr1;
    private AngPrdCycOpr T0prdCycOpr2;
    private AngPrdCycOprLnk T0prdCycOprLnk;
    private AngPrdCyc T0prdCyc;
    
    private AngItm T1emptyItem;
    private AngPrdVrnCat T1emptyVrnCat;
    private AngPrdVrn T1emptyVrn;
    private AngItmVrn T1emptyItmVrn;
    private AngItmMdl T1emptyItmMdl;

    private AngPrdVrn T1vrn;
    
    private AngItmVrn T1itmVrn;

    private AngItmMdl T1itmMdl;

    private AngItmMdlVrn T1itmMdlVrn;

    private AngPrdCyc T1prdCyc;
    
    private AngPrdCycOpr T1prdCycOpr;
    
    private AngPrdCycOprLnk T1prdCycOprLnk;
    
    
    
    private AngPrdVrn test;
    private AngPrdVrnCat test2;
    
    private AngPrdVrn T2vrn;
    
    private AngPrdVrnCat T2vrnCat;
   
    
    
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
    // 21. Dichiarazione oggetti utili solo come riferimenti mancanti con newIstance() [TODO]
    // 22. Sistemare AngPrdCycOprLnk in run2() [TODO]
    // 23. Capire perchè per i PrdCyc devo fare .setItm (AngItm.newInstance ().setItmCod (T0item.getItmCod ())) e non posso fare .setItm(itm già inizializzato) [TODO]
    // 24. Risolvere problema creazione di parecchie entità AngUniMea [TODO]
    // 25. setPrdCycStd non funziona in run2();
    
    @Override
    protected String title () { return "Inserimento dati di base e verifica"; }
        
    // Test con tutto impostato correttamente
    private void run1 () {
        T0sup = sysBas.supplier ("[sup] Fornitore Metalmeccanico srl");
        T0sup = apsTmp.post (T0sup);


        T0uniMea = sysItm.measureUnit ("[uniMea] Pezzi")
            .setUniMeaLngDsc ("[uniMea] Pezzi estesa");
        T0uniMea = apsTmp.post (T0uniMea);


        T0item = sysItm.item ("[item] Vite M8")
            .setItmLngDsc ("[item] Vite M8")
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
            .setUniMea (T0uniMea)
            .setSup (T0sup);

        T0item = apsTmp.post (T0item);

        
        T0vrnCat = sysItm.variantCategory ("[varCat] Materiale");
        T0vrnCat = apsTmp.post (T0vrnCat);
    
          
        T0vrn = sysItm.variant ("[vrn] Acciaio Inox", T0vrnCat)
            .setCat (T0vrnCat);
        T0vrn = apsTmp.post (T0vrn);

          
        T0itmVrn = sysItm.itemVariant (T0item, T0vrn, true)
            .setItm (T0item)
            .setVrn (T0vrn);
        T0itmVrn = apsTmp.post (T0itmVrn);
        

        T0itmMdl = sysItm.itemModel (T0item, "Vite Test M8x20", false)
            .setItm (T0item);
        T0itmMdl = apsTmp.post (T0itmMdl);


        T0itmMdlVrn = sysItm.itemModelVariant (T0itmVrn, T0itmMdl)
            .setItmVrn (T0itmVrn)
            .setMdl (T0itmMdl);
        T0itmMdlVrn = apsTmp.post (T0itmMdlVrn);


        AngPrdCycTyp typLinked = AngPrdCycTyp.newInstance ().setUid (AngPrdCycTyp.LINKED_CYCLE);
        T0prdCyc = sysItm.cycle (T0item, "Ciclo produttivo VITE M8")
            .setItm (AngItm.newInstance ().setItmCod (T0item.getItmCod ()))
            .setTyp (typLinked)
            .setPrdCycStd (false);

        T0prdCyc = apsTmp.post (T0prdCyc);

        
        T0prdCycOpr1 = sysItm.cycleOperation (T0prdCyc, T0itmVrn, "1", "Tornitura")
            .setPrdCyc (T0prdCyc)
            .setItmVrn (T0itmVrn)
            .setOprNum ("1")
            .setOprEff (new BigDecimal ("100")); 
        
        T0prdCycOpr1 = apsTmp.post (T0prdCycOpr1);


        T0prdCycOpr2 = sysItm.cycleOperation (T0prdCyc, null, "2", "Filettatura") 
            .setPrdCyc (T0prdCyc)
            .setItmVrn (T0itmVrn)
            .setOprNum ("2")
            .setOprEff (new BigDecimal ("100"));

        T0prdCycOpr2 = apsTmp.post (T0prdCycOpr2);


        T0prdCycOprLnk = sysItm.cycleOperationLink (T0prdCycOpr1, T0prdCycOpr2)
            .setPrePrdCycOpr (T0prdCycOpr1)
            .setNxtPrdCycOpr (T0prdCycOpr2);

        T0prdCycOprLnk = apsTmp.post (T0prdCycOprLnk);
        
        T0item1 = sysItm.item ("Componente grezzo")
            .setItmLngDsc ("Componente grezzo");
        T0item1 = apsTmp.post (T0item1);
        
        T0bom = sysItm.billOfMaterial (T0item, T0item1, T0itmVrn, new BigDecimal ("10"))
            .setPrdItm (T0item)
            .setItm (T0item1)   
            .setItmVrn (T0itmVrn);
        T0bom = apsTmp.post (T0bom);

    }
    

    // Test con i riferimenti obbligatori inesistenti -> dovrebbe associare un record dummy in automatico per andare in errore di mancanza del "padre"
    // Test con i riferimenti facoltativi inesistenti -> non dovrebbe settare nulla quando viene importato in aps, quindi il riferimento a null
    private void run2 () {                       
        
        T1emptyItem = sysItm.item ("T1emptyItem");
        T1emptyVrnCat = sysItm.variantCategory("T1emptyVrnCat");
        T1emptyVrn = sysItm.variant ("T1emptyVrn", T1emptyVrnCat).setCat (T1emptyVrnCat);
        T1emptyItmMdl = sysItm.itemModel (T1emptyItem, null, false).setItm (T1emptyItem);
           
        // AngPrdVrn --/--> AngPrdVrnCat      
        T1vrn = apsTmp.post (sysItm.variant ("T1vrn", T1emptyVrnCat).setCat (T1emptyVrnCat));
            
        // AngItmVrn --/--> AngItm
        // AngItmVrn --/--> AngPrdVrn
        T1itmVrn = sysItm.itemVariant (T1emptyItem, T1emptyVrn, false).setItm (T1emptyItem).setVrn (T1emptyVrn);
  
        // AngItmMdl --/--> AngItm
        T1itmMdl = apsTmp.post (sysItm.itemModel (T1emptyItem, "T1itmMdl", false).setItm (T1emptyItem));
        
        // AngItmMdlVrn --/--> AngItmMdl
        // AngItmMdlVrn --/--> AngItmVrn
        T1itmMdlVrn = apsTmp.post (sysItm.itemModelVariant (T1itmVrn, T1emptyItmMdl).setItmVrn (T1itmVrn).setMdl (T1emptyItmMdl));
         
        // AngPrdCyc --/--> AngItm
        AngPrdCycTyp T1typLinked = AngPrdCycTyp.newInstance ().setUid (AngPrdCycTyp.ALTERNATIVE_CYCLE);     
        
        T1prdCyc = sysItm.cycle (T1emptyItem ,"T1prdCyc")
            .setItm (AngItm.newInstance ().setItmCod (T1emptyItem.getItmCod ()))
            .setTyp (T1typLinked);
        T1prdCyc = apsTmp.post (T1prdCyc);
  
        // AngPrdCycOpr --/--> AngPrdCyc
        T1prdCycOpr = sysItm.cycleOperation (T1prdCyc, null, "1", "T1prdCycOpr")
            .setPrdCyc (T1prdCyc)
            .setOprNum ("1");
        T1prdCycOpr = apsTmp.post (T1prdCycOpr);
        
        
        // AngPrdCycOprLnk --/--> AngPrdCycOpr  
        AngItm T1item5 = sysItm.item ("T1item5")
          .setItmLngDsc ("T1Item5");
        
        AngPrdCycTyp T1typLinked2 = AngPrdCycTyp.newInstance ().setUid (2);
        
        AngPrdCyc T1prdCyc2 = sysItm.cycle (T1item5 ,"T1prdCyc2")
            .setItm (AngItm.newInstance ().setItmCod (T1item5.getItmCod ()))
            .setTyp (T1typLinked2);
        T1prdCyc2 = apsTmp.post (T1prdCyc2);
    
        AngPrdCycOpr T1prdCycOpr1 = sysItm.cycleOperation (T1prdCyc2, null, "1", "T1prdCycOpr1")
              .setPrdCyc (T1prdCyc2)
              .setOprNum ("1");
        T1prdCycOpr1 = apsTmp.post (T1prdCycOpr1);
         
        
        AngPrdCycOpr T1prdCycOpr2 = sysItm.cycleOperation (T1prdCyc2, null, "2", "T1prdCycOpr2")
              .setPrdCyc (T1prdCyc2)
              .setOprNum ("2");
        T1prdCycOpr2 = apsTmp.post (T1prdCycOpr2);
          

        T1prdCycOprLnk = sysItm.cycleOperationLink (T1prdCycOpr1, T1prdCycOpr2)
            .setPrePrdCycOpr (T1prdCycOpr1)
            .setNxtPrdCycOpr (T1prdCycOpr2);

        T1prdCycOprLnk = apsTmp.post (T1prdCycOprLnk);
       
    }
    
    
    // Test di inserimento di entità con riferimenti già esistenti in aps ma non nel database temporaneo.
    // Test di aggiornamento campi.
    private void run3 () {
        
        //AngPrdVrnCat
        T2vrnCat = sysItm.variantCategory ("T2vrnCat");
        T2vrnCat = sysItm.post (T2vrnCat);
    
        
        //AngPrdVrn
        T2vrn = sysItm.variant ("T2vrn", T2vrnCat)
            .setCat (T2vrnCat);
        T2vrn = sysItm.post (T2vrn);
       
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
        AngPrdVrnCat dummyPrdVrnCat = AngPrdVrnCat.newInstance ().setCatCod ("@dummy");
        AngPrdVrn dummyPrdVrn = AngPrdVrn.newInstance ().setVrnCod ("@dummy").setCat (dummyPrdVrnCat);
        AngItmVrn dummyItmVrn = AngItmVrn.newInstance ().setItm (dummyItm).setVrn (dummyPrdVrn);
        AngItmMdl dummyItmMdl = AngItmMdl.newInstance ().setMdlCod ("@dummy").setItm (dummyItm);
        
        
        // AngPrdVrn --/--> AngPrdVrnCat
        List<AngPrdVrn> T1prdVrns = apsTmp.getActiveAngPrdVrn ();
        Map<String, AngPrdVrn> T1prdVrnByCode = T1prdVrns.stream ().collect (Collectors.toMap (e -> AngPrdVrnTmp.get ().getCode (e), e -> e));
     
        T1vrn.setCat (dummyPrdVrnCat);
        
        AngPrdVrn T1apsVrn = T1prdVrnByCode.get (AngPrdVrnTmp.get ().getCode (T1vrn));
        apsTmp.checkEq (T1vrn, T1apsVrn);
        
     
        // AngItmVrn --/--> AngItm
        // AngItmVrn --/--> AngPrdVrn
        List<AngItmVrn> T1itmVrns = apsTmp.getActiveAngItmVrn ();
        Map<String, AngItmVrn> T1itmVrnByCode = T1itmVrns.stream ().collect (Collectors.toMap (e -> AngItmVrnTmp.get ().getCode (e), e -> e));
        
        T1itmVrn.setItm (dummyItm);
        T1itmVrn.setVrn (dummyPrdVrn);
        
        AngItmVrn T1apsItmVrn = T1itmVrnByCode.get(AngItmVrnTmp.get ().getCode (T1itmVrn));
        apsTmp.checkEq (T1itmVrn, T1apsItmVrn);

        
        // AngItmMdl --/--> AngItm
        List<AngItmMdl> T1itmMdls = apsTmp.getActiveAngItmMdl ();
        Map<String, AngItmMdl> T1itmMdlByCode = T1itmMdls.stream ().collect (Collectors.toMap (e -> AngItmMdlTmp.get ().getCode (e), e -> e));
        T1itmMdl.setItm (dummyItm);
        
        AngItmMdl T1apsItmMdl = T1itmMdlByCode.get (AngItmMdlTmp.get ().getCode (T1itmMdl));
             
        apsTmp.checkEq (T1itmMdl, T1apsItmMdl);
        
        
        // AngItmMdlVrn --/--> AngItmMdl
        // AngItmMdlVrn --/--> AngItmVrn
        List<AngItmMdlVrn> T1itmMdlVrns = apsTmp.getActiveAngItmMdlVrn ();
        Map<String, AngItmMdlVrn> T1itmMdlVrnByCode = T1itmMdlVrns.stream ().collect (Collectors.toMap (e -> AngItmMdlVrnTmp.get().getCode(e), e -> e));
        T1itmMdlVrn.setItmVrn (dummyItmVrn);
        T1itmMdlVrn.setMdl (dummyItmMdl);

        AngItmMdlVrn T1apsItmMdlVrn = T1itmMdlVrnByCode.get (AngItmMdlVrnTmp.get ().getCode (T1itmMdlVrn));
  
        apsTmp.checkEq (T1itmMdlVrn, T1apsItmMdlVrn);

        
         // AngPrdCyc --/--> AngItm
         List<AngPrdCyc> T1prdCycs = apsTmp.getActiveAngPrdCyc ();
         Map<String, AngPrdCyc> T1prdCycByCode = T1prdCycs.stream ().collect (Collectors.toMap (e -> AngPrdCycTmp.get ().getCode (e), e -> e));
    
         T1prdCyc.setItm (dummyItm);
    
         AngPrdCyc T1apsPrdCyc = T1prdCycByCode.get (AngPrdCycTmp.get ().getCode (T1prdCyc));
         apsTmp.checkEq (T1prdCyc, T1apsPrdCyc);
         
         
         // AngPrdCycOpr --/--> AngPrdCyc
         List<AngPrdCycOpr> T1prdCycOprs = apsTmp.getActiveAngPrdCycOpr ();
         Map<String, AngPrdCycOpr> T1prdCycOprByCode = T1prdCycOprs.stream ().collect (Collectors.toMap (e -> AngPrdCycOprTmp.get ().getCode (e), e -> e));


         AngPrdCycTyp T1dummyTyp = AngPrdCycTyp.newInstance ()
             .setUid (2);

         AngPrdCyc T1dummyPrdCyc = AngPrdCyc.newInstance ()
             .setPrdCycCod ("@dummy")
             .setItm (dummyItm)
             .setTyp (T1dummyTyp) 
             .setPrdCycTypUid (2);
         
         T1prdCycOpr.setPrdCyc (T1dummyPrdCyc);

         AngPrdCycOpr T1apsPrdCycOpr = T1prdCycOprByCode.get (AngPrdCycOprTmp.get ().getCode (T1prdCycOpr));

         apsTmp.checkEq (T1prdCycOpr, T1apsPrdCycOpr);
        
        
          // AngPrdCycOprLnk --/--> AngPrdCycOpr
          List<AngPrdCycOprLnk> T1prdCycOprLnks = apsTmp.getActiveAngPrdCycOprLnk ();
          Map<String, AngPrdCycOprLnk> T1prdCycOprLnkByCode = T1prdCycOprLnks.stream ().collect (Collectors.toMap (e -> AngPrdCycOprLnkTmp.get ().getCode (e), e -> e));
    
          AngItm T1dummyItem = AngItm.newInstance().setItmCod("@dummy");
          
          AngPrdCycTyp T1dummyTyp1 = AngPrdCycTyp.newInstance ()
              .setUid (2);
          
          AngPrdCyc T1dummyPrdCycLnk = AngPrdCyc.newInstance()
              .setPrdCycCod("@dummy")
              .setItm(T1dummyItem)
              .setTyp (T1dummyTyp1)
              .setPrdCycTypUid(2);

          AngPrdCycOpr T1dummyPreOpr = AngPrdCycOpr.newInstance()
              .setPrdCyc(T1dummyPrdCycLnk)
              .setOprNum("@dummy");

          AngPrdCycOpr T1dummyNxtOpr = AngPrdCycOpr.newInstance()
              .setPrdCyc(T1dummyPrdCycLnk)
              .setOprNum("@dummy");
    
          T1prdCycOprLnk.setPrePrdCycOpr (T1dummyPreOpr);
          T1prdCycOprLnk.setNxtPrdCycOpr (T1dummyNxtOpr);
    
          AngPrdCycOprLnk T1apsPrdCycOprLnk = T1prdCycOprLnkByCode.get (AngPrdCycOprLnkTmp.get ().getCode (T1prdCycOprLnk));
          
          apsTmp.checkEq (T1prdCycOprLnk, T1apsPrdCycOprLnk);
    }
    
    
    private void checkRun3() {

        //AngPrdVrnCat
        AngPrdVrnCat T2vrnCatTMP = sysItm.variantCategory ("T2vrnCat")
            .setCatCod (T2vrnCat.getCatCod ())
            .setUid (T2vrnCat.getUid ());
        T2vrnCatTMP = apsTmp.post (T2vrnCatTMP);

        apsTmp.checkEq (T2vrnCat, T2vrnCatTMP);
        
        
        // AngPrdVrn
        AngPrdVrn T2vrnTMP = sysItm.variant ("T2vrn", T2vrnCatTMP)
            .setVrnCod (T2vrn.getVrnCod ())
            .setUid (T2vrn.getUid ())
            .setCat (T2vrnCatTMP);
        T2vrnTMP = apsTmp.post (T2vrnTMP);
        
        apsTmp.checkEq (T2vrn, T2vrnTMP);
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
