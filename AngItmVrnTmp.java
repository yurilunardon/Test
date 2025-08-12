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
import net.synergy2.db.base.SAlias;
import net.synergy2.db.base.batch.SBatchOptions;
import net.synergy2.db.base.batch.SBatchResult;
import net.synergy2.db.base.dao.TableDao;
import net.synergy2.db.sys.itm.AngItm;
import net.synergy2.db.sys.itm.AngItmVrn;
import net.synergy2.db.sys.itm.AngItmVrnDao;
import net.synergy2.db.sys.itm.AngItmVrnImpl.AngItmVrnFields;
import net.synergy2.db.sys.itm.AngItmVrnMeta;
import net.synergy2.db.sys.itm.AngPrdVrn;
import net.synergy2.db.sys.itm.AngPrdVrnCat;
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

public class AngItmVrnTmp extends AGenericTmp<AngItmVrn, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> {
    
    private AngItmVrnTmp () {}
    private static class Singleton {
        private static final SSingletonHolder<AngItmVrnTmp> INSTANCE = new SSingletonHolder<> (AngItmVrnTmp.class);
    } // Singleton
    public static AngItmVrnTmp get () { return Singleton.INSTANCE.get (); }
    
    @Override
    protected List<TmpField<?, AngItmVrn>> initFields () {
        var itmField = new TmpField<AngItm, AngItmVrn> ()
           .setCode (true)
            .setValueGetter (AngItmVrn::getItm)
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
        
        var vrnField = new TmpField<AngPrdVrn, AngItmVrn> ()
           .setCode (true)
            .setValueGetter (AngItmVrn::getVrn)
            .setValueSetter ((value, entity) -> entity.setVrn (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setVrnUid (incomingEntity.getVrnUid ()))
            .setComparator ((value1, value2) -> value1 != null && value2 != null &&
                value1.getUid () == value2.getUid () &&
                !value2.getLogDel ())
            .setComparatorByCode ((value1, value2) -> value1 != null && value2 != null &&
                value1.getCat () != null && value2.getCat () != null &&
                value1.getCat ().getCatCod ().equals (value2.getCat ().getCatCod ()) &&
                value1.getVrnCod ().equals (value2.getVrnCod ()) &&
                !value2.getLogDel ())
            .setSqlFieldGetter (() -> "CodiceVariante")
            .setSqlGetter ((rs, field) -> {
                AngPrdVrnCat cat = AngPrdVrnCat.newInstance ();
                AngPrdVrn vrn = AngPrdVrn.newInstance ();
                String[] splitCode = rs.getString (field).split (delimiter);
                String catCod = splitCode[0];
                String vrnCod = splitCode[1];
                cat.setCatCod (catCod);
                vrn.setVrnCod (vrnCod);
                vrn.setCat (cat); 
                return vrn;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngPrdVrn vrn = entity.getVrn ();
                String code = vrn.getCat ().getCatCod () + delimiter + vrn.getVrnCod ();                
                stm.setString (index, code);
            });
        
        var stdField = new TmpField<Boolean, AngItmVrn> ()
            .setValueGetter (AngItmVrn::getVrnStd)
            .setValueSetter ((value, entity) -> entity.setVrnStd (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "Standard")
            .setSqlGetter (ResultSet::getBoolean)
            .setSqlSetter ((stm, index, entity) -> stm.setInt (index, entity.getVrnStd () ? 1 : 0));
        
        return Arrays.asList (
            itmField,
            vrnField,
            stdField
        );
    } // initFields

    @Override public String getCode (AngItmVrn entity) {
        return entity.getItm ().getItmCod () + delimiter +
            entity.getVrn ().getCat ().getCatCod () + delimiter + entity.getVrn ().getVrnCod ();
    } // getCode
    @Override protected String getTempTableName () { return "VariantiArticolo"; } // getTempTableName
    @Override protected Supplier<AngItmVrn> getNewInstance () { return AngItmVrn::newInstance; } // getNewInstance
    @Override protected Class<AngItmVrn> getModelClass () { return AngItmVrn.class; } // getModelClass
    @Override public TableDao<AngItmVrn> getDao () { return AngItmVrnDao.get (); }
    @Override public String getAlias () { return SAlias.AngItmVrn; } // getAlias
    @Override public Long getTableUid () { return AngItmVrnMeta.get ().tableUid (); } // getTableUid
    @Override public ISField getUidField () { return AngItmVrnFields.Uid; } // getUidField
    @Override public String getVrtStoreKey () { return ApsTmpStore.ITEM_VARIANTS_KEY; } // getVrtStoreKey
    
    @Override public List<AngItmVrn> getActiveList (ApsImportContext ctx) throws SException {
        SQuery s = SQuery.of (ctx.getContext (), ctx.getWCon ()).from (SAlias.AngItmVrn, "Tbl", true);
        s.innerJoin ("Tbl", SAlias.AngItmVrn_FKI_Itm, "Itm", true);
        s.innerJoin ("Tbl", SAlias.AngItmVrn_FKI_Vrn, "Vrn", true);
        s.innerJoin ("Vrn", SAlias.AngPrdVrn_FKI_Cat, "Cat", true);
        s.whereActive ("Tbl");
        s.and ().whereActive ("Itm");
        s.and ().whereActive ("Cat").and ().whereActive ("Vrn");
        s.sortByAsc ("Tbl.Uid");
        s.execute ();
        List<AngItmVrn> toReturn = s.readMergedValuesAndCast ();
        s.close ();
        return toReturn;
    } // getActiveList

    @Override
    public SBatchResult<AngItmVrn> insertBatch (List<AngItmVrn> entities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        // Non utilizziamo i batch.
        return null;
    } // insertBatch

    @Override
    public SBatchResult<AngItmVrn> updateBatch (List<AngItmVrn> entities, List<AngItmVrn> oldEntities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        // Non utilizziamo i batch.
        return null;
    } // updateBatch
    
    @Override public AngItmVrn getInsert (AngItmVrn entity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().insertAngItmVrn (entity, false, context, wCon);
    } // getInsert
    
    @Override public AngItmVrn getUpdate (AngItmVrn entity, AngItmVrn oldEntity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().updateAllAngItmVrn (entity, false, context, wCon);
    } // getUpdate

    @Override
    public StoreSetter<AngItmVrn, ApsImportContext> getStoreSetter () {
        return (upsertedEntities, removeCandidates, ctx) -> {
            ctx.getStore ().setItemVariants (upsertedEntities);
            ctx.getDeletionStore ().setToRemoveAngItmVrn (removeCandidates);
        };
    } // getStoreSetter
    
    @Override
    public void align (AngItmVrn entity, ApsImportContext ctx, boolean insert) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        String itmCod = AngApsItmTmp.get ().getCode (entity.getItm ());
        Long itmUid = store.getItems ().get (itmCod);
        if (itmUid == null) {
            List<Object> values = Arrays.asList (itmCod, insert ? "inserimento" : "modifica", entity.getUid ());
            FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpItmUidItmVrn, values, ApsTmpTableKey.AngItmVrn.getKeyLabel (), insert, store, context, wCon);

            AngItm dummyAngItm = AngApsItmTmp.get ().getDummy (ctx);
            entity.setItmUid (dummyAngItm.getUid ());
            entity.setItm (dummyAngItm);
        } else {
            entity.setItmUid (itmUid);
            entity.getItm ().setUid (itmUid);
        } // if - else
        
        String vrnCod = AngPrdVrnTmp.get ().getCode (entity.getVrn ());
        Long vrnUid = store.getVariants ().get (vrnCod);
        if (vrnUid == null) {
            List<Object> values = Arrays.asList (vrnCod, insert ? "inserimento" : "modifica", entity.getUid ());
            FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpVrnUidItmVrn, values, ApsTmpTableKey.AngItmVrn.getKeyLabel (), insert, store, context, wCon);

            AngPrdVrn dummyAngPrdVrn = AngPrdVrnTmp.get ().getDummy (ctx);
            entity.setVrnUid (dummyAngPrdVrn.getUid ());
            entity.setVrn (dummyAngPrdVrn);
        } else {
            entity.setVrnUid (vrnUid);
            entity.getVrn ().setUid (vrnUid);
        } // if - else
    } // align

    @Override
    public AngItmVrn getDummy (ApsImportContext ctx) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ApsTmpStoreDeletion deletionStore = ctx.getDeletionStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        AngItmVrn dummy = store.getDummyAngItmVrn ();

        if (dummy == null) {
            AngItmVrn tmpDummy = AngItmVrn.newInstance ();
            
            AngItm dummyAngItm = AngApsItmTmp.get ().getDummy (ctx);
            AngPrdVrn dummyAngPrdVrn = AngPrdVrnTmp.get ().getDummy (ctx);
            tmpDummy.setItm (dummyAngItm);
            tmpDummy.setVrn (dummyAngPrdVrn);
            tmpDummy.setItmUid (dummyAngItm.getUid ());
            tmpDummy.setVrnUid (dummyAngPrdVrn.getUid ());

            String tmpDummyCode = getCode (tmpDummy);
            AngItmVrn removingDummy = deletionStore.removeAngItmVrn (tmpDummyCode);
            if (removingDummy == null) {
                // Potrebbe essere stato inserito nello stesso istante di import e quindi non è stato segnato come "dummy", essendo una tabella simil "molti-molti"
                dummy = AngItmVrnDao.get ().getByCode (tmpDummy, context, wCon);
                if (dummy == null) {
                    dummy = AngItmVrnDao.get ().insert (tmpDummy, false, context, wCon);
                }
            } else {
                dummy = removingDummy;
            }

            dummy.setItm (dummyAngItm);
            dummy.setVrn (dummyAngPrdVrn);
            store.setDummyAngItmVrn (dummy);
            String code = AngItmVrnTmp.get ().getCode (dummy);
            long uid = dummy.getUid ();
            store.addItemVariant (code, uid);
        } // if 
        
        return dummy;
    } // getDummy

} // AngItmVrnTmp
