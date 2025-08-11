package net.synergy2.logic.fct.tmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.synergy2.base.exceptions.SException;
import net.synergy2.base.util.collections.EntitiesHandler;
import net.synergy2.base.util.collections.EntitiesHandler.EntityComparator;
import net.synergy2.base.util.collections.EntitiesHandler.EntityKeyGetter;
import net.synergy2.base.util.collections.EntitiesHandler.EntityOnEqual;
import net.synergy2.base.util.collections.EntitiesHandler.EntityOnNew;
import net.synergy2.base.util.collections.EntitiesHandler.EntityOnNotEquals;
import net.synergy2.base.util.collections.EntitiesHandler.EntityOnOld;
import net.synergy2.base.util.datas.BooleanUtil;
import net.synergy2.base.util.function.SConsumer;
import net.synergy2.db.base.SModel;
import net.synergy2.logic.fct.tmp.common.FctTmpStore;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import net.synergy2.logic.fct.tmp.common.TmpField;
import org.slf4j.Logger;

/**
 * Utility per avere sempre stesso logger e contesto per tutte le tabelle importate.
 */
public class FctImportManager<S extends FctTmpStore, D extends FctTmpStoreDeletion, C extends FctImportContext<S, D>> {

    protected Logger LOGGER;
    protected C context;

    public FctImportManager (Logger logger, C ctx) {
        this.LOGGER = logger;
        this.context = ctx;
    } // FctImportManager
    
    /**
     * Esegue l'import di una singola tabella con entities handler custom
     * 
     * @param tempTable Tabella da importare
     * @param entitiesHandler Entities handler custom
     */
    public <M extends SModel> void importWithCustomEntitiesHandler (
        AGenericTmp<M, S, D, C> tempTable,
        EntitiesHandler<M, String> entitiesHandler,
        SConsumer<M> aligner,
        List<M> insertCandidates,
        List<M> updateCandidates,
        Map<String, M> removeCandidates,
        Map<String, Long> upsertedEntities
    ) throws SException {
        // Ottengo le nuove entità.
        List<M> newEntities = tempTable.getList (context);
        new FctTmpImporter<M, S, D, C> (
            LOGGER, tempTable, context, entitiesHandler, aligner,
            insertCandidates, updateCandidates, upsertedEntities, removeCandidates
        )
        .logStart ()
        .initLists ()
        .runEntitiesHandler ()
        .logCandidates ()
        .runInsert ()
        .runUpdate ()
        .setEntitiesOnStore ()
        .logEnd ();
    } // importWithCustomEntitiesHandler

    /**
     * Esegue l'import standard di una singola tabella con entities handler di default (considera anche impDel e impUpd).
     * 
     * @param tempTable Tabella da importare.
     * @return Record da rimuovere dopo la comparazione, tenendo conto dei flag ImpDel e ImpUpd.
     */
    public <M extends SModel> void standardImport (AGenericTmp<M, S, D, C> tempTable) throws SException {
        Map<String, Long> upsertedEntities = new HashMap<> ();
        // Lista di entità da inserire, aggiornare e rimuovere.
        List<M> insertCandidates = new ArrayList<> ();
        List<M> updateCandidates = new ArrayList<> ();
        Map<String, M> removeCandidates = new HashMap<> ();
        // Ottengo l'entities handler di base.
        EntitiesHandler<M, String> entitiesHandler = getBaseEntitiesHandler (tempTable, upsertedEntities, insertCandidates, updateCandidates, removeCandidates);
        FctTmpImporter<M, S, D, C> singleImporter = new FctTmpImporter<M, S, D, C> (
            LOGGER, tempTable, context, entitiesHandler, newEntity -> {},
            insertCandidates, updateCandidates, upsertedEntities, removeCandidates
        );
        singleImporter
            .logStart ()
            .initLists ()
            .runEntitiesHandler ()
            .logCandidates ()
            .runInsert ()
            .runUpdate ()
            .setEntitiesOnStore ()
            .logEnd ();
    } // standardImport

    /**
     * Esegue l'import di una singola tabella con entities handler di default (considera anche impDel e impUpd) e gestendo l'align.
     * 
     * @return Record da rimuovere dopo la comparazione, tenendo conto dei flag ImpDel e ImpUpd.
     */
    public <M extends SModel> void standardImportWithAlign (AGenericTmp<M, S, D, C> tempAlignTable) throws SException {
        Map<String, Long> upsertedEntities = new HashMap<> ();
        // Lista di entità da inserire, aggiornare e rimuovere.
        List<M> insertCandidates = new ArrayList<> ();
        List<M> updateCandidates = new ArrayList<> ();
        Map<String, M> removeCandidates = new HashMap<> ();
        // Ottengo l'entities handler che gestisce l'align.
        EntitiesHandler<M, String> entitiesHandler = getEntitiesHandlerWithAlign (tempAlignTable, upsertedEntities, insertCandidates, updateCandidates, removeCandidates);
        FctTmpImporter<M, S, D, C> singleImporter = new FctTmpImporter<M, S, D, C> (
            LOGGER, tempAlignTable, context, entitiesHandler, newEntity -> tempAlignTable.align (newEntity, context, true),
            insertCandidates, updateCandidates, upsertedEntities, removeCandidates
        );
        singleImporter
            .logStart ()
            .initLists ()
            .runEntitiesHandler ()
            .logCandidates ()
            .runInsert ()
            .runUpdate ()
            .setEntitiesOnStore ()
            .logEnd ();
    } // standardImportWithAlign

    /**
     * Ritorna l'EntitiesHandler base per le entità anagrafiche.
     * 
     * @param tmpIst Istanza della classa di mappatura da temp a tabella applicativa
     * @param upsertedEntities Entità aggiornate.
     * @param toRemove Record da rimuovere.
     */
    public <M extends SModel> EntitiesHandler<M, String> getBaseEntitiesHandler (
        AGenericTmp<M, S, D, C> tmpIst,
        // Map<String, M> oldEntitiesByCode,
        Map<String, Long> upsertedEntities,
        List<M> toInsert,
        List<M> toUpdate,
        Map<String, M> toRemove
    ) throws SException {
        List<TmpField<?, M>> fields = tmpIst.getFields (context);
        return new EntitiesHandler<M, String> ()
            .keyGetter (getDefaultKeyGetter (tmpIst))
            // .onCheckUniqueness (
            //     newEntity -> tmpIst.getCode (newEntity),
            //     (entityCode, entity) -> new SCodeExistsException (tmpIst.getTableUid (), tmpIst.getAlias (), entity.getByField (tmpIst.getUidField ()), entityCode)
            // )
            // .onBefore (getDefaultOnBefore (newEntity -> {}))
            .onNew (getDefaultOnNew (tmpIst, toInsert))
            .comparator (getDefaultComparator (tmpIst, fields))
            .onEquals (getDefaultOnEquals (tmpIst, upsertedEntities, toRemove))
            .onNotEquals (getDefaultOnNotEquals (tmpIst, fields, toUpdate, toRemove))
            .onOld (getDefaultOnOld (tmpIst, upsertedEntities, toRemove));
    } // getBaseEntitiesHandler
    
    /**
     * Ritorna l'EntitiesHandler base per le entità anagrafiche.
     * 
     * @param alignTmpIst Istanza della classa di mappatura da temp a tabella applicativa con allineamento dei dati.
     * @param upsertedEntities Entità aggiornate.
     */
    public <M extends SModel> EntitiesHandler<M, String> getEntitiesHandlerWithAlign (
        AGenericTmp<M, S, D, C> alignTmpIst,
        // Map<String, M> oldEntitiesByCode,
        Map<String, Long> upsertedEntities,
        List<M> toInsert,
        List<M> toUpdate,
        Map<String, M> toRemove
    ) throws SException {
        List<TmpField<?, M>> fields = alignTmpIst.getFields (context);
        return new EntitiesHandler<M, String> ()
            .keyGetter (getDefaultKeyGetter (alignTmpIst))
            // .onCheckUniqueness (
            //     newEntity -> alignTmpIst.getCode (newEntity),
            //     (entityCode, entity) -> new SCodeExistsException (alignTmpIst.getTableUid (), alignTmpIst.getAlias (), entity.getByField (alignTmpIst.getUidField ()), entityCode)
            // )
            // .onBefore (getDefaultOnBefore (newEntity -> alignTmpIst.align (newEntity, context, true)))
            .onNew (getDefaultOnNew (alignTmpIst, toInsert))
            .comparator (getDefaultComparator (alignTmpIst, fields))
            .onEquals (getDefaultOnEquals (alignTmpIst, upsertedEntities, toRemove))
            .onNotEquals (getDefaultOnNotEquals (alignTmpIst, fields, toUpdate, toRemove))
            .onOld (getDefaultOnOld (alignTmpIst, upsertedEntities, toRemove));
    } // getEntityHandlerWithAlign

    /****** FUNZIONI DI DEFAULT PER ENTITIES HANDLER ******/

    public <M extends SModel> EntityKeyGetter<M, String> getDefaultKeyGetter (AGenericTmp<M, S, D, C> tmpIst) {
        return newEntity -> tmpIst.getCode (newEntity);
    } // getDefaultKeyGetter
    
    // public <M extends SModel> EntityOnBefore<M> getDefaultOnBefore (SConsumer<M> aligner) {
    //     return (newEntity, index) -> {
    //         aligner.accept (newEntity);
    //     };
    // }

    public <M extends SModel> EntityOnNew<M> getDefaultOnNew (
        AGenericTmp<M, S, D, C> tempInstance,
        // Map<String, M> oldEntitiesByCode,
        /*SConsumer<M> aligner,*/
        // Map<String, Long> upsertedEntities,
        List<M> toInsert
    ) throws SException {
        return newEntity -> {
            // Se arriva che dev'essere eliminato in principio, non lo creo.
            if (BooleanUtil.isNotTrue (tempInstance.getImpDel (newEntity, context))) {
//                aligner.accept (newEntity);
//                String newEntityCode = tempInstance.getCode (newEntity);

                // Check sul codice @dummy, generato da aps, l'align potrebbe aver cambiato il codice,
                // assegnandogli dei codici dummy per riparare la mancanza di records referenziati.
                // Se è già stato inserito in questo istante un codice uguale identico non faccio nulla.
//                if (upsertedEntities.containsKey (newEntityCode)) {
//                    return null;
//                    // Se esisteva già, potrei doverlo aggiornare.
//                } else if (oldEntitiesByCode.containsKey (newEntityCode)) {
//                    Long oldEntityUid = (Long) oldEntitiesByCode.get (newEntityCode).getByField (tempInstance.getUidField ());
//                    upsertedEntities.put (newEntityCode, oldEntityUid);
//                } else {
                    // Insert.
                    // tempInstance.checkInsertRequirements (newEntity, context.getContext (), context.getWCon ());
                    tempInstance.setImpDel (newEntity, context, null);
                    toInsert.add (newEntity);
                    return newEntity;
//                } // if - else
            } // if
            return null;
        };
    } // getDefaultOnNew

    public <M extends SModel> EntityComparator<M> getDefaultComparator (AGenericTmp<M, S, D, C> tmpIst, List<TmpField<?, M>> fields) {
        return (newEntity, oldEntity) -> {
            // Se non è da aggiornare il record, ritorno che è "uguale", impropriamente, ma per ottimizzazione.
            boolean isEqual;
            if (tmpIst.getImpUpd (oldEntity, context)) {
                isEqual = FctTmpUtil.get ().areEqual (newEntity, oldEntity, fields);
            } else {
                isEqual = true;
            } // if - else
            return isEqual;
        };
    } // getDefaultComparator

    public <M extends SModel> EntityOnEqual<M> getDefaultOnEquals (AGenericTmp<M, S, D, C> tmpIst, Map<String, Long> upsertedEntities, Map<String, M> toRemove) {
        return (newEntity, oldEntity) -> {
            boolean isAssignable = removeOrCheckRecord (newEntity, oldEntity, tmpIst);

            String newEntityCode = tmpIst.getCode (newEntity);
            if (isAssignable) {
                Long oldEntityUid = (Long) oldEntity.getByField (tmpIst.getUidField ());
                upsertedEntities.put (newEntityCode, oldEntityUid);
            } else {
                toRemove.put (newEntityCode, oldEntity);
            }
            return oldEntity;
        };
    } // getDefaultOnEquals

    public <M extends SModel> EntityOnNotEquals<M> getDefaultOnNotEquals (
        AGenericTmp<M, S, D, C> tmpIst,
        List<TmpField<?, M>> fields,
        // Map<String, M> oldEntitiesByCode,
//         SConsumer<M> aligner,
        // Map<String, Long> upsertedEntities,
        List<M> toUpdate,
        Map<String, M> toRemove
    ) {
        return (newEntity, oldEntity) -> {
//            Long oldEntityUid = (Long) oldEntity.getByField (tmpIst.getUidField ());
            // Se entro in questo punto, sono sicuro che posso aggiornarlo perché ho già verificato il flag di update.
            // N.B.: Se però ho il flag di "rimozione" da aps, lo devo rimuovere.
            boolean checkRecord = removeOrCheckRecord (newEntity, oldEntity, tmpIst);
            
            boolean isUpdatable = false;
            if (checkRecord) {
                // Se usassi la variabile oldEntity, mi varierebbe per riferimento la vecchia entità.
                FctTmpUtil.get ().merge (oldEntity, newEntity, fields);
//                aligner.accept (oldEntity);
//                String newOldEntityCode = tmpIst.getCode (oldEntity);
//                // Se presente come codice in un dummy, lo rimuovo.
//                if (oldEntitiesByCode.containsKey (newOldEntityCode)) {
//                    M reOldEntity = oldEntitiesByCode.get (newOldEntityCode);
//                    Long oldEntityByMapUid = (Long) reOldEntity.getByField (tmpIst.getUidField ());
//                    isUpdatable = removeOrMarkForCheck (oldEntityByMapUid, oldEntityUid, oldEntity, isUpdatable, toRemove);
//                } else if (upsertedEntities.containsKey (newOldEntityCode)) {
//                    return null;
//                } else {
//                    isUpdatable = true;
//                } // if - else

                isUpdatable = true;

                if (isUpdatable) {
                    // Update.
                    // tmpIst.checkUpdateRequirements (oldEntity, prevOldEntity, context.getContext (), context.getWCon ());
                    toUpdate.add (oldEntity);
                } // if
            } else {
                String newEntityCode = tmpIst.getCode (newEntity);
                toRemove.put (newEntityCode, newEntity);
            }

            return oldEntity;
        };
    } // getDefaultOnNotEquals
    
    public <M extends SModel> EntityOnOld<M> getDefaultOnOld (AGenericTmp<M, S, D, C> tmpIst, Map<String, Long> upsertedEntities, Map<String, M> toRemove) {
        return oldEntity -> {
            String oldEntityCode = tmpIst.getCode (oldEntity);
            // Elimino se e solo se voglio che sia eliminato in import o voglio forzare la rimozione.
            if (BooleanUtil.isTrue (tmpIst.getImpDel (oldEntity, context)) || (tmpIst.getImpDel (oldEntity, context) == null && context.getForceRemove ())) {
                // Se è rimasto in upserted significa che era un entità riferita ad un dummy e
                // che continua ad essere riferita al dummy o che lo voglio mantenere, quindi non lo cancello.
                if (!upsertedEntities.containsKey (oldEntityCode)) {
                    toRemove.put (oldEntityCode, oldEntity);
                } // if
            } else {
                Long oldEntityUid = (Long) oldEntity.getByField (tmpIst.getUidField ());
                upsertedEntities.put (oldEntityCode, oldEntityUid);
            } // if - else
        };
    } // getDefaultOnOld
    
    /**
     * Attenzione che questo metodo può aggiungere elementi alla lista passata di record da rimuovere.
     */
    public <M extends SModel> boolean removeOrCheckRecord (M newEntity, M oldEntity, AGenericTmp<M, S, D, C> tmpIst) throws SException {
        Boolean oldImpDel = tmpIst.getImpDel (oldEntity, context);
        Boolean newImpDel = tmpIst.getImpDel (newEntity, context);
        
        if (BooleanUtil.isTrue (oldImpDel) || BooleanUtil.isTrue (newImpDel)) {
            return false;
        } else {
            return true;
        } // if - else
    } // removeOrCheckRecord
    
    /**
     * Attenzione che questo metodo può aggiungere elementi alla lista passata di record da rimuovere.
     */
    public <M extends SModel> boolean removeOrMarkForCheck (Long oldEntityByMapUid, Long oldEntityUid, M oldEntity, boolean isUpdatable, List<M> toRemove) {
        if (oldEntityByMapUid != oldEntityUid) {
            toRemove.add (oldEntity);
        } else {
            isUpdatable = true;
        } // if - else
        return isUpdatable;
    } // removeOrMarkForCheck
    
} // FctSuperImporter
