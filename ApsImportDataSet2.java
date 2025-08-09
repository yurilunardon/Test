package net.synergy2.tester.aps.tmp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.synergy2.db.aps.sce.ApsSce;
import net.synergy2.db.sys.grp.AngGrp;
import net.synergy2.db.sys.grp.AngResGrp;
import net.synergy2.db.sys.res.AngRes;
import net.synergy2.logic.aps.tmp.impl.AngResGrpTmp;
import net.synergy2.logic.aps.tmp.impl.AngResTmp;
import net.synergy2.tester.aps.sce.ApsSceTestService;
import net.synergy2.tester.sys.itm.SysItmTestService;
import net.synergy2.tester.sys.res.SysResTestService;

public class ApsImportDataSet2 extends ApsImportDataSet {
    private final ApsSce scenario;
    private final SysItmTestService sysItm;
    private final SysResTestService sysRes;
    private final ApsSceTestService apsSce;
    private final ApsTmpTestService apsTmp;

    public ApsImportDataSet2 (
        ApsSce scenario,
        SysItmTestService sysItm,
        SysResTestService sysRes,
        ApsSceTestService apsSce,
        ApsTmpTestService apsTmp
    ) {
        this.scenario = scenario;
        this.sysItm = sysItm;
        this.sysRes = sysRes;
        this.apsSce = apsSce;
        this.apsTmp = apsTmp;
    }

    private AngRes R1res;
    private AngResGrp R1resGrp;
    
    @Override
    protected String title () { return "Inserimento dati Risorse e verifica"; }

    private void run1 () {
        
    }
    
    private void run2 () {
        
        // Test con i riferimenti obbligatori inesistenti -> dovrebbe associare un record dummy in automatico per andare in errore di mancanza del "padre"
        // Test con i riferimenti facoltativi inesistenti -> non dovrebbe settare nulla quando viene importato in aps, quindi il riferimento a null
        
        // Creazione di un gruppo inesistente/vuoto per testare riferimenti mancanti
        AngGrp R1emptyGrp = AngGrp.newInstance()
            .setGrpCod("R1emptyGrp");
        
        // AngResGrp --/--> AngGrp (riferimento obbligatorio mancante)
        R1resGrp = AngResGrp.newInstance()
            .setResGrpCod("R1resGrp")
            .setGrp(R1emptyGrp);
        R1resGrp = apsTmp.post (R1resGrp);
        
        // AngRes --/--> AngResGrp (riferimento facoltativo mancante)  
        R1res = AngRes.newInstance()
            .setResCod("R1res")
            .setResGrp(R1resGrp);
        R1res = apsTmp.post (R1res);
        
    }
    
    private void run3 () {
        
    }
    
    private void checkRun1 () {
        
    }
    
    private void checkRun2 () {
        
        // Creazione di oggetti dummy per verificare che i riferimenti siano stati sostituiti con dummy
        AngGrp dummyGrp = AngGrp.newInstance().setGrpCod("@dummy");
        AngResGrp dummyResGrp = AngResGrp.newInstance().setResGrpCod("@dummy").setGrp(dummyGrp);
        
        // AngResGrp --/--> AngGrp
        List<AngResGrp> R1resGrps = apsTmp.getActiveAngResGrp();
        Map<String, AngResGrp> R1resGrpByCode = R1resGrps.stream().collect(Collectors.toMap(e -> AngResGrpTmp.get().getCode(e), e -> e));
        
        R1resGrp.setGrp(dummyGrp);
        
        AngResGrp R1apsResGrp = R1resGrpByCode.get(AngResGrpTmp.get().getCode(R1resGrp));
        apsTmp.checkEq(R1resGrp, R1apsResGrp);
        
        // AngRes --/--> AngResGrp  
        List<AngRes> R1ress = apsTmp.getActiveAngRes();
        Map<String, AngRes> R1resByCode = R1ress.stream().collect(Collectors.toMap(e -> AngResTmp.get().getCode(e), e -> e));
        
        R1res.setResGrp(dummyResGrp);
        
        AngRes R1apsRes = R1resByCode.get(AngResTmp.get().getCode(R1res));
        apsTmp.checkEq(R1res, R1apsRes);
        
    }
    
    private void checkRun3 () {
        
    }
    
    
    @Override
    protected void run () {
        run1();
        run2();
        run3();
    }

    @Override
    protected void check () {
        checkRun1();
        checkRun2();
        checkRun3();
    }

}
