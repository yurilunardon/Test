package net.synergy2.logic.aps.tmp.impl;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import net.synergy2.base.connections.SWriteCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.exceptions.SException;
import net.synergy2.base.types.ISField;
import net.synergy2.base.types.SDate;
import net.synergy2.base.types.SSingletonHolder;
import net.synergy2.base.util.datas.NumberUtil;
import net.synergy2.base.util.datas.NumberUtil.Operators;
import net.synergy2.base.util.datas.StringUtil;
import net.synergy2.db.base.SAlias;
import net.synergy2.db.base.batch.SBatchOptions;
import net.synergy2.db.base.batch.SBatchResult;
import net.synergy2.db.base.dao.TableDao;
import net.synergy2.db.sys.itm.AngItm;
import net.synergy2.db.sys.itm.AngPrdCyc;
import net.synergy2.db.sys.itm.AngPrdCycDao;
import net.synergy2.db.sys.itm.AngPrdCycImpl.AngPrdCycFields;
import net.synergy2.db.sys.itm.AngPrdCycMeta;
import net.synergy2.db.sys.itm.AngPrdCycTyp;
import net.synergy2.db.sys.itm.AngPrdVrn;
import net.synergy2.db.sys.itm.AngPrdVrnDao;
import net.synergy2.db.sys.itm.abstracts._MAngPrdCyc;
import net.synergy2.logic.aps.tmp.ApsImportContext;
import net.synergy2.logic.aps.tmp.ApsTmpMessage;
import net.synergy2.logic.aps.tmp.ApsTmpStoreDeletion;
import net.synergy2.logic.aps.tmp.common.ApsTmpStore;
import net.synergy2.logic.aps.tmp.common.ApsTmpTableKey;
import net.synergy2.logic.fct.tmp.AGenericTmp;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import net.synergy2.logic.fct.tmp.common.TmpField;
import net.synergy2.logic.sys.itm.SysItmLogic;

public class AngPrdCycTmp extends AGenericTmp<AngPrdCyc, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> {

    private AngPrdCycTmp () {}
    private static class Singleton {
        private static final SSingletonHolder<AngPrdCycTmp> INSTANCE = new SSingletonHolder<> (AngPrdCycTmp.class);
    } // Singleton
    public static AngPrdCycTmp get () { return Singleton.INSTANCE.get (); }

    @Override
    protected List<TmpField<?, AngPrdCyc>> initFields () {
        var codField = new TmpField<String, AngPrdCyc> ()
            .setCode (true)
            .setValueGetter (_MAngPrdCyc::getPrdCycCod)
            .setValueSetter ((value, entity) -> entity.setPrdCycCod (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Codice")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getPrdCycCod ()));

        var dscField = new TmpField<String, AngPrdCyc> ()
            .setValueGetter (_MAngPrdCyc::getPrdCycDsc)
            .setValueSetter ((value, entity) -> entity.setPrdCycDsc (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Descrizione")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getPrdCycDsc ()));

        var itmField = new TmpField<AngItm, AngPrdCyc> ()
            .setCode (true)
            .setValueGetter (_MAngPrdCyc::getItm)
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
            .setSqlSetter((stm, index, entity) -> {
                AngItm item = entity.getItm ();
                stm.setString (index, item.getItmCod ());
            });

        var prdCycTypField = new TmpField<Long, AngPrdCyc> ()
            .setCode (true)
            .setValueGetter (_MAngPrdCyc::getPrdCycTypUid)
            .setValueSetter ((value, entity) -> entity.setPrdCycTypUid (value))
            .setComparator ((value1, value2) -> NumberUtil.compareLong (value1, Operators.EQUAL, value2, true))
            .setSqlFieldGetter (() -> "CodiceTipoCiclo")
            .setSqlGetter ((rs, field) -> {
                String prdCycTypCod = rs.getString (field);
                long prdCycTypUid = prdCycTypCod.equals ("Standard") ? AngPrdCycTyp.LINKED_CYCLE : AngPrdCycTyp.ALTERNATIVE_CYCLE;
                return prdCycTypUid;
            })
            .setSqlSetter ((stm, index, entity) -> {
                Long prdCycTypUid = entity.getPrdCycTypUid ();
                String value = (prdCycTypUid != null && prdCycTypUid == AngPrdCycTyp.LINKED_CYCLE) ? "Standard" : "Alternativo";
                stm.setString (index, value);
            });

        var cycStdField = new TmpField<Boolean, AngPrdCyc> ()
            .setValueGetter (_MAngPrdCyc::getPrdCycStd)
            .setValueSetter ((value, entity) -> entity.setPrdCycStd (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "CicloStandard")
            .setSqlGetter (ResultSet::getBoolean)
            .setSqlSetter ((stm, index, entity) -> stm.setInt (index, entity.getPrdCycStd () ? 1 : 0));

        var valStrDatField = new TmpField<SDate, AngPrdCyc> ()
            .setValueGetter (_MAngPrdCyc::getValStrDat)
            .setValueSetter ((value, entity) -> entity.setValStrDat (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "DataInizioValidita")
            .setSqlGetter ((rs, field) -> FctTmpUtil.get ().getDate (rs.getTimestamp (field)))
            .setSqlSetter((stm, index, entity) -> stm.setTimestamp (index, entity.getValStrDat ()));

        var valEndDatField = new TmpField<SDate, AngPrdCyc> ()
            .setValueGetter (_MAngPrdCyc::getValEndDat)
            .setValueSetter ((value, entity) -> entity.setValEndDat (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "DataFineValidita")
            .setSqlGetter ((rs, field) -> FctTmpUtil.get ().getDate (rs.getTimestamp (field)))
            .setSqlSetter((stm, index, entity) -> stm.setTimestamp (index, entity.getValEndDat ()));

        return Arrays.asList (
            codField,
            dscField,
            itmField,
            prdCycTypField,
            cycStdField,
            valStrDatField,
            valEndDatField
        );
    } // initFields

    @Override
    public String getCode (AngPrdCyc entity) {
        return entity.getPrdCycCod () + delimiter +
            entity.getItm ().getItmCod () + delimiter +
            entity.getPrdCycTypUid ();
    } // getCode

    @Override protected String getTempTableName () { return "CicliLavoro"; } // getTempTableName 
    @Override protected Supplier<AngPrdCyc> getNewInstance () { return AngPrdCyc::newInstance; } // getNewInstance
    @Override protected Class<AngPrdCyc> getModelClass () { return AngPrdCyc.class; } // getModelClass
    @Override public TableDao<AngPrdCyc> getDao () { return AngPrdCycDao.get (); } 
    @Override public String getAlias () { return SAlias.AngPrdCyc; } // getAlias 
    @Override public Long getTableUid () { return AngPrdCycMeta.get ().tableUid (); } // getTableUid 
    @Override public ISField getUidField () { return AngPrdCycFields.Uid; } // getUidField 
    @Override public String getVrtStoreKey () { return ApsTmpStore.ROUTES_KEY; } // getVrtStoreKey

    @Override
    public List<AngPrdCyc> getActiveList (ApsImportContext ctx) throws SException {
        return SysItmLogic.get ().getActiveWithJoinAngPrdCyc (true, ctx.getContext (), ctx.getWCon ());
    } // getActiveList

    @Override
    public SBatchResult<AngPrdCyc> insertBatch (List<AngPrdCyc> entities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().insertBatchAngPrdCyc (entities, new HashMap<> (), options, context, wCon);
    } // insertBatch

    @Override
    public SBatchResult<AngPrdCyc> updateBatch (List<AngPrdCyc> entities, List<AngPrdCyc> oldEntities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        Map<Long, AngPrdCyc> entityByUid = new HashMap<> ();
        for (AngPrdCyc oldEntity : oldEntities) {
            entityByUid.put (oldEntity.getUid (), oldEntity);
        } // for
        return SysItmLogic.get ().updateAllBatchAngPrdCyc (entities, entityByUid, new HashMap<> (), options, context, wCon);
    } // updateBatch
    

    @Override
    public AngPrdCyc getInsert (AngPrdCyc entity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().insertAngPrdCyc (entity, false, context, wCon);
    } // getInsert

    @Override
    public AngPrdCyc getUpdate (AngPrdCyc entity, AngPrdCyc oldEntity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().updateAllAngPrdCyc (entity, false, context, wCon);
    } // getUpdate

    @Override
    public StoreSetter<AngPrdCyc, ApsImportContext> getStoreSetter () {
        return (upsertedEntities, removeCandidates, ctx) -> {
            ctx.getStore ().setRoutes (upsertedEntities);
            ctx.getDeletionStore ().setToRemoveAngPrdCyc (removeCandidates);
        };
    } // getStoreSetter

    @Override
    public void align (AngPrdCyc entity, ApsImportContext ctx, boolean insert) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        String itmCod = AngApsItmTmp.get ().getCode (entity.getItm ());
        Long itmUid = store.getItems ().get (itmCod);
        if (itmUid == null) {
            List<Object> values = Arrays.asList (itmCod, insert ? "inserimento" : "modifica", entity.getPrdCycCod ());
            FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpItmUidPrdCyc, values, ApsTmpTableKey.AngPrdCyc.getKeyLabel (), insert, store, context, wCon);

            AngItm dummyAngItm = AngApsItmTmp.get ().getDummy (ctx);
            entity.setItmUid (dummyAngItm.getUid ());
            entity.setItm (dummyAngItm);
        } else {
            entity.setItmUid (itmUid);
            entity.getItm ().setUid (itmUid);
        } // if - else
    } // align

    @Override
    public AngPrdCyc getDummy (ApsImportContext ctx) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ApsTmpStoreDeletion deletionStore = ctx.getDeletionStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        AngPrdCyc dummy = store.getDummyAngPrdCyc ();
 
        if (dummy == null) {
            String dummyCode = "@dummy";
            
            AngPrdCyc tmpDummy = AngPrdCyc.newInstance ();
            tmpDummy.setPrdCycCod (dummyCode);
            tmpDummy.setPrdCycDsc (dummyCode);
            tmpDummy.setPrdCycTypUid (AngPrdCycTyp.ALTERNATIVE_CYCLE);
            
            AngItm dummyAngItm = AngApsItmTmp.get ().getDummy (ctx);
            tmpDummy.setItm (dummyAngItm);
            tmpDummy.setItmUid (dummyAngItm.getUid ());

            AngPrdCyc removingDummy = deletionStore.removeAngPrdCyc (getCode (tmpDummy));
            dummy = removingDummy == null ? AngPrdCycDao.get ().insert (tmpDummy, false, context, wCon) : removingDummy;

            dummy.setItm (dummyAngItm);
            store.setDummyAngPrdCyc (dummy);
            String code = AngPrdCycTmp.get ().getCode (dummy);
            long uid = dummy.getUid ();
            store.addRoute (code, uid);
        } // if
        
        return dummy;
    } // getDummy

} // AngPrdCycTmp
