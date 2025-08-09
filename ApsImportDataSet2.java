package net.synergy2.tester.aps.tmp;

import net.synergy2.db.aps.sce.ApsSce;
import net.synergy2.db.sys.grp.AngResGrp;
import net.synergy2.db.sys.res.AngRes;
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
        
    }
    
    private void run3 () {
        
    }
    
    private void checkRun1 () {
        
    }
    
    private void checkRun2 () {
        
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
