package net.synergy2.tester.aps.tmp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.synergy2.db.aps.sce.ApsSce;
import net.synergy2.db.sys.itm.AngPrdVrnCat;
import net.synergy2.logic.aps.tmp.impl.AngPrdVrnCatTmp;
import net.synergy2.tester.aps.sce.ApsSceTestService;
import net.synergy2.tester.sys.bas.SysBasTestService;
import net.synergy2.tester.sys.itm.SysItmTestService;

public class ApsImportUpdateDataSet1 extends ApsImportDataSet {

    private final ApsSce scenario;
    private final SysItmTestService sysItm;
    private final SysBasTestService sysBas;
    private final ApsSceTestService apsSce;
    private final ApsTmpTestService apsTmp;

    public ApsImportUpdateDataSet1 (
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

    private ApsImportInsertDataSet1 dataSet1;

    private AngPrdVrnCat T0vrnCat;
   

    @Override
    protected String title () { return "Inserimento dati di base e verifica"; }
        
    // Aggiornamento campi semplici di record esistenti
    private void run1 () {
        T0vrnCat = apsTmp.put (dataSet1.getT0vrnCat ().setCatDsc ("PROVAAAAA"));
    }
    

    private void checkRun1 () {
        List<AngPrdVrnCat> T0prdVrnCats = apsTmp.getActiveAngPrdVrnCat ();
        Map<String, AngPrdVrnCat> T0prdVrnCatByCode = T0prdVrnCats.stream ().collect (Collectors.toMap (e -> AngPrdVrnCatTmp.get ().getCode (e), e -> e));
        AngPrdVrnCat T0apsVrnCat = T0prdVrnCatByCode.get (AngPrdVrnCatTmp.get ().getCode (T0vrnCat));
        apsTmp.checkEq (T0vrnCat, T0apsVrnCat);
    }

    @Override
    protected void preRun () {
        dataSet1 = new ApsImportInsertDataSet1 (scenario, sysItm, sysBas, apsSce, apsTmp);
        dataSet1.doRun ();
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
