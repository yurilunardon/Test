package net.synergy2.tester.aps.tmp;

import net.synergy2.base.util.devel.SynergyLogger;

public abstract class ApsImportDataSet {

    protected abstract String title ();

    protected abstract void run ();

    protected abstract void check ();

    public ApsImportDataSet doRun () {
        SynergyLogger.get ().info ("======> " + title () + " <======");
        run ();
        return this;
    }

    public ApsImportDataSet doCheck () {
        check ();
        return this;
    }

}
