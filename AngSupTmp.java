package net.synergy2.logic.fct.tmp.impl;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.synergy2.base.connections.SWriteCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.exceptions.SException;
import net.synergy2.base.types.ISField;
import net.synergy2.base.util.datas.StringUtil;
import net.synergy2.db.base.SAlias;
import net.synergy2.db.base.batch.SBatchOptions;
import net.synergy2.db.base.batch.SBatchResult;
import net.synergy2.db.base.dao.TableDao;
import net.synergy2.db.sys.bas.AngSup;
import net.synergy2.db.sys.bas.AngSupDao;
import net.synergy2.db.sys.bas.AngSupImpl.AngSupFields;
import net.synergy2.db.sys.bas.AngSupMeta;
import net.synergy2.db.sys.bas.abstracts._MAngSup;
import net.synergy2.logic.fct.tmp.AGenericTmp;
import net.synergy2.logic.fct.tmp.FctImportContext;
import net.synergy2.logic.fct.tmp.FctTmpStoreDeletion;
import net.synergy2.logic.fct.tmp.common.FctTmpStore;
import net.synergy2.logic.fct.tmp.common.TmpField;
import net.synergy2.logic.sys.bas.SysBasLogic;

public class AngSupTmp<S extends FctTmpStore, D extends FctTmpStoreDeletion, C extends FctImportContext<S, D>> extends AGenericTmp<AngSup, S, D, C> {
    
    @Override
    protected List<TmpField<?, AngSup>> initFields () {
        var codField = new TmpField<String, AngSup> ()
            .setCode (true)
            .setValueGetter (_MAngSup::getSupCod)
            .setValueSetter ((value, entity) -> entity.setSupCod (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Codice")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getSupCod ()));
            

        var dscField = new TmpField<String, AngSup> ()
            .setValueGetter (_MAngSup::getSupDsc)
            .setValueSetter ((value, entity) -> entity.setSupDsc (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Descrizione")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getSupDsc ()));
        
        return Arrays.asList(codField, dscField);
    } // initFields

    @Override public String getCode (AngSup entity) { return entity.getSupCod (); } // getCode 
    @Override protected String getTempTableName () { return "Fornitori"; } // getTempTableName 
    @Override protected Supplier<AngSup> getNewInstance () { return AngSup::newInstance; } // getNewInstance
    @Override protected Class<AngSup> getModelClass () { return AngSup.class; } // getModelClass 
    @Override public TableDao<AngSup> getDao () { return AngSupDao.get (); }
    @Override public String getAlias () { return SAlias.AngSup; } // getAlias 
    @Override public Long getTableUid () { return AngSupMeta.get ().tableUid (); } // getTableUid 
    @Override public ISField getUidField () { return AngSupFields.Uid; } // getUidField 
    @Override public String getVrtStoreKey () { return FctTmpStore.VENDORS_KEY; } // getVrtStoreKey

    @Override
    public SBatchResult<AngSup> insertBatch (List<AngSup> entities, SBatchOptions options, S store, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysBasLogic.get ().insertBatchAngSup (entities, options, context, wCon);
    } // insertBatch

    @Override
    public SBatchResult<AngSup> updateBatch (List<AngSup> entities, List<AngSup> oldEntities, SBatchOptions options, S store, ExecutionContext context, SWriteCon wCon) throws SException {
        Map<Long, AngSup> entityByUid = new HashMap<> ();
        for (AngSup entity : entities) {
            entityByUid.put (entity.getUid (), entity);
        } // for
        return SysBasLogic.get ().updateAllBatchAngSup (entities, entityByUid, options, context, wCon);
    } // updateBatch
    
    @Override
    public AngSup getInsert (AngSup entity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysBasLogic.get ().postAngSup (entity, context, wCon);
    } // getInsert

    @Override
    public AngSup getUpdate (AngSup entity, AngSup oldEntity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysBasLogic.get ().updateAllAngSup (entity, false, context, wCon);
    } // getUpdate
    
    @Override
    public StoreSetter<AngSup, C> getStoreSetter () {
        return (upsertedEntities, removeCandidates, ctx) -> {
            ctx.getStore ().setVendors (upsertedEntities);
            ctx.getDeletionStore ().setToRemoveAngSup (removeCandidates);
        };
    } // getStoreSetter

    @Override
    public AngSup getDummy (C ctx) throws SException {
        FctTmpStore store = ctx.getStore ();
        D deletionStore = ctx.getDeletionStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        AngSup dummy = store.getDummyAngSup ();

        if (dummy == null) {
            String dummyCode = "@dummy";
            AngSup tmpDummy = AngSup.newInstance ();
            tmpDummy.setSupCod (dummyCode);
            tmpDummy.setSupIco ("mdi mdi-check");

            AngSup removingDummy = deletionStore.removeAngSup (getCode (tmpDummy));
            dummy = removingDummy == null ? AngSupDao.get ().insert (tmpDummy, false, context, wCon) : removingDummy;
            
            store.setDummyAngSup (dummy);
            String code = getCode (dummy);
            long uid = dummy.getUid ();
            store.addVendor (code, uid);
        } // if
        
        return dummy;
    } // getDummy

} // AngSupTmp
