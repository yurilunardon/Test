package net.synergy2.logic.aps.tmp.impl;

import java.util.Arrays;
import java.util.List;
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
import net.synergy2.db.sys.itm.AngItmMdl;
import net.synergy2.db.sys.itm.AngItmMdlVrn;
import net.synergy2.db.sys.itm.AngItmMdlVrnDao;
import net.synergy2.db.sys.itm.AngItmMdlVrnImpl.AngItmMdlVrnFields;
import net.synergy2.db.sys.itm.AngItmMdlVrnMeta;
import net.synergy2.db.sys.itm.AngItmVrn;
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

public class AngItmMdlVrnTmp extends AGenericTmp<AngItmMdlVrn, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> {
    
    private AngItmMdlVrnTmp () {}
    private static class Singleton {
        private static final SSingletonHolder<AngItmMdlVrnTmp> INSTANCE = new SSingletonHolder<> (AngItmMdlVrnTmp.class);
    } // Singleton
    public static AngItmMdlVrnTmp get () { return Singleton.INSTANCE.get (); }
    
    @Override
    protected List<TmpField<?, AngItmMdlVrn>> initFields () {
        
        var mdlField = new TmpField<AngItmMdl, AngItmMdlVrn> ()
            .setValueGetter (AngItmMdlVrn::getMdl)
            .setValueSetter ((value, entity) -> entity.setMdl (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setMdlUid (incomingEntity.getMdlUid ()))
            .setComparator ((value1, value2) -> value1 != null && value2 != null &&
                value1.getUid () == value2.getUid () &&
                !value2.getLogDel ())
            .setComparatorByCode ((value1, value2) -> value1 != null && value2 != null &&
                value1.getItm () != null && value2.getItm () != null &&
                value1.getItm ().getItmCod ().equals (value2.getItm ().getItmCod ()) &&
                value1.getMdlCod ().equals (value2.getMdlCod ()) &&
                !value2.getLogDel ())
            .setSqlFieldGetter (() -> "CodiceModello")
            .setSqlGetter ((rs, field) -> {
                AngItm itm = AngItm.newInstance ();
                AngItmMdl mdl = AngItmMdl.newInstance ();
                String[] splitCode = rs.getString (field).split (delimiter);
                String itmCod = splitCode[0];
                String mdlCod = splitCode[1];
                itm.setItmCod (itmCod);
                mdl.setMdlCod (mdlCod);
                mdl.setItm (itm); 
                return mdl;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngItmMdl mdl = entity.getMdl ();
                String code = mdl.getItm ().getItmCod () + delimiter + mdl.getMdlCod ();
                stm.setString (index, code);
            });
        
        var vrnField = new TmpField<AngItmVrn, AngItmMdlVrn> ()
            .setValueGetter (AngItmMdlVrn::getItmVrn)
            .setValueSetter ((value, entity) -> entity.setItmVrn (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setItmVrnUid (incomingEntity.getItmVrnUid ()))
            .setComparator ((value1, value2) -> value1 != null && value2 != null &&
                value1.getUid () == value2.getUid () &&
                !value2.getLogDel ())
            .setComparatorByCode ((value1, value2) -> value1 != null && value2 != null &&
                value1.getItm () != null && value2.getItm () != null &&
                value1.getItm ().getItmCod ().equals (value2.getItm ().getItmCod ()) &&
                value1.getVrn () != null && value2.getVrn () != null &&
                value1.getVrn ().getCat () != null && value2.getVrn ().getCat () != null &&
                value1.getVrn ().getCat ().getCatCod ().equals (value2.getVrn ().getCat ().getCatCod ()) &&
                value1.getVrn ().getVrnCod ().equals (value2.getVrn ().getVrnCod ()) &&
                !value2.getLogDel ())
            .setSqlFieldGetter (() -> "CodiceVarianteArticolo")
            .setSqlGetter ((rs, field) -> {
                AngItm itm = AngItm.newInstance ();
                AngPrdVrnCat cat = AngPrdVrnCat.newInstance ();
                AngPrdVrn vrn = AngPrdVrn.newInstance ();
                AngItmVrn itmVrn = AngItmVrn.newInstance ();
                String[] splitCode = rs.getString (field).split (delimiter);
                String itmCod = splitCode[0];
                String catCod = splitCode[1];
                String vrnCod = splitCode[2];
                itm.setItmCod (itmCod);
                cat.setCatCod (catCod);
                vrn.setVrnCod (vrnCod);
                vrn.setCat (cat);
                itmVrn.setItm (itm);
                itmVrn.setVrn (vrn);
                return itmVrn;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngItmVrn itmVrn = entity.getItmVrn ();
                String code = itmVrn.getItm ().getItmCod () + delimiter + itmVrn.getVrn ().getCat ().getCatCod () + delimiter + itmVrn.getVrn ().getVrnCod ();
                stm.setString (index, code);
            });
        
        return Arrays.asList (
            mdlField,
            vrnField
        );
    } // initFields

    @Override public String getCode (AngItmMdlVrn entity) {
        return entity.getMdl ().getItm ().getItmCod () + delimiter + entity.getMdl ().getMdlCod () + // CodiceModello
            delimiter + entity.getItmVrn ().getItm ().getItmCod () + delimiter + entity.getItmVrn ().getVrn ().getCat ().getCatCod () + delimiter + entity.getItmVrn ().getVrn ().getVrnCod (); // CodiceVarianteArticolo
    } // getCode
    @Override protected String getTempTableName () { return "ModelliArticoloVarianti"; } // getTempTableName
    @Override protected Supplier<AngItmMdlVrn> getNewInstance () { return AngItmMdlVrn::newInstance; } // getNewInstance
    @Override protected Class<AngItmMdlVrn> getModelClass () { return AngItmMdlVrn.class; } // getModelClass
    @Override public TableDao<AngItmMdlVrn> getDao () { return AngItmMdlVrnDao.get (); }
    @Override public String getAlias () { return SAlias.AngItmMdlVrn; } // getAlias
    @Override public Long getTableUid () { return AngItmMdlVrnMeta.get ().tableUid (); } // getTableUid
    @Override public ISField getUidField () { return AngItmMdlVrnFields.Uid; } // getUidField
    @Override public String getVrtStoreKey () { return ApsTmpStore.ITEM_MODEL_VARIANTS_KEY; } // getVrtStoreKey
    
    @Override public List<AngItmMdlVrn> getActiveList (ApsImportContext ctx) throws SException {
        SQuery s = SQuery.of (ctx.getContext (), ctx.getWCon ()).from (SAlias.AngItmMdlVrn, "Tbl", true);
        s.innerJoin ("Tbl", SAlias.AngItmMdlVrn_FKI_Mdl, "Mdl", true);
        s.innerJoin ("Mdl", SAlias.AngItmMdl_FKI_Itm, "MdlItm", true);
        s.innerJoin ("Tbl", SAlias.AngItmMdlVrn_FKI_ItmVrn, "ItmVrn", true);
        s.innerJoin ("ItmVrn", SAlias.AngItmVrn_FKI_Itm, "VrnItm", true);
        s.innerJoin ("ItmVrn", SAlias.AngItmVrn_FKI_Vrn, "Vrn", true);
        s.innerJoin ("Vrn", SAlias.AngPrdVrn_FKI_Cat, "Cat", true);
        s.whereActive ("Tbl");
        s.and ().whereActive ("Mdl").and ().whereActive ("MdlItm");
        s.and ().whereActive ("VrnItm").and ().whereActive ("Vrn").and ().whereActive ("Cat");
        s.sortByAsc ("Tbl.Uid");
        s.execute ();
        List<AngItmMdlVrn> toReturn = s.readMergedValuesAndCast ();
        s.close ();
        return toReturn;
    } // getActiveList
    
    @Override
    public SBatchResult<AngItmMdlVrn> insertBatch (List<AngItmMdlVrn> entities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        // Non utilizziamo i batch.
        return null;
    } // insertBatch

    @Override
    public SBatchResult<AngItmMdlVrn> updateBatch (List<AngItmMdlVrn> entities, List<AngItmMdlVrn> oldEntities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        // Non utilizziamo i batch.
        return null;
    } // updateBatch
    
    @Override public AngItmMdlVrn getInsert (AngItmMdlVrn entity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().insertAngItmMdlVrn (entity, false, context, wCon);
    } // getInsert
    
    @Override public AngItmMdlVrn getUpdate (AngItmMdlVrn entity, AngItmMdlVrn oldEntity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().updateAllAngItmMdlVrn (entity, false, context, wCon);
    } // getUpdate

    @Override
    public StoreSetter<AngItmMdlVrn, ApsImportContext> getStoreSetter () {
        return (upsertedEntities, removeCandidates, ctx) -> {
            ctx.getStore ().setItemModelVariants (upsertedEntities);
            ctx.getDeletionStore ().setToRemoveAngItmMdlVrn (removeCandidates);
        };
    } // getStoreSetter
    
    @Override
    public void align (AngItmMdlVrn entity, ApsImportContext ctx, boolean insert) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        String itmMdlCod = AngItmMdlTmp.get ().getCode (entity.getMdl ());
        Long mdlUid = store.getItemModels ().get (itmMdlCod);
        if (mdlUid == null) {
            List<Object> values = Arrays.asList (itmMdlCod, insert ? "inserimento" : "modifica", entity.getUid ());
            FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpMdlUidItmMdlVrn, values, ApsTmpTableKey.AngItmMdlVrn.getKeyLabel (), insert, store, context, wCon);

            AngItmMdl dummyAngItmMdl = AngItmMdlTmp.get ().getDummy (ctx);
            entity.setMdlUid (dummyAngItmMdl.getUid ());
            entity.setMdl (dummyAngItmMdl);
        } else {
            entity.setMdlUid (mdlUid);
            entity.getMdl ().setUid (mdlUid);
        } // if - else
        
        String itmVrnCod = AngItmVrnTmp.get ().getCode (entity.getItmVrn ());
        Long itmVrnUid = store.getItemVariants ().get (itmVrnCod);
        if (itmVrnUid == null) {
            List<Object> values = Arrays.asList (itmVrnCod, insert ? "inserimento" : "modifica", entity.getUid ());
            FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpItmVrnUidItmMdlVrn, values, ApsTmpTableKey.AngItmMdlVrn.getKeyLabel (), insert, store, context, wCon);

            AngItmVrn dummyAngItmVrn = AngItmVrnTmp.get ().getDummy (ctx);
            entity.setItmVrnUid (dummyAngItmVrn.getUid ());
            entity.setItmVrn (dummyAngItmVrn);
        } else {
            entity.setItmVrnUid (itmVrnUid);
            entity.getItmVrn ().setUid (itmVrnUid);
        } // if - else
    } // align

} // AngItmMdlVrnTmp
