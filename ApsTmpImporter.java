package net.synergy2.logic.aps.tmp;

import java.util.List;
import java.util.Map;
import net.synergy2.base.util.collections.EntitiesHandler;
import net.synergy2.base.util.function.SConsumer;
import net.synergy2.db.base.SModel;
import net.synergy2.logic.aps.tmp.common.ApsTmpStore;
import net.synergy2.logic.fct.tmp.AGenericTmp;
import net.synergy2.logic.fct.tmp.FctTmpImporter;
import org.slf4j.Logger;

public class ApsTmpImporter<M extends SModel> extends FctTmpImporter<M, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> {

    public ApsTmpImporter (
        Logger logger,
        AGenericTmp<M, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> tempInstance,
        ApsImportContext context,
        EntitiesHandler<M, String> entitiesHandler,
        SConsumer<M> aligner,
        
        // Variabili di output durante il processo di entitiesHandler
        List<M> insertCandidates,
        List<M> updateCandidates,
        Map<String, Long> upsertedEntities,
        Map<String, M> removeCandidates
    ) {
        super (
            logger, tempInstance, context, entitiesHandler, aligner,
            insertCandidates, updateCandidates, upsertedEntities, removeCandidates
        );
    } // ApsTmpImporter
    
} // ApsTmpImporter
