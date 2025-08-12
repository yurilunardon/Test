package net.synergy2.logic.fct.tmp;

import java.time.Instant;
import net.synergy2.base.connections.SReadRemCon;
import net.synergy2.base.connections.SWriteCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.types.SDateTime;
import net.synergy2.logic.fct.tmp.common.FctTmpStore;

public interface IGenericImportContext<S extends FctTmpStore, D extends FctTmpStoreDeletion> {
    public ExecutionContext getContext ();

    public SWriteCon getWCon ();

    public SReadRemCon getRrCon ();

    public Instant getStart ();

    public void setStart (Instant start);

    public SDateTime getTssImp ();

    public S getStore ();

    public D getDeletionStore ();
    
    public boolean getForceRemove ();

} // IGenericImportContext
