package net.synergy2.logic.aps.tmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.synergy2.base.exceptions.SException;
import net.synergy2.base.util.collections.EntitiesHandler;
import net.synergy2.base.util.collections.EntitiesHandler.EntityOnEqual;
import net.synergy2.base.util.collections.EntitiesHandler.EntityOnOld;
import net.synergy2.base.util.datas.BooleanUtil;
import net.synergy2.base.util.function.SConsumer;
import net.synergy2.db.base.SModel;
import net.synergy2.logic.aps.tmp.common.ApsTmpStore;
import net.synergy2.logic.fct.tmp.AGenericTmp;
import net.synergy2.logic.fct.tmp.FctImportManager;
import net.synergy2.logic.fct.tmp.common.TmpField;
import org.slf4j.Logger;

/**
 * Utility per avere sempre stesso logger e contesto per tutte le tabelle importate.
 */
public class ApsImportManager extends FctImportManager<ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> {

    public ApsImportManager (Logger logger, ApsImportContext importContext) {
        super (logger, importContext);
    } // ApsImportManager
    
    /**
     * NB UTILIZZO METODI:
     * 
     * importApsWithAlignAndScenario: Da utilizzare per le entità che gestiscono l'align (con entities handler presente in FctSuperImporter)
     * ma necessitano anche di settare lo scenaro nell'onBefore.
     * 
     * importApsWithScenarioAndEntityMap: Da utilizzare per le entità che necessitano di gestire lo scenario il popolamento della mappa aggiuntiva
     * che ha per chiave l'uid dell'entità e per valore l'entità stessa.
     * 
     * importAnagraphicWithAdditionalMapSetter: Da utilizzare per le entità che necessitano di gestire il popolamento di una mappa
     * che ha per chiave l'uid dell'entità e per valore l'entità stessa e di un'altra mappa di oggetti di tipo M dato l'uid di un'entità collegata.
     * 
     * importAnagraphicWithScenarioAndAdditionalMapSetter: Come quella precedente ma deve anche essere gestito lo scenario.
     *
     */

    /**
     * Esegue l'import di una singola tabella con entities handler di default con allineamento e gestione dello scenario.
     * 
     * @param tempTable Tabella da importare
     */
    public <M extends SModel> void standardScenarioImportWithAlign (AApsGenericTmp<M> tempTable) throws SException {
        Map<String, Long> upsertedEntities = new HashMap<> ();
        // Lista di entità da inserire, aggiornare e rimuovere.
        List<M> insertCandidates = new ArrayList<> ();
        List<M> updateCandidates = new ArrayList<> ();
        Map<String, M> removeCandidates = new HashMap<> ();
        // Ottengo l'entities handler che gestisce l'align.
        EntitiesHandler<M, String> entitiesHandler = getEntitiesHandlerWithAlign (tempTable, upsertedEntities, insertCandidates, updateCandidates, removeCandidates)
            .onBefore ((newEntity, index) -> tempTable.setSceUid (newEntity, context.getScenarioUid ()));

        ApsTmpImporter<M> singleImporter = new ApsTmpImporter<M> (
            LOGGER, tempTable, context, entitiesHandler, newEntity -> tempTable.align (newEntity, context, true),
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
    } // standardScenarioImportWithAlign

    /**
     * Esegue l'import di una singola tabella con entities handler che prevede la gestione dello scenario e di una mappa di entità dato il loro uid.
     * 
     * @param tempTable Tabella da importare.
     * @param entityByUid Entità dato l'identificativo.
     */
    public <M extends SModel> void standardScenarioImportWithAlignAndMap (AApsGenericTmp<M> tempTable, Map<Long, M> entityByUid) throws SException {
        standardScenarioImportWithAlignAndMap (tempTable, entityByUid, true);
    } // standardScenarioImportWithAlignAndMap
    
    public <M extends SModel> void standardImportWithAlignAndMap (AApsGenericTmp<M> tempTable, Map<Long, M> entityByUid) throws SException {
        standardScenarioImportWithAlignAndMap (tempTable, entityByUid, false);
    } // standardImportWithAlignAndMap

    public <M extends SModel> void standardScenarioImportWithAlignAndMap (AApsGenericTmp<M> tempTable, Map<Long, M> entityByUid, boolean hasSceUid) throws SException {
        Map<String, Long> upsertedEntities = new HashMap<> ();
        // Lista di entità da inserire, aggiornare e rimuovere.
        List<M> insertCandidates = new ArrayList<> ();
        List<M> updateCandidates = new ArrayList<> ();
        Map<String, M> removeCandidates = new HashMap<> ();
        // Ottengo l'entities handler di base.
        EntitiesHandler<M, String> entitiesHandler = getBaseEntitiesHandler (tempTable, e -> {}, entityByUid, upsertedEntities, insertCandidates, updateCandidates, removeCandidates);
        if (hasSceUid) {
            entitiesHandler.onBefore ((newEntity, index) -> tempTable.setSceUid (newEntity, context.getScenarioUid ()));
        } // if
        
        ApsTmpImporter<M> singleImporter =  new ApsTmpImporter<M> (
            LOGGER, tempTable, context, entitiesHandler, newEntity -> tempTable.align (newEntity, context, true),
            insertCandidates, updateCandidates, upsertedEntities, removeCandidates
        );
        singleImporter
            .logStart ()
            .initLists ()
            .setConsumerOnUpsertSuccess (e -> {
                Long uid = (Long) e.getByField (tempTable.getUidField ());
                entityByUid.put (uid, e);
            })
            .runEntitiesHandler ()
            .logCandidates ()
            .runInsert ()
            .runUpdate ()
            .setEntitiesOnStore ()
            .logEnd ();
    } // standardScenarioImportWithAlignAndMap

    /**
     * Esegue l'import di una singola tabella con entities handler che prevede la gestione di una mappa
     * di entità dato il loro uid e il lancio di una funzione fornita per popolare una mappa dato l'uid
     * di un'entità padre.
     * 
     * @param tempTable Tabella da importare.
     * @param entityByUid Entità dato l'identificativo.
     */
    public <M extends SModel> Map<String, M> standardScenarioImportWithAlignMapAndConsumer (AApsGenericTmp<M> tempTable, SConsumer<M> consumerOnSuccess, Map<Long, M> entityByUid) throws SException {
        return standardScenarioImportWithAlignMapAndConsumer (tempTable, consumerOnSuccess, entityByUid, true);
    } // standardScenarioImportWithAlignAndMap
    
    public <M extends SModel> Map<String, M> standardImportWithAlignMapAndConsumer (AApsGenericTmp<M> tempTable, SConsumer<M> consumerOnSuccess, Map<Long, M> entityByUid) throws SException {
        return standardScenarioImportWithAlignMapAndConsumer (tempTable, consumerOnSuccess, entityByUid, false);
    } // standardImportWithAlignAndMap
    
    public <M extends SModel> Map<String, M> standardScenarioImportWithAlignMapAndConsumer (
        AApsGenericTmp<M> tempTable,
        SConsumer<M> consumerOnSuccess,
        Map<Long, M> entityByUid,
        boolean hasSceUid
    ) throws SException {
        Map<String, Long> upsertedEntities = new HashMap<> ();
        // Lista di entità da inserire, aggiornare e rimuovere.
        List<M> insertCandidates = new ArrayList<> ();
        List<M> updateCandidates = new ArrayList<> ();
        Map<String, M> removeCandidates = new HashMap<> ();
        // Ottengo l'entities handler di base.
        EntitiesHandler<M, String> entitiesHandler = getBaseEntitiesHandler (tempTable, consumerOnSuccess, entityByUid, upsertedEntities, insertCandidates, updateCandidates, removeCandidates);
        if (hasSceUid) {
            entitiesHandler.onBefore ((newEntity, index) -> tempTable.setSceUid (newEntity, context.getScenarioUid ()));
        } // if
        
        ApsTmpImporter<M> singleImporter = new ApsTmpImporter<M> (
            LOGGER, tempTable, context, entitiesHandler, newEntity -> tempTable.align (newEntity, context, true),
            insertCandidates, updateCandidates, upsertedEntities, removeCandidates
        );
        singleImporter
            .logStart ()
            .initLists ()
            .setConsumerOnUpsertSuccess (e -> {
                Long uid = (Long) e.getByField (tempTable.getUidField ());
                entityByUid.put (uid, e);
                consumerOnSuccess.accept (e);
            })
            .runEntitiesHandler ()
            .logCandidates ()
            .runInsert ()
            .runUpdate ()
            .setEntitiesOnStore ()
            .logEnd ();
        return removeCandidates;
    } // standardScenarioImportWithAlignMapAndConsumer

    /**
     * Ritorna l'EntitiesHandler che gestisce la cancellazione delle entità e ritorna una mappa delle entità dato il loro identificativo.
     *
     * @param tempInstance Istanza della classa di mappatura da temp a tabella applicativa.
     * @param upsertedEntities Entità aggiornate.
     * @param toRemove Record da rimuovere.
     * @param entityByUid Entità dato l'identificativo.
     */
    protected <M extends SModel> EntitiesHandler<M, String> getBaseEntitiesHandler (
        AApsGenericTmp<M> tempInstance,
        // Map<String, M> oldEntitiesByCode,
        SConsumer<M> consumerOnSuccess,
        Map<Long, M> entityByUid,
        Map<String, Long> upsertedEntities,
        List<M> toInsert,
        List<M> toUpdate,
        Map<String, M> toRemove
    ) throws SException {
        List<TmpField<?, M>> fields = tempInstance.getFields (context);
        return new EntitiesHandler<M, String> ()
            .keyGetter (getDefaultKeyGetter (tempInstance))
            // .onCheckUniqueness (
            //     newEntity -> tempInstance.getCode (newEntity),
            //     (entityCode, entity) -> new SCodeExistsException (tempInstance.getTableUid (), tempInstance.getAlias (), entity.getByField (tempInstance.getUidField ()), entityCode)
            // )
            // .onBefore (getDefaultOnBefore (newEntity -> tempInstance.align (newEntity, context, true)))
            .onNew (getDefaultOnNew (tempInstance, toInsert))
            .comparator (getDefaultComparator (tempInstance, fields))
            .onEquals (getDefaultOnEquals (tempInstance, upsertedEntities, entityByUid, toRemove))
            .onNotEquals (getDefaultOnNotEquals (tempInstance, fields, toUpdate, toRemove))
            .onOld (getDefaultOnOld (tempInstance, consumerOnSuccess, upsertedEntities, toRemove));
    } // getBaseBySceEntityHandler
    
    public <M extends SModel> EntityOnEqual<M> getDefaultOnEquals (
        AGenericTmp<M, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> tmpIst,
        Map<String, Long> upsertedEntities,
        Map<Long, M> entityByUid,
        Map<String, M> toRemove
    ) {
        return (newEntity, oldEntity) -> {
            boolean isAssignable = removeOrCheckRecord (newEntity, oldEntity, tmpIst);

            String newEntityCode = tmpIst.getCode (newEntity);
            if (isAssignable) {
                Long oldEntityUid = (Long) oldEntity.getByField (tmpIst.getUidField ());
                upsertedEntities.put (newEntityCode, oldEntityUid);
                entityByUid.put (oldEntityUid, oldEntity);
            } else {
                toRemove.put (newEntityCode, oldEntity);
            }
            return oldEntity;
        };
    } // getDefaultOnEquals
    
    public <M extends SModel> EntityOnOld<M> getDefaultOnOld (
        AGenericTmp<M, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> tmpIst,
        SConsumer<M> consumerOnSuccess,
        Map<String, Long> upsertedEntities,
        Map<String, M> toRemove
    ) {
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
                consumerOnSuccess.accept (oldEntity);
            } // if - else
        };
    } // getDefaultOnOld
    
    
} // ApsSuperImporter
