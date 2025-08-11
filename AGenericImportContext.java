package net.synergy2.logic.fct.tmp;

import java.time.Instant;
import net.synergy2.base.connections.SReadRemCon;
import net.synergy2.base.connections.SWriteCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.types.SDateTime;
import net.synergy2.logic.fct.tmp.common.FctTmpStore;

public abstract class AGenericImportContext<S extends FctTmpStore, D extends FctTmpStoreDeletion> implements IGenericImportContext<S, D> {
    
    protected ExecutionContext context;
    @Override public ExecutionContext getContext () { return context; }
    
    protected SWriteCon wCon;
    @Override public SWriteCon getWCon () { return wCon; }
    
    protected SReadRemCon rrCon;
    @Override public SReadRemCon getRrCon () { return rrCon; }
    
    protected Instant start;
    @Override public Instant getStart () { return start; }
    @Override public void setStart (Instant start) { this.start = start; }
    
    protected SDateTime tssImp;
    @Override public SDateTime getTssImp () { return tssImp; }
    
    // Eliminazione forzata per riallineamento completo.
    protected boolean forceRemove;
    @Override public boolean getForceRemove () { return forceRemove; }

    protected S store;
    @Override public abstract S getStore ();// { return store; }

    protected D deletionStore;
    @Override public abstract D getDeletionStore ();// { return store; }

} // AGenericImportContext
