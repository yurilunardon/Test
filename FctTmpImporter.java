package net.synergy2.logic.fct.tmp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.synergy2.base.connections.SWriteCon;
import net.synergy2.base.exceptions.SException;
import net.synergy2.base.types.IMessage;
import net.synergy2.base.util.collections.EntitiesHandler;
import net.synergy2.base.util.function.SConsumer;
import net.synergy2.db.base.SModel;
import net.synergy2.db.base.batch.SBatchOptions;
import net.synergy2.logic.base.ExceptionLogic;
import net.synergy2.logic.fct.tmp.common.FctTmpStore;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import org.slf4j.Logger;

/**
 * Modello base per l'import di una tabella da tempDb a db applicativo
 */
public class FctTmpImporter<M extends SModel, S extends FctTmpStore, D extends FctTmpStoreDeletion, C extends FctImportContext<S, D>> {

    public FctTmpImporter (
        Logger logger,
        AGenericTmp<M, S, D, C> tempInstance,
        C context,
        EntitiesHandler<M, String> entitiesHandler,
        SConsumer<M> aligner,
        
        // Variabili di output durante il processo di entitiesHandler
        List<M> insertCandidates,
        List<M> updateCandidates,
        Map<String, Long> upsertedEntities,
        Map<String, M> removeCandidates
    ) {
        this.LOGGER = logger;
        this.tempInstance = tempInstance;
        this.context = context;
        this.entitiesHandler = entitiesHandler;
        this.aligner = aligner;
        
        this.insertCandidates = insertCandidates;
        this.updateCandidates = updateCandidates;
        this.upsertedEntities = upsertedEntities;
        this.removeCandidates = removeCandidates;
    } // FctTmpImporter
    
    /**********************************************************/
    /******* VARIABILI ****************************************/
    /**********************************************************/

    /** @return Il logger per i messaggi di debug durante l'import. */
    private Logger LOGGER;
    
    /** @return Il messaggio di errore in caso di ordine di importazione non rispettato da logica sviluppatore. */
    private IMessage msgCod = FctTmpMessage.tmpImpEtyOrdNotVld;
    public FctTmpImporter<M, S, D, C> setMessage (IMessage msgCod) { this.msgCod = msgCod; return this; }

    /** @return Il contenitore con tutte le informazioni per l'import corrente. */
    private C context;
    
    /** @return L'istanza della classe di mappatura da temp a tabella applicativa. */
    private AGenericTmp<M, S, D, C> tempInstance;
    
    /** @return L'EntitiesHandler da utilizzare per la singola importazione. */
    private EntitiesHandler<M, String> entitiesHandler;

    /** Aligner da lanciare prima del reperimento delle nuove entità. */
    private SConsumer<M> aligner;
    
    /** @return La lista di entità preesistenti che verranno aggiornate per riferimento dall'entitiesHandler. */
    @SuppressWarnings("unused")
    private List<M> oldEntities; // NON UTILIZZATO.
    
    /** @return La mappa entità preesistenti che verranno aggiornate per riferimento dall'entitiesHandler dato il codice logico. */
    private Map<String, M> oldEntitiesByCode;
    
    /** @return La lista di nuove entità da valutare con l'entities handler. */
    private List<M> newEntities;
    
    /** @return La lista di entità preesistenti prima dell'esecuzione dell'entities handler. */
    private List<M> prevOldEntities;
    
    private SBatchOptions batchOptions = SBatchOptions.of ().setCheckLang (true).setCheckCode (false);
    public FctTmpImporter<M, S, D, C> setBatchOptions (SBatchOptions batchOptions) { this.batchOptions = batchOptions; return this; }
    
    private SConsumer<M> consumerOnUpsertSuccess = (e) -> { };
    public FctTmpImporter<M, S, D, C> setConsumerOnUpsertSuccess (SConsumer<M> consumerOnUpsertSuccess) { this.consumerOnUpsertSuccess = consumerOnUpsertSuccess; return this; }
    
    /**********************************************************/
    /******* VARIABILI ESECUTIVE ******************************/
    /**********************************************************/
    
    /** @return Ultimo istante di esecuzione di un'importazione. */
    private Instant lastEnd = null;
    public Instant getLastEnd () { return lastEnd; }
    
    /** @return [OUTPUT] Le entità candidate all'inserimento. */
    private List<M> insertCandidates;
    public List<M> getInsertCandidates () { return insertCandidates; }

    /** @return [OUTPUT] Le entità candidate all'aggiornamento. */
    private List<M> updateCandidates;
    public List<M> getUpdateCandidates () { return updateCandidates; }

    /** @return [OUTPUT] La mappa che dato il codice logico ottiene l'uid effettivo dell'entità una volta inserita/aggiornata. */
    private Map<String, Long> upsertedEntities;
    public Map<String, Long> getUpsertedEntities () { return upsertedEntities; }

    /** @return [OUTPUT] La mappa di entità che verranno cancellate. */
    private Map<String, M> removeCandidates;
    public Map<String, M> getRemoveCandidates () { return removeCandidates; }

    /**********************************************************/
    /******* METODI *******************************************/
    /**********************************************************/
    
    public FctTmpImporter<M, S, D, C> logStart () {
        FctTmpUtil.get ().infoProcessingStart (LOGGER, "Start %s".formatted (tempInstance.getAlias ()));
        return this;
    } // logStart

    public FctTmpImporter<M, S, D, C> initLists () throws SException {
        // Ottengo le nuove entità.
        this.newEntities = new ArrayList<M> ();
        List<M> localNewEntities = tempInstance.getList (context);
        Set<String> uniquenessCodes = new HashSet<> ();
        for (M localNewEntity : localNewEntities) {
            this.aligner.accept (localNewEntity);
            if (uniquenessCodes.add (tempInstance.getCode (localNewEntity))) {
                this.newEntities.add (localNewEntity);
            }
        }
        // Ottengo le preesistenti.
        this.oldEntities = tempInstance.getActiveList (context);
        // Mappa di entità old dato il codice.
        this.oldEntitiesByCode = oldEntities.stream ().collect (Collectors.toMap ((entity) -> tempInstance.getCode (entity), entity -> entity));
        return initLists (newEntities, oldEntities, oldEntitiesByCode);
    } // initLists

    public FctTmpImporter<M, S, D, C> initLists (List<M> newEntities, List<M> oldEntities, Map<String, M> oldEntitiesByCode) throws SException {
        this.newEntities = newEntities;
        this.oldEntities = oldEntities;
        this.oldEntitiesByCode = oldEntitiesByCode;
        // Duplico le preesistenti in modo da tenere traccia delle vecchie, prima dell'aggiornamento.
        this.prevOldEntities = cloneList (tempInstance, oldEntities);
        return this;
    } // initLists

    public FctTmpImporter<M, S, D, C> logCandidates () {
        LOGGER.info (" - insertCandidates = %s, updateCandidates = %s, removeCandiates = %s".formatted (insertCandidates.size (), updateCandidates.size (), removeCandidates.size ()));
        return this;
    } // logUpsertSizes

    public FctTmpImporter<M, S, D, C> logEnd () {
        lastEnd = FctTmpUtil.get ().infoProcessingEnd (LOGGER, context.getStart (), "End %s in".formatted (tempInstance.getAlias ()));
        return updateContextStart ();
    } // logEnd

    public FctTmpImporter<M, S, D, C> updateContextStart () {
        context.setStart (lastEnd);
        return this;
    } // updateContextStart

    /**
     * Metodo principale che esegue l'import
     */
    public FctTmpImporter<M, S, D, C> runEntitiesHandler () throws SException {
        // Eseguo l'entities handler.
        entitiesHandler.iterateCompare (newEntities, oldEntitiesByCode);
        // Controllo ordine dipendenze
        boolean dependencyOrder = FctTmpUtil.get ().clearObjectsAndFlushMemory (context.getStore (), tempInstance.getVrtStoreKey ());
        if (!dependencyOrder) {
            SWriteCon wCon = context.getWCon ();
            throw ExceptionLogic.error (msgCod, new Object[] { tempInstance.getVrtStoreKey () }, context.getContext (), wCon);
        } // if
        return this;
    } // runEntitiesHandler
    
    public FctTmpImporter<M, S, D, C> runInsert () throws SException {
        if (tempInstance.hasDaoBatch ()) {
            insertBatch (insertCandidates, upsertedEntities);
        } else {
            insertSingle (insertCandidates, upsertedEntities);
        } // if - else
        return this;
    } // insert
    
    private void insertSingle (List<M> entities, Map<String, Long> upsertedEntities) throws SException {
        if (entities.isEmpty ()) { return; }
        for (M entity : entities) {
            M inserted = tempInstance.getInsert (entity, context.getContext (), context.getWCon ());
            Long uid = (Long) inserted.getByField (tempInstance.getUidField ());
            upsertedEntities.put (tempInstance.getCode (inserted), uid);
            consumerOnUpsertSuccess.accept (inserted);
        } // for
    } // insertSingle
    
    private void insertBatch (List<M> entities, Map<String, Long> upsertedEntities) throws SException {
        if (entities.isEmpty ()) { return; }
        var result = tempInstance.insertBatch (entities, batchOptions, context.getStore (), context.getContext (), context.getWCon ());
        FctTmpUtil.get ().checkInsertBatchResult (LOGGER, result, tempInstance.getClass ());
        for (M inserted : result.getPersisted ()) {
            Long uid = (Long) inserted.getByField (tempInstance.getUidField ());
            upsertedEntities.put (tempInstance.getCode (inserted), uid);
            consumerOnUpsertSuccess.accept (inserted);
        } // for
    } // insertBatch
    
    public FctTmpImporter<M, S, D, C> runUpdate () throws SException {
        if (tempInstance.hasDaoBatch ()) {
            updateBatch (updateCandidates, prevOldEntities, upsertedEntities);
        } else {
            updateSingle (updateCandidates, prevOldEntities, upsertedEntities);
        } // if - else
        return this;
    } // update
    
    private void updateSingle (List<M> entities, List<M> oldEntities, Map<String, Long> upsertedEntities) throws SException {
        if (entities.isEmpty ()) { return; }
        Map<Long, M> entityByUid = new HashMap<> ();
        for (M oldEntity : oldEntities) {
            Long uid = (Long) oldEntity.getByField (tempInstance.getUidField ());
            entityByUid.put (uid, oldEntity);
        } // for
        
        for (M entity : entities) {
            Long uid = (Long) entity.getByField (tempInstance.getUidField ());
            M oldEntity = entityByUid.get (uid);
            tempInstance.getUpdate (entity, oldEntity, context.getContext (), context.getWCon ());
            upsertedEntities.put (tempInstance.getCode (entity), uid);
            consumerOnUpsertSuccess.accept (entity);
        } // for
    } // updateSingle
    
    private void updateBatch (List<M> entities, List<M> oldEntities, Map<String, Long> upsertedEntities) throws SException {
        if (entities.isEmpty ()) { return; }
        var result = tempInstance.updateBatch (entities, oldEntities, batchOptions, context.getStore (), context.getContext (), context.getWCon ());
        FctTmpUtil.get ().checkUpdateBatchResult (LOGGER, result, tempInstance.getClass ());
        for (M updated : result.getPersisted ()) {
            Long uid = (Long) updated.getByField (tempInstance.getUidField ());
            upsertedEntities.put (tempInstance.getCode (updated), uid);
            consumerOnUpsertSuccess.accept (updated);
        } // for
    } // updateBatch
    
    public FctTmpImporter<M, S, D, C> setEntitiesOnStore () {
        // Caricamento in cache delle entità salvate e da cancellare.
        tempInstance.setEntitiesOnStore (upsertedEntities, removeCandidates, context);
        return this;
    } // setEntitiesOnStore

    private <M1 extends SModel> List<M> cloneList (AGenericTmp<M, S, D, C> tempTable, List<M> entities) {
        return entities.stream ().map (tempTable::getCopy).collect (Collectors.toList ());
    }

} // FctTmpImporter
