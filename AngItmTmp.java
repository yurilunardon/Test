package net.synergy2.logic.fct.tmp.impl;

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
import net.synergy2.base.util.datas.NumberUtil;
import net.synergy2.base.util.datas.NumberUtil.Operators;
import net.synergy2.base.util.datas.StringUtil;
import net.synergy2.db.base.SAlias;
import net.synergy2.db.base.batch.SBatchOptions;
import net.synergy2.db.base.batch.SBatchResult;
import net.synergy2.db.base.dao.TableDao;
import net.synergy2.db.sys.bas.AngSup;
import net.synergy2.db.sys.itm.AngItm;
import net.synergy2.db.sys.itm.AngItmDao;
import net.synergy2.db.sys.itm.AngItmImpl.AngItmFields;
import net.synergy2.db.sys.itm.AngItmMeta;
import net.synergy2.db.sys.itm.AngUniMea;
import net.synergy2.db.sys.itm.abstracts._MAngItm;
import net.synergy2.logic.fct.tmp.AGenericTmp;
import net.synergy2.logic.fct.tmp.FctImportContext;
import net.synergy2.logic.fct.tmp.FctTmpMessage;
import net.synergy2.logic.fct.tmp.FctTmpStoreDeletion;
import net.synergy2.logic.fct.tmp.common.FctTmpStore;
import net.synergy2.logic.fct.tmp.common.FctTmpTableKey;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import net.synergy2.logic.fct.tmp.common.TmpField;
import net.synergy2.logic.sys.itm.SysItmLogic;

public class AngItmTmp<S extends FctTmpStore, D extends FctTmpStoreDeletion, C extends FctImportContext<S, D>> extends AGenericTmp<AngItm, S, D, C> {

    @Override
    protected List<TmpField<?, AngItm>> initFields () {
        var codField = new TmpField<String, AngItm> ()
            .setCode (true)
            .setValueGetter (_MAngItm::getItmCod)
            .setValueSetter ((value, entity) -> entity.setItmCod (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Codice")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getItmCod ()));

        var dscField = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getItmDsc)
            .setValueSetter ((value, entity) -> entity.setItmDsc (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Descrizione")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getItmDsc ()));

        var dscLngField = new TmpField<String, AngItm> ()
            .setValueGetter ((entity) -> entity.getItmLngDsc ())
            .setValueSetter ((value, entity) -> entity.setItmLngDsc (value))
            .setComparator ((value1, value2) -> StringUtil.areEquals (value1, value2))
            .setSqlFieldGetter (() -> "Descrizione")
            .setSqlGetter ((rs, field) -> rs.getString (field));
        
        var icoField = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getItmIco)
            .setValueSetter ((value, entity) -> entity.setItmIco (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Icona")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getItmIco ()));

        var clrField = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getItmClr)
            .setValueSetter ((value, entity) -> entity.setItmClr (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Colore")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getItmClr ()));

        var ignMisField = new TmpField<Boolean, AngItm> ()
            .setValueGetter (_MAngItm::getIgnMis)
            .setValueSetter ((value, entity) -> entity.setIgnMis (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "IgnoraMancante")
            .setSqlGetter (ResultSet::getBoolean)
            .setSqlSetter ((stm, index, entity) -> stm.setInt (index, entity.getIgnMis () ? 1 : 0));

        var dlvBufDayField = new TmpField<BigDecimal, AngItm> ()
            .setValueGetter (_MAngItm::getDlvBufDay)
            .setValueSetter ((value, entity) -> entity.setDlvBufDay (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "BufferDiConsegna")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getDlvBufDay ()));

        var minStkLvlField = new TmpField<BigDecimal, AngItm> ()
            .setValueGetter (_MAngItm::getMinStkLvl)
            .setValueSetter ((value, entity) -> entity.setMinStkLvl (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "ScortaDiSicurezza")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getMinStkLvl ()));

        var minBtcField = new TmpField<BigDecimal, AngItm> ()
            .setValueGetter (_MAngItm::getMinBtc)
            .setValueSetter ((value, entity) -> entity.setMinBtc (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "LottoMinimo")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getMinBtc ()));

        var maxBtcField = new TmpField<BigDecimal, AngItm> ()
            .setValueGetter (_MAngItm::getMaxBtc)
            .setValueSetter ((value, entity) -> entity.setMaxBtc (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "LottoMassimo")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getMaxBtc ()));

        var supOnlField = new TmpField<Boolean, AngItm> ()
            .setValueGetter (_MAngItm::getSupOnl)
            .setValueSetter ((value, entity) -> entity.setSupOnl (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "SoloOrdiniFornitore")
            .setSqlGetter (ResultSet::getBoolean)
            .setSqlSetter ((stm, index, entity) -> stm.setInt (index, entity.getSupOnl () ? 1 : 0));

        var itmCstField = new TmpField<BigDecimal, AngItm> ()
            .setValueGetter (_MAngItm::getItmCst)
            .setValueSetter ((value, entity) -> entity.setItmCst (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "Costo")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getItmCst ())); 

        var itmAcrField = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getItmAcr)
            .setValueSetter ((value, entity) -> entity.setItmAcr (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Sigla")
            .setSqlGetter (ResultSet::getString)
            .setSqlSetter ((stm, index, entity) -> stm.setString (index, entity.getItmAcr ()));

        var cnvRatField = new TmpField<BigDecimal, AngItm> ()
            .setValueGetter (_MAngItm::getCnvRat)
            .setValueSetter ((value, entity) -> entity.setCnvRat (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "CoefficienteConversione")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getCnvRat ())); 

        var minBomQtyField = new TmpField<BigDecimal, AngItm> ()
            .setValueGetter (_MAngItm::getMinBomQty)
            .setValueSetter ((value, entity) -> entity.setMinBomQty (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "LivelloMinimoBom")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getMinBomQty ())); 

        var srtQtyField = new TmpField<BigDecimal, AngItm> ()
            .setValueGetter (_MAngItm::getSrtQty)
            .setValueSetter ((value, entity) -> entity.setSrtQty (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "PuntoDiRiordinamento")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getSrtQty ())); 

        var advDayField = new TmpField<BigDecimal, AngItm> ()
            .setValueGetter (_MAngItm::getAdvDay)
            .setValueSetter ((value, entity) -> entity.setAdvDay (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "GiorniDiAnticipo")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getAdvDay ()));   

        var covDayField = new TmpField<BigDecimal, AngItm> ()
            .setValueGetter (_MAngItm::getCovDay)
            .setValueSetter ((value, entity) -> entity.setCovDay (value))
            .setComparator (NumberUtil::equalsBigDecimal)
            .setSqlFieldGetter (() -> "GiorniDiCopertura")
            .setSqlGetter (ResultSet::getBigDecimal)
            .setSqlSetter ((stm, index, entity) -> stm.setBigDecimal (index, entity.getCovDay ()));

        var jobPlnField = new TmpField<Boolean, AngItm> ()
            .setValueGetter (_MAngItm::getJobPln)
            .setValueSetter ((value, entity) -> entity.setJobPln (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "PianificarePerCommessa")
            .setSqlGetter (ResultSet::getBoolean)
            .setSqlSetter ((stm, index, entity) -> stm.setInt (index, entity.getJobPln () ? 1 : 0));

        var mulBatField = new TmpField<Boolean, AngItm> ()
            .setValueGetter (_MAngItm::getMulBat)
            .setValueSetter ((value, entity) -> entity.setMulBat (value))
            .setComparator (Objects::equals)
            .setSqlFieldGetter (() -> "LottoMultiplo")
            .setSqlGetter (ResultSet::getBoolean)
            .setSqlSetter ((stm, index, entity) -> stm.setInt (index, entity.getMulBat () ? 1 : 0));

        var angSupField = new TmpField<AngSup, AngItm> ()
            .setValueGetter (_MAngItm::getSup)
            .setValueSetter ((value, entity) -> entity.setSup (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setSupUid (incomingEntity.getSupUid ()))
            .setComparator ((value1, value2) -> ((value1 == null && value2 == null) ||
                (value1 != null && value2 != null && value1.getUid () == value2.getUid () && !value2.getLogDel ())))
            .setComparatorByCode ((value1, value2) -> ((value1 == null && value2 == null) ||
                (value1 != null && value2 != null && value1.getSupCod ().equals (value2.getSupCod ()) && !value2.getLogDel ())))
            .setSqlFieldGetter (() -> "CodiceFornitore")
            .setSqlGetter ((rs, field) -> {
                String supCod = rs.getString (field);
                if (!StringUtil.isEmpty (supCod)) {
                    AngSup sup = AngSup.newInstance ();
                    sup.setSupCod (supCod);
                    return sup;
                } // if
                return null;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngSup sup = entity.getSup ();
                stm.setString (index, (sup != null ? sup.getSupCod () : null));
            });
            

        var angUniMeaAltField = new TmpField<AngUniMea, AngItm> ()
            .setValueGetter (_MAngItm::getUniMeaAlt)
            .setValueSetter ((value, entity) -> entity.setUniMeaAlt (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setUniMeaAltUid (incomingEntity.getUniMeaAltUid ()))
            .setComparator ((value1, value2) -> ((value1 == null && value2 == null) ||
                (value1 != null && value2 != null && value1.getUid () == value2.getUid ())))
            .setComparatorByCode ((value1, value2) -> ((value1 == null && value2 == null) ||
                (value1 != null && value2 != null && value1.getUniMeaCod ().equals (value2.getUniMeaCod ()))))
            .setSqlFieldGetter (() -> "CodiceUnitaDiMisuraAlternativo")
            .setSqlGetter ((rs, field) -> {
                String uniMeaCod = rs.getString (field);
                if (!StringUtil.isEmpty (uniMeaCod)) {
                    AngUniMea uniMea = AngUniMea.newInstance ();
                    uniMea.setUniMeaCod (uniMeaCod);
                    return uniMea;
                } // if
                return null;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngUniMea uniMeaAlt = entity.getUniMeaAlt ();
                stm.setString (index, (uniMeaAlt != null ? uniMeaAlt.getUniMeaCod () : null));
            });

        var angUniMeaField = new TmpField<AngUniMea, AngItm> ()
            .setValueGetter (_MAngItm::getUniMea)
            .setValueSetter ((value, entity) -> entity.setUniMea (value))
            .setReferenceMerger ((entity, incomingEntity) -> entity.setUniMeaUid (incomingEntity.getUniMeaUid ()))
            .setComparator ((value1, value2) -> ((value1 == null && value2 == null) ||
                (value1 != null && value2 != null && value1.getUid () == value2.getUid ())))
            .setComparatorByCode ((value1, value2) -> ((value1 == null && value2 == null) ||
                (value1 != null && value2 != null && value1.getUniMeaCod ().equals (value2.getUniMeaCod ()))))
            .setSqlFieldGetter (() -> "CodiceUnitaDiMisura")
            .setSqlGetter ((rs, field) -> {
                String uniMeaCod = rs.getString (field);
                if (!StringUtil.isEmpty (uniMeaCod)) {
                    AngUniMea uniMea = AngUniMea.newInstance ();
                    uniMea.setUniMeaCod (uniMeaCod);
                    return uniMea;
                } // if
                return null;
            })
            .setSqlSetter ((stm, index, entity) -> {
                AngUniMea uniMea = entity.getUniMea ();
                stm.setString (index, (uniMea != null ? uniMea.getUniMeaCod () : null));
            });

        var str001Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getStr001)
            .setValueSetter ((value, entity) -> entity.setStr001 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Str001")
            .setSqlGetter (ResultSet::getString);

        var str002Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getStr002)
            .setValueSetter ((value, entity) -> entity.setStr002 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Str002")
            .setSqlGetter (ResultSet::getString);

        var str003Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getStr003)
            .setValueSetter ((value, entity) -> entity.setStr003 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Str003")
            .setSqlGetter (ResultSet::getString);

        var str004Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getStr004)
            .setValueSetter ((value, entity) -> entity.setStr004 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Str004")
            .setSqlGetter (ResultSet::getString);

        var str005Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getStr005)
            .setValueSetter ((value, entity) -> entity.setStr005 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "Str005")
            .setSqlGetter (ResultSet::getString);

        var dec001Field = new TmpField<BigDecimal, AngItm> ()
            .setValueGetter (_MAngItm::getDec001)
            .setValueSetter ((value, entity) -> entity.setDec001 (value))
            .setComparator ((value1, value2) -> NumberUtil.compareBigDecimal (value1, Operators.EQUAL, value2, true))
            .setSqlFieldGetter (() -> "Dec001")
            .setSqlGetter (ResultSet::getBigDecimal);

        var dec002Field = new TmpField<BigDecimal, AngItm> ()
            .setValueGetter (_MAngItm::getDec002)
            .setValueSetter ((value, entity) -> entity.setDec002 (value))
            .setComparator ((value1, value2) -> NumberUtil.compareBigDecimal (value1, Operators.EQUAL, value2, true))
            .setSqlFieldGetter (() -> "Dec002")
            .setSqlGetter (ResultSet::getBigDecimal);

        var clsFre001Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre001)
            .setValueSetter ((value, entity) -> entity.setClsFre001 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre001")
            .setSqlGetter (ResultSet::getString);

        var clsFre002Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre002)
            .setValueSetter ((value, entity) -> entity.setClsFre002 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre002")
            .setSqlGetter (ResultSet::getString);

        var clsFre003Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre003)
            .setValueSetter ((value, entity) -> entity.setClsFre003 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre003")
            .setSqlGetter (ResultSet::getString);

        var clsFre004Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre004)
            .setValueSetter ((value, entity) -> entity.setClsFre004 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre004")
            .setSqlGetter (ResultSet::getString);

        var clsFre005Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre005)
            .setValueSetter ((value, entity) -> entity.setClsFre005 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre005")
            .setSqlGetter (ResultSet::getString);

        var clsFre006Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre006)
            .setValueSetter ((value, entity) -> entity.setClsFre006 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre006")
            .setSqlGetter (ResultSet::getString);

        var clsFre007Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre007)
            .setValueSetter ((value, entity) -> entity.setClsFre007 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre007")
            .setSqlGetter (ResultSet::getString);

        var clsFre008Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre008)
            .setValueSetter ((value, entity) -> entity.setClsFre008 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre008")
            .setSqlGetter (ResultSet::getString);

        var clsFre009Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre009)
            .setValueSetter ((value, entity) -> entity.setClsFre009 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre009")
            .setSqlGetter (ResultSet::getString);

        var clsFre010Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre010)
            .setValueSetter ((value, entity) -> entity.setClsFre010 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre010")
            .setSqlGetter (ResultSet::getString);

        var clsFre011Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre011)
            .setValueSetter ((value, entity) -> entity.setClsFre011 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre011")
            .setSqlGetter (ResultSet::getString);

        var clsFre012Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre012)
            .setValueSetter ((value, entity) -> entity.setClsFre012 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre012")
            .setSqlGetter (ResultSet::getString);

        var clsFre013Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre013)
            .setValueSetter ((value, entity) -> entity.setClsFre013 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre013")
            .setSqlGetter (ResultSet::getString);

        var clsFre014Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre014)
            .setValueSetter ((value, entity) -> entity.setClsFre014 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre014")
            .setSqlGetter (ResultSet::getString);

        var clsFre015Field = new TmpField<String, AngItm> ()
            .setValueGetter (_MAngItm::getClsFre015)
            .setValueSetter ((value, entity) -> entity.setClsFre015 (value))
            .setComparator (StringUtil::areEquals)
            .setSqlFieldGetter (() -> "ClsFre015")
            .setSqlGetter (ResultSet::getString);

        var toReturn = new ArrayList<> (Arrays.asList (
            codField,
            dscField,
            dscLngField,
            icoField,
            clrField,
            ignMisField,
            dlvBufDayField,
            minStkLvlField,
            minBtcField,
            maxBtcField,
            supOnlField,
            itmCstField,
            itmAcrField,
            cnvRatField,
            minBomQtyField,
            srtQtyField,
            advDayField,
            covDayField,
            jobPlnField,
            mulBatField,
            angSupField,
            angUniMeaAltField,
            angUniMeaField,
            str001Field,
            str002Field,
            str003Field,
            str004Field,
            str005Field,
            dec001Field,
            dec002Field,
            clsFre001Field,
            clsFre002Field,
            clsFre003Field,
            clsFre004Field,
            clsFre005Field,
            clsFre006Field,
            clsFre007Field,
            clsFre008Field,
            clsFre009Field,
            clsFre010Field,
            clsFre011Field,
            clsFre012Field,
            clsFre013Field,
            clsFre014Field,
            clsFre015Field
        ));
        return toReturn;
    } // initFields

    @Override
    public List<TmpField<?, AngItm>> getUserFields (C ctx) throws SException {
        return getUserFields (ctx.getContext (), ctx.getWCon ());
    } // getUserFields
    
    @Override public boolean hasTempUserFields () { return true; }

    @Override public String getCode (AngItm entity) { return entity.getItmCod (); } // getCode 
    @Override protected String getTempTableName () { return "Articoli"; } // getTempTableName 
    @Override protected Supplier<AngItm> getNewInstance () { return AngItm::newInstance; } // getNewInstance
    @Override protected Class<AngItm> getModelClass () { return AngItm.class; } // getModelClass 
    @Override public TableDao<AngItm> getDao () { return AngItmDao.get (); }
    @Override public String getAlias () { return SAlias.AngItm; } // getAlias 
    @Override public Long getTableUid () { return AngItmMeta.get ().tableUid (); } // getTableUid 
    @Override public ISField getUidField () { return AngItmFields.Uid; } // getUidField 
    @Override public String getVrtStoreKey () { return FctTmpStore.ITEMS_KEY; } // getVrtStoreKey

    @Override
    public List<AngItm> getActiveList (C ctx) throws SException {
        return SysItmLogic.get ().getActiveWithJoinAngItm (true, true, true, ctx.getContext (), ctx.getWCon ());
    } // getActiveList

    @Override
    public SBatchResult<AngItm> insertBatch (List<AngItm> entities, SBatchOptions options, S store, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().insertBatchAngItm (entities, options, context, wCon);
    } // insertBatch

    @Override
    public SBatchResult<AngItm> updateBatch (List<AngItm> entities, List<AngItm> oldEntities, SBatchOptions options, S store, ExecutionContext context, SWriteCon wCon) throws SException {
        Map<Long, AngItm> entityByUid = new HashMap<> ();
        for (AngItm entity : entities) {
            entityByUid.put (entity.getUid (), entity);
        } // for
        return SysItmLogic.get ().updateAllBatchAngItm (entities, entityByUid, options, context, wCon);
    } // updateBatch
    
    @Override
    public AngItm getInsert (AngItm entity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().insertAngItm (entity, false, context, wCon);
    } // getInsert

    @Override
    public AngItm getUpdate (AngItm entity, AngItm oldEntity, ExecutionContext context, SWriteCon wCon) throws SException {
        return SysItmLogic.get ().updateAllAngItm (entity, false, context, wCon);
    } // getUpdate

    @Override
    public StoreSetter<AngItm, C> getStoreSetter () {
        return (upsertedEntities, removeCandidates, ctx) -> {
            ctx.getStore ().setItems (upsertedEntities);
            ctx.getDeletionStore ().setToRemoveAngItm (removeCandidates);
        };
    } // getStoreSetter

    @Override
    public AngItm getDummy (C ctx) throws SException {
        FctTmpStore store = ctx.getStore ();
        D deletionStore = ctx.getDeletionStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        AngItm dummy = store.getDummyAngItm ();
        if (dummy == null) {
            String dummyCode = "@dummy";
            AngItm tmpDummy = AngItm.newInstance ();
            tmpDummy.setItmCod (dummyCode);
            tmpDummy.setItmDsc (dummyCode);
            tmpDummy.setItmLngDsc (dummyCode);
            
            
            AngItm removingDummy = deletionStore.removeAngItm (getCode (tmpDummy));
            dummy = removingDummy == null ? AngItmDao.get ().insert (tmpDummy, false, context, wCon) : removingDummy;
           
            store.setDummyAngItm (dummy);
            String code = getCode (dummy);
            long uid = dummy.getUid ();
            store.addItem (code, uid);
        } // if
        
        return dummy;
    } // getDummy

    @Override
    public void align (AngItm entity, C ctx, boolean insert) throws SException {
        FctTmpStore store = ctx.getStore ();
        ExecutionContext context = ctx.getContext ();
        SWriteCon wCon = ctx.getWCon ();

        Long supUid = null;
        if (entity.getSup () != null) {
            supUid = store.getVendors ().get (entity.getSup ().getSupCod ());
            if (supUid == null) {
                List<Object> values = Arrays.asList (entity.getSup ().getSupCod (), insert ? "inserimento" : "modifica", entity.getItmCod ());
                FctTmpUtil.get ().createWarning (FctTmpMessage.tmpImpSupUidItm, values, FctTmpTableKey.AngItm.getKeyLabel (), insert, store, context, wCon);
            } else {
                entity.setSup(null);
            } // if - else
        } // if
        entity.setSupUid (supUid);

        Long uniMeaAltUid = null;
        if (entity.getUniMeaAlt () != null) {
            uniMeaAltUid = store.getMeasureUnits ().get (entity.getUniMeaAlt ().getUniMeaCod ());
            if (uniMeaAltUid == null) {
                List<Object> values = Arrays.asList (entity.getUniMeaAlt ().getUniMeaCod (), insert ? "inserimento" : "modifica", entity.getItmCod ());
                FctTmpUtil.get ().createWarning (FctTmpMessage.tmpImpUniMeaAltUidItm, values, FctTmpTableKey.AngItm.getKeyLabel (), insert, store, context, wCon);
            } else {
                entity.setUniMeaAlt (null);
            } // if - else
        } // if
        entity.setUniMeaAltUid (uniMeaAltUid);

        Long uniMeaUid = null;
        if (entity.getUniMea () != null) {
            uniMeaUid = store.getMeasureUnits ().get (entity.getUniMea ().getUniMeaCod ());
            if (uniMeaUid == null) {
                List<Object> values = Arrays.asList (entity.getUniMea ().getUniMeaCod (), insert ? "inserimento" : "modifica", entity.getItmCod ());
                FctTmpUtil.get ().createWarning (FctTmpMessage.tmpImpUniMeaUidItm, values, FctTmpTableKey.AngItm.getKeyLabel (), insert, store, context, wCon);
            } else {
                entity.setUniMea (null);
            } // if - else 
        } // if
        entity.setUniMeaUid (uniMeaUid);
    } // align

} // AngItmTmp
