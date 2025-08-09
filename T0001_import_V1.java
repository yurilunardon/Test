package net.synergy2.tester.aps.tmp;

import java.util.List;
import net.synergy2.db.aps.sce.ApsSce;
import net.synergy2.tester.aps.sce.ApsSceTestService;
import net.synergy2.tester.base.RestTest;
import net.synergy2.tester.sys.bas.SysBasTestService;
import net.synergy2.tester.sys.itm.SysItmTestService;


public class T0001_import_V1 extends RestTest {

    SysItmTestService sysItm = new SysItmTestService (this);
    SysBasTestService sysBas = new SysBasTestService (this);
    ApsSceTestService apsSce = new ApsSceTestService (this);
    ApsTmpTestService apsTmp = new ApsTmpTestService (this);

    
    @Override public String getTestTitle () {
        return "Test importazione";
    } // getTestTitle
    
    @Override protected void setPrerequisites (boolean fullTest) {
    } // setPrerequisites
    
    @Override protected List<TestError> run (boolean fullTest) {

        ApsSce scenario = apsSce.post (apsSce.scenario ());

        ApsImportDataSet dataSet1 = new ApsImportDataSet1 (scenario, sysItm, sysBas, apsSce, apsTmp).doRun ();

        apsTmp.importToAps (scenario);

        dataSet1.doCheck ();

        return errorList;
    } // run

} // T0001_insertApsSceRes_V1