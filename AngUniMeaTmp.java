package net.synergy2.logic.fct.tmp.impl;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
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
import net.synergy2.db.sys.itm.AngUniMea;
import net.synergy2.db.sys.itm.AngUniMeaDao;
import net.synergy2.db.sys.itm.AngUniMeaImpl.AngUniMeaFields;
import net.synergy2.db.sys.itm.AngUniMeaMeta;
import net.synergy2.db.sys.itm.abstracts._MAngUniMea;
import net.synergy2.logic.fct.tmp.AGenericTmp;
import net.synergy2.logic.fct.tmp.FctImportContext;
import net.synergy2.logic.fct.tmp.FctTmpStoreDeletion;
import net.synergy2.logic.fct.tmp.common.FctTmpStore;
import net.synergy2.logic.fct.tmp.common.TmpField;

public class AngUniMeaTmp<S extends FctTmpStore, D extends FctTmpStoreDeletion, C extends FctImportContext<S, D>> extends AGenericTmp<AngUniMea, S, D, C> {

    @Override
    protected List<TmpField<?, AngUniMea>> initFields () {
        var codField = new TmpField<String, AngUniMea> ()
            .setCode (true)
            .setValueGetter (_MAngUniMea::getUniMeaCod)
            .setValueSetter ((value, entity) -> entity.setUniMeaCod (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Codice")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getUniMeaCod ()));

        var dscField = new TmpField<String, AngUniMea> ()
            .setValueGetter (_MAngUniMea::getUniMeaDsc)
            .setValueSetter ((value, entity) -> entity.setUniMeaDsc (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Descrizione")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getUniMeaDsc ()));

        var dscLngField = new TmpField<String, AngUniMea> ()
            .setValueGetter ((entity) -> entity.getUniMeaLngDsc ())
            .setValueSetter ((value, entity) -> entity.setUniMeaLngDsc (value))
            .setComparator ((value1, value2) -> StringUtil.areEquals (value1, value2))
            .setSqlFieldGetter (() -> "Descrizione")
            .setSqlGetter ((rs, field) -> rs.getString (field));

        return Arrays.asList (
            codField,
            dscField,
            dscLngField
            );
    } // initFields

    @Override protected String getTempTableName () { return "UnitaMisura"; } // getTempTableName
    @Override public String getCode (AngUniMea entity) { return entity.getUniMeaCod (); } // getCode
    @Override protected Supplier<AngUniMea> getNewInstance () { return AngUniMea::newInstance; } // getNewInstance
    @Override protected Class<AngUniMea> getModelClass () { return AngUniMea.class; } // getModelClass
    @Override public TableDao<AngUniMea> getDao () { return AngUniMeaDao.get (); }
    @Override public String getAlias () { return SAlias.AngUniMea; } // getAlias
    @Override public Long getTableUid () { return AngUniMeaMeta.get ().tableUid (); } // getTableUid
    @Override public ISField getUidField () { return AngUniMeaFields.Uid; } // getUidField
    @Override public String getVrtStoreKey () { return FctTmpStore.MEASURE_UNITS_KEY; } // getVrtStoreKey
    
    @Override
    public SBatchResult<AngUniMea> insertBatch (List<AngUniMea> entities, SBatchOptions options, S store, ExecutionContext context, SWriteCon wCon) throws SException {
        return AngUniMeaDao.get ().insertBatch (entities, options, context, wCon);
    } // insertBatch

    @Override
    public SBatchResult<AngUniMea> updateBatch (List<AngUniMea> entities, List<AngUniMea> oldEntities, SBatchOptions options, S store, ExecutionContext context, SWriteCon wCon) throws SException {
        return AngUniMeaDao.get ().updateAllBatch (entities, options, context, wCon);
    } // updateBatch
    
    @Override public AngUniMea getInsert (AngUniMea entity, ExecutionContext context, SWriteCon wCon) throws SException {
        return AngUniMeaDao.get ().insert (entity, false, context, wCon);
    } // getInsert
    
    @Override public AngUniMea getUpdate (AngUniMea entity, AngUniMea oldEntity, ExecutionContext context, SWriteCon wCon) throws SException {
        return AngUniMeaDao.get ().updateAll (entity, false, context, wCon);
    } // getUpdate
    
    @Override
    public StoreSetter<AngUniMea, C> getStoreSetter () {
        return (upsertedEntities, removeCandidates, ctx) -> {
            ctx.getStore ().setMeasureUnits (upsertedEntities);
            ctx.getDeletionStore ().setToRemoveAngUniMea (removeCandidates);
        };
    } // getStoreSetter
    
} // AngUniMeaTmp
