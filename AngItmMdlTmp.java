package net.synergy2.logic.aps.tmp.impl;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
import net.synergy2.db.sys.itm.AngItm;
import net.synergy2.db.sys.itm.AngItmMdl;
import net.synergy2.db.sys.itm.AngItmMdlDao;
import net.synergy2.db.sys.itm.AngItmMdlImpl.AngItmMdlFields;
import net.synergy2.db.sys.itm.AngItmMdlMeta;
import net.synergy2.logic.aps.tmp.ApsImportContext;
import net.synergy2.logic.aps.tmp.ApsTmpMessage;
import net.synergy2.logic.aps.tmp.ApsTmpStoreDeletion;
import net.synergy2.logic.aps.tmp.common.ApsTmpStore;
import net.synergy2.logic.aps.tmp.common.ApsTmpTableKey;
import net.synergy2.logic.fct.tmp.AGenericTmp;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import net.synergy2.logic.fct.tmp.common.TmpField;
import net.synergy2.logic.sys.itm.SysItmLogic;
import net.synergy2.query.SQuery;

public class AngItmMdlTmp extends AGenericTmp<AngItmMdl, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> {
    
    private AngItmMdlTmp () {}
    private static class Singleton {
        private static final SSingletonHolder<AngItmMdlTmp> INSTANCE = new SSingletonHolder<> (AngItmMdlTmp.class);
    } // Singleton
    public static AngItmMdlTmp get () { return Singleton.INSTANCE.get (); }
    
    @Override
    protected List<TmpField<?, AngItmMdl>> initFields () {
        var itmField = new TmpField<AngItm, AngItmMdl> ()
           .setCode (true)
            .setValueGetter (AngItmMdl::getItm)
            .setValueSetter ((value, entity) -> entity.setItm (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setItmUid (incomingEntity.getItmUid ()))
            .setComparator ((value1, value2) -> value1 != null && value2 != null && value1.getUid () == value2.getUid () && !value2.getLogDel ())
            .setComparatorByCode ((value1, value2) -> value1 != null && value2 != null && value1.getItmCod ().equals (value2.getItmCod ()) && !value2.getLogDel ())
            .setSqlFieldGetter (() -> "CodiceArticolo")
            .setSqlGetter ((rs, field) -> {
                AngItm item = AngItm.newInstance ();
                item.setItmCod (rs.getString (field));
                return item;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngItm item = entity.getItm ();
                stm.setString (index, item.getItmCod ());
            });
        
        var codField = new TmpField<String, AngItmMdl> ()
            .setCode (true)
            .setValueGetter (AngItmMdl::getMdlCod)
            .setValueSetter ((value, entity) -> entity.setMdlCod (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Codice")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getMdlCod ()));

        var dscField = new TmpField<String, AngItmMdl> ()
            .setValueGetter (AngItmMdl::getMdlDsc)
            .setValueSetter ((value, entity) -> entity.setMdlDsc (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Descrizione")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getMdlDsc ()));
        
        var stdField = new TmpField<Boolean, AngItmMdl> ()
            .setValueGetter (AngItmMdl::getMdlStd)
            .setValueSetter ((value, entity) -> entity.setMdlStd (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "Standard")
            .setSqlGetter (ResultSet::getBoolean)
            .setSqlSetter ((stm, index, entity) -> stm.setInt (index, entity.getMdlStd () ? 1 : 0));
        
        return Arrays.asList (
            itmField,
            codField,
            dscField,
            stdField
        );
    } // initFields

    @Override public String getCode (AngItmMdl entity) { return entity.getItm ().getItmCod () + delimiter + entity.getMdlCod (); } // getCode
    @Override protected String getTempTableName () { return "ModelliArticolo"; } // getTempTableName
    @Override protected Supplier<AngItmMdl> getNewInstance () { return AngItmMdl::newInstance; } // getNewInstance
    @Override protected Class<AngItmMdl> getModelClass () { return AngItmMdl.class; } // getModelClass
    @Override public TableDao<AngItmMdl> getDao () { return AngItmMdlDao.get (); }
    @Override public String getAlias () { return SAlias.AngItmMdl; } // getAlias
    @Override public Long getTableUid () { return AngItmMdlMeta.get ().tableUid (); } // getTableUid
    @Override public ISField getUidField () { return AngItmMdlFields.Uid; } // getUidField
    @Override public String getVrtStoreKey () { return ApsTmpStore.ITEM_MODELS_KEY; } // getVrtStoreKey
    
    @Override public List<AngItmMdl> getActiveList (ApsImportContext ctx) throws SException {
        SQuery s = SQuery.of (ctx.getContext (), ctx.getWCon ()).from (SAlias.AngItmMdl, "Tbl", true);
        s.innerJoin ("Tbl", SAlias.AngItmMdl_FKI_Itm, "Itm", true);
        s.whereActive ("Tbl");
        s.and ().whereActive ("Itm");
        s.sortByAsc ("Tbl.Uid");
        s.execute ();
        List<AngItmMdl> toReturn = s.readMergedValuesAndCast ();
        s.close ();
        return toReturn;
    } // getActiveList

    @Override
    public SBatchResult<AngItmMdl> insertBatch (List<AngItmMdl> entities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        // Non utilizziamo i batch.
        return null;
    } // insertBatch

    @Override
    public SBatchResult<AngItmMdl> updateBatch (List<AngItmMdl> entities, List<AngItmMdl> oldEntities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        // Non utilizziamo i batch.
        return null;
    } // updateBatch
    
    @Override public AngItmMdl getInsert (AngItmMdl entity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().insertAngItmMdl (entity, false, context, wCon);
    } // getInsert
    
    @Override public AngItmMdl getUpdate (AngItmMdl entity, AngItmMdl oldEntity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().updateAllAngItmMdl (entity, false, context, wCon);
    } // getUpdate
    
    @Override
    public StoreSetter<AngItmMdl, ApsImportContext> getStoreSetter () {
        return (upsertedEntities, removeCandidates, ctx) -> {
            ctx.getStore ().setItemModels (upsertedEntities);
            ctx.getDeletionStore ().setToRemoveAngItmMdl (removeCandidates);
        };
    } // getStoreSetter
    
    @Override
    public void align (AngItmMdl entity, ApsImportContext ctx, boolean insert) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        String itmCod = AngApsItmTmp.get ().getCode (entity.getItm ());
        Long itmUid = store.getItems ().get (itmCod);
        if (itmUid == null) {
            List<Object> values = Arrays.asList (itmCod, insert ? "inserimento" : "modifica", entity.getMdlCod ());
            FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpItmUidItmMdl, values, ApsTmpTableKey.AngItmMdl.getKeyLabel (), insert, store, context, wCon);

            AngItm dummyAngItm = AngApsItmTmp.get ().getDummy (ctx);
            entity.setItmUid (dummyAngItm.getUid ());
            entity.setItm (dummyAngItm);
        } else {
            entity.setItmUid (itmUid);
            entity.getItm ().setUid (itmUid);
        } // if - else
    } // align

    @Override
    public AngItmMdl getDummy (ApsImportContext ctx) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ApsTmpStoreDeletion deletionStore = ctx.getDeletionStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        AngItmMdl dummy = store.getDummyAngItmMdl ();
        
        if (dummy == null) {
            String dummyCode = "@dummy";
            
            AngItmMdl tmpDummy = AngItmMdl.newInstance ();
            tmpDummy.setMdlCod (dummyCode);
            tmpDummy.setMdlDsc (dummyCode);

            AngItm dummyAngItm = AngApsItmTmp.get ().getDummy (ctx);
            tmpDummy.setItm (dummyAngItm);
            tmpDummy.setItmUid (dummyAngItm.getUid ());

            AngItmMdl removingDummy = deletionStore.removeAngItmMdl (getCode (tmpDummy));
            dummy = removingDummy == null ? AngItmMdlDao.get ().insert (tmpDummy, false, context, wCon) : removingDummy;

            dummy.setItm (dummyAngItm);
            store.setDummyAngItmMdl (dummy);
            String code = AngItmMdlTmp.get ().getCode (dummy);
            long uid = dummy.getUid ();
            store.addItemModel (code, uid);
        } // if
        
        return dummy;
    } // getDummy

} // AngItmMdlTmp
