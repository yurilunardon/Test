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
    private AngUniMea T0uniMea;
    private AngItm T0item;

    private AngPrdVrnCat T0vrnCat;
    private AngPrdVrn T0vrn;
    
    private AngItmVrn T0itmVrn;

    private AngItmMdl T0itmMdl;
    private AngItmMdlVrn T0itmMdlVrn;

    
    @Override
    protected String title () { return "Inserimento dati di base e verifica"; }
        
    // Test con tutto impostato correttamente
    private void run1 () {
        T0sup = sysBas.supplier ("Fornitore Metalmeccanico srl");
        T0sup = apsTmp.post (T0sup);


        T0uniMea = sysItm.measureUnit ("Pezzi")
            .setUniMeaLngDsc ("Pezzi estesa");
        T0uniMea = apsTmp.post (T0uniMea);


        T0item = sysItm.item ("Vite M8")
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
            .setUniMea (T0uniMea)
            .setSup (T0sup);

        T0item = apsTmp.post (T0item);


        T0vrnCat = sysItm.variantCategory ("Materiale");
        T0vrnCat = apsTmp.post (T0vrnCat);


        T0vrn = apsTmp.post (sysItm.variant ("Acciaio Inox", T0vrnCat).setCat (T0vrnCat));
            


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

    }
    

    @Override
    protected void preRun () {
    }

    @Override
    protected void run () {
       run1 ();
    }
    
    
    @Override
    protected void check () {
       checkRun1();
    }

}
