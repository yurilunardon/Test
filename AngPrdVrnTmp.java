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
import net.synergy2.db.sys.itm.AngPrdVrn;
import net.synergy2.db.sys.itm.AngPrdVrnCat;
import net.synergy2.db.sys.itm.AngPrdVrnCatDao;
import net.synergy2.db.sys.itm.AngPrdVrnDao;
import net.synergy2.db.sys.itm.AngPrdVrnImpl.AngPrdVrnFields;
import net.synergy2.db.sys.itm.AngPrdVrnMeta;
import net.synergy2.logic.aps.tmp.ApsTmpStoreDeletion;
import net.synergy2.logic.aps.tmp.ApsImportContext;
import net.synergy2.logic.aps.tmp.ApsTmpMessage;
import net.synergy2.logic.aps.tmp.common.ApsTmpStore;
import net.synergy2.logic.aps.tmp.common.ApsTmpTableKey;
import net.synergy2.logic.fct.tmp.AGenericTmp;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import net.synergy2.logic.fct.tmp.common.TmpField;
import net.synergy2.logic.sys.itm.SysItmLogic;
import net.synergy2.query.SQuery;

public class AngPrdVrnTmp extends AGenericTmp<AngPrdVrn, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> {
    
    // TODO AVVISARE ANTHONY
    
    
    private AngPrdVrnTmp () {}
    private static class Singleton {
        private static final SSingletonHolder<AngPrdVrnTmp> INSTANCE = new SSingletonHolder<> (AngPrdVrnTmp.class);
    } // Singleton
    public static AngPrdVrnTmp get () { return Singleton.INSTANCE.get (); }
    
    @Override
    protected List<TmpField<?, AngPrdVrn>> initFields () {
        var vrnCatField = new TmpField<AngPrdVrnCat, AngPrdVrn> ()
            .setValueGetter (AngPrdVrn::getCat)
            .setValueSetter ((value, entity) -> entity.setCat (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setCatUid (incomingEntity.getCatUid ()))
            .setComparator ((value1, value2) -> value1 != null && value2 != null && value1.getUid () == value2.getUid () && !value2.getLogDel ())
            .setComparatorByCode ( (value1, value2) -> value1 != null && value2 != null && value1.getCatCod ().equals (value2.getCatCod ()) && !value2.getLogDel ())
            .setSqlFieldGetter (() -> "CodiceCategoriaVariante")
            .setSqlGetter ((rs, field) -> {
                AngPrdVrnCat category = AngPrdVrnCat.newInstance ();
                category.setCatCod (rs.getString (field));
                return category;
            })
            .setSqlSetter ( (stm, index, entity) -> {
                var cat = entity.getCat ();
                stm.setString (index, cat.getCatCod ());
            });
        
        var codField = new TmpField<String, AngPrdVrn> ()
            .setValueGetter (AngPrdVrn::getVrnCod)
            .setValueSetter ( (value, entity) -> entity.setVrnCod (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter ( () -> "Codice")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter  ((stm, index, entity) -> stm.setString (index, entity.getVrnCod ()));

        var dscField = new TmpField<String, AngPrdVrn> ()
            .setValueGetter (AngPrdVrn::getVrnDsc)
            .setValueSetter ( (value, entity) -> entity.setVrnDsc (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter ( () -> "Descrizione")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ( (stm, index, entity) -> stm.setString (index, entity.getVrnDsc ()));
        
        return Arrays.asList (
            vrnCatField,
            codField,
            dscField
        );
    } // initFields

    @Override public String getCode (AngPrdVrn entity) { return entity.getCat ().getCatCod () + delimiter + entity.getVrnCod (); } // getCode
    @Override protected String getTempTableName () { return "Varianti"; } // getTempTableName
    @Override protected Supplier<AngPrdVrn> getNewInstance () { return AngPrdVrn::newInstance; } // getNewInstance
    @Override protected Class<AngPrdVrn> getModelClass () { return AngPrdVrn.class; } // getModelClass
    @Override public TableDao<AngPrdVrn> getDao () { return AngPrdVrnDao.get (); }
    @Override public String getAlias () { return SAlias.AngPrdVrn; } // getAlias
    @Override public Long getTableUid () { return AngPrdVrnMeta.get ().tableUid (); } // getTableUid
    @Override public ISField getUidField () { return AngPrdVrnFields.Uid; } // getUidField
    @Override public String getVrtStoreKey () { return ApsTmpStore.VARIANTS_KEY; } // getVrtStoreKey
    
    @Override public List<AngPrdVrn> getActiveList (ApsImportContext ctx) throws SException {
        SQuery s = SQuery.of (ctx.getContext (), ctx.getWCon ()).from (SAlias.AngPrdVrn, "Tbl", true);
        s.innerJoin ("Tbl", SAlias.AngPrdVrn_FKI_Cat, "Cat", true);
        s.whereActive ("Tbl");
        s.and ().whereActive ("Cat");
        s.sortByAsc ("Tbl.Uid");
        s.execute ();
        List<AngPrdVrn> toReturn = s.readMergedValuesAndCast ();
        s.close ();
        return toReturn;
    } // getActiveList

    @Override
    public SBatchResult<AngPrdVrn> insertBatch (List<AngPrdVrn> entities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        // Non utilizziamo i batch.
        return null;
    } // insertBatch

    @Override
    public SBatchResult<AngPrdVrn> updateBatch (List<AngPrdVrn> entities, List<AngPrdVrn> oldEntities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        // Non utilizziamo i batch.
        return null;
    } // updateBatch
    
    @Override public AngPrdVrn getInsert (AngPrdVrn entity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().insertAngPrdVrn (entity, false, context, wCon);
    } // getInsert
    
    @Override public AngPrdVrn getUpdate (AngPrdVrn entity, AngPrdVrn oldEntity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().updateAllAngPrdVrn (entity, false, context, wCon);
    } // getUpdate
    
    @Override public StoreSetter<AngPrdVrn, ApsImportContext> getStoreSetter () {
        return (upsertedEntities, removeCandidates, ctx) -> {
            ctx.getStore ().setVariants (upsertedEntities);
            ctx.getDeletionStore ().setToRemoveAngPrdVrn (removeCandidates);
        };
    } // getStoreSetter
    
    @Override
    public void align (AngPrdVrn entity, ApsImportContext ctx, boolean insert) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        String catCod = AngPrdVrnCatTmp.get ().getCode (entity.getCat ());
        Long catUid = store.getVariantCategories ().get (catCod);
        if (catUid == null) {
            List<Object> values = Arrays.asList (catCod, insert ? "inserimento" : "modifica", entity.getVrnCod ());
            FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpCatUidPrdVrn, values, ApsTmpTableKey.AngPrdVrn.getKeyLabel (), insert, store, context, wCon);

            AngPrdVrnCat dummyAngPrdVrnCat = AngPrdVrnCatTmp.get ().getDummy (ctx);
            entity.setCatUid (dummyAngPrdVrnCat.getUid ());
            entity.setCat (dummyAngPrdVrnCat);     
        } else {
            entity.setCatUid (catUid);
            entity.getCat ().setUid (catUid);
        } // if - else
    } // align

    @Override
    public AngPrdVrn getDummy (ApsImportContext ctx) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ApsTmpStoreDeletion deletionStore = ctx.getDeletionStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        AngPrdVrn dummy = store.getDummyAngPrdVrn ();
        
        if (dummy == null ) {
            String dummyCode = "@dummy";
            AngPrdVrn tmpDummy = AngPrdVrn.newInstance ();
            tmpDummy.setVrnCod (dummyCode);
            tmpDummy.setVrnDsc (dummyCode);
            AngPrdVrnCat dummyAngPrdVrnCat = AngPrdVrnCatTmp.get ().getDummy (ctx);
            tmpDummy.setCat (dummyAngPrdVrnCat);
            tmpDummy.setCatUid (dummyAngPrdVrnCat.getUid ());
            

            AngPrdVrn removingDummy = deletionStore.removeAngPrdVrn (getCode (tmpDummy));
            dummy = removingDummy == null ? AngPrdVrnDao.get ().insert (tmpDummy, false, context, wCon) : removingDummy;
            
            dummy.setCat (dummyAngPrdVrnCat);
            store.setDummyAngPrdVrn (dummy);
            String code = AngPrdVrnTmp.get ().getCode (dummy);
            long uid = dummy.getUid ();
            store.addVariant (code, uid);
        } // if
        
        return dummy;
    } // getDummy

} // AngPrdVrnTmp
