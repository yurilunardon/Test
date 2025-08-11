package net.synergy2.logic.fct.tmp;

import java.time.Instant;
import net.synergy2.base.connections.SReadRemCon;
import net.synergy2.base.connections.SWriteCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.types.SDateTime;
import net.synergy2.logic.fct.tmp.common.FctTmpStore;

public class FctImportContext<S extends FctTmpStore, D extends FctTmpStoreDeletion> extends AGenericImportContext<S, D> {
    
    public FctImportContext (
        S store,
        D deletionStore,
        ExecutionContext context,
        SWriteCon wCon,
        SReadRemCon rrCon,
        SDateTime tssImp,
        Instant start,
        Boolean forceRemove
    ) {
        this.store = store;
        this.deletionStore = deletionStore;
        this.context = context;
        this.wCon = wCon;
        this.rrCon = rrCon;
        this.start = start;
        this.tssImp = tssImp;
        this.forceRemove = forceRemove;
    } // FctImportContext

    @Override public S getStore () {
        return store;
    } // getStore

    @Override public D getDeletionStore () {
        return deletionStore;
    } // getStore
    
} // ApsImportContext
