package net.synergy2.logic.aps.tmp.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import net.synergy2.base.types.SSingletonHolder;
import net.synergy2.base.util.datas.NumberUtil;
import net.synergy2.base.util.datas.NumberUtil.Operators;
import net.synergy2.base.util.datas.StringUtil;
import net.synergy2.db.base.SAlias;
import net.synergy2.db.base.batch.SBatchOptions;
import net.synergy2.db.base.batch.SBatchResult;
import net.synergy2.db.base.dao.TableDao;
import net.synergy2.db.sys.itm.AngBom;
import net.synergy2.db.sys.itm.AngBomDao;
import net.synergy2.db.sys.itm.AngBomImpl.AngBomFields;
import net.synergy2.db.sys.itm.AngBomMeta;
import net.synergy2.db.sys.itm.AngItm;
import net.synergy2.db.sys.itm.AngItmVrn;
import net.synergy2.db.sys.itm.AngPrdVrn;
import net.synergy2.db.sys.itm.AngPrdVrnCat;
import net.synergy2.db.sys.itm.abstracts._MAngBom;
import net.synergy2.logic.aps.tmp.ApsImportContext;
import net.synergy2.logic.aps.tmp.ApsTmpMessage;
import net.synergy2.logic.aps.tmp.ApsTmpStoreDeletion;
import net.synergy2.logic.aps.tmp.common.ApsTmpStore;
import net.synergy2.logic.aps.tmp.common.ApsTmpTableKey;
import net.synergy2.logic.fct.tmp.AGenericTmp;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import net.synergy2.logic.fct.tmp.common.TmpField;
import net.synergy2.logic.sys.itm.SysItmLogic;

public class AngBomTmp extends AGenericTmp<AngBom, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> {
    
    private AngBomTmp () {}
    private static class Singleton {
        private static final SSingletonHolder<AngBomTmp> INSTANCE = new SSingletonHolder<> (AngBomTmp.class);
    } // Singleton
    public static AngBomTmp get () { return Singleton.INSTANCE.get (); }
    
    @Override
    protected List<TmpField<?, AngBom>> initFields () {
        var prdItmField = new TmpField<AngItm, AngBom> ()
            .setValueGetter (_MAngBom::getPrdItm)
            .setValueSetter ((value, entity) -> entity.setPrdItm (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setItmUid (incomingEntity.getItmUid ()))
            .setComparator ((value1, value2) -> value1 != null && value2 != null && value1.getUid () == value2.getUid () && !value2.getLogDel ())
            .setComparatorByCode ((value1, value2) -> value1 != null && value2 != null && value1.getItmCod ().equals (value2.getItmCod ()) && !value2.getLogDel ())
            .setSqlFieldGetter (() -> "CodiceArticoloProdotto")
            .setSqlGetter ((rs, field) -> {
                AngItm item = AngItm.newInstance ();
                item.setItmCod (rs.getString (field));
                return item;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngItm item = entity.getPrdItm ();
                stm.setString (index, item.getItmCod ());
            });
        
        var itmField = new TmpField<AngItm, AngBom> ()
            .setValueGetter (_MAngBom::getItm)
            .setValueSetter ((value, entity) -> entity.setItm (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setItmUid (incomingEntity.getItmUid ()))
            .setComparator ((value1, value2) -> value1 != null && value2 != null && value1.getUid () == value2.getUid () && !value2.getLogDel ())
            .setComparatorByCode ((value1, value2) -> value1 != null && value2 != null && value1.getItmCod ().equals (value2.getItmCod ()) && !value2.getLogDel ())
            .setSqlFieldGetter (() -> "CodiceArticoloComponente")
            .setSqlGetter ((rs, field) -> {
                AngItm item = AngItm.newInstance ();
                item.setItmCod (rs.getString (field));
                return item;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngItm item = entity.getItm ();
                stm.setString (index, item.getItmCod ());
            });
        
        var vrnField = new TmpField<AngItmVrn, AngBom> ()
            .setValueGetter (_MAngBom::getItmVrn)
            .setValueSetter ((value, entity) -> entity.setItmVrn (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setItmVrnUid (incomingEntity.getItmVrnUid ()))
            .setComparator ((value1, value2) -> ((value1 == null && value2 == null) ||
                (
                    value1 != null && value2 != null &&
                    value1.getUid () == value2.getUid () &&
                    !value2.getLogDel ())
                )
            )
            .setComparatorByCode ((value1, value2) -> ((value1 == null && value2 == null) ||
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
            .setSqlSetter ((stm, index, entity) -> {
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
        
        
        var mtcField = new TmpField<String, AngBom> ()
            .setValueGetter (_MAngBom::getMtc)
            .setValueSetter ((value, entity) -> entity.setMtc (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "CodiceAssociazione")
            .setSqlGetter ((rs, field) -> {
                String value = rs.getString (field);
                if (value != null && !value.equals ("@NULL")) {
                    return value;
                } // if
                return null;
            })
            .setSqlSetter ((stm, index, entity) -> {
                String mtc = entity.getMtc ();
                stm.setString (index, mtc != null ? mtc : "@NULL");
            });
        
        var ignMisField = new TmpField<Boolean, AngBom> ()
            .setValueGetter (_MAngBom::getIgnMis)
            .setValueSetter ((value, entity) -> entity.setIgnMis (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "IgnoraSeMancante")
            .setSqlGetter (ResultSet::getBoolean)
            .setSqlSetter ((stm, index, entity) -> stm.setInt (index, entity.getIgnMis () ? 1 : 0));

        var bomQtyField = new TmpField<BigDecimal, AngBom> ()
            .setValueGetter (_MAngBom::getBomQty)
            .setValueSetter ((value, entity) -> entity.setBomQty (value))
            .setComparator ((value1, value2) -> NumberUtil.compareBigDecimal (value1, Operators.EQUAL, value2, true))
            .setSqlFieldGetter (() -> "QuantitaRichiesta")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getBomQty ()));
        
        var fixQtyField = new TmpField<Boolean, AngBom> ()
            .setValueGetter (_MAngBom::getFixQty)
            .setValueSetter ((value, entity) -> entity.setFixQty (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "QuantitaFissa")
            .setSqlGetter (ResultSet::getBoolean)
            .setSqlSetter ((stm, index, entity) -> stm.setInt (index, entity.getFixQty () ? 1 : 0));
        
        return Arrays.asList (
            prdItmField,
            itmField,
            vrnField,
            mtcField,
            ignMisField,
            bomQtyField,
            fixQtyField
        );
    } // initFields

    @Override
    public String getCode (AngBom entity) {
        return entity.getPrdItm ().getItmCod () + delimiter + 
               entity.getItm ().getItmCod () + delimiter +
               (entity.getItmVrn () == null ? 
                   "@NULL" : 
                   entity.getItmVrn ().getItm ().getItmCod () + delimiter + entity.getItmVrn ().getVrn ().getCat ().getCatCod () + delimiter + entity.getItmVrn ().getVrn ().getVrnCod ()
               ) + delimiter + 
               (entity.getMtc () == null ? "@NULL" : entity.getMtc ())
       ;
    } // getCode
    @Override protected String getTempTableName () { return "DistintaBase"; } // getTempTableName
    @Override protected Supplier<AngBom> getNewInstance () { return AngBom::newInstance; } // getNewInstance
    @Override protected Class<AngBom> getModelClass () { return AngBom.class; } // getModelClass
    @Override public TableDao<AngBom> getDao () { return AngBomDao.get (); }
    @Override public String getAlias () { return SAlias.AngBom; } // getAlias
    @Override public Long getTableUid () { return AngBomMeta.get ().tableUid (); } // getTableUid
    @Override public ISField getUidField () { return AngBomFields.Uid; } // getUidField
    @Override public String getVrtStoreKey () { return ApsTmpStore.BOMS_KEY; } // getVrtStoreKey

    @Override public List<AngBom> getActiveList (ApsImportContext ctx) throws SException {
        return SysItmLogic.get ().getActiveWithJoinAngBom (true, true, ctx.getContext (), ctx.getWCon ());
    } // getActiveList
    
    @Override
    public SBatchResult<AngBom> insertBatch (List<AngBom> entities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        Map<Long, AngItm> itemByUid = new HashMap<> ();
        Map<Long, AngItmVrn> itemVariantByUid = new HashMap<> (); // TODO si potrebbe avere nello store una mappa che dato l'uid ritorna l'oggetto effettivo, però peserebbe in ram.
        Map<Long, List<AngBom>> bomsByItemUid = new HashMap<> ();
        for (AngBom entity : entities) {
            itemByUid.put (entity.getPrdItmUid (), entity.getPrdItm ());
            itemByUid.put (entity.getItmUid (), entity.getItm ());
        } // for
        return SysItmLogic.get ().insertBatchAngBom (entities, itemByUid, itemVariantByUid, bomsByItemUid, true, options, context, wCon);
    } // insertBatch

    @Override
    public SBatchResult<AngBom> updateBatch (List<AngBom> entities, List<AngBom> oldEntities, SBatchOptions options, ApsTmpStore store, ExecutionContext context, SWriteCon wCon) throws SException {
        Map<Long, AngBom> entityByUid = new HashMap<> ();
        Map<Long, AngItm> itemByUid = new HashMap<> ();
        Map<Long, AngItmVrn> itemVariantByUid = new HashMap<> (); // TODO si potrebbe avere nello store una mappa che dato l'uid ritorna l'oggetto effettivo, però peserebbe in ram.
        Map<Long, List<AngBom>> bomsByItemUid = new HashMap<> ();
        for (AngBom entity : entities) {
            itemByUid.put (entity.getPrdItmUid (), entity.getPrdItm ());
            itemByUid.put (entity.getItmUid (), entity.getItm ());
        } // for
        for (AngBom entity : entities) {
            bomsByItemUid.computeIfAbsent (entity.getPrdItmUid (), k -> new ArrayList<> ()).add (entityByUid.get (entity.getUid ()));
        } // for
        for (AngBom oldEntity : oldEntities) {
            entityByUid.put (oldEntity.getUid (), oldEntity);
        } // for
        return SysItmLogic.get ().updateAllBatchAngBom (entities, entityByUid, itemByUid, itemVariantByUid, bomsByItemUid, true, options, context, wCon);
    } // updateBatch
    
    @Override
    public AngBom getInsert (AngBom entity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().insertAngBom (entity, false, context, wCon);
    } // getInsert

    @Override
    public AngBom getUpdate (AngBom entity, AngBom oldEntity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().updateAllAngBom (entity, false, context, wCon);
    } // getUpdate

    @Override
    public StoreSetter<AngBom, ApsImportContext> getStoreSetter () {
        return (upsertedEntities, removeCandidates, ctx) -> {
            ctx.getStore ().setBoms (upsertedEntities);
            ctx.getDeletionStore ().setToRemoveAngBom (removeCandidates);
        };
    } // getStoreSetter

    @Override
    public void align (AngBom entity, ApsImportContext ctx, boolean insert) throws SException {
        ApsTmpStore store = ctx.getStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        String prdItmCod = AngApsItmTmp.get ().getCode (entity.getPrdItm ());
        Long prdItmUid = store.getItems ().get (prdItmCod);
        if (prdItmUid == null) {
            List<Object> values = Arrays.asList (prdItmCod, insert ? "inserimento" : "modifica", entity.getUid ());
            FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpPrdItmUidBom, values, ApsTmpTableKey.AngBom.getKeyLabel (), insert, store, context, wCon);

            AngItm dummyAngItm = AngApsItmTmp.get ().getDummy (ctx);
            entity.setPrdItmUid (dummyAngItm.getUid ());
            entity.setPrdItm (dummyAngItm);
        } else {
            entity.setPrdItmUid (prdItmUid);
            entity.getPrdItm ().setUid(prdItmUid);
        } // if - else

        String itmCod = AngApsItmTmp.get ().getCode (entity.getItm ());
        Long itmUid = store.getItems ().get (itmCod);
        if (itmUid == null) {
            List<Object> values = Arrays.asList (itmCod, insert ? "inserimento" : "modifica", entity.getUid ());
            FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpItmUidBom, values, ApsTmpTableKey.AngBom.getKeyLabel (), insert, store, context, wCon);

            AngItm dummyAngItm = AngApsItmTmp.get ().getDummy (ctx);
            entity.setItmUid (dummyAngItm.getUid ());
            entity.setItm (dummyAngItm);
        } else {
            entity.setItmUid (itmUid);
            entity.getItm ().setUid(itmUid);
        } // if - else
        
        if (entity.getItmVrn () != null) {
            var itmVrnCod = AngItmVrnTmp.get ().getCode (entity.getItmVrn ());
            Long itmVrnUid = store.getItemVariants ().get (itmVrnCod);
            if (itmVrnUid == null) {
                List<Object> values = Arrays.asList (itmVrnCod, insert ? "inserimento" : "modifica", entity.getUid ());
                FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpItmVrnUidBom, values, ApsTmpTableKey.AngBom.getKeyLabel (), insert, store, context, wCon);

                AngItmVrn dummyAngItmVrn = AngItmVrnTmp.get ().getDummy (ctx);
                entity.setItmVrnUid (dummyAngItmVrn.getUid ());
                entity.setItmVrn (dummyAngItmVrn);
            } else {
                entity.setItmVrnUid (itmVrnUid);
            } // if - else
        } else {
            entity.setItmVrnUid (null);
        } // if - else
    } // align
    
} // AngBomTmp
