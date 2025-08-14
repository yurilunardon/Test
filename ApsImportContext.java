package net.synergy2.logic.aps.tmp;

import java.time.Instant;
import java.util.Map;
import net.synergy2.base.connections.SReadRemCon;
import net.synergy2.base.connections.SWriteCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.types.SDateTime;
import net.synergy2.logic.aps.tmp.common.ApsTmpStore;
import net.synergy2.logic.fct.tmp.FctImportContext;

public class ApsImportContext extends FctImportContext<ApsTmpStore, ApsTmpStoreDeletion> {
    protected Long scenarioUid;
    protected Map<Long, Long> priorityGroupUidByGroupUid;

    public ApsImportContext (
        ApsTmpStore store,
        ApsTmpStoreDeletion deletionStore,
        ExecutionContext context,
        SWriteCon wCon,
        SReadRemCon rrCon,
        SDateTime tssImp,
        Instant start,
        Long scenarioUid,
        Map<Long, Long> priorityGroupUidByGroupUid,
        boolean forceRemove
    ) {
        super (store, deletionStore, context, wCon, rrCon, tssImp, start, forceRemove);
        this.scenarioUid = scenarioUid;
        this.priorityGroupUidByGroupUid = priorityGroupUidByGroupUid;
    } // ApsImportContext

    public Long getScenarioUid () {
        return scenarioUid;
    } // getScenarioUid
    
    public Map<Long, Long> getPriorityGroupUidByGroupUid () {
        return priorityGroupUidByGroupUid;
    } // getPriorityGroupUidByGroupUid

} // ApsImportContext
