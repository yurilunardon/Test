package net.synergy2.logic.aps.tmp.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import net.synergy2.db.sys.itm.AngPrdCyc;
import net.synergy2.db.sys.itm.AngPrdCycOpr;
import net.synergy2.db.sys.itm.AngPrdCycOprLnk;
import net.synergy2.db.sys.itm.AngPrdCycOprLnkDao;
import net.synergy2.db.sys.itm.AngPrdCycOprLnkImpl.AngPrdCycOprLnkFields;
import net.synergy2.db.sys.itm.AngPrdCycOprLnkMeta;
import net.synergy2.db.sys.itm.AngPrdCycTyp;
import net.synergy2.db.sys.itm.AngPrdVrn;
import net.synergy2.db.sys.itm.AngPrdVrnCat;
import net.synergy2.db.sys.itm.abstracts._MAngPrdCycOprLnk;
import net.synergy2.logic.aps.tmp.ApsImportContext;
import net.synergy2.logic.aps.tmp.ApsTmpMessage;
import net.synergy2.logic.aps.tmp.ApsTmpStoreDeletion;
import net.synergy2.logic.aps.tmp.common.ApsTmpStore;
import net.synergy2.logic.aps.tmp.common.ApsTmpTableKey;
import net.synergy2.logic.fct.tmp.AGenericTmp;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import net.synergy2.logic.fct.tmp.common.TmpField;
import net.synergy2.logic.sys.itm.SysItmLogic;

public class AngPrdCycOprLnkTmp extends AGenericTmp<AngPrdCycOprLnk, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> {

    private AngPrdCycOprLnkTmp () {}
    private static class Singleton {
        private static final SSingletonHolder<AngPrdCycOprLnkTmp> INSTANCE = new SSingletonHolder<> (AngPrdCycOprLnkTmp.class);
    } // Singleton
    public static AngPrdCycOprLnkTmp get () { return Singleton.INSTANCE.get (); }
    
    @Override
    protected List<TmpField<?, AngPrdCycOprLnk>> initFields () {
        var prePrdCycOprField = new TmpField<AngPrdCycOpr, AngPrdCycOprLnk> ()
            .setValueGetter (_MAngPrdCycOprLnk::getPrePrdCycOpr)
            .setValueSetter ((value, entity) -> entity.setPrePrdCycOpr (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setPrePrdCycOprUid (incomingEntity.getUid ()))
            .setComparator ((value1, value2) -> value1 != null && value2 != null &&
                value1.getUid () == value2.getUid () &&
                !value2.getLogDel ()
             )
            .setComparatorByCode ((value1, value2) -> value1 != null && value2 != null &&
            value1.getPrdCyc () != null && value2.getPrdCyc () != null &&
            value1.getPrdCyc ().getItm () != null && value2.getPrdCyc ().getItm () != null &&
            value1.getOprNum ().equals (value2.getOprNum ()) &&
            value1.getPrdCyc ().getItm ().getItmCod ().equals (value2.getPrdCyc ().getItm ().getItmCod ()) &&
            value1.getPrdCyc ().getPrdCycTypUid () == value2.getPrdCyc ().getPrdCycTypUid () &&
            value1.getPrdCyc ().getPrdCycCod ().equals (value2.getPrdCyc ().getPrdCycCod ()) &&
            (
                (value1.getItmVrn () == null && value2.getItmVrn () == null) ||
                (
                    value1.getItmVrn () != null && value2.getItmVrn () != null && 
                    value1.getItmVrn ().getVrn () != null && value2.getItmVrn ().getVrn () != null && 
                    value1.getItmVrn ().getVrn ().getCat () != null && value2.getItmVrn ().getVrn ().getCat () != null &&
                    value1.getItmVrn ().getVrn ().getCat ().getCatCod ().equals (value2.getItmVrn ().getVrn ().getCat ().getCatCod ()) &&
                    value1.getItmVrn ().getVrn ().getVrnCod ().equals (value2.getItmVrn ().getVrn ().getVrnCod ())
                )
            ) &&  
            !value2.getLogDel ()
         )
            .setSqlFieldGetter (() -> "CodiceCicloFasePrecedente")
            .setSqlGetter ((rs, field) -> {
                AngItm itm = AngItm.newInstance ();
                AngPrdCyc prdCyc = AngPrdCyc.newInstance ();
                AngPrdCycTyp prdCycTyp = AngPrdCycTyp.newInstance ();
                AngPrdCycOpr prdCycOpr = AngPrdCycOpr.newInstance ();
                String[] splitCode = rs.getString (field).split (delimiter);
                String prdCycCod = splitCode[0];
                String itmCod = splitCode[1];
                String prdCycTypCod = splitCode[2];
                String prdCycOprNum = splitCode[3];
                itm.setItmCod (itmCod);
                prdCyc.setPrdCycCod (prdCycCod);
                prdCyc.setItm (itm);
                prdCycTyp.setPrdCycTypCod (prdCycTypCod);
                long prdCycTypUid = prdCycTypCod.equals ("Standard") ? AngPrdCycTyp.LINKED_CYCLE : AngPrdCycTyp.ALTERNATIVE_CYCLE;
                prdCycTyp.setUid (prdCycTypUid);
                prdCyc.setPrdCycTypUid (prdCycTypUid);
                prdCycOpr.setPrdCyc (prdCyc);
                prdCycOpr.setOprNum (prdCycOprNum);
                
                if (!splitCode[4].equals ("@NULL")) {
                    AngItm preItmVrn = AngItm.newInstance ();
                    AngItmVrn itmVrn = AngItmVrn.newInstance ();
                    AngPrdVrn vrn = AngPrdVrn.newInstance ();
                    AngPrdVrnCat cat = AngPrdVrnCat.newInstance ();
                    preItmVrn.setItmCod (splitCode[4]);
                    cat.setCatCod (splitCode[5]);
                    vrn.setVrnCod (splitCode[6]);
                    vrn.setCat (cat);
                    itmVrn.setVrn (vrn);
                    itmVrn.setItm (preItmVrn);
                    prdCycOpr.setItmVrn (itmVrn);
                } // if
                
                return prdCycOpr;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngPrdCycOpr preOpr = entity.getPrePrdCycOpr ();
                String code = null;

                if (preOpr != null &&
                    preOpr.getPrdCyc () != null &&
                    preOpr.getPrdCyc ().getItm () != null &&
                    preOpr.getPrdCyc ().getItm ().getItmCod () != null &&
                    preOpr.getPrdCyc ().getPrdCycCod () != null &&
                    preOpr.getOprNum () != null) {

                    String prdCycCod = preOpr.getPrdCyc ().getPrdCycCod ();
                    String itmCod = preOpr.getPrdCyc ().getItm ().getItmCod ();
                    String prdCycTypCod = preOpr.getPrdCyc ().getPrdCycTypUid () == AngPrdCycTyp.LINKED_CYCLE
                        ? "Standard" : "Alternativo";
                    String oprNum = preOpr.getOprNum ();

                    String itmVrnCod = null;
                    String catCod = null;
                    String vrnCod = null;

                    if (preOpr.getItmVrn () != null &&
                        preOpr.getItmVrn ().getItm () != null &&
                        preOpr.getItmVrn ().getItm ().getItmCod () != null &&
                        preOpr.getItmVrn ().getVrn () != null &&
                        preOpr.getItmVrn ().getVrn ().getCat () != null &&
                        preOpr.getItmVrn ().getVrn ().getVrnCod () != null &&
                        preOpr.getItmVrn ().getVrn ().getCat ().getCatCod () != null) {

                        itmVrnCod = preOpr.getItmVrn ().getItm ().getItmCod ();
                        catCod = preOpr.getItmVrn ().getVrn ().getCat ().getCatCod ();
                        vrnCod = preOpr.getItmVrn ().getVrn ().getVrnCod ();

                        code = prdCycCod + delimiter + itmCod + delimiter + prdCycTypCod + delimiter + oprNum +
                               delimiter + itmVrnCod + delimiter + catCod + delimiter + vrnCod;
                    } else {
                        code = prdCycCod + delimiter + itmCod + delimiter + prdCycTypCod + delimiter + oprNum +
                               delimiter + "@NULL";
                    }
                }

                stm.setString(index, code);
            });
        
        var nxtPrdCycOprField = new TmpField<AngPrdCycOpr, AngPrdCycOprLnk> ()
            .setValueGetter (_MAngPrdCycOprLnk::getNxtPrdCycOpr)
            .setValueSetter ((value, entity) -> entity.setNxtPrdCycOpr (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setNxtPrdCycOprUid (incomingEntity.getUid ()))
            .setComparator ((value1, value2) -> value1 != null && value2 != null &&
                value1.getUid () == value2.getUid () &&
                !value2.getLogDel ()
             )
            .setComparatorByCode ((value1, value2) -> value1 != null && value2 != null &&
            value1.getPrdCyc () != null && value2.getPrdCyc () != null &&
            value1.getPrdCyc ().getItm () != null && value2.getPrdCyc ().getItm () != null &&
            value1.getOprNum ().equals (value2.getOprNum ()) &&
            value1.getPrdCyc ().getItm ().getItmCod ().equals (value2.getPrdCyc ().getItm ().getItmCod ()) &&
            value1.getPrdCyc ().getPrdCycTypUid () == value2.getPrdCyc ().getPrdCycTypUid () &&
            value1.getPrdCyc ().getPrdCycCod ().equals (value2.getPrdCyc ().getPrdCycCod ()) &&
            (
                (value1.getItmVrn () == null && value2.getItmVrn () == null) ||
                (
                    value1.getItmVrn () != null && value2.getItmVrn () != null && 
                    value1.getItmVrn ().getVrn () != null && value2.getItmVrn ().getVrn () != null && 
                    value1.getItmVrn ().getVrn ().getCat () != null && value2.getItmVrn ().getVrn ().getCat () != null &&
                    value1.getItmVrn ().getVrn ().getCat ().getCatCod ().equals (value2.getItmVrn ().getVrn ().getCat ().getCatCod ()) &&
                    value1.getItmVrn ().getVrn ().getVrnCod ().equals (value2.getItmVrn ().getVrn ().getVrnCod ())
                )
            ) && 
            !value2.getLogDel ()
         )
            .setSqlFieldGetter (() -> "CodiceCicloFaseSuccessiva")
            .setSqlGetter ((rs, field) -> {
                AngItm itm = AngItm.newInstance ();
                AngPrdCyc prdCyc = AngPrdCyc.newInstance ();
                AngPrdCycTyp prdCycTyp = AngPrdCycTyp.newInstance ();
                AngPrdCycOpr prdCycOpr = AngPrdCycOpr.newInstance ();
                String[] splitCode = rs.getString (field).split (delimiter);
                String prdCycCod = splitCode[0];
                String itmCod = splitCode[1];
                String prdCycTypCod = splitCode[2];
                String prdCycOprNum = splitCode[3];
                itm.setItmCod (itmCod);
                prdCyc.setPrdCycCod (prdCycCod);
                prdCyc.setItm (itm);
                prdCycTyp.setPrdCycTypCod (prdCycTypCod);
                long prdCycTypUid = prdCycTypCod.equals ("Standard") ? AngPrdCycTyp.LINKED_CYCLE : AngPrdCycTyp.ALTERNATIVE_CYCLE;
                prdCycTyp.setUid (prdCycTypUid);
                prdCyc.setPrdCycTypUid (prdCycTypUid);
                prdCycOpr.setOprNum (prdCycOprNum);
                prdCycOpr.setPrdCyc (prdCyc);
                
                if (!splitCode[4].equals ("@NULL")) {
                    AngItm nxtItmVrn = AngItm.newInstance ();
                    AngItmVrn itmVrn = AngItmVrn.newInstance ();
                    AngPrdVrn vrn = AngPrdVrn.newInstance ();
                    AngPrdVrnCat cat = AngPrdVrnCat.newInstance ();
                    nxtItmVrn.setItmCod (splitCode[4]);
                    cat.setCatCod (splitCode[5]);
                    vrn.setVrnCod (splitCode[6]);
                    vrn.setCat (cat);
                    itmVrn.setVrn (vrn);
                    itmVrn.setItm (nxtItmVrn);
                    prdCycOpr.setItmVrn (itmVrn);
                } // if
                
                return prdCycOpr;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngPrdCycOpr nextOpr = entity.getNxtPrdCycOpr ();
                String code = null;

                String prdCycCod = nextOpr.getPrdCyc ().getPrdCycCod ();
                String itmCod = nextOpr.getPrdCyc ().getItm ().getItmCod ();
                String prdCycTypCod = nextOpr.getPrdCyc ().getPrdCycTypUid () == AngPrdCycTyp.LINKED_CYCLE
                    ? "Standard" : "Alternativo";
                String oprNum = nextOpr.getOprNum ();

                String vrnCod = "@NULL";
                if (nextOpr.getItmVrn () != null) {
                    String itmVrnCod = nextOpr.getItmVrn ().getItm ().getItmCod ();
                    String catCod = nextOpr.getItmVrn ().getVrn ().getCat ().getCatCod ();
                    vrnCod = nextOpr.getItmVrn ().getVrn ().getVrnCod ();
                    
                    vrnCod = itmVrnCod + delimiter + catCod + delimiter + vrnCod;
                } // if
                code = prdCycCod + delimiter + itmCod + delimiter + prdCycTypCod + delimiter + oprNum +
                    delimiter + vrnCod;

                stm.setString (index, code);
            });
        
        return Arrays.asList (
            prePrdCycOprField,
            nxtPrdCycOprField
        );
    } // initFields

    @Override
    public String getCode (AngPrdCycOprLnk entity) {
        return entity.getPrePrdCycOpr ().getPrdCyc ().getPrdCycCod () + delimiter +
            entity.getPrePrdCycOpr ().getPrdCyc ().getItm ().getItmCod () + delimiter +
            entity.getPrePrdCycOpr ().getPrdCyc ().getPrdCycTypUid () + delimiter +
            entity.getPrePrdCycOpr ().getOprNum () + delimiter +
            (entity.getPrePrdCycOpr ().getItmVrn () == null ? "@NULL" :
                entity.getPrePrdCycOpr ().getItmVrn ().getItm ().getItmCod () + delimiter + entity.getPrePrdCycOpr ().getItmVrn ().getVrn ().getCat ().getCatCod () + delimiter + entity.getPrePrdCycOpr ().getItmVrn ().getVrn ().getVrnCod ()) + delimiter +
            entity.getNxtPrdCycOpr ().getPrdCyc ().getPrdCycCod () + delimiter +
            entity.getNxtPrdCycOpr ().getPrdCyc ().getItm ().getItmCod () + delimiter +
            entity.getNxtPrdCycOpr ().getPrdCyc ().getPrdCycTypUid () + delimiter +
            entity.getNxtPrdCycOpr ().getOprNum ()  + delimiter +
            (entity.getNxtPrdCycOpr ().getItmVrn () == null ? "@NULL" : 
                entity.getNxtPrdCycOpr ().getItmVrn ().getItm ().getItmCod () + delimiter + entity.getNxtPrdCycOpr ().getItmVrn ().getVrn ().getCat ().getCatCod () + delimiter + entity.getNxtPrdCycOpr ().getItmVrn ().getVrn ().getVrnCod ());
    } // getCode

    @Override protected String getTempTableName () { return "DipendenzeCicliFaseLavoro"; } // getTempTableName 
    @Override protected Supplier<AngPrdCycOprLnk> getNewInstance () { return AngPrdCycOprLnk::newInstance; } // getNewInstance
    @Override protected Class<AngPrdCycOprLnk> getModelClass () { return AngPrdCycOprLnk.class; } // getModelClass
    @Override public TableDao<AngPrdCycOprLnk> getDao () { return AngPrdCycOprLnkDao.get (); } 
    @Override public String getAlias () { return SAlias.AngPrdCycOprLnk; } // getAlias 
    @Override public Long getTableUid () { return AngPrdCycOprLnkMeta.get ().tableUid (); } // getTableUid 
    @Override public ISField getUidField () { return AngPrdCycOprLnkFields.Uid; } // getUidField 
    @Override public String getVrtStoreKey () { return ApsTmpStore.ROUTE_ROW_LINKS_KEY; } // getVrtStoreKey

    @Override
    public List<AngPrdCycOprLnk> getActiveList (ApsImportContext ctx) throws SException {
        return SysItmLogic.get ().getActiveWithJoinAngPrdCycOprLnk (true, ctx.getContext (), ctx.getWCon ());
    } // getActiveList

    @Override
    public SBatchResult<AngPrdCycOprLnk> insertBatch (List<AngPrdCycOprLnk> entities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().insertBatchAngPrdCycOprLnk (entities, new HashMap<> (), options, context, wCon);
    } // insertBatch

    @Override
    public SBatchResult<AngPrdCycOprLnk> updateBatch (List<AngPrdCycOprLnk> entities, List<AngPrdCycOprLnk> oldEntities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        Map<Long, AngPrdCycOprLnk> entityByUid = new HashMap<> ();
        for (AngPrdCycOprLnk oldEntity : oldEntities) {
            entityByUid.put (oldEntity.getUid (), oldEntity);
        } // for
        return SysItmLogic.get ().updateAllBatchAngPrdCycOprLnk (entities, entityByUid, new HashMap<> (), options, context, wCon);
    } // updateBatch

    @Override
    public AngPrdCycOprLnk getInsert (AngPrdCycOprLnk entity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().postAngPrdCycOprLnk (entity, context, wCon);
    } // getInsert

    @Override
    public AngPrdCycOprLnk getUpdate (AngPrdCycOprLnk entity, AngPrdCycOprLnk oldEntity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().updateAllAngPrdCycOprLnk (entity, false, context, wCon);
    }

    @Override
    public StoreSetter<AngPrdCycOprLnk, ApsImportContext> getStoreSetter () {
        return (upsertedEntities, removeCandidates, ctx) -> {
            ctx.getStore ().setRouteRowLinks (upsertedEntities);
            ctx.getDeletionStore ().setToRemoveAngPrdCycOprLnk (removeCandidates);
        };
    } // getStoreSetter

    @Override
    public void align (AngPrdCycOprLnk entity, ApsImportContext ctx, boolean insert) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        String prePrdCycOprCod = AngPrdCycOprTmp.get ().getCode (entity.getPrePrdCycOpr ());
        Long prePrdCycOprUid = store.getRouteRows ().get (prePrdCycOprCod);
        if (prePrdCycOprUid == null) {
            List<Object> values = Arrays.asList (prePrdCycOprCod, insert ? "inserimento" : "modifica", entity.getUid ());
            FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpCycOprUidCycOprLnk, values, ApsTmpTableKey.AngPrdCycOprLnk.getKeyLabel (), insert, store, context, wCon);

            AngPrdCycOpr dummyAngPrdCycOpr = AngPrdCycOprTmp.get ().getDummy (ctx);
            entity.setPrePrdCycOprUid (dummyAngPrdCycOpr.getUid ());
            entity.setPrePrdCycOpr (dummyAngPrdCycOpr);
        } else {
            entity.setPrePrdCycOprUid (prePrdCycOprUid);
            entity.getPrePrdCycOpr ().setUid (prePrdCycOprUid);
        } // if - else

        String nxtPrdCycOprCod = AngPrdCycOprTmp.get ().getCode (entity.getNxtPrdCycOpr ());
        Long nxtPrdCycOprUid = store.getRouteRows ().get (nxtPrdCycOprCod);
        if (nxtPrdCycOprUid == null) {
            List<Object> values = Arrays.asList (nxtPrdCycOprCod, insert ? "inserimento" : "modifica", entity.getUid ());
            FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpCycOprUidCycOprLnk, values, ApsTmpTableKey.AngPrdCycOprLnk.getKeyLabel (), insert, store, context, wCon);

            AngPrdCycOpr dummyAngPrdCycOpr = AngPrdCycOprTmp.get ().getDummy (ctx);
            entity.setNxtPrdCycOprUid (dummyAngPrdCycOpr.getUid ());
            entity.setNxtPrdCycOpr (dummyAngPrdCycOpr);
        } else {
            entity.setNxtPrdCycOprUid (nxtPrdCycOprUid);
            entity.getNxtPrdCycOpr ().setUid (nxtPrdCycOprUid);
        } // if - else
    }

} // AngPrdCycOprLnkTemp
