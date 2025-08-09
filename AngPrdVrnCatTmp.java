package net.synergy2.logic.aps.tmp.impl;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.synergy2.base.connections.SWriteCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.exceptions.SException;
import net.synergy2.base.types.ISField;
import net.synergy2.base.types.SSingletonHolder;
import net.synergy2.base.util.datas.StringUtil;
import net.synergy2.db.base.SAlias;
import net.synergy2.db.base.batch.SBatchOptions;
import net.synergy2.db.base.batch.SBatchResult;
import net.synergy2.db.base.dao.TableDao;
import net.synergy2.db.sys.itm.AngPrdVrnCat;
import net.synergy2.db.sys.itm.AngPrdVrnCatDao;
import net.synergy2.db.sys.itm.AngPrdVrnCatImpl.AngPrdVrnCatFields;
import net.synergy2.db.sys.itm.AngPrdVrnCatMeta;
import net.synergy2.logic.aps.tmp.ApsImportContext;
import net.synergy2.logic.aps.tmp.ApsTmpStoreDeletion;
import net.synergy2.logic.aps.tmp.common.ApsTmpStore;
import net.synergy2.logic.fct.tmp.AGenericTmp;
import net.synergy2.logic.fct.tmp.common.TmpField;
import net.synergy2.logic.sys.itm.SysItmLogic;
import net.synergy2.query.SQuery;

public class AngPrdVrnCatTmp extends AGenericTmp<AngPrdVrnCat, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> {
    
    private AngPrdVrnCatTmp () {}
    private static class Singleton {
        private static final SSingletonHolder<AngPrdVrnCatTmp> INSTANCE = new SSingletonHolder<> (AngPrdVrnCatTmp.class);
    } // Singleton
    public static AngPrdVrnCatTmp get () { return Singleton.INSTANCE.get (); }
    
    @Override
    protected List<TmpField<?, AngPrdVrnCat>> initFields () {
        var codField = new TmpField<String, AngPrdVrnCat> ()
            .setValueGetter (AngPrdVrnCat::getCatCod)
            .setValueSetter ( (value, entity) -> entity.setCatCod (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter ( () -> "Codice")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ( (stm, index, entity) -> stm.setString (index, entity.getCatCod ()));

        var dscField = new TmpField<String, AngPrdVrnCat> ()
            .setValueGetter (AngPrdVrnCat::getCatDsc)
            .setValueSetter ( (value, entity) -> entity.setCatDsc (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter ( () -> "Descrizione")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ( (stm, index, entity) -> stm.setString (index, entity.getCatDsc ()));
        
        return Arrays.asList (
            codField,
            dscField
        );
    } // initFields

    @Override public String getCode (AngPrdVrnCat entity) { return entity.getCatCod (); } // getCode
    @Override protected String getTempTableName () { return "CategorieVarianti"; } // getTempTableName
    @Override protected Supplier<AngPrdVrnCat> getNewInstance () { return AngPrdVrnCat::newInstance; } // getNewInstance
    @Override protected Class<AngPrdVrnCat> getModelClass () { return AngPrdVrnCat.class; } // getModelClass
    @Override public TableDao<AngPrdVrnCat> getDao () { return AngPrdVrnCatDao.get (); }
    @Override public String getAlias () { return SAlias.AngPrdVrnCat; } // getAlias
    @Override public Long getTableUid () { return AngPrdVrnCatMeta.get ().tableUid (); } // getTableUid
    @Override public ISField getUidField () { return AngPrdVrnCatFields.Uid; } // getUidField
    @Override public String getVrtStoreKey () { return ApsTmpStore.VARIANT_CATEGORIES_KEY; } // getVrtStoreKey
    
    @Override public List<AngPrdVrnCat> getActiveList (ApsImportContext ctx) throws SException {
        SQuery s = SQuery.of (ctx.getContext (), ctx.getWCon ()).from (SAlias.AngPrdVrnCat, "Tbl", true);
        s.whereActive ("Tbl");
        s.sortByAsc ("Tbl.Uid");
        s.execute ();
        List<AngPrdVrnCat> toReturn = s.readMergedValuesAndCast ();
        s.close ();
        return toReturn;
    } // getActiveList

    @Override
    public SBatchResult<AngPrdVrnCat> insertBatch (List<AngPrdVrnCat> entities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        // Non utilizziamo i batch.
        return null;
    } // insertBatch

    @Override
    public SBatchResult<AngPrdVrnCat> updateBatch (List<AngPrdVrnCat> entities, List<AngPrdVrnCat> oldEntities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        // Non utilizziamo i batch.
        return null;
    } // updateBatch
    
    @Override public AngPrdVrnCat getInsert (AngPrdVrnCat entity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().insertAngPrdVrnCat (entity, false, context, wCon);
    } // getInsert
    
    @Override public AngPrdVrnCat getUpdate (AngPrdVrnCat entity, AngPrdVrnCat oldEntity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().updateAllAngPrdVrnCat (entity, false, context, wCon);
    } // getUpdate
    
    @Override public StoreSetter<AngPrdVrnCat, ApsImportContext> getStoreSetter () {
        return (upsertedEntities, removeCandidates, ctx) -> {
            ctx.getStore ().setVariantCategories (upsertedEntities);
            ctx.getDeletionStore ().setToRemoveAngPrdVrnCat (removeCandidates);
        };
    } // getStoreSetter

    @Override
    public AngPrdVrnCat getDummy (ApsImportContext ctx) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ApsTmpStoreDeletion deletionStore = ctx.getDeletionStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();
        
        AngPrdVrnCat dummy = store.getDummyAngPrdVrnCat ();
        
        if (dummy == null) {
            String dummyCode = "@dummy";
            AngPrdVrnCat tmpDummy = AngPrdVrnCat.newInstance ();
            tmpDummy.setCatCod (dummyCode);
            tmpDummy.setCatDsc (dummyCode);

            AngPrdVrnCat removingDummy = deletionStore.removeAngPrdVrnCat (getCode (tmpDummy));
            dummy = removingDummy == null ? AngPrdVrnCatDao.get ().insert (tmpDummy, false, context, wCon) : removingDummy;

            store.setDummyAngPrdVrnCat (dummy);
            String code = AngPrdVrnCatTmp.get ().getCode (dummy);
            long uid = dummy.getUid ();
            store.addVariantCategory (code, uid);
        } // if

        return dummy;
    } // getDummy

} // AngPrdVrnCatTmp
