package net.synergy2.tester.aps.tmp;

import net.synergy2.base.util.devel.SynergyLogger;

public abstract class ApsImportDataSet {

    protected abstract String title ();

    protected abstract void preRun ();

    protected abstract void run ();

    protected abstract void check ();

    public ApsImportDataSet printTitle () {
        SynergyLogger.get ().info ("======> " + title () + " <======");
        return this;
    }

    public ApsImportDataSet doPreRun () {
        preRun ();
        return this;
    }

    public ApsImportDataSet doRun () {
        run ();
        return this;
    }

    public ApsImportDataSet doCheck () {
        check ();
        return this;
    }

}
