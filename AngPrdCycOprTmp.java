package net.synergy2.logic.aps.tmp.impl;

import java.math.BigDecimal;
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
import net.synergy2.db.sys.grp.AngGrp;
import net.synergy2.db.sys.itm.AngItm;
import net.synergy2.db.sys.itm.AngItmVrn;
import net.synergy2.db.sys.itm.AngPrdCyc;
import net.synergy2.db.sys.itm.AngPrdCycDao;
import net.synergy2.db.sys.itm.AngPrdCycOpr;
import net.synergy2.db.sys.itm.AngPrdCycOprDao;
import net.synergy2.db.sys.itm.AngPrdCycOprImpl;
import net.synergy2.db.sys.itm.AngPrdCycOprImpl.AngPrdCycOprFields;
import net.synergy2.db.sys.itm.AngPrdCycOprMeta;
import net.synergy2.db.sys.itm.AngPrdCycTyp;
import net.synergy2.db.sys.itm.AngPrdVrn;
import net.synergy2.db.sys.itm.AngPrdVrnCat;
import net.synergy2.db.sys.itm.abstracts._MAngPrdCycOpr;
import net.synergy2.logic.aps.tmp.ApsImportContext;
import net.synergy2.logic.aps.tmp.ApsTmpMessage;
import net.synergy2.logic.aps.tmp.ApsTmpStoreDeletion;
import net.synergy2.logic.aps.tmp.common.ApsTmpStore;
import net.synergy2.logic.aps.tmp.common.ApsTmpTableKey;
import net.synergy2.logic.fct.tmp.AGenericTmp;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import net.synergy2.logic.fct.tmp.common.TmpField;
import net.synergy2.logic.sys.itm.SysItmLogic;

public class AngPrdCycOprTmp extends AGenericTmp<AngPrdCycOpr, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> {

    private AngPrdCycOprTmp () {}
    private static class Singleton {
        private static final SSingletonHolder<AngPrdCycOprTmp> INSTANCE = new SSingletonHolder<> (AngPrdCycOprTmp.class);
    } // Singleton
    public static AngPrdCycOprTmp get () { return Singleton.INSTANCE.get (); }

    @Override
    protected List<TmpField<?, AngPrdCycOpr>> initFields () {
        var prdCycField = new TmpField<AngPrdCyc, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getPrdCyc)
            .setValueSetter ((value, entity) -> entity.setPrdCyc (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setPrdCycUid (incomingEntity.getPrdCycUid ()))
            .setComparator ((value1, value2) -> value1 != null && value2 != null &&
                value1.getUid () == value2.getUid () &&
                !value2.getLogDel ()
             )
            .setComparatorByCode ((value1, value2) -> value1 != null && value2 != null &&
                value1.getItm () != null && value2.getItm () != null &&
                value1.getItm ().getItmCod ().equals (value2.getItm ().getItmCod ()) &&
                value1.getPrdCycTypUid () == value2.getPrdCycTypUid () &&
                value1.getPrdCycCod ().equals (value2.getPrdCycCod ()) &&
                !value2.getLogDel ()
            ) 
            .setSqlFieldGetter (() -> "CodiceCiclo")
            .setSqlGetter ((rs, field) -> {
                AngItm itm = AngItm.newInstance ();
                AngPrdCyc prdCyc = AngPrdCyc.newInstance ();
                AngPrdCycTyp prdCycTyp = AngPrdCycTyp.newInstance ();
                String[] splitCode = rs.getString (field).split (delimiter);
                String prdCycCod = splitCode[0];
                String itmCod = splitCode[1];
                String prdCycTypCod = splitCode[2];
                itm.setItmCod (itmCod);
                prdCyc.setPrdCycCod (prdCycCod);
                prdCyc.setItm (itm);
                prdCycTyp.setPrdCycTypCod (prdCycTypCod);
                long prdCycTypUid = prdCycTypCod.equals ("Standard") ? AngPrdCycTyp.LINKED_CYCLE : AngPrdCycTyp.ALTERNATIVE_CYCLE;
                prdCycTyp.setUid (prdCycTypUid);
                prdCyc.setPrdCycTypUid (prdCycTypUid);
                return prdCyc;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngPrdCyc prdCyc = entity.getPrdCyc ();
      
                String prdCycCod = prdCyc.getPrdCycCod ();
                String itmCod = prdCyc.getItm ().getItmCod ();
                String prdCycTypCod = prdCyc.getPrdCycTypUid () == AngPrdCycTyp.LINKED_CYCLE
                     ? "Standard"
                     : "Alternativo";

                 String code = prdCycCod + delimiter + itmCod + delimiter + prdCycTypCod;
               
                 stm.setString (index, code);
            });

        var oprNumField = new TmpField<String, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getOprNum)
            .setValueSetter ((value, entity) -> entity.setOprNum (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "CodiceFase")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getOprNum ()));
        
        var vrnField = new TmpField<AngItmVrn, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getItmVrn)
            .setValueSetter ((value, entity) -> entity.setItmVrn (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setItmVrnUid (incomingEntity.getItmVrnUid ()))
            .setComparator ((value1, value2) -> ((value1 == null && value2 == null) ||
                (
                    value1 != null && value2 != null &&
                    value1.getUid () == value2.getUid () &&
                    !value2.getLogDel ())
                )
            )
            .setComparator ((value1, value2) -> ((value1 == null  && value2 == null) ||
                (
                    value1 != null && value2 != null &&
                    value1.getVrn () != null && value2.getVrn () != null &&
                    value1.getVrn ().getCat () != null && value2.getVrn ().getCat () != null &&
                    value1.getVrn ().getCat ().getCatCod ().equals (value2.getVrn ().getCat ().getCatCod ()) &&
                    value1.getVrn ().getVrnCod ().equals (value2.getVrn ().getVrnCod ()) &&
                    !value2.getLogDel ())
                )
            )
            .setSqlFieldGetter (() -> "CodiceVarianteArticolo")
            .setSqlGetter ((rs, field) -> {
                String itmVrnConcat = rs.getString (field);
                if (itmVrnConcat != null && !itmVrnConcat.equals ("@NULL")) {
                    AngPrdVrnCat category = AngPrdVrnCat.newInstance ();
                    AngPrdVrn variant = AngPrdVrn.newInstance ();
                    AngItmVrn itemVariant = AngItmVrn.newInstance ();
                    AngItm item = AngItm.newInstance ();
                    String[] splitCode = itmVrnConcat.split (delimiter);
                    item.setItmCod (splitCode[0]);
                    category.setCatCod (splitCode[1]);
                    variant.setVrnCod (splitCode[2]);
                    variant.setCat (category);
                    itemVariant.setVrn (variant);
                    itemVariant.setItm (item);
                    return itemVariant;
                } // if
                return null;
            })
            .setSqlSetter ( (stm, index, entity) -> {
                String code;

                AngItmVrn itmVrn = entity.getItmVrn ();
                if (itmVrn == null) {
                    code = "@NULL";
                } else {
                    AngItm itm = itmVrn.getItm ();
                    AngPrdVrn vrn = itmVrn.getVrn ();
                    AngPrdVrnCat cat = (vrn != null) ? vrn.getCat () : null;

                    if (itm != null && itm.getItmCod () != null &&
                        vrn != null && vrn.getVrnCod () != null &&
                        cat != null && cat.getCatCod () != null) {

                        code = itm.getItmCod () + delimiter + cat.getCatCod () + delimiter + vrn.getVrnCod ();
                    } else {
                        code = "@NULL";
                    }
                }

                stm.setString (index, code);
            });

        var mtcField = new TmpField<String, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getMtc)
            .setValueSetter ((value, entity) -> entity.setMtc (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "CodiceAssociazione")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getMtc ()));

        var oprDscField = new TmpField<String, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getOprDsc)
            .setValueSetter ((value, entity) -> entity.setOprDsc (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Descrizione")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getOprDsc ()));

        var trfQtyField = new TmpField<BigDecimal, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getTrfQty)
            .setValueSetter ((value, entity) -> entity.setTrfQty (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "QuantitaDiTrasferimento")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getTrfQty ()));

        var valStrDatField = new TmpField<SDate, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getValStrDat)
            .setValueSetter ((value, entity) -> entity.setValStrDat (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "DataInizioValidita")
            .setSqlGetter ((rs, field) -> FctTmpUtil.get ().getDate (rs.getTimestamp (field)))
            .setSqlSetter((stm, index, entity) -> stm.setTimestamp(index, entity.getValStrDat ()));  

        var valEndDatField = new TmpField<SDate, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getValEndDat)
            .setValueSetter ((value, entity) -> entity.setValEndDat (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "DataFineValidita")
            .setSqlGetter ((rs, field) -> FctTmpUtil.get ().getDate (rs.getTimestamp (field)))
            .setSqlSetter((stm, index, entity) -> stm.setTimestamp (index, entity.getValEndDat ()));

        var durSetField = new TmpField<BigDecimal, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getDurSet)
            .setValueSetter ((value, entity) -> entity.setDurSet (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "TempoDiSetup")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getDurSet ()));

        var durWrkItmField = new TmpField<BigDecimal, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getDurWrkItm)
            .setValueSetter ((value, entity) -> entity.setDurWrkItm (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "TempoDiProcessoArticolo")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getDurWrkItm ()));

        var durWrkBatField = new TmpField<BigDecimal, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getDurWrkBat)
            .setValueSetter ((value, entity) -> entity.setDurWrkBat (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "TempoDiProcessoLotto")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getDurWrkBat ()));

        var durMinPreField = new TmpField<BigDecimal, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getDurMinPre)
            .setValueSetter ((value, entity) -> entity.setDurMinPre (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "TempoMinimoDiAttesaFasePrecedente")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getDurMinPre ()));

        var durMinNexField = new TmpField<BigDecimal, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getDurMinNex)
            .setValueSetter ((value, entity) -> entity.setDurMinNex (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "TempoMinimoDiAttesaFaseSuccessiva")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getDurMinNex ()));

        var durMaxNexField = new TmpField<BigDecimal, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getDurMaxNex)
            .setValueSetter ((value, entity) -> entity.setDurMaxNex (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "TempoMassimoDiAttesaFaseSuccessiva")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getDurMaxNex ()));

        var oprEffField = new TmpField<BigDecimal, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getOprEff)
            .setValueSetter ((value, entity) -> entity.setOprEff (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "EfficienzaFase")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getOprEff ()));

        var plnGrpField = new TmpField<AngGrp, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getPlnGrp)
            .setValueSetter ((value, entity) -> entity.setPlnGrp (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setPlnGrpUid (incomingEntity.getPlnGrpUid ()))
            .setComparator ((value1, value2) -> ((value1 == null && value2 == null) ||
                (value1 != null && value2 != null && value1.getUid () == value2.getUid () && !value2.getLogDel ())))
            .setComparatorByCode ((value1, value2) -> ((value1 == null && value2 == null) ||
                (value1 != null && value2 != null && value1.getGrpCod ().equals (value2.getGrpCod ()) && !value2.getLogDel ())))
            .setSqlFieldGetter (() -> "CodiceGruppoRisorsePianificato")
            .setSqlGetter ((rs, field) -> {
                String groupCode = rs.getString (field);
                if (!StringUtil.isEmpty (groupCode)) {
                    AngGrp group = AngGrp.newInstance ();
                    group.setGrpCod (groupCode);
                    return group;
                } // if
                return null;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngGrp group = entity.getPlnGrp ();
                stm.setString (index, group != null ? group.getGrpCod () : null);
            });

        var durMinWrkPreField = new TmpField<BigDecimal, AngPrdCycOpr> ()
            .setValueGetter (_MAngPrdCycOpr::getDurMinWrkPre)
            .setValueSetter ((value, entity) -> entity.setDurMinWrkPre (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "TempoMinimoLavorativoAttesaFasePrecedente")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getDurMinWrkPre ()));

        var prdVelCoeffField = new TmpField<BigDecimal, AngPrdCycOpr> ()
            .setValueGetter (a -> ((AngPrdCycOprImpl) a).getPrdVelQtyItm ())
            .setValueSetter ((value, entity) -> ((AngPrdCycOprImpl) entity).setPrdVelQtyItm (value))
            .setComparator ((value1, value2) -> NumberUtil.compareBigDecimal (value1, Operators.EQUAL, value2, true))
            .setSqlFieldGetter (() -> "CoefficienteImpiegoProduttivo")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter((stm, index, entity) -> {
                BigDecimal value = ((AngPrdCycOprImpl) entity).getPrdVelQtyItm ();
                stm.setBigDecimal (index, value);
             });

        return Arrays.asList (
            prdCycField,
            oprNumField,
            vrnField,
            mtcField,
            oprDscField,
            trfQtyField,
            valStrDatField,
            valEndDatField,
            durSetField,
            durWrkItmField,
            durWrkBatField,
            durMinPreField,
            durMinNexField,
            durMaxNexField,
            oprEffField,
            plnGrpField,
            durMinWrkPreField,
            prdVelCoeffField
        );
    } // initFields

    @Override
    public String getCode (AngPrdCycOpr entity) {
        return entity.getPrdCyc ().getPrdCycCod () + delimiter +
            entity.getPrdCyc ().getItm ().getItmCod () + delimiter +
            entity.getPrdCyc ().getPrdCycTypUid () + delimiter +
            entity.getOprNum () + delimiter +
            (entity.getItmVrn () == null ? "@NULL" : entity.getItmVrn ().getItm ().getItmCod () + delimiter + entity.getItmVrn ().getVrn ().getCat ().getCatCod () + delimiter + entity.getItmVrn ().getVrn ().getVrnCod ());
    } // getCode

    @Override protected String getTempTableName () { return "CicliFaseLavoro"; } // getTempTableName 
    @Override protected Supplier<AngPrdCycOpr> getNewInstance () { return AngPrdCycOpr::newInstance; } // getNewInstance
    @Override protected Class<AngPrdCycOpr> getModelClass () { return AngPrdCycOpr.class; } // getModelClass
    @Override public TableDao<AngPrdCycOpr> getDao () { return AngPrdCycOprDao.get (); } 
    @Override public String getAlias () { return SAlias.AngPrdCycOpr; } // getAlias 
    @Override public Long getTableUid () { return AngPrdCycOprMeta.get ().tableUid (); } // getTableUid 
    @Override public ISField getUidField () { return AngPrdCycOprFields.Uid; } // getUidField 
    @Override public String getVrtStoreKey () { return ApsTmpStore.ROUTE_ROWS_KEY; } // getVrtStoreKey
    
    @Override
    public List<AngPrdCycOpr> getActiveList (ApsImportContext ctx) throws SException {
        return SysItmLogic.get ().getActiveWithJoinAngPrdCycOpr (true, ctx.getContext (), ctx.getWCon ());
    } // getActiveList
    
    @Override
    public SBatchResult<AngPrdCycOpr> insertBatch (List<AngPrdCycOpr> entities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        Map<Long, AngPrdCyc> cycleByUid = new HashMap<> ();
        Map<Long, AngItmVrn> itemVariantByUid = new HashMap<> ();
        for (AngPrdCycOpr entity : entities) {
            cycleByUid.put (entity.getPrdCycUid (), entity.getPrdCyc ());
            if (entity.getItmVrnUid () != null) {
                itemVariantByUid.put (entity.getItmVrnUid (), entity.getItmVrn ());
            } // if
        } // for
        return SysItmLogic.get ().insertBatchAngPrdCycOpr (entities, cycleByUid, itemVariantByUid, options, context, wCon);
    } // insertBatch

    @Override
    public SBatchResult<AngPrdCycOpr> updateBatch (List<AngPrdCycOpr> entities, List<AngPrdCycOpr> oldEntities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        Map<Long, AngPrdCyc> cycleByUid = new HashMap<> ();
        Map<Long, AngItmVrn> itemVariantByUid = new HashMap<> ();
        for (AngPrdCycOpr entity : entities) {
            cycleByUid.put (entity.getPrdCycUid (), entity.getPrdCyc ());
            if (entity.getItmVrnUid () != null) {
                itemVariantByUid.put (entity.getItmVrnUid (), entity.getItmVrn ());
            } // if
        } // for
        Map<Long, AngPrdCycOpr> entityByUid = new HashMap<> ();
        for (AngPrdCycOpr oldEntity : oldEntities) {
            entityByUid.put (oldEntity.getUid (), oldEntity);
        } // for
        return SysItmLogic.get ().updateAllBatchAngPrdCycOpr (entities, entityByUid, cycleByUid, itemVariantByUid, options, context, wCon);
    } // updateBatch

    @Override
    public AngPrdCycOpr getInsert (AngPrdCycOpr entity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().insertAngPrdCycOpr (entity, false, false, context, wCon);
    } // getInsert

    @Override
    public AngPrdCycOpr getUpdate (AngPrdCycOpr entity, AngPrdCycOpr oldEntity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().updateAllAngPrdCycOpr (entity, false, false, context, wCon);
    } // getUpdate

    @Override
    public StoreSetter<AngPrdCycOpr, ApsImportContext> getStoreSetter () {
        return (upsertedEntities, removeCandidates, ctx) -> {
            ctx.getStore ().setRouteRows (upsertedEntities);
            ctx.getDeletionStore ().setToRemoveAngPrdCycOpr (removeCandidates);
        };
    } // getStoreSetter

    @Override
    public void align (AngPrdCycOpr entity, ApsImportContext ctx, boolean insert) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        String prdCycCod = AngPrdCycTmp.get ().getCode (entity.getPrdCyc ());
        Long prdCycUid = store.getRoutes ().get (prdCycCod);
        if (prdCycUid == null) {
            List<Object> values = Arrays.asList (prdCycCod, insert ? "inserimento" : "modifica", entity.getOprNum ());
            FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpPrdCycUidCycOpr, values, ApsTmpTableKey.AngPrdCycOpr.getKeyLabel (), insert, store, context, wCon);

            AngPrdCyc dummyAngPrdCyc = AngPrdCycTmp.get ().getDummy (ctx);
            entity.setPrdCycUid (dummyAngPrdCyc.getUid ());
            entity.setPrdCyc (dummyAngPrdCyc);
        } else {
            entity.setPrdCycUid (prdCycUid);
            entity.getPrdCyc ().setUid (prdCycUid);
        } // if - else
        
        
        // TODO Ricontrollare
        if (entity.getItmVrn () != null) {
            String itmVrnCod = AngItmVrnTmp.get ().getCode (entity.getItmVrn ());
            Long itmVrnUid = store.getItemVariants ().get (itmVrnCod);
            if (itmVrnUid == null) {
                List<Object> values = Arrays.asList (itmVrnCod, insert ? "inserimento" : "modifica", entity.getOprNum ());
                FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpItmVrnUidCycOpr, values, ApsTmpTableKey.AngPrdCycOpr.getKeyLabel (), insert, store, context, wCon);

                AngItmVrn dummyAngItmVrn = AngItmVrnTmp.get ().getDummy (ctx);
                entity.setItmVrnUid (dummyAngItmVrn.getUid ());
                entity.setItmVrn (dummyAngItmVrn);
            } else {
                entity.setItmVrnUid (itmVrnUid);
                entity.getItmVrn().setUid (itmVrnUid);
            } // if - else
        } else {
            entity.setItmVrnUid (null);
            entity.setItmVrn (null);
        } // if - else

        
        // TODO Ricontrollare all'introduzione dei PlnGrp nei test.
        Long plnGrpUid = null;
        if (entity.getPlnGrp () != null) {
            String plnGrpCod = AngGrpTmp.get ().getCode (entity.getPlnGrp ());
            plnGrpUid = store.getGroups ().get (plnGrpCod);
            if (plnGrpUid == null) {
                List<Object> values = Arrays.asList (plnGrpCod, insert ? "inserimento" : "modifica", entity.getOprNum ());
                FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpPlnGrpUidCycOpr, values, ApsTmpTableKey.AngPrdCycOpr.getKeyLabel (), insert, store, context, wCon);
            } else {
                entity.setPlnGrp (null);
            } // if - else
        } // if
        entity.setPlnGrpUid (plnGrpUid);
    } // align

    @Override
    public AngPrdCycOpr getDummy (ApsImportContext ctx) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ApsTmpStoreDeletion deletionStore = ctx.getDeletionStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        AngPrdCycOpr dummy = store.getDummyAngPrdCycOpr ();

        if (dummy == null) {
            AngPrdCycOpr tmpDummy = AngPrdCycOpr.newInstance ();
            tmpDummy.setOprNum ("@dmy");
            tmpDummy.setOprDsc ("@dummy");
            AngPrdCyc dummyAngPrdCyc = AngPrdCycTmp.get ().getDummy (ctx);
            tmpDummy.setPrdCyc (dummyAngPrdCyc);
            tmpDummy.setPrdCycUid (dummyAngPrdCyc.getUid ());

            AngPrdCycOpr removingDummy = deletionStore.removeAngPrdCycOpr (getCode (tmpDummy));
            dummy = removingDummy == null ? AngPrdCycOprDao.get ().insert (tmpDummy, false, context, wCon) : removingDummy;
            
            dummy.setPrdCyc (dummyAngPrdCyc);
            store.setDummyAngPrdCycOpr (dummy);
            String code = getCode (dummy);
            long uid = dummy.getUid ();
            store.addRouteRow (code, uid);
        } // if

        return dummy;
    } // getDummy

} // AngPrdCycOprTmp
