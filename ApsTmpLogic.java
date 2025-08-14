/* KEEP */
package net.synergy2.logic.aps.tmp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import net.synergy2.base.connections.ConnectionsUtil;
import net.synergy2.base.connections.SDirectWriteCon;
import net.synergy2.base.connections.SReadCon;
import net.synergy2.base.connections.SReadRemCon;
import net.synergy2.base.connections.SWriteCon;
import net.synergy2.base.connections.SWriteRemCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.context.SynergyJavaInvoker;
import net.synergy2.base.exceptions.SApplicationException;
import net.synergy2.base.exceptions.SException;
import net.synergy2.base.types.SDateTime;
import net.synergy2.base.types.SSingletonHolder;
import net.synergy2.base.util.collections.ArrayUtil;
import net.synergy2.base.util.collections.CollectionUtil;
import net.synergy2.base.util.collections.CollectionUtil.ComparePair;
import net.synergy2.base.util.collections.CollectionUtil.CompareResult;
import net.synergy2.base.util.collections.CollectionUtil.KeyGetter;
import net.synergy2.base.util.collections.CollectionUtil.ValueComparator;
import net.synergy2.base.util.collections.EntitiesHandler;
import net.synergy2.base.util.collections.MapUtil;
import net.synergy2.base.util.datas.BooleanUtil;
import net.synergy2.base.util.datas.DateTimeUtil.DateModifier;
import net.synergy2.base.util.datas.NumberUtil;
import net.synergy2.base.util.datas.StringUtil;
import net.synergy2.base.util.function.SConsumer;
import net.synergy2.db.aps.att.AngApsAttValDao;
import net.synergy2.db.aps.att.AngApsPrdCycOprAttDao;
import net.synergy2.db.aps.cst.AngApsCstDao;
import net.synergy2.db.aps.cst.AngApsCstGrpDao;
import net.synergy2.db.aps.cst.AngApsPrdCycOprCstDao;
import net.synergy2.db.aps.cst.AngApsPrdCycOprCstGrpDao;
import net.synergy2.db.aps.lnk.AngApsLnkStd;
import net.synergy2.db.aps.opr.AngApsOprClrDao;
import net.synergy2.db.aps.opr.AngApsOprSts;
import net.synergy2.db.aps.opr.AngApsOprStsDao;
import net.synergy2.db.aps.opr.AngApsShpOrdIcoDao;
import net.synergy2.db.aps.opr.ApsCusOrd;
import net.synergy2.db.aps.opr.ApsDem;
import net.synergy2.db.aps.opr.ApsDemDao;
import net.synergy2.db.aps.opr.ApsItmCop;
import net.synergy2.db.aps.opr.ApsMat;
import net.synergy2.db.aps.opr.ApsMatDao;
import net.synergy2.db.aps.opr.ApsOpr;
import net.synergy2.db.aps.opr.ApsOprAtt;
import net.synergy2.db.aps.opr.ApsOprCst;
import net.synergy2.db.aps.opr.ApsOprCstGrp;
import net.synergy2.db.aps.opr.ApsOprDao;
import net.synergy2.db.aps.opr.ApsOprLnk;
import net.synergy2.db.aps.opr.ApsOprResExp;
import net.synergy2.db.aps.opr.ApsOrdLnk;
import net.synergy2.db.aps.opr.ApsOrdLnkDao;
import net.synergy2.db.aps.opr.ApsShpOrd;
import net.synergy2.db.aps.opr.ApsShpOrdDao;
import net.synergy2.db.aps.opr.ApsShpOrdImpl.ApsShpOrdFields;
import net.synergy2.db.aps.opr.ApsSup;
import net.synergy2.db.aps.opr.ApsSupDao;
import net.synergy2.db.aps.opr.ApsSupOrd;
import net.synergy2.db.aps.opt.ApsOpt;
import net.synergy2.db.aps.opt.ApsOptions;
import net.synergy2.db.aps.quo.ApsQuo;
import net.synergy2.db.aps.quo.abstracts._MApsQuo;
import net.synergy2.db.aps.res.AngApsRes;
import net.synergy2.db.aps.res.AngApsResDao;
import net.synergy2.db.aps.sce.ApsSce;
import net.synergy2.db.aps.sce.ApsSceDao;
import net.synergy2.db.aps.sce.ApsSceImpl.ApsSceFields;
import net.synergy2.db.aps.sce.ApsSceRes;
import net.synergy2.db.aps.sce.ApsSceResCurAttVal;
import net.synergy2.db.aps.sce.ApsSceResCurCst;
import net.synergy2.db.aps.sce.ApsSceResDao;
import net.synergy2.db.base.SAlias;
import net.synergy2.db.base.SLanguageModel;
import net.synergy2.db.base.SModel;
import net.synergy2.db.base.batch.SBatchOptions;
import net.synergy2.db.base.batch.SBatchResult;
import net.synergy2.db.base.batch.SDaoBatch;
import net.synergy2.db.sys.AngLng;
import net.synergy2.db.sys.bas.AngCusDao;
import net.synergy2.db.sys.bas.AngSupDao;
import net.synergy2.db.sys.cal.CalResAbs;
import net.synergy2.db.sys.grp.AngGrp;
import net.synergy2.db.sys.grp.AngGrpDao;
import net.synergy2.db.sys.grp.AngGrpImpl;
import net.synergy2.db.sys.grp.AngResGrpDao;
import net.synergy2.db.sys.grp.abstracts._MAngGrp;
import net.synergy2.db.sys.itm.AngBomDao;
import net.synergy2.db.sys.itm.AngItm;
import net.synergy2.db.sys.itm.AngItmDao;
import net.synergy2.db.sys.itm.AngItmMdlDao;
import net.synergy2.db.sys.itm.AngItmMdlVrnDao;
import net.synergy2.db.sys.itm.AngItmVrnDao;
import net.synergy2.db.sys.itm.AngPrdCyc;
import net.synergy2.db.sys.itm.AngPrdCycDao;
import net.synergy2.db.sys.itm.AngPrdCycOpr;
import net.synergy2.db.sys.itm.AngPrdCycOprDao;
import net.synergy2.db.sys.itm.AngPrdCycOprLnkDao;
import net.synergy2.db.sys.itm.AngPrdCycTyp;
import net.synergy2.db.sys.itm.AngPrdCycTypDao;
import net.synergy2.db.sys.itm.AngPrdVrnCatDao;
import net.synergy2.db.sys.itm.AngPrdVrnDao;
import net.synergy2.db.sys.itm.AngUniMeaDao;
import net.synergy2.db.sys.job.AngJobDao;
import net.synergy2.db.sys.job.AngSubJobDao;
import net.synergy2.db.sys.lgs.AngWhsDao;
import net.synergy2.db.sys.org.OrgLvlDao;
import net.synergy2.db.sys.res.AngRes;
import net.synergy2.db.sys.res.AngResDao;
import net.synergy2.logic.aps.base.ApsCacheLogic;
import net.synergy2.logic.aps.grp.ApsGrpLogic;
import net.synergy2.logic.aps.lnk.ApsLnkExcLogic;
import net.synergy2.logic.aps.lnk.ApsLnkLogic;
import net.synergy2.logic.aps.lnk.common.ApsLnkConfig;
import net.synergy2.logic.aps.lnk.common.ApsLnkContext;
import net.synergy2.logic.aps.lnk.common.ApsLnkDemEntity;
import net.synergy2.logic.aps.lnk.common.ApsLnkEntity;
import net.synergy2.logic.aps.lnk.common.ApsLnkItmEntity;
import net.synergy2.logic.aps.lnk.common.ApsLnkItmQtyEntity;
import net.synergy2.logic.aps.lnk.common.ApsLnkMatEntity;
import net.synergy2.logic.aps.lnk.common.ApsLnkOprEntity;
import net.synergy2.logic.aps.lnk.common.ApsLnkOprLnkEntity;
import net.synergy2.logic.aps.lnk.common.ApsLnkReqEntity;
import net.synergy2.logic.aps.lnk.common.ApsLnkSatEntity;
import net.synergy2.logic.aps.lnk.common.ApsLnkShpOrdEntity;
import net.synergy2.logic.aps.lnk.common.ApsLnkSupEntity;
import net.synergy2.logic.aps.lnk.util.ApsLnkRulStdUtil;
import net.synergy2.logic.aps.opt.ApsOptLogic;
import net.synergy2.logic.aps.quo.ApsQuoLogic;
import net.synergy2.logic.aps.sce.ApsSceCopyLogic;
import net.synergy2.logic.aps.sce.ApsSceMessage;
import net.synergy2.logic.aps.tmp.common.ApsDatabaseTmpUtil;
import net.synergy2.logic.aps.tmp.common.ApsTmpStore;
import net.synergy2.logic.aps.tmp.common.ApsTmpTableKey;
import net.synergy2.logic.aps.tmp.impl.AngApsAttValTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsCstGrpTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsCstTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsItmTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsOprClrTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsPrdCycOprAttTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsPrdCycOprCstGrpTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsPrdCycOprCstTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsResTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsShpOrdIcoTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsSupTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsUniMeaTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsWhsTmp;
import net.synergy2.logic.aps.tmp.impl.AngBomTmp;
import net.synergy2.logic.aps.tmp.impl.AngCusTmp;
import net.synergy2.logic.aps.tmp.impl.AngGrpTmp;
import net.synergy2.logic.aps.tmp.impl.AngItmMdlTmp;
import net.synergy2.logic.aps.tmp.impl.AngItmMdlVrnTmp;
import net.synergy2.logic.aps.tmp.impl.AngItmVrnTmp;
import net.synergy2.logic.aps.tmp.impl.AngJobTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdCycOprLnkTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdCycOprTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdCycTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdVrnCatTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdVrnTmp;
import net.synergy2.logic.aps.tmp.impl.AngResGrpTmp;
import net.synergy2.logic.aps.tmp.impl.AngResTmp;
import net.synergy2.logic.aps.tmp.impl.AngSubJobTmp;
import net.synergy2.logic.aps.tmp.impl.ApsCusOrdTmp;
import net.synergy2.logic.aps.tmp.impl.ApsDemTmp;
import net.synergy2.logic.aps.tmp.impl.ApsItmCopTmp;
import net.synergy2.logic.aps.tmp.impl.ApsMatTmp;
import net.synergy2.logic.aps.tmp.impl.ApsOprAggTmp;
import net.synergy2.logic.aps.tmp.impl.ApsOprAttTmp;
import net.synergy2.logic.aps.tmp.impl.ApsOprCstGrpTmp;
import net.synergy2.logic.aps.tmp.impl.ApsOprCstTmp;
import net.synergy2.logic.aps.tmp.impl.ApsOprLnkTmp;
import net.synergy2.logic.aps.tmp.impl.ApsOprMulPalTmp;
import net.synergy2.logic.aps.tmp.impl.ApsOprResExpTmp;
import net.synergy2.logic.aps.tmp.impl.ApsOprTmp;
import net.synergy2.logic.aps.tmp.impl.ApsOrdLnkTmp;
import net.synergy2.logic.aps.tmp.impl.ApsSceCstTmp;
import net.synergy2.logic.aps.tmp.impl.ApsSceResCurAttValTmp;
import net.synergy2.logic.aps.tmp.impl.ApsSceResCurCstTmp;
import net.synergy2.logic.aps.tmp.impl.ApsSceResTmp;
import net.synergy2.logic.aps.tmp.impl.ApsSceTmp;
import net.synergy2.logic.aps.tmp.impl.ApsShpOrdTmp;
import net.synergy2.logic.aps.tmp.impl.ApsSupOrdTmp;
import net.synergy2.logic.aps.tmp.impl.ApsSupTmp;
import net.synergy2.logic.aps.tmp.impl.CalResAbsTmp;
import net.synergy2.logic.aps.tmp.impl.OrgLvlTmp;
import net.synergy2.logic.aps.util.ApsEntityDeleteUtil;
import net.synergy2.logic.aps.util.ApsEntityUtil;
import net.synergy2.logic.aps.util.delete.ApsEntityDeleteStore;
import net.synergy2.logic.aps.util.delete.ApsEntityDeleteSupport;
import net.synergy2.logic.base.CacheLogic;
import net.synergy2.logic.base.ExceptionLogic;
import net.synergy2.logic.fct.opt.FctOptMessage;
import net.synergy2.logic.fct.tmp.FctTmpMessage;
import net.synergy2.logic.fct.tmp.common.FctConstant;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import net.synergy2.logic.fct.tmp.common.TmpField;
import net.synergy2.logic.fct.tmp.impl.AngItmTmp;
import net.synergy2.logic.fct.tmp.impl.LogTmpEntity;
import net.synergy2.logic.fct.tmp.impl.LogsTmp;
import net.synergy2.logic.fct.tmp.impl.TmpOutput;
import net.synergy2.logic.sys.cal.SysCalLogic;
import net.synergy2.logic.sys.itm.SysItmLogic;
import net.synergy2.query.SQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApsTmpLogic {

    protected ApsTmpLogic () {}
    private static class Singleton {
        private static final SSingletonHolder<ApsTmpLogic> INSTANCE = new SSingletonHolder<> (ApsTmpLogic.class);
    } // Singleton
    public static ApsTmpLogic get () { return Singleton.INSTANCE.get (); }

    private static Logger LOGGER = LoggerFactory.getLogger (ApsTmpLogic.class);
    private static final String APS_LOGS_TMP_TABLE_NAME = "ApsLogs";

    public TmpOutput importScenarios (String token, ExecutionContext context, SWriteCon wCon, SReadRemCon rrCon) throws SException {
        // Connessione al db remoto e creazione del database temporaneo
        ApsDatabaseTmpUtil.get ().importScenarios (token, context, wCon);
        LogsTmp logTmp = new LogsTmp ();
        List<LogTmpEntity> errorsList = logTmp.getLogTempEntityList (APS_LOGS_TMP_TABLE_NAME, context, wCon, rrCon);
        if (!ArrayUtil.isEmpty (errorsList)) {
            return new TmpOutput (false, errorsList, null);
        } // if

        // Creo lo store virtuale
        ApsTmpStore store = new ApsTmpStore (new HashMap<> ());
        // Store per cancellazioni
        ApsTmpStoreDeletion deletionStore = new ApsTmpStoreDeletion ();
        // Creo il contesto di import per APS.
        ApsImportContext ictx = new ApsImportContext (store, deletionStore, context, wCon, rrCon, null, Instant.now (), null, null, true);
        // Creo l'import per APS.
        ApsImportManager asi = new ApsImportManager (LOGGER, ictx);

        // Scenari
        asi.standardImport (ApsSceTmp.get ());

        return new TmpOutput (true, null, null);
    } // importScenarios

    public TmpOutput importToTemp (long scenarioUid, boolean importBaseData, boolean importOperationalData, String token, ExecutionContext context, SWriteCon wCon, SWriteCon wConOpt, SReadRemCon rrCon) throws SException {
        ApsSce scenario = ApsSceDao.get ().getById (scenarioUid, context, wCon);

        // Controllo che lo scenario esista
        if (scenario == null) {
            throw ExceptionLogic.error (ApsSceMessage.sceSceNotVld, context, wCon);
        } // if

        // Ottengo le opzioni globali.
        ApsOptions options = ApsOptLogic.get ().getOptions (context, wConOpt);
        updateImportErpStatusOption (options, ApsOpt.IMPORT_ERP_IN_PROGRESS, wConOpt, context);
        wConOpt.commit ();

        // Importazione dati nel database temporaneo.
        try {
            ApsDatabaseTmpUtil.get ().importData (scenario.getSceCod (), importBaseData, importOperationalData, token, context, wCon);
        } catch (SApplicationException e) {
            // In caso di errore aggiorno nuovamente l'opzione globale impostando lo stato "In errore".
            updateImportErpStatusOption (options, ApsOpt.IMPORT_ERP_ERROR, wConOpt, context);
            wConOpt.commit ();
            // Rilacio l'eccezione.
            throw e;
        } // try - catch

        // Se le trasformazioni hanno generato degli errori interrompo l'import e restituisco la lista di errori
        LogsTmp logTmp = new LogsTmp ();
        List<LogTmpEntity> errorsList = logTmp.getLogTempEntityList (APS_LOGS_TMP_TABLE_NAME, context, wCon, rrCon);
        if (!ArrayUtil.isEmpty (errorsList)) {
            // In caso di errore aggiorno nuovamente l'opzione globale impostando lo stato "In errore".
            updateImportErpStatusOption (options, ApsOpt.IMPORT_ERP_ERROR, wConOpt, context);
            return new TmpOutput (false, errorsList, null);
        } // if

        // Se non ci sono stati errori aggiorno nuovamente l'opzione globale impostando lo stato "Inattivo".
        updateImportErpStatusOption (options, ApsOpt.IMPORT_ERP_NOT_WORK, wConOpt, context);

        return new TmpOutput (true, null, null);
    } // importToTemp

    private void updateImportErpStatusOption (ApsOptions opt, int impErpSts, SWriteCon wConOpt, ExecutionContext context) throws SException {
        // Modifico lo stato dell'import da ERP impostando quello passato in input.
        opt.setApsImpErpSts (impErpSts);
        // Aggiorno l'opzione globale settando il nuovo valore.
        ApsOptLogic.get ().updateOptions (opt, context, wConOpt);
    } // updateImportErpStatusOption

    public TmpOutput importBySceUid (Long sceUid, boolean importBaseData, boolean importOperationalData, ExecutionContext context, SWriteCon wCon, SDirectWriteCon wdCon, SWriteRemCon wrCon) throws SException {
        ApsSce scenario = ApsSceDao.get ().getById (sceUid, context, wdCon);
        if (scenario == null) {
            throw ExceptionLogic.error (ApsSceMessage.sceSceNotVld, context, wdCon);
        } // if
        return importToAps (scenario, importBaseData, importOperationalData, context, wCon, wdCon, wrCon);
    } // importBySceUid

    public TmpOutput importBySceCod (String scenarioCode, boolean importBaseData, boolean importOperativeData, ExecutionContext context, SWriteCon wCon, SDirectWriteCon wdCon, SWriteRemCon wrCon) throws SException {
        ApsSce scenario = ApsSceDao.get ().getByCode (scenarioCode, context, wdCon);
        if (scenario == null) {
            throw ExceptionLogic.error (ApsSceMessage.sceSceNotVld, context, wdCon);
        } // if
        return importToAps (scenario, importBaseData, importOperativeData, context, wCon, wdCon, wrCon);
    } // importBySceCod
    
    public TmpOutput importToAps (ApsSce scenario, boolean importBaseData, boolean importOperativeData, ExecutionContext context, SWriteCon wCon, SDirectWriteCon wdCon, SWriteRemCon wrCon) throws SException {
        var start = Instant.now ();
        String delimiter = "#_#"; // TODO Da prendere da configurazioni
        String executionType = "I";

        // Ottengo la risorsa di base da utilizzare per l'import.
        AngRes importResource = AngResDao.get ().getByCode (FctConstant.IMPORT_RESOURCE_CODE, context, wCon);
        // Creo il contesto di importazione così da poter utilizzare la risorsa corretta per l'inserimento e l'update.
        ExecutionContext serviceContext = new ExecutionContext (importResource.getUid (), null, 1L, new SynergyJavaInvoker (ApsTmpLogic.class.getName () + ".importToAps"));
        AngLng stdLng = CacheLogic.get ().getStandardLanguage (serviceContext, wCon);
        serviceContext.setStandardLanguageUid (stdLng.getUid (), stdLng.getLngIsoCod ());

        // Controllo se son presenti scenari bloccati
        List<ApsSce> apsSce = getAllLockedScenarios (context, wdCon);
        if (!apsSce.isEmpty ()) {
            throw ExceptionLogic.error (ApsSceMessage.sceSceLck, context, wdCon);
        } // if
        
        // Ottengo le opzioni globali.
        ApsOptions options = ApsOptLogic.get ().getOptions (context, wCon);
        // Verifico se l'opzione globale che indica lo stato dell'import da ERP ha il valore "In corso" o "In errore" e in tal caso blocco l'esecuzione.
        if (options.getApsImpErpSts () == ApsOpt.IMPORT_ERP_IN_PROGRESS) {
            throw ExceptionLogic.error (FctOptMessage.optImpErpStsInPrgNotVal, context, wdCon);
        } else if (options.getApsImpErpSts () == ApsOpt.IMPORT_ERP_ERROR) {
            throw ExceptionLogic.error (FctOptMessage.optImpErpStsInErrNotVal, context, wdCon, "APS");
        } // if - else

        // Ottengo dalle opzioni il campo che indica se importare per differenza.
        boolean importWithDifference = options.getApsEnbImpDif ();

        LOGGER.info (String.format ("**** Import Aps [%s] start ****", scenario.getSceCod ()));
        try {
            long scenarioUid = scenario.getUid ();
            SDateTime tssImp = scenario.getTssImp (); // N.B: Se null viene riallineato tutto.
            SDateTime nextTssImp = new SDateTime ();
            boolean forceRemove = false;
            // Se voglio importare per differenza, faccio delle valutazioni preliminari per capire se importare senza controlli ugualmente.
            if (importWithDifference) {
                if (tssImp == null) {
                    forceRemove = true;
                } else {
                    // Se più vecchio di 2 settimane, forzo il tssImp a null e il remove.
                    if (tssImp.before (new SDateTime ().modify (DateModifier.ADD_WEEK, -2))) {
                        tssImp = null;
                        forceRemove = true;
                    } // if
                } // if - else
            } else {
                // Non voglio importare per differenza, forzo tutto.
                forceRemove = true;
                tssImp = null;
                nextTssImp = null;
            } // if - else
            // Blocco lo scenario.
            scenario.setFlgLck (true);
            scenario.setExcTyp (executionType);
            scenario.setTssStrExc (new SDateTime ());
            scenario.setTssEndExc (null);
            scenario = ApsSceDao.get ().updateAll (scenario, true, context, wdCon);

            // Ottengo i moduli sotto licenza.
            boolean hasConstraints = ApsCacheLogic.get ().hasApsModuleConstraints (context, wCon);
            boolean hasAttributes = ApsCacheLogic.get ().hasApsModuleAttributes (context, wCon);
            boolean hasOperationResourceExceptions = ApsCacheLogic.get ().hasApsModuleOperationResourceExceptions (context, wCon);

            // Cancello tutti i legami per lo scenario interessato dall'import.
            // Questa query aiuta leggermente il passo dei legami, perché cancella direttamente quelli che sicuramente non voglio mantenere in Aps.
            ApsOrdLnkDao.get ().deleteBySceUidAndImpDel (scenarioUid, context, wCon);

            // Estraggo tutte le offerte per lo scenario.
            List<ApsQuo> apsQuoList = ApsQuoLogic.get ().getQuotationBySceUid (scenarioUid, context, wCon);
            Map<String, Long> quoUidByQuoCod = apsQuoList.stream ().collect (Collectors.toMap (_MApsQuo::getQuoCod, _MApsQuo::getUid));

            // Estraggo tutti i gruppi che possiedono un gruppo prioritario.
            List<AngGrp> apsGroups = ApsGrpLogic.get ().getApsGrpWithPtyGrp (context, wCon);
            // Mappa che ha per chiave l'identificativo del gruppo non prioritario e per valore l'identificativo del gruppo prioritario.
            Map<Long, Long> priorityGroupUidByGroupUid = apsGroups.stream ().collect (Collectors.toMap (_MAngGrp::getUid, grp -> ((AngGrpImpl)grp).getApsGrp ().getGrpPtyUid ()));

            // Imposto le dipendenze delle tabelle da importare.
            HashMap<String, Set<String>> dependencies = getEntityDependencies (importBaseData, importOperativeData);

            // Creo lo store virtuale
            ApsTmpStore store = new ApsTmpStore (dependencies);
            // Aggiungo le quo allo store.
            store.setCurrentScenario (scenarioUid);
            store.setQuotations (quoUidByQuoCod);

            boolean dependencyOrder;
            // Support per le mappe di eliminazione dei dati.
            ApsTmpStoreDeletion deletionStore = new ApsTmpStoreDeletion ();
            // Creo il contesto di import per APS.
            ApsImportContext importContext = new ApsImportContext (store, deletionStore, serviceContext, wCon, wrCon, nextTssImp, start, scenarioUid, priorityGroupUidByGroupUid, forceRemove);
            // Creo l'import per APS.
            ApsImportManager manager = new ApsImportManager (LOGGER, importContext);
            // Oggetto di supporto per la cancellazione dei dati in batch.
            ApsEntityDeleteSupport deleteSupport = new ApsEntityDeleteSupport ();
            // Memorizzazione dei dati di eliminazione.
            ApsEntityDeleteStore deleteStore = new ApsEntityDeleteStore ();

            Map<Long, AngItm> itmByUid = new HashMap<> ();

            if (importBaseData) {
                // Magazzini (base)
                manager.standardImport (AngApsWhsTmp.get ());
                // Commesse (base)
                manager.standardImport (AngJobTmp.get ());
                // Sottocommesse (base)
                manager.standardImportWithAlign (AngSubJobTmp.get ());
                // Unità di misura (base)
                manager.standardImport (AngApsUniMeaTmp.get ());
                // Colori fase (base)
                manager.standardImport (AngApsOprClrTmp.get ());
                // Icona ordine di produzione (base)
                manager.standardImport (AngApsShpOrdIcoTmp.get ());
                // Stabilimenti (base)
                manager.standardImport (OrgLvlTmp.get ());
                // Attributi
                if (hasAttributes) {
                    // Valori attributi (base)
                    manager.standardImport (AngApsAttValTmp.get ());
                } // if
                // Gruppi synergy (base)
                manager.standardImport (AngGrpTmp.get ());
                // Risorse synergy (base)
                manager.standardImport (AngResTmp.get ());
                // Risorse primarie (base)
                manager.standardImportWithAlign (AngApsResTmp.get ());
                // Risorse - Scenari (base)
                manager.standardScenarioImportWithAlign (ApsSceResTmp.get ());
                // Risorse - Gruppi synergy (base)
                manager.standardImportWithAlign (AngResGrpTmp.get ());
                // Controllo se ha il modulo dei vincoli.
                if (hasConstraints) {
                    // Vincoli secondari (base)
                    manager.standardImport (AngApsCstTmp.get ());
                    // Vincoli secondari - Scenari (base)
                    manager.standardScenarioImportWithAlign (ApsSceCstTmp.get ());
                    // Gruppi vincoli secondari (base)
                    manager.standardImport (AngApsCstGrpTmp.get ());
                } // if
                // Fornitori (base)
                manager.standardImport (AngApsSupTmp.get ());
                // Clienti (base)
                manager.standardImport (AngCusTmp.get ());
                // Articoli (base)
                manager.standardImportWithAlign (AngApsItmTmp.get ());
                // Categorie varianti (base)
                manager.standardImport (AngPrdVrnCatTmp.get ());
                // Varianti (base)
                manager.standardImportWithAlign (AngPrdVrnTmp.get ());
                // Varianti articolo (base)
                manager.standardImportWithAlign (AngItmVrnTmp.get ());
                // Modelli articolo (base)
                manager.standardImportWithAlign (AngItmMdlTmp.get ());
                // Varianti modello articolo (base)
                manager.standardImportWithAlign (AngItmMdlVrnTmp.get ());
                // Cicli di lavoro (base)
//                manager.standardImportWithAlign (AngPrdCycTmp.get ());
                importAngPrdCyc (importContext, manager);
                // Cicli fase di lavoro (base)
                manager.standardImportWithAlign (AngPrdCycOprTmp.get ());
                // Distinta base (base)
                manager.standardImportWithAlign (AngBomTmp.get ());
                // Dipendenze cicli fase di lavoro (base)
                manager.standardImportWithAlign (AngPrdCycOprLnkTmp.get ());
                if (hasConstraints) {
                    // Vincoli di cicli fase di lavoro (base)
                    manager.standardImportWithAlign (AngApsPrdCycOprCstTmp.get ());
                    // Gruppi vincolo di cicli fase di lavoro (base)
                    manager.standardImportWithAlign (AngApsPrdCycOprCstGrpTmp.get ());
                } // if
                // Attributi di cicli fase di lavoro (base)
                if (hasAttributes) {
                    manager.standardImportWithAlign (AngApsPrdCycOprAttTmp.get ());
                } // if
            } else {
                AngItmTmp<ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> itmTmp = AngApsItmTmp.get ();
                // Ottengo le preesistenti.
                List<AngItm> oldItemEntities = itmTmp.getActiveList (importContext);
                for (AngItm oldItemEntity : oldItemEntities) {
                    itmByUid.put (oldItemEntity.getUid (), oldItemEntity);
                } // for
            } // if - else
            
            if (importOperativeData) {
                // Ordini cliente
                Map<Long, ApsCusOrd> cusOrdByUid = new HashMap<> ();
                manager.standardScenarioImportWithAlignAndMap (ApsCusOrdTmp.get(), cusOrdByUid);
                
                // Domande
                Map<Long, ApsDem> demByUid = new HashMap<> ();
                Map<Long, Set<ApsDem>> demsByCusOrdUid = new HashMap<> ();
                manager.standardScenarioImportWithAlignMapAndConsumer (ApsDemTmp.get (), (entity) -> {
                    if (entity.getCusOrdUid () != null) {
                        demsByCusOrdUid.computeIfAbsent (entity.getCusOrdUid (), (k) -> new HashSet<> ()).add (entity);
                    } // if
                }, demByUid);
                
                // Ordini fornitore
                Map<Long, ApsSupOrd> supOrdByUid = new HashMap<> ();
                manager.standardScenarioImportWithAlignAndMap (ApsSupOrdTmp.get (), supOrdByUid);
                
                // Righe ordine fornitore
                Map<Long, ApsSup> supByUid = new HashMap<> ();
                Map<Long, Set<ApsSup>> supsBySupOrdUid = new HashMap<> ();
                manager.standardScenarioImportWithAlignMapAndConsumer (ApsSupTmp.get (), (entity) -> {
                    if (entity.getSupOrdUid () != null) {
                        supsBySupOrdUid.computeIfAbsent (entity.getSupOrdUid (), (k) -> new HashSet<> ()).add (entity);
                    } // if
                }, supByUid);
                
                // Ordini produzione
                Map<Long, ApsShpOrd> shpOrdByUid = new HashMap<> ();
                manager.standardScenarioImportWithAlignAndMap (ApsShpOrdTmp.get (), shpOrdByUid);
                
                // Coprodotti
                Map<Long, ApsItmCop> itmCopByUid = new HashMap<> ();
                Map<Long, Set<ApsItmCop>> itmCopsByShpOrdUid = new HashMap<> ();
                manager.standardImportWithAlignMapAndConsumer (ApsItmCopTmp.get (), (entity) -> {
                    itmCopsByShpOrdUid.computeIfAbsent (entity.getShpOrdUid (), (k) -> new HashSet<> ()).add (entity);
                }, itmCopByUid);
                
                // Fasi aggregate
                Map<Long, ApsOpr> oprAggByUid = new HashMap<> ();
                Map<Long, Set<ApsOpr>> oprAggsByShpOrdUid = new HashMap<> ();
                manager.standardImportWithAlignMapAndConsumer (ApsOprAggTmp.get (), (entity) -> {
                    oprAggsByShpOrdUid.computeIfAbsent (entity.getShpOrdUid (), k -> new HashSet<> ()).add (entity);
                }, oprAggByUid);
                
                // Fasi multipallet
                Map<Long, ApsOpr> oprMulPalByUid = new HashMap<> ();
                Map<Long, Set<ApsOpr>> oprMulPalsByShpOrdUid = new HashMap<> ();
                manager.standardImportWithAlignMapAndConsumer (ApsOprMulPalTmp.get (), (entity) -> {
                    oprMulPalsByShpOrdUid.computeIfAbsent (entity.getShpOrdUid (), k -> new HashSet<> ()).add (entity);
                }, oprMulPalByUid);
                
                // Fasi
                Map<Long, ApsOpr> oprByUid = new HashMap<> ();
                Map<Long, Set<ApsOpr>> oprsByShpOrdUid = new HashMap<> ();
                Map<Long, Set<ApsOpr>> oprsByOprAggUid = new HashMap<> ();
                Map<Long, Set<ApsOpr>> oprsByMulPalUid = new HashMap<> ();
                manager.standardImportWithAlignMapAndConsumer (ApsOprTmp.get (), (entity) -> {
                    oprsByShpOrdUid.computeIfAbsent (entity.getShpOrdUid (), k -> new HashSet<> ()).add (entity);
                    if (entity.getOprAggUid () != null) {
                        oprsByOprAggUid.computeIfAbsent (entity.getOprAggUid (), k -> new HashSet<> ()).add (entity);
                    } // if
                    if (entity.getMulPalUid () != null) {
                        oprsByMulPalUid.computeIfAbsent (entity.getMulPalUid (), k -> new HashSet<> ()).add (entity);
                    } // if
                }, oprByUid);
                
                // Materiali
                Map<Long, ApsMat> matByUid = new HashMap<> ();
                Map<Long, Set<ApsMat>> matsByOprUid = new HashMap<> ();
                manager.standardImportWithAlignMapAndConsumer (ApsMatTmp.get (), (entity) -> {
                    matsByOprUid.computeIfAbsent (entity.getOprUid (), (k) -> new HashSet<> ()).add (entity);
                }, matByUid);
                
                // Dipendenze fasi
                Map<Long, ApsOprLnk> oprLnkByUid = new HashMap<> ();
                Map<Long, Set<ApsOprLnk>> oprLnksByPreOprUid = new HashMap<> ();
                Map<Long, Set<ApsOprLnk>> oprLnksByNxtOprUid = new HashMap<> ();
                manager.standardImportWithAlignMapAndConsumer (ApsOprLnkTmp.get (), (entity) -> {
                    oprLnksByPreOprUid.computeIfAbsent (entity.getPreOprUid (), (k) -> new HashSet<> ()).add (entity);
                    oprLnksByNxtOprUid.computeIfAbsent (entity.getNxtOprUid (), (k) -> new HashSet<> ()).add (entity);
                }, oprLnkByUid);

                // Legami
                start = Instant.now ();
                FctTmpUtil.get ().infoProcessingStart (LOGGER, "Start ApsOrdLnk");
                // Se ho creato un dummy, lo aggiungo alle mappe necessarie.
                addDummyAngItm (store, itmByUid);
                // Se ho creato un dummy, lo aggiungo alle mappe necessarie.
                addDummyApsShpOrd (store, shpOrdByUid);
                // Se ho creato un dummy, lo aggiungo alle mappe necessarie.
                addDummyApsOpr (store, oprByUid, oprsByShpOrdUid, oprsByOprAggUid, oprsByMulPalUid);
                // Punto eccezionale: unisco le fasi aggregate e quelle non, da passare ai legami.
                Map<Long, ApsOpr> allOprByUid = MapUtil.mergeMap (oprByUid, oprAggByUid, (v1, v2) -> v1); // Il combiner non servirebbe perché univoci.
                MapUtil.mergeMapInFirst (allOprByUid, oprMulPalByUid, (v1, v2) -> v1); // Il combiner non servirebbe perché univoci.
                Map<Long, ApsOrdLnk> ordLnkByUid = new HashMap<> ();
                Map<Long, Set<ApsOrdLnk>> ordLnksByMatUid = new HashMap<> ();
                Map<Long, Set<ApsOrdLnk>> ordLnksByDemUid = new HashMap<> ();
                Map<Long, Set<ApsOrdLnk>> ordLnksByShpOrdUid = new HashMap<> ();
                Map<Long, Set<ApsOrdLnk>> ordLnksBySupUid = new HashMap<> ();
                List<ApsOrdLnk> toRemoveApsOrdLnk = insertApsOrdLnk (scenarioUid, tssImp, store, itmByUid, demByUid, matByUid, supByUid, shpOrdByUid, itmCopByUid, allOprByUid, oprLnkByUid, forceRemove, ordLnksByMatUid, ordLnksByDemUid, ordLnksByShpOrdUid, ordLnksBySupUid, delimiter, serviceContext, wCon, wrCon);
                /* itmByUid = null; demByUid = null; matByUid = null; supByUid = null; shpOrdByUid = null; itmCopByUid = null; oprByUid = null; oprAggByUid = null; oprLnkByUid = null; */
                FctTmpUtil.get ().clearMemory ();
                dependencyOrder = FctTmpUtil.get ().clearObjectsAndFlushMemory (store, ApsTmpStore.ORDER_LINKS_KEY);
                if (!dependencyOrder) { throw ExceptionLogic.error (FctTmpMessage.tmpImpEtyOrdNotVld, new Object[] {ApsTmpStore.ORDER_LINKS_KEY}, context, wCon); }
                start = FctTmpUtil.get ().infoProcessingEnd (LOGGER, start, "End ApsOrdLnk in");
                
                Map<Long, ApsOprCst> oprCstByUid = new HashMap<> ();
                Map<Long, Set<ApsOprCst>> oprCstsByOprUid = new HashMap<> ();
                Map<Long, ApsOprCstGrp> oprCstGrpByUid = new HashMap<> ();
                Map<Long, Set<ApsOprCstGrp>> oprCstGrpsByOprUid = new HashMap<> ();

                if (hasConstraints) {
                    // Fasi - Vincoli
                    manager.standardImportWithAlignMapAndConsumer (ApsOprCstTmp.get (), (entity) -> {
                        oprCstsByOprUid.computeIfAbsent (entity.getOprUid (), (k) -> new HashSet<> ()).add (entity);
                    }, oprCstByUid);
                    // No cascade delete.
                    for (ApsOprCst toRemove : deletionStore.getToRemoveApsOprCstList ()) {
                        deleteSupport.addOprCstUidsToDelete (toRemove.getUid ());
                    } // for

                    // Fasi - Gruppo vincoli
                    manager.standardImportWithAlignMapAndConsumer (ApsOprCstGrpTmp.get (), (entity) -> {
                        oprCstGrpsByOprUid.computeIfAbsent (entity.getOprUid (), (k) -> new HashSet<> ()).add (entity);
                    }, oprCstGrpByUid);
                    // No cascade delete.
                    for (ApsOprCstGrp toRemove : deletionStore.getToRemoveApsOprCstGrpList ()) {
                        deleteSupport.addOprCstGrpUidsToDelete (toRemove.getUid ());
                    } // for

                    // Risorse primarie di scenario - Vincoli secondari di scenario - In uso.
                    manager.standardImportWithAlign (ApsSceResCurCstTmp.get ());
                    // No cascade delete.
                    for (ApsSceResCurCst toRemove : deletionStore.getToRemoveApsSceResCurCstList ()) {
                        deleteSupport.addSceResCurCstUidsToDelete (toRemove.getUid ());
                    } // for
                } // if
                
                Map<Long, ApsOprAtt> oprAttByUid = new HashMap<> ();
                Map<Long, Set<ApsOprAtt>> oprAttsByOprUid = new HashMap<> ();
                if (hasAttributes) {
                    // Fasi - Attributi
                    manager.standardImportWithAlignMapAndConsumer (ApsOprAttTmp.get (), (entity) -> {
                        oprAttsByOprUid.computeIfAbsent (entity.getOprUid (), (k) -> new HashSet<> ()).add (entity);
                    }, oprAttByUid);
                    // No cascade delete.
                    for (ApsOprAtt toRemove : deletionStore.getToRemoveApsOprAttList ()) {
                        deleteSupport.addOprAttUidsToDelete (toRemove.getUid ());
                    } // for

                    // Risorse primarie di scenario - Valori attributi - In uso.
                    manager.standardImportWithAlign (ApsSceResCurAttValTmp.get ());
                    // No cascade delete.
                    for (ApsSceResCurAttVal toRemove : deletionStore.getToRemoveApsSceResCurAttValList ()) {
                        deleteSupport.addSceResCurAttValUidsToDelete (toRemove.getUid ());
                    } // for
                } // if
                
                Map<Long, ApsOprResExp> oprResExpByUid = new HashMap<> ();
                Map<Long, Set<ApsOprResExp>> oprResExpsByOprUid = new HashMap<> ();
                if (hasOperationResourceExceptions) {
                    // Fasi - Eccezioni risorsa
                    manager.standardImportWithAlignMapAndConsumer (ApsOprResExpTmp.get (), (entity) -> {
                        oprResExpsByOprUid.computeIfAbsent (entity.getOprUid (), (k) -> new HashSet<> ()).add (entity);
                    }, oprResExpByUid);
                    
                    // No cascade delete.
                    for (ApsOprResExp toRemove : deletionStore.getToRemoveApsOprResExpList ()) {
                        deleteSupport.addOprResExpUidsToDelete (toRemove.getUid ());
                    } // for
                } // if
                
                // Punto eccezionale: unisco le fasi aggregate e quelle non, da passare alla cancellazione dell'ordine di produzione.
                Map<Long, Set<ApsOpr>> allOprsByShpOrdUid = MapUtil.mergeMap (oprsByShpOrdUid, oprMulPalsByShpOrdUid, (v1, v2) -> {
                    v1.addAll (v2);
                    return v1;
                });
                MapUtil.mergeMapInFirst (allOprsByShpOrdUid, oprMulPalsByShpOrdUid, (v1, v2) -> {
                    v1.addAll (v2);
                    return v1;
                });
                
                deleteStore
                    .setItmCopByUid (itmCopByUid)
                    .setItmCopsByShpOrdUid (itmCopsByShpOrdUid)
                    .setOprByUid (allOprByUid)
                    .setOprCstByUid (oprCstByUid)
                    .setOprCstsByOprUid (oprCstsByOprUid)
                    .setOprCstGrpByUid (oprCstGrpByUid)
                    .setOprCstGrpsByOprUid (oprCstGrpsByOprUid)
                    .setOprAttByUid (oprAttByUid)
                    .setOprAttsByOprUid (oprAttsByOprUid)
                    .setOprResExpByUid (oprResExpByUid)
                    .setOprResExpsByOprUid (oprResExpsByOprUid)
                    .setOprsByShpOrdUid (allOprsByShpOrdUid)
                    .setOprAggsByOprUid (oprsByOprAggUid)
                    .setOprMulPalsByOprUid (oprsByMulPalUid)
                    .setOprLnkByUid (oprLnkByUid)
                    .setOprLnksByPreOprUid (oprLnksByPreOprUid)
                    .setOprLnksByNxtOprUid (oprLnksByNxtOprUid)
                    .setMatByUid (matByUid)
                    .setMatsByOprUid (matsByOprUid)
                    .setCusOrdByUid (cusOrdByUid)
                    .setDemByUid (demByUid)
                    .setDemsByCusOrdUid (demsByCusOrdUid)
                    .setSupOrdByUid (supOrdByUid)
                    .setSupByUid (supByUid)
                    .setSupsBySupOrdUid (supsBySupOrdUid)
                    .setOrdLnkByUid (ordLnkByUid)
                    .setOrdLnksByShpOrdUid (ordLnksByShpOrdUid)
                    .setOrdLnksByDemUid (ordLnksByDemUid)
                    .setOrdLnksByMatUid (ordLnksByMatUid)
                    .setOrdLnksBySupUid (ordLnksBySupUid);
                
                // -------------
                // CANCELLAZIONE
                // -------------
                
                ApsEntityDeleteUtil.get ().loadApsShpOrdChilds (scenarioUid, deleteStore, serviceContext, wCon);
                ApsEntityDeleteUtil.get ().loadApsCusOrdChilds (scenarioUid, deleteStore, serviceContext, wCon);
                ApsEntityDeleteUtil.get ().loadApsSupOrdChilds (scenarioUid, deleteStore, serviceContext, wCon);
                ApsEntityDeleteUtil.get ().loadApsOrdLnkChilds (scenarioUid, deleteStore, serviceContext, wCon);

                LOGGER.info (String.format ("ApsOrdLnk toRemove size: %d", toRemoveApsOrdLnk.size ()));
                // Cascade delete.
                ApsEntityDeleteUtil.get ().loadApsOrdLnksSupport (toRemoveApsOrdLnk, deleteStore, deleteSupport, serviceContext, wCon);
                toRemoveApsOrdLnk = null; FctTmpUtil.get ().clearMemory ();

                // No cascade delete.
                for (ApsOprLnk toRemove : deletionStore.getToRemoveApsOprLnkList ()) {
                    deleteSupport.addOprLnkUidsToDelete (toRemove.getUid ());
                } // for
                deletionStore.clearApsOprLnk (); FctTmpUtil.get ().clearMemory ();

                // Cascade delete.
                ApsEntityDeleteUtil.get ().loadApsMatsSupport (deletionStore.getToRemoveApsMatList (), deleteStore, deleteSupport, serviceContext, wCon);
                deletionStore.clearApsMat (); FctTmpUtil.get ().clearMemory ();

                if (hasConstraints) {
                    // Cascade delete.
                    ApsEntityDeleteUtil.get ().loadApsOprCstGrpsSupport (deletionStore.getToRemoveApsOprCstGrpList (), deleteStore, deleteSupport, serviceContext, wCon);
                } // if
                
                // Cascade delete.
                ApsEntityDeleteUtil.get ().loadApsOprsSupport (deletionStore.getToRemoveApsOprList (), deleteStore, deleteSupport, serviceContext, wCon);
                deletionStore.clearApsOpr (); FctTmpUtil.get ().clearMemory ();

                // Cascade delete.
                ApsEntityDeleteUtil.get ().loadApsOprsSupport (deletionStore.getToRemoveApsOprAggList (), deleteStore, deleteSupport, serviceContext, wCon);
                deletionStore.clearApsOprAgg (); FctTmpUtil.get ().clearMemory ();

                // Cascade delete.
                ApsEntityDeleteUtil.get ().loadApsOprsSupport (deletionStore.getToRemoveApsOprMulPalList (), deleteStore, deleteSupport, serviceContext, wCon);
                deletionStore.clearApsOprMulPal (); FctTmpUtil.get ().clearMemory ();

                for (ApsItmCop toRemove : deletionStore.getToRemoveApsItmCopList ()) {
                    // No cascade delete.
                    deleteSupport.addItmCopUidsToDelete (toRemove.getUid ());
                } // for
                deletionStore.clearApsItmCop (); FctTmpUtil.get ().clearMemory ();

                // Cascade delete.
                ApsEntityDeleteUtil.get ().loadApsShpOrdsSupport (deletionStore.getToRemoveApsShpOrdList (), deleteStore, deleteSupport, serviceContext, wCon);
                deletionStore.clearApsShpOrd (); FctTmpUtil.get ().clearMemory ();

                // Cascade delete.
                ApsEntityDeleteUtil.get ().loadApsSupsSupport (deletionStore.getToRemoveApsSupList (), deleteStore, deleteSupport, serviceContext, wCon);
                deletionStore.clearApsSup (); FctTmpUtil.get ().clearMemory ();

                // Cascade delete.
                ApsEntityDeleteUtil.get ().loadApsSupOrdsSupport (deletionStore.getToRemoveApsSupOrdList (), deleteStore, deleteSupport, serviceContext, wCon);
                deletionStore.clearApsSupOrd (); FctTmpUtil.get ().clearMemory ();

                // Cascade delete.
                ApsEntityDeleteUtil.get ().loadApsDemsSupport (deletionStore.getToRemoveApsDemList (), deleteStore, deleteSupport, serviceContext, wCon);
                deletionStore.clearApsDem (); FctTmpUtil.get ().clearMemory ();

                // Cascade delete.
                ApsEntityDeleteUtil.get ().loadApsCusOrdsSupport (deletionStore.getToRemoveApsCusOrdList (), deleteStore, deleteSupport, serviceContext, wCon);
                deletionStore.clearApsCusOrd (); FctTmpUtil.get ().clearMemory ();

                LOGGER.info (String.format ("Start delete batch"));
                ApsEntityDeleteUtil.get ().deleteAllBatch (deleteSupport, serviceContext, wCon);
                start = FctTmpUtil.get ().infoProcessingEnd (LOGGER, start, "End delete batch in");
            } // if

            if (importBaseData) {
                LOGGER.info (String.format ("Start delete base data"));
                // Le cancellazioni sulla parte di anagrafica sono dirette perchè il 90% delle entità ha cancellazione logica.
                SBatchOptions batchOptions = SBatchOptions.of ();
                if (hasAttributes) {
                  deleteBatch (AngApsPrdCycOprAttDao.get (), deletionStore.getToRemoveAngApsPrdCycOprAttList (), batchOptions, serviceContext, wCon);
                } // if
                if (hasConstraints) {
                  deleteBatch (AngApsPrdCycOprCstGrpDao.get (), deletionStore.getToRemoveAngApsPrdCycOprCstGrpList (), batchOptions, serviceContext, wCon);
                  deleteBatch (AngApsPrdCycOprCstDao.get (), deletionStore.getToRemoveAngApsPrdCycOprCstList (), batchOptions, serviceContext, wCon);
                } // if
                deleteBatch (AngPrdCycOprLnkDao.get (), deletionStore.getToRemoveAngPrdCycOprLnkList (), batchOptions, serviceContext, wCon);
                for (var r : deletionStore.getToRemoveAngItmMdlVrnList ()) { AngItmMdlVrnDao.get ().delete (r, serviceContext, wCon); }
                for (var r : deletionStore.getToRemoveAngItmMdlList ()) { AngItmMdlDao.get ().delete (r, serviceContext, wCon); }
                for (var r : deletionStore.getToRemoveAngItmVrnList ()) { AngItmVrnDao.get ().delete (r, serviceContext, wCon); }
                for (var r : deletionStore.getToRemoveAngPrdVrnList ()) { AngPrdVrnDao.get ().delete (r, serviceContext, wCon); }
                for (var r : deletionStore.getToRemoveAngPrdVrnCatList ()) { AngPrdVrnCatDao.get ().delete (r, serviceContext, wCon); }
                deleteBatch (AngBomDao.get (), deletionStore.getToRemoveAngBomList (), batchOptions, serviceContext, wCon);
                deleteBatch (AngPrdCycOprDao.get (), deletionStore.getToRemoveAngPrdCycOprList (), batchOptions, serviceContext, wCon);
                deleteBatch (AngPrdCycDao.get (), deletionStore.getToRemoveAngPrdCycList (), batchOptions, serviceContext, wCon);
                deleteBatch (AngItmDao.get (), deletionStore.getToRemoveAngItmList (), batchOptions, serviceContext, wCon);
                deleteBatch (AngCusDao.get (), deletionStore.getToRemoveAngCusList (), batchOptions, serviceContext, wCon);
                deleteBatch (AngSupDao.get (), deletionStore.getToRemoveAngSupList (), batchOptions, serviceContext, wCon);
                if (hasConstraints) {
                  deleteBatch (AngApsCstGrpDao.get (), deletionStore.getToRemoveAngApsCstGrpList (), batchOptions, serviceContext, wCon);
                  for (var r : deletionStore.getToRemoveApsSceCstList ()) { ApsEntityUtil.get ().deleteApsSceCstAndSubEntity (r, true, true, serviceContext, wCon); }
                  deleteBatch (AngApsCstDao.get (), deletionStore.getToRemoveAngApsCstList (), batchOptions, serviceContext, wCon);
                } // if
                for (var r : deletionStore.getToRemoveAngResGrpList ()) { AngResGrpDao.get ().delete (r, serviceContext, wCon); }
                for (var r : deletionStore.getToRemoveApsSceResList ()) { ApsEntityUtil.get ().deleteApsSceResAndSubEntity (r, true, true, serviceContext, wCon); }
                deleteBatch (AngApsResDao.get (), deletionStore.getToRemoveAngApsResList (), batchOptions, serviceContext, wCon);
                for (var r : deletionStore.getToRemoveAngResList ()) { AngResDao.get ().delete (r, serviceContext, wCon); }
                for (var r : deletionStore.getToRemoveAngGrpList ()) { AngGrpDao.get ().delete (r, serviceContext, wCon); }
                if (hasAttributes) {
                  deleteBatch (AngApsAttValDao.get (), deletionStore.getToRemoveAngApsAttValList (), batchOptions, serviceContext, wCon);
                } // if
                for (var r : deletionStore.getToRemoveOrgLvlList ()) { OrgLvlDao.get ().delete (r, serviceContext, wCon); }
                deleteBatch (AngApsShpOrdIcoDao.get (), deletionStore.getToRemoveAngApsShpOrdIcoList (), batchOptions, serviceContext, wCon);
                deleteBatch (AngApsOprClrDao.get (), deletionStore.getToRemoveAngApsOprClrList (), batchOptions, serviceContext, wCon);
                deleteBatch (AngUniMeaDao.get (), deletionStore.getToRemoveAngUniMeaList (), batchOptions, serviceContext, wCon);
                deleteBatch (AngSubJobDao.get (), deletionStore.getToRemoveAngSubJobList (), batchOptions, serviceContext, wCon);
                deleteBatch (AngJobDao.get (), deletionStore.getToRemoveAngJobList (), batchOptions, serviceContext, wCon);
                deleteBatch (AngWhsDao.get (), deletionStore.getToRemoveAngWhsList (), batchOptions, serviceContext, wCon);
                start = FctTmpUtil.get ().infoProcessingEnd (LOGGER, start, "End delete base data in");
            } // if

            LOGGER.info (String.format ("**** Import Aps completed ****"));
            
            // Sblocco lo scenario.
            // Va usata necessariamente la connessione della transazione perché la tabella dello scenario è in LOCK e non è accessibile da altre connessioni.
            scenario.setFlgLck (false);
            scenario.setTssEndExc (new SDateTime ());
            scenario.setTssImp (nextTssImp);
            scenario = ApsSceDao.get ().updateAll (scenario, true, serviceContext, wCon);
            
            // Aggiorno il timestamp di importazione in aps per lo scenario.
            ApsSceTmp.get ().updateTimestampImportScenario (scenario, tssImp, serviceContext, wCon, wrCon);
            return new TmpOutput (true, null, store.getWarnings ());
        } catch (Throwable e) {
            // In caso di errore dobbiamo rilasciare la connessione wCon altrimenti non possiamo fare modifiche allo scenario perché in LOCK,
            // A causa di referenze di record, i database lockano anche le tabelle referenziate per permettere l'integrità dei dati.
            ConnectionsUtil.safeRollback (wCon);
            scenario.setFlgLck (false);
            scenario = ApsSceDao.get ().updateAll (scenario, true, serviceContext, wCon);
            ConnectionsUtil.commit (wCon.getConnection ());
            throw e;
        } // try - catch
    } // importToAps

    /**
     * Metodo che ritorna le dipendenze necessarie per l'import di APS.
     * 
     * @return Dipendenze standard di APS.
     */
    private HashMap<String, Set<String>> getEntityDependencies (boolean importBaseData, boolean importOperativeData) {
        HashMap<String, Set<String>> dependencies = new HashMap<> ();
        if (importBaseData) {
            dependencies.put (ApsTmpStore.SUB_JOBS_KEY, Set.of (ApsTmpStore.JOBS_KEY));
            dependencies.put (ApsTmpStore.PRIMARY_RESOURCES_KEY, Set.of (ApsTmpStore.RESOURCES_KEY));
            dependencies.put (ApsTmpStore.SCENARIO_RESOURCES_KEY, Set.of (ApsTmpStore.PRIMARY_RESOURCES_KEY));
            dependencies.put (ApsTmpStore.RESOURCES_GROUPS_KEY, Set.of (ApsTmpStore.RESOURCES_KEY, ApsTmpStore.GROUPS_KEY));
            dependencies.put (ApsTmpStore.SCENARIO_SECONDARY_CONSTRAINTS_KEY, Set.of (ApsTmpStore.SECONDARY_CONSTRAINTS_KEY));
            dependencies.put (ApsTmpStore.SCENARIO_RESOURCES_CURRENT_SECONDARY_CONSTRAINTS_KEY, Set.of (ApsTmpStore.SCENARIO_RESOURCES_KEY, ApsTmpStore.SCENARIO_SECONDARY_CONSTRAINTS_KEY));
            dependencies.put (ApsTmpStore.SCENARIO_RESOURCES_CURRENT_ATTRIBUTE_VALUES_KEY, Set.of (ApsTmpStore.SCENARIO_RESOURCES_KEY, ApsTmpStore.ATTRIBUTE_VALUES_KEY));
            dependencies.put (ApsTmpStore.ITEMS_KEY, Set.of (ApsTmpStore.VENDORS_KEY, ApsTmpStore.MEASURE_UNITS_KEY));
            dependencies.put (ApsTmpStore.VARIANT_CATEGORIES_KEY, Set.of ());
            dependencies.put (ApsTmpStore.VARIANTS_KEY, Set.of (ApsTmpStore.VARIANT_CATEGORIES_KEY));
            dependencies.put (ApsTmpStore.ITEM_VARIANTS_KEY, Set.of (ApsTmpStore.ITEMS_KEY, ApsTmpStore.VARIANTS_KEY));
            dependencies.put (ApsTmpStore.ITEM_MODELS_KEY, Set.of (ApsTmpStore.ITEMS_KEY));
            dependencies.put (ApsTmpStore.ITEM_MODEL_VARIANTS_KEY, Set.of (ApsTmpStore.ITEM_MODELS_KEY, ApsTmpStore.ITEM_VARIANTS_KEY));
            dependencies.put (ApsTmpStore.ROUTES_KEY, Set.of (ApsTmpStore.ITEMS_KEY));
            dependencies.put (ApsTmpStore.ROUTE_ROWS_KEY, Set.of (ApsTmpStore.ROUTES_KEY, ApsTmpStore.ITEM_VARIANTS_KEY, ApsTmpStore.GROUPS_KEY));
            dependencies.put (ApsTmpStore.BOMS_KEY, Set.of (ApsTmpStore.ROUTE_ROWS_KEY, ApsTmpStore.ITEM_VARIANTS_KEY, ApsTmpStore.ITEMS_KEY));
            dependencies.put (ApsTmpStore.ROUTE_ROW_LINKS_KEY, Set.of (ApsTmpStore.ROUTE_ROWS_KEY));
            dependencies.put (ApsTmpStore.ROUTE_ROW_SECONDARY_CONSTRAINTS_KEY, Set.of (ApsTmpStore.ROUTE_ROWS_KEY, ApsTmpStore.SECONDARY_CONSTRAINTS_KEY));
            dependencies.put (ApsTmpStore.ROUTE_ROW_SECONDARY_CONSTRAINT_GROUPS_KEY, Set.of (ApsTmpStore.ROUTE_ROWS_KEY, ApsTmpStore.SECONDARY_CONSTRAINT_GROUPS_KEY));
            dependencies.put (ApsTmpStore.ROUTE_ROW_ATTRIBUTE_VALUES_KEY, Set.of (ApsTmpStore.ROUTE_ROWS_KEY, ApsTmpStore.ATTRIBUTE_VALUES_KEY));
        } // if
        if (importOperativeData) {
            dependencies.put (ApsTmpStore.CUSTOMER_ORDERS_KEY, importBaseData ?
                Set.of (ApsTmpStore.CUSTOMERS_KEY)
                : Set.of ());
            dependencies.put (ApsTmpStore.DEMANDS_KEY, importBaseData ?
                Set.of (ApsTmpStore.CUSTOMER_ORDERS_KEY, ApsTmpStore.ITEMS_KEY, ApsTmpStore.PLANTS_KEY, ApsTmpStore.WAREHOUSES_KEY, ApsTmpStore.JOBS_KEY, ApsTmpStore.SUB_JOBS_KEY)
                : Set.of (ApsTmpStore.CUSTOMER_ORDERS_KEY));
            dependencies.put (ApsTmpStore.SUPPLY_ORDERS_KEY, importBaseData ?
                Set.of (ApsTmpStore.VENDORS_KEY)
                : Set.of ());
            dependencies.put (ApsTmpStore.SUPPLIES_KEY, importBaseData ?
                Set.of (ApsTmpStore.SUPPLY_ORDERS_KEY, ApsTmpStore.ITEMS_KEY, ApsTmpStore.PLANTS_KEY, ApsTmpStore.WAREHOUSES_KEY, ApsTmpStore.JOBS_KEY, ApsTmpStore.SUB_JOBS_KEY)
                : Set.of (ApsTmpStore.SUPPLY_ORDERS_KEY));
            dependencies.put (ApsTmpStore.SHOP_ORDERS_KEY, importBaseData ?
                Set.of (ApsTmpStore.ITEMS_KEY, ApsTmpStore.PLANTS_KEY, ApsTmpStore.WAREHOUSES_KEY, ApsTmpStore.JOBS_KEY, ApsTmpStore.SUB_JOBS_KEY, ApsTmpStore.CUSTOMERS_KEY, ApsTmpStore.ROUTES_KEY, ApsTmpStore.SHOP_ORDER_ICONS_KEY)
                : Set.of ());
            dependencies.put (ApsTmpStore.CO_PRODUCTS_KEY, importBaseData ?
                Set.of (ApsTmpStore.ITEMS_KEY, ApsTmpStore.SHOP_ORDERS_KEY)
                : Set.of (ApsTmpStore.SHOP_ORDERS_KEY));
            dependencies.put (ApsTmpStore.AGGREGATED_OPERATIONS_KEY, importBaseData ?
                Set.of (ApsTmpStore.SHOP_ORDERS_KEY, ApsTmpStore.ROUTE_ROWS_KEY, ApsTmpStore.OPERATION_COLORS_KEY, ApsTmpStore.GROUPS_KEY, ApsTmpStore.SCENARIO_RESOURCES_KEY)
                : Set.of (ApsTmpStore.SHOP_ORDERS_KEY));
            dependencies.put (ApsTmpStore.MULTIPALLET_OPERATIONS_KEY, importBaseData ?
                Set.of (ApsTmpStore.SHOP_ORDERS_KEY, ApsTmpStore.ROUTE_ROWS_KEY, ApsTmpStore.OPERATION_COLORS_KEY, ApsTmpStore.GROUPS_KEY, ApsTmpStore.SCENARIO_RESOURCES_KEY)
                : Set.of (ApsTmpStore.SHOP_ORDERS_KEY));
            dependencies.put (ApsTmpStore.OPERATIONS_KEY, importBaseData ?
                Set.of (ApsTmpStore.SHOP_ORDERS_KEY, ApsTmpStore.ROUTE_ROWS_KEY, ApsTmpStore.OPERATION_COLORS_KEY, ApsTmpStore.AGGREGATED_OPERATIONS_KEY, ApsTmpStore.MULTIPALLET_OPERATIONS_KEY, ApsTmpStore.GROUPS_KEY, ApsTmpStore.SCENARIO_RESOURCES_KEY)
                : Set.of (ApsTmpStore.SHOP_ORDERS_KEY, ApsTmpStore.AGGREGATED_OPERATIONS_KEY, ApsTmpStore.MULTIPALLET_OPERATIONS_KEY));
            dependencies.put (ApsTmpStore.MATERIALS_KEY, importBaseData ?
                Set.of (ApsTmpStore.ITEMS_KEY, ApsTmpStore.OPERATIONS_KEY, ApsTmpStore.WAREHOUSES_KEY, ApsTmpStore.BOMS_KEY)
                : Set.of (ApsTmpStore.OPERATIONS_KEY));
            dependencies.put (ApsTmpStore.OPERATION_LINKS_KEY, importBaseData ?
                Set.of (ApsTmpStore.OPERATIONS_KEY)
                : Set.of (ApsTmpStore.OPERATIONS_KEY));
            dependencies.put (ApsTmpStore.ORDER_LINKS_KEY, importBaseData ?
                Set.of (ApsTmpStore.ITEMS_KEY, ApsTmpStore.DEMANDS_KEY, ApsTmpStore.MATERIALS_KEY, ApsTmpStore.SUPPLIES_KEY, ApsTmpStore.SHOP_ORDERS_KEY, ApsTmpStore.CO_PRODUCTS_KEY)
                : Set.of (ApsTmpStore.DEMANDS_KEY, ApsTmpStore.MATERIALS_KEY, ApsTmpStore.SUPPLIES_KEY, ApsTmpStore.SHOP_ORDERS_KEY, ApsTmpStore.CO_PRODUCTS_KEY));
            dependencies.put (ApsTmpStore.OPERATION_SCENARIO_SECONDARY_CONSTRAINTS_KEY, importBaseData ?
                Set.of (ApsTmpStore.OPERATIONS_KEY, ApsTmpStore.SCENARIO_SECONDARY_CONSTRAINTS_KEY)
                : Set.of (ApsTmpStore.OPERATIONS_KEY));
            dependencies.put (ApsTmpStore.OPERATION_SECONDARY_CONSTRAINT_GROUPS_KEY, importBaseData ?
                Set.of (ApsTmpStore.OPERATIONS_KEY, ApsTmpStore.SECONDARY_CONSTRAINT_GROUPS_KEY)
                : Set.of (ApsTmpStore.OPERATIONS_KEY));
            dependencies.put (ApsTmpStore.OPERATION_ATTRIBUTE_VALUES_KEY, importBaseData ?
                Set.of (ApsTmpStore.OPERATIONS_KEY, ApsTmpStore.ATTRIBUTE_VALUES_KEY, ApsTmpStore.ROUTE_ROW_ATTRIBUTE_VALUES_KEY)
                : Set.of (ApsTmpStore.OPERATIONS_KEY));
            dependencies.put (ApsTmpStore.OPERATION_RESOURCE_EXCEPTIONS_KEY, importBaseData ?
                Set.of (ApsTmpStore.OPERATIONS_KEY, ApsTmpStore.SCENARIO_RESOURCES_KEY)
                : Set.of (ApsTmpStore.OPERATIONS_KEY));
        } // if
        return dependencies;
    } // getEntityDependencies

    public TmpOutput exportAll (long sceUid, String token, ExecutionContext context, SWriteCon wCon, SDirectWriteCon wCon2, SWriteRemCon wrCon) throws SException {
        String scenarioCode = "";
        String delimiter = "#_#";
        String executionType = "E";
        ApsSce scenario = ApsSceDao.get ().getById (sceUid, context, wCon);

        // Controllo se son presenti scenari bloccati
        List<ApsSce> apsSce = getAllLockedScenarios (context, wCon);
        if (!apsSce.isEmpty ()) {
            throw ExceptionLogic.error (ApsSceMessage.sceSceLck, context, wCon);
        } // if

        try {
            if (!scenario.isValid ()) {
                throw ExceptionLogic.error (ApsSceMessage.sceSceNotVld, context, wCon);
            } // if

            scenarioCode = scenario.getSceCod ();
            // Blocco lo scenario
            scenario.setFlgLck (true);
            scenario.setExcTyp (executionType);
            scenario.setTssStrExc (new SDateTime ());
            scenario.setTssEndExc (null);
            scenario = ApsSceDao.get ().updateAll (scenario, true, context, wCon2);

            // Preparo i dati da esportare
            ApsOprTmp.get ().removeAll (wCon, wrCon);
            ApsShpOrdTmp.get ().removeAll (wCon, wrCon);
            SQuery s = SQuery.of (context, wCon).from (SAlias.ApsShpOrd, "ShpOrd", true);
            s.withUserFields ();
            s.innerJoin ("ShpOrd", SAlias.ApsShpOrd_FKI_Sts, "Sts", true);
            s.innerJoin ("ShpOrd", SAlias.ApsShpOrd_FKI_Itm, "Itm", true);
            s.innerJoin ("ShpOrd", SAlias.ApsShpOrd_FKO_OprLst, "Opr", true);
            s.withUserFields ();
            s.whereEQ ("ShpOrd", ApsShpOrdFields.SceUid, sceUid);
            s.execute ();
            List<ApsShpOrd> shopOrders = s.readMergedValuesAndCast (ApsShpOrd.class);
            s.close ();
            for (ApsShpOrd shopOrder : shopOrders) {
                List<ApsOpr> operations = shopOrder.getOprLst ();
                shopOrder.setSce (scenario);
                AngItm item = AngItmDao.get ().getById (shopOrder.getItmUid (), context, wCon);
                shopOrder.setItm (item);
                // TODO possibile ottimizzazione: Evitare di fare una query per ogni dato, valutare se mettere la join sopra o fare un'altra query per le fasi.
                for (ApsOpr o : operations) {
                    o.setShpOrd (shopOrder);
                    AngApsOprSts sts = AngApsOprStsDao.get ().getById (o.getStsUid (), context, wCon);
                    o.setSts (sts);
                    if (o.getSchResUid () != null) {
                        ApsSceRes sceRes = ApsSceResDao.get ().getById (o.getSchResUid (), context, wCon);
                        AngApsRes apsRes = AngApsResDao.get ().getById (sceRes.getApsResUid (), context, wCon);
                        AngRes res = AngResDao.get ().getById (apsRes.getResUid (), context, wCon);
                        apsRes.setRes (res);
                        sceRes.setApsRes (apsRes);
                        o.setSchRes (sceRes);
                    } // if
                    if (o.getPlnGrpUid () != null) {
                        AngGrp grp = AngGrpDao.get ().getById (o.getPlnGrpUid (), context, wCon);
                        o.setPlnGrp (grp);
                    } // if
                    if (o.getPrdCycOprUid () != null) {
                        AngPrdCycOpr prdCycOpr = AngPrdCycOprDao.get ().getById (o.getPrdCycOprUid (), context, wCon);
                        AngPrdCyc prdCyc = AngPrdCycDao.get ().getById (prdCycOpr.getPrdCycUid (), context, wCon);
                        AngPrdCycTyp prdCycTyp = AngPrdCycTypDao.get ().getById (prdCyc.getPrdCycTypUid (), context, wCon);
                        AngItm itm = AngItmDao.get ().getById (prdCyc.getItmUid (), context, wCon);
                        prdCycOpr.setPrdCyc (prdCyc);
                        prdCyc.setTyp (prdCycTyp);
                        prdCyc.setItm (itm);
                        o.setPrdCycOpr (prdCycOpr);
                    } // if
                    ApsOprTmp.get ().insertRow (o, delimiter, context, wCon, wrCon);
                } // for
                ApsShpOrdTmp.get ().insertRow (shopOrder, delimiter, context, wCon, wrCon);
            } // for
            wrCon.commit ();
            ApsDatabaseTmpUtil.get ().exportData (scenarioCode, token, context, wCon);
            LogsTmp logTmp = new LogsTmp ();
            List<LogTmpEntity> errorsList = logTmp.getLogTempEntityList (APS_LOGS_TMP_TABLE_NAME, context, wCon, wrCon);
            if (!ArrayUtil.isEmpty (errorsList)) {
                return new TmpOutput (false, errorsList, null);
            } // if
            // Sblocco scenario
            scenario.setFlgLck (false);
            scenario.setTssEndExc (new SDateTime ());
            scenario = ApsSceDao.get ().updateAll (scenario, true, context, wCon);

            // Copia dello scenario esportato nello scenario di esportazione synergy
            if (scenario.getExpSceUid () != null) {
                ApsSceCopyLogic.get ().copyFromScenarioToScenario (sceUid, scenario.getExpSceUid (), false, true, context, wCon);
            } // if
            return new TmpOutput (true, null, null);
        } catch (Throwable e) {
            // In caso di errore dobbiamo rilasciare la connessione wCon altrimenti non possiamo fare modifiche allo scenario perché in LOCK,
            // A causa di referenze di record, i database lockano anche le tabelle referenziate per permettere l'integrità dei dati.
            ConnectionsUtil.safeRollback (wCon);
            ConnectionsUtil.safeRollback (wrCon);
            scenario.setFlgLck (false);
            scenario = ApsSceDao.get ().updateAll (scenario, true, context, wCon2);
            throw e;
        } // try - catch
    } // exportAll

    public void importExternal (String token, ExecutionContext context, SWriteCon wCon, SReadRemCon rrCon) throws SException {
        String delimiter = "#_#";

        // Importazione dati nel database temporaneo
        ApsDatabaseTmpUtil.get ().importExternalData (token, context, wCon);

        // Assenze
        SQuery s = SQuery.of (context, wCon).from (SAlias.AngRes, "AngRes", true);
        s.whereActive ("AngRes");
        s.execute ();
        List<AngRes> angResList = s.readMergedValuesAndCast (AngRes.class);
        s.close ();
        Map<String, Long> angResMap = angResList.stream ().collect (Collectors.toMap (res -> res.getResCod (), res -> res.getUid ()));

        Map<String, CalResAbs> newItems = CalResAbsTmp.get ().getMapByCode (null, delimiter, context, wCon, rrCon);
        SQuery q = SQuery.of (context, wCon);
        q.from (SAlias.CalResAbs, "CalResAbs", true);
        q.innerJoin ("CalResAbs", SAlias.CalResAbs_FKI_Res, "Res", true);
        q.innerJoin ("CalResAbs", SAlias.CalResAbs_FKI_Day, "AngDay", true);
        q.whereActive ("CalResAbs");
        q.execute ();
        List<CalResAbs> oldItems = q.readMergedValuesAndCast (CalResAbs.class);
        q.close ();

        // Trasformo la lista in una mappa <String, CalResAbs> per fare il check (la stringa è formata così: Day#_#Res)
        Map<String, CalResAbs> newItemsCheck = new HashMap<> ();
        for (Entry<String, CalResAbs> entry : newItems.entrySet ()) {
            CalResAbs item = entry.getValue ();
            newItemsCheck.put (item.getDay ().getDayCod ().getUniversal () + delimiter + item.getRes ().getResCod (), item);
        } // for

        List<CalResAbs> oldEtlItems = new ArrayList<> ();
        for (CalResAbs abs : oldItems) {
            if (StringUtil.areEquals (abs.getAbsSrcUid (), "ETL")) {
                oldEtlItems.add (abs);
            } else if (newItemsCheck.containsKey (abs.getDay ().getDayCod ().getUniversal () + delimiter + abs.getRes ().getResCod ())) {
                // Cancello le assenze create da utente sovrapposte ad almeno un'assenza giornaliera
                oldEtlItems.add (abs);
            } // if - else
        } // for

        List<TmpField<?, CalResAbs>> calResAbsFields = CalResAbsTmp.get ().getFields ();
        CompareResult<CalResAbs> compCalResAbs = getResourceAbsences (oldEtlItems, newItems, calResAbsFields, delimiter, context, wCon, rrCon);
        List<CalResAbs> toRemoveCalResAbs = compCalResAbs.onlyLeft;

        for (CalResAbs resAbs : toRemoveCalResAbs) { SysCalLogic.get ().deleteCalResAbs (resAbs.getUid (), context, wCon); }

        upsertCalResAbs (compCalResAbs, angResMap, calResAbsFields, delimiter, context, wCon);
        compCalResAbs = null;

        // installare un lavoro schedulato che chiami importExternal disabilitato di default
    } // importExternal

    /************************************************************************************/
    /** ApsSce **************************************************************************/
    /************************************************************************************/

    // Metodo che restituisce tutti gli scenari bloccati, ovvero con il campo FlgLck settato a true
    public List<ApsSce> getAllLockedScenarios (ExecutionContext context, SReadCon rCon) throws SException {
        SQuery s = SQuery.of (context, rCon).from (SAlias.ApsSce, "ApsSce", false);
        s.whereEQ ("ApsSce", ApsSceFields.FlgLck, true);
        s.execute ();
        List<ApsSce> apsSce = s.readMergedValuesAndCast (ApsSce.class);
        s.close ();
        return apsSce;
    } // getAllLockedScenarios

    /************************************************************************************/
    /** AngItm **************************************************************************/
    /************************************************************************************/

    private void addDummyAngItm (ApsTmpStore store, Map<Long, AngItm> entityByUid) {
        // Se ho creato un dummy, lo aggiungo alle mappe.
        AngItm dummyAngItm = store.getDummyAngItm ();
        if (dummyAngItm != null) {
            entityByUid.put (dummyAngItm.getUid (), dummyAngItm);
        } // if
    } // addDummyAngItm

    /************************************************************************************/
    /** AngPrdCyc ***********************************************************************/
    /************************************************************************************/

    private void importAngPrdCyc (ApsImportContext importContext, ApsImportManager manager) throws SException {
        // Creo l'entità temporanea.
        AngPrdCycTmp prdCycTmp = AngPrdCycTmp.get ();
        // N.B.: Otteniamo le liste da fuori perché una parte serve nell'onNew.
        // Ottengo le preesistenti.
        List<AngPrdCyc> oldEntities = prdCycTmp.getActiveList (importContext);
        Map<String, AngPrdCyc> oldEntitiesByCode = oldEntities.stream ().collect (Collectors.toMap ((entity) -> prdCycTmp.getCode (entity), entity -> entity));
        // Lista di cicli da rimuovere.
        List<AngPrdCyc> insertCandidates = new ArrayList<> ();
        List<AngPrdCyc> updateCandidates = new ArrayList<> ();
        Map<String, AngPrdCyc> removeCandidates = new HashMap<> ();
        // Creo la mappa delle entità aggiornate.
        Map<String, Long> upsertedEntities = new HashMap<> ();
        // Ottengo l'entities handler di base.
        EntitiesHandler<AngPrdCyc, String> entitiesHandler = getCustomAngPrdCycEntitiesHandler (manager, prdCycTmp, importContext, oldEntitiesByCode, insertCandidates, updateCandidates, removeCandidates, upsertedEntities);     ;
        ApsTmpImporter<AngPrdCyc> singleImporter = new ApsTmpImporter<> (
            LOGGER, prdCycTmp, importContext, entitiesHandler, newEntity -> prdCycTmp.align (newEntity, importContext, true),
            insertCandidates, updateCandidates, upsertedEntities, removeCandidates
        );
        singleImporter
            .logStart ()
            .initLists()
            .runEntitiesHandler ()
            .logCandidates ()
            .runInsert ()
            .runUpdate ()
            .setEntitiesOnStore ()
            .logEnd ();
    } // importAngPrdCyc

    private EntitiesHandler<AngPrdCyc, String> getCustomAngPrdCycEntitiesHandler (
        ApsImportManager manager,
        AngPrdCycTmp tempInstance,
        ApsImportContext importContext,
        Map<String, AngPrdCyc> oldEntitiesByCode,
        List<AngPrdCyc> toInsert,
        List<AngPrdCyc> toUpdate,
        Map<String, AngPrdCyc> toRemove,
        Map<String, Long> upsertedEntities
    ) throws SException {
        return manager.getEntitiesHandlerWithAlign (tempInstance, upsertedEntities, toInsert, toUpdate, toRemove)
            .onNew (newEntity -> {
                // Se arriva che dev'essere eliminato in principio, non lo creo.
                if (BooleanUtil.isNotTrue (tempInstance.getImpDel (newEntity, importContext))) {
                    // Serve per cancellare eventuali cicli standard per lo stesso articolo del ciclo che si sta inserendo.
                    AngPrdCyc toDelete = oldEntitiesByCode.get (newEntity.getItm ().getItmCod ());
                    if (toDelete != null && newEntity.getPrdCycStd ()) {
                        // se già presente un ciclo standard per lo stesso articolo viene eliminato il ciclo già presente
                        SysItmLogic.get ().deleteAngPrdCyc (toDelete.getUid (), importContext.getContext (), importContext.getWCon ());
                        // Rimuovo il ciclo cancellato dalla mappa dei toReturn, altrimenti andrebbe in errore nell'import
                        // degli ordini associati.
                        String toRemoveCode = tempInstance.getCode (toDelete);
                        upsertedEntities.remove (toRemoveCode);
                    } // if
                    
//                    tempInstance.align (newEntity, ctx, true);
//                    String newEntityCode = tempInstance.getCode (newEntity);

                    // Check sul codice @dummy, generato da aps, l'align potrebbe aver cambiato il codice,
                    // assegnandogli dei codici dummy per riparare la mancanza di records referenziati.
//                    if (upsertedEntities.containsKey (newEntityCode) || oldEntitiesByCode.containsKey (newEntityCode)) {
//                        return null;
//                    } else {
                        // Insert.
                        toInsert.add (newEntity);
                        return newEntity;
//                    } // if - else
                } // if
                return null;
            });
    } // getCustomAngPrdCycEntitiesHandler

    /************************************************************************************/
    /** ApsShpOrd ***********************************************************************/
    /************************************************************************************/
    
    private void addDummyApsShpOrd (ApsTmpStore store, Map<Long, ApsShpOrd> entityByUid) {
        // Se ho creato un dummy, lo aggiungo alle mappe.
        ApsShpOrd dummyApsShpOrd = store.getDummyApsShpOrd ();
        if (dummyApsShpOrd != null) {
            entityByUid.put (dummyApsShpOrd.getUid (), dummyApsShpOrd);
        } // if
    } // addDummyApsShpOrd

    /************************************************************************************/
    /** ApsOpr **************************************************************************/
    /************************************************************************************/

    private void addDummyApsOpr (ApsTmpStore store, Map<Long, ApsOpr> entityByUid, Map<Long, Set<ApsOpr>> entitiesByShpOrdUid, Map<Long, Set<ApsOpr>> entitiesByOprAggUid,
        Map<Long, Set<ApsOpr>> entitiesByMulPalUid) {
        // Se ho creato un dummy, lo aggiungo alle mappe.
        ApsOpr dummyApsOpr = store.getDummyApsOpr ();
        if (dummyApsOpr != null) {
            entityByUid.put (dummyApsOpr.getUid (), dummyApsOpr);
            entitiesByShpOrdUid.computeIfAbsent (dummyApsOpr.getShpOrdUid (), k -> new HashSet<> ()).add (dummyApsOpr);
            if (dummyApsOpr.getOprAggUid () != null) {
                entitiesByOprAggUid.computeIfAbsent (dummyApsOpr.getOprAggUid (), k -> new HashSet<> ()).add (dummyApsOpr);
            } // if
            if (dummyApsOpr.getMulPalUid () != null) {
                entitiesByMulPalUid.computeIfAbsent (dummyApsOpr.getMulPalUid (), k -> new HashSet<> ()).add (dummyApsOpr);
            } // if
        } // if
    } // addDummyApsOpr

    /************************************************************************************/
    /** ApsOrdLnk ***********************************************************************/
    /************************************************************************************/

    public record ApsOrdLnkOriginalField (BigDecimal itmQty, boolean lnkFix) { }

    private List<ApsOrdLnk> insertApsOrdLnk (
        long scenarioUid, SDateTime tssImp,
        ApsTmpStore store,
        Map<Long, AngItm> itemsMap,
        Map<Long, ApsDem> demandsMap, Map<Long, ApsMat> materialsMap,
        Map<Long, ApsSup> suppliesMap, Map<Long, ApsShpOrd> shopOrdersMap, Map<Long, ApsItmCop> coProductsMap,
        Map<Long, ApsOpr> operationsMap, Map<Long, ApsOprLnk> operationLinksMap,
        boolean forceRemove,
        Map<Long, Set<ApsOrdLnk>> ordLnksByMatUid, Map<Long, Set<ApsOrdLnk>> ordLnksByDemUid, Map<Long, Set<ApsOrdLnk>> ordLnksByShpOrdUid, Map<Long, Set<ApsOrdLnk>> ordLnksBySupUid, // OUTPUT
        String delimiter,
        ExecutionContext context, SWriteCon wCon, SReadRemCon rrCon
    ) throws SException {
        List<ApsOrdLnk> toRemoveApsOrdLnk = new ArrayList<> ();

        List<ApsOrdLnk> oldItems = ApsLnkLogic.get ().getOrderLinks (scenarioUid, context, wCon);
        List<ApsOrdLnk> newItems = ApsOrdLnkTmp.get ().getList (tssImp, delimiter, context, wCon, rrCon);
        Iterator<ApsOrdLnk> newItemsIt = newItems.iterator ();

        // Anche se newItems è vuoto, proseguo avanti comunque per sistemare eventuali quantità incorrette.

        // Creo una mappa dei vecchi legami, per capire cosa va mantenuto o cancellato.
        Map<String, ApsOrdLnk> oldOrderLinkByKey = new HashMap<> ();
        for (ApsOrdLnk oldItem : oldItems) {
            String key = ApsLnkContext.generateOrderLinkKey (oldItem);
            oldOrderLinkByKey.put (key, oldItem);
        } // for

        // Creo un array che conterrà l'insieme dei vecchi legami e quelli ipoteticamente nuovi da inserire.
        // N.B.: Questo array serve perché passato al linkContext, filtrerà via eventuali legami con quantità o entità errate.
        List<ApsOrdLnk> possibleOrderLinks = new ArrayList<> ();
        // Creo una mappa per tener traccia dei valori prima di eventuali aggiornamenti sui legami.
        Map<Long, ApsOrdLnkOriginalField> orderLinkOriginalFieldsByUid = new HashMap<> ();

        // Creo dei set per tener traccia di tutto quello che è valido e devo aggiornare o inserire se nuovo, successivamente.
        Set<ApsOrdLnk> effectiveOldOrderLinks = new HashSet<> ();
        Set<ApsOrdLnk> effectiveNewOrderLinks = new HashSet<> ();

        // Rimuovo i legami che non hanno riferimenti ad entità effettivamente presenti.
        while (newItemsIt.hasNext ()) {
            boolean toRemove = false;
            ApsOrdLnk newItem = newItemsIt.next ();
            
            // Usiamo "inserimento/modifica" perchè altrimenti per capire se il legame esisteva o no nel db di Aps,
            // bisogna fare una mappa con chiave la concatenazione dei campi "Codice" di ogni entità, ottimizziamo lasciando perdere quest'informazione.
            
            Long demUid = null;
            Long matUid = null;
            if (newItem.getDem () != null) {
                demUid = store.getDemands ().get (newItem.getDem ().getDemCod ());
                if (demUid == null) {
                    List<Object> values = Arrays.asList (newItem.getDem ().getDemCod (), "inserimento/modifica", newItem.getUid ());
                    FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpDemUidOrdLnkExp, values, ApsTmpTableKey.ApsOrdLnk.getKeyLabel (), true, store, context, wCon);
                    demandsMap.remove (demUid);
                    toRemove = true;
                } else {
                    newItem.setDemUid (demUid);
                } // if - else
            } else if (newItem.getMat () != null) {
                String matCod = ApsMatTmp.get ().getCode (newItem.getMat ());
                matUid = store.getMaterials ().get (matCod);
                ApsMat mat = materialsMap.get (matUid);
                if (mat == null) {
                    toRemove = true;
                } else {
                    ApsOpr opr = operationsMap.get (mat.getOprUid ());
                    if (opr == null || opr == store.getDummyApsOpr ()) {
                        toRemove = true;
                    } // if
                } // if - else
                if (toRemove) {
                    List<Object> values = Arrays.asList (matCod, "inserimento/modifica", newItem.getUid ());
                    FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpMatUidOrdLnkExp, values, ApsTmpTableKey.ApsOrdLnk.getKeyLabel (), true, store, context, wCon);
                    materialsMap.remove (matUid);
                } else {
                    newItem.setMatUid (matUid);
                } // if - else
            } // if - else

            Long supUid = null;
            Long shpOrdUid = null;
            if (newItem.getSup () != null) {
                supUid = store.getSupplies ().get (newItem.getSup ().getSupCod ());
                if (supUid == null) {
                    List<Object> values = Arrays.asList (newItem.getSup ().getSupCod (), "inserimento/modifica", newItem.getUid ());
                    FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpSupUidOrdLnkExp, values, ApsTmpTableKey.ApsOrdLnk.getKeyLabel (), true, store, context, wCon);
                    suppliesMap.remove (supUid);
                    toRemove = true;
                } else {
                    newItem.setSupUid (supUid);
                } // if - else
            } else if (newItem.getShpOrd () != null) {
                shpOrdUid = store.getShopOrders ().get (newItem.getShpOrd ().getShpOrdNum ());
                ApsShpOrd shpOrd = shopOrdersMap.get (shpOrdUid);
                if (shpOrd == null || shpOrd == store.getDummyApsShpOrd ()) {
                    List<Object> values = Arrays.asList (newItem.getShpOrd ().getShpOrdNum (), "inserimento/modifica", newItem.getUid ());
                    FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpShpOrdUidOrdLnkExp, values, ApsTmpTableKey.ApsOrdLnk.getKeyLabel (), true, store, context, wCon);
                    shopOrdersMap.remove (shpOrdUid);
                    toRemove = true;
                } else {
                    newItem.setShpOrdUid (shpOrdUid);
                } // if - else
            } // if - else

            if (newItem.getItm () != null) {
                Long itmUid = store.getItems ().get (newItem.getItm ().getItmCod ());
                if (itmUid == null) {
                    List<Object> values = Arrays.asList (newItem.getItm ().getItmCod (), "inserimento/modifica", newItem.getUid ());
                    FctTmpUtil.get ().createWarning (ApsTmpMessage.tmpImpItmUidOrdLnkExp, values, ApsTmpTableKey.ApsOrdLnk.getKeyLabel (), true, store, context, wCon);
                    
                    if (demUid != null) { demandsMap.remove (demUid); }
                    else if (matUid != null) { materialsMap.remove (matUid); }
                    
                    if (supUid != null) { suppliesMap.remove (supUid); }
                    else if (shpOrdUid != null) { shopOrdersMap.remove (shpOrdUid); }
                    
                    itemsMap.remove (itmUid);
                    
                    toRemove = true;
                } else {
                    newItem.setItmUid (itmUid);
                } // if - else
            } // if
            
            // Man mano che trovo un match lo rimuovo dalla mappa dei vecchi, in modo che successivamente, abbia solo i "vecchi" che non hanno avuto un match col "nuovo".
            String key = ApsLnkContext.generateOrderLinkKey (newItem);
            ApsOrdLnk oldOrderLink = oldOrderLinkByKey.remove (key);

            // Se è da cancellare non lo elaboro e lo imposto come da cancellare.
            if (!toRemove) {
                if (oldOrderLink == null) {
                    if (newItem.getImpDel ()) {
                        toRemove = true;
                    } // if
                } else {
                    if (oldOrderLink.getImpDel () == null) {
                        // E' determinato da quello che mi arriva.
                        if (newItem.getImpDel ()) {
                            toRemove = true;
                        } // if
                    } else {
                        // In questo caso vogliamo mantere il record anche se c'è il force.
                        if (oldOrderLink.getImpDel ()) {
                            toRemove = true;
                        } // if
                    } // if - else
                } // if - else
            } // if

            if (toRemove) {
                // Non lo elaboro successivamente.
                newItemsIt.remove ();

                // Controllo alla fine se, oltre al flag eliminato, le entità del legame sono valide, altrimenti va cancellato in questo punto.
                if (oldOrderLink != null) {
                    toRemoveApsOrdLnk.add (oldOrderLink);
                } // if
            } else {
                if (oldOrderLink == null) {
                    newItem.setSceUid (scenarioUid);
                    newItem.setImpDel (null);
                    effectiveNewOrderLinks.add (newItem);
                    possibleOrderLinks.add (newItem);
                } else {
                    orderLinkOriginalFieldsByUid.put (oldOrderLink.getUid (), new ApsOrdLnkOriginalField (oldOrderLink.getItmQty (), oldOrderLink.getLnkFix ()));
                    oldOrderLink.setItmQty (newItem.getItmQty ());
                    oldOrderLink.setLnkFix (newItem.getLnkFix ());
                    effectiveOldOrderLinks.add (oldOrderLink);
                    possibleOrderLinks.add (oldOrderLink);
                } // if - else
            } // if - else
        } // while

        // I legami restati sulla mappa, che non hanno avuto riscontro con quelli nuovi, sono da eliminare se impostati come da "Eliminare", e non sono fissi.
        for (Entry<String, ApsOrdLnk> entry : oldOrderLinkByKey.entrySet ()) {
            ApsOrdLnk oldOrderLink = entry.getValue ();
            boolean toRemove = false;
            if (BooleanUtil.isTrue (oldOrderLink.getImpDel ())) {
                toRemove = true;
                toRemoveApsOrdLnk.add (oldOrderLink);
            } else if (oldOrderLink.getImpDel () == null && forceRemove) {
                if (!oldOrderLink.getLnkFix ()) {
                    toRemove = true;
                    toRemoveApsOrdLnk.add (oldOrderLink);
                } // if
            } // if - else
            if (!toRemove) {
                orderLinkOriginalFieldsByUid.put (oldOrderLink.getUid (), new ApsOrdLnkOriginalField (oldOrderLink.getItmQty (), oldOrderLink.getLnkFix ()));
                effectiveOldOrderLinks.add (oldOrderLink);
                possibleOrderLinks.add (oldOrderLink);
            } // if
        } // for

        // Imposto oggi se passato, altrimenti assegno "ora".
        ApsLnkConfig lnkConfig = new ApsLnkConfig ();
        lnkConfig.now = lnkConfig.now != null ? lnkConfig.now : new SDateTime ();

        // Genero il context con i dati già pronti.
        List<ApsOpr> operations = new ArrayList<> (operationsMap.values ()); // loadOperations (scenarioUid, context, rCon);
        List<ApsLnkOprEntity> operationEntities = new ArrayList<> ();
        List<ApsOprLnk> operationLinks = new ArrayList<> (operationLinksMap.values ()); // loadOperationLinks (scenarioUid, context, rCon);
        List<ApsLnkOprLnkEntity> operationLinkEntities = new ArrayList<> ();
        List<ApsItmCop> coProducts = new ArrayList<> (coProductsMap.values ()); // loadCoProducts (scenarioUid, context, rCon);
        List<AngItm> items = new ArrayList<> (itemsMap.values ()); // loadItems (context, rCon);
        List<ApsLnkItmEntity> itemEntities = new ArrayList<> ();
        List<ApsDem> demands = new ArrayList<> (demandsMap.values ()); // loadDemands (scenarioUid, context, rCon);
        List<ApsLnkDemEntity> demandEntities = new ArrayList<> ();
        List<ApsMat> materials = new ArrayList<> (materialsMap.values ()); // loadMaterials (scenarioUid, context, rCon);
        List<ApsLnkMatEntity> materialEntities = new ArrayList<> ();
        List<ApsSup> supplies = new ArrayList<> (suppliesMap.values ()); // loadSupplies (scenarioUid, context, rCon);
        List<ApsLnkSupEntity> supplyEntities = new ArrayList<> ();
        List<ApsShpOrd> shopOrders = new ArrayList<> (shopOrdersMap.values ());
        List<ApsLnkShpOrdEntity> shopOrderEntities = new ArrayList<> ();
        Map<Long, ApsLnkItmEntity> itemByUid = new HashMap<> ();
        Map<Long, ApsLnkDemEntity> demandByUid = new HashMap<> ();
        Map<Long, ApsLnkMatEntity> materialByUid = new HashMap<> ();
        Map<Long, ApsLnkSupEntity> supplyByUid = new HashMap<> ();
        Map<Long, ApsLnkOprEntity> operationByUid = new HashMap<> ();
        Map<Long, ApsLnkShpOrdEntity> shopOrderByUid = new HashMap<> ();

        // Scorro gli articoli creando entità Articoli.
        for (AngItm item : items) {
            ApsLnkItmEntity itemEntity = new ApsLnkItmEntity (item);
            itemEntities.add (itemEntity);
            itemByUid.put (itemEntity.getUid (), itemEntity);
        } // for

        // Scorro le domande creando entità Domande.
        for (ApsDem demand : demands) {
            // Potrei avere articolo importato ma ora cancellato e assegnato all'entità creata in aps e non importata, l'entità non è da considerare.
            ApsLnkItmEntity itemEntity = itemByUid.get (demand.getItmUid ());
            if (itemEntity != null) {
                ApsLnkDemEntity demandEntity = new ApsLnkDemEntity (demand, itemEntity);
                demandEntities.add (demandEntity);
                demandByUid.put (demandEntity.getUid (), demandEntity);
            } // if
        } // for

        // Scorro gli approvviggionamenti creando entità Approvvigionamenti.
        for (ApsSup supply : supplies) {
            // Potrei avere articolo importato ma ora cancellato e assegnato all'entità creata in aps e non importata, l'entità non è da considerare.
            ApsLnkItmEntity itemEntity = itemByUid.get (supply.getItmUid ());
            if (itemEntity != null) {
                ApsLnkSupEntity supplyEntity = new ApsLnkSupEntity (supply, itemEntity);
                supplyEntities.add (supplyEntity);
                supplyByUid.put (supplyEntity.getUid (), supplyEntity);
            } // if
        } // for

        // Scorro i co-prodotti creando della mappe per ordine di produzione.
        Map<Long, Map<Long, ApsLnkItmEntity>> coProductsMapByShopOrderUid = new HashMap<> ();
        Map<Long, Map<ApsLnkItmEntity, BigDecimal>> coProductQuantitiesByShopOrderUid = new HashMap<> ();
        for (ApsItmCop coProduct : coProducts) {
            coProductsMapByShopOrderUid.compute (coProduct.getShpOrdUid (), (Long shopOrderUid, Map<Long, ApsLnkItmEntity> coProductItems) -> {
                if (coProductItems == null) { coProductItems = new HashMap<> (); }
                // Potrei avere articolo importato ma ora cancellato e assegnato all'entità creata in aps e non importata, l'entità non è da considerare.
                ApsLnkItmEntity itemEntity = itemByUid.get (coProduct.getItmUid ());
                if (itemEntity != null) {
                    coProductItems.put (coProduct.getItmUid (), itemEntity);
                } // if
                return coProductItems;
            });
            coProductQuantitiesByShopOrderUid.compute (coProduct.getShpOrdUid (), (Long shopOrderUid, Map<ApsLnkItmEntity, BigDecimal> coProductQuantities) -> {
                if (coProductQuantities == null) { coProductQuantities = new HashMap<> (); }
                // Potrei avere articolo importato ma ora cancellato e assegnato all'entità creata in aps e non importata, l'entità non è da considerare.
                ApsLnkItmEntity itemEntity = itemByUid.get (coProduct.getItmUid ());
                if (itemEntity != null) {
                    coProductQuantities.put (itemEntity, coProduct.getOrdQty ());
                } // if
                return coProductQuantities;
            });
        } // for

        // Scorro le fasi creando entità Fasi e Ordini di produzione.
        for (ApsShpOrd shopOrder : shopOrders) {
            // Se non esiste l'articolo dell'ordine, non lo carico nella lista.
            ApsLnkItmEntity shopOrderItem = itemByUid.get (shopOrder.getItmUid ());
            // Potrei avere articolo importato ma ora cancellato e assegnato all'entità creata in aps e non importata, l'entità non è da considerare.
            if (shopOrderItem != null) {
                // Mergio l'articolo dell'ordine e tutti i suoi co-prodotti, se presenti.
                Map<Long, ApsLnkItmEntity> shopOrderItemsByItemUid = coProductsMapByShopOrderUid.get (shopOrder.getUid ());
                Map<ApsLnkItmEntity, BigDecimal> shopOrderQuantitiesByItem = coProductQuantitiesByShopOrderUid.get (shopOrder.getUid ());
                // Se uno dei due insiemi è null, allora tutti e due erano null.
                if (shopOrderItemsByItemUid == null) {
                    shopOrderItemsByItemUid = new HashMap<> ();
                    shopOrderQuantitiesByItem = new HashMap<> ();
                } // if
                shopOrderItemsByItemUid.put (shopOrderItem.getUid (), shopOrderItem);
                shopOrderQuantitiesByItem.put (shopOrderItem, shopOrder.getOrdQty ());
                ApsLnkShpOrdEntity shopOrderEntity = new ApsLnkShpOrdEntity (shopOrder, shopOrderItemsByItemUid, shopOrderQuantitiesByItem);
                shopOrderEntity.setDemandEnd (shopOrder.getTssDemEnd ()); // Imposto la data domanda che arriva da Temp.
                shopOrderEntities.add (shopOrderEntity);
                shopOrderByUid.put (shopOrder.getUid (), shopOrderEntity);
            } // if
        } // for

        for (ApsOpr operation : operations) {
            ApsLnkShpOrdEntity shopOrderEntity = shopOrderByUid.get (operation.getShpOrdUid ());
            // Arrivando da import, non sò se i padri sono allineati ai figli.
            if (shopOrderEntity != null) {
                ApsLnkOprEntity operationEntity = new ApsLnkOprEntity (operation, shopOrderEntity);
                operationEntities.add (operationEntity);
                operationByUid.put (operation.getUid (), operationEntity);
            } // if
        } // for

        // Scorro i materiali creando entità Materiali.
        for (ApsMat material : materials) {
            ApsLnkOprEntity operationEntity = operationByUid.get (material.getOprUid ());
            ApsLnkItmEntity itemEntity = itemByUid.get (material.getItmUid ());
            // Arrivando da import, non sò se i padri sono allineati ai figli.
            if (operationEntity != null && itemEntity != null) {
                ApsLnkMatEntity materialEntity = new ApsLnkMatEntity (material, operationEntity, itemEntity, null);
                materialEntities.add (materialEntity);
                materialByUid.put (materialEntity.getUid (), materialEntity);
            } // if
        } // for

        // Scorro le dipendenze fasi creando entità Dipendenze.
        for (ApsOprLnk operationLink : operationLinks) {
            ApsLnkOprEntity preOperationEntity = operationByUid.get (operationLink.getPreOprUid ());
            ApsLnkOprEntity nxtOperationEntity = operationByUid.get (operationLink.getNxtOprUid ());
            // Arrivando da import, non sò se i padri sono allineati ai figli.
            if (preOperationEntity != null && nxtOperationEntity != null) {
                ApsLnkOprLnkEntity operationLinkEntity = new ApsLnkOprLnkEntity (operationLink, null, preOperationEntity, nxtOperationEntity);
                operationLinkEntities.add (operationLinkEntity);
            } // if
        } // for

        Set<ApsLnkEntity> validOldOrderLinksUpdated = new HashSet<> ();

        // Passo i legami provenienti dal gestionale come "precedenti", ma non genero i legami, inizializzo solo le richieste e soddisfazioni.
        ApsLnkContext lnkContext = new ApsLnkContext (
            scenarioUid,
            lnkConfig,
            CacheLogic.get ().getContextRegionalSettings (context, wCon),
            true,
            possibleOrderLinks,
            validOldOrderLinksUpdated,
            operationEntities,
            operationLinkEntities,
            itemEntities,
            demandEntities,
            materialEntities,
            supplyEntities,
            shopOrderEntities,
            ApsLnkRulStdUtil.get ().getStandardLinkByCode (AngApsLnkStd.STANDARD),
            null,
            itemByUid,
            demandByUid,
            materialByUid,
            supplyByUid,
            operationByUid,
            shopOrderByUid,
            null,
            null,
            null,
            null
        );

        // Ottengo le richieste e soddisfazioni, serviranno al salvataggio delle entità per ottenere le quantità.
        Map<Long, ApsLnkReqEntity> requestEntityByDemUid = lnkContext.getRequestEntityByDemUid ();
        Map<Long, ApsLnkReqEntity> requestEntityByMatUid = lnkContext.getRequestEntityByMatUid ();
        Map<Long, ApsLnkSatEntity> satisfactionEntityBySupUid = lnkContext.getSatisfactionEntityBySupUid ();
        Map<Long, ApsLnkSatEntity> satisfactionEntityByShpOrdUid = lnkContext.getSatisfactionEntityByShpOrdUid ();

        // Rimuovo i vecchi legami che sono:
        // 1) Vecchi e che sono:
            // 1) Mancanti.
            // 2) In avanzo.
            // 3) Il cui articolo è variato.
            // 4) Quantità di richieste o soddisfazioni andate a zero a causa di modifiche ad esse.
        List<ApsOrdLnk> toInsertApsOrdLnks = new ArrayList<> ();
        List<ApsOrdLnk> toUpdateApsOrdLnks = new ArrayList<> ();

        var defaultBatchOptions = SBatchOptions.of ().setCheckCode (false);

        for (ApsLnkEntity prevOrderLinkEntity : lnkContext.getMissingPrevOrderLinks ()) {
            ApsOrdLnk orderLink = prevOrderLinkEntity.getLink ();
            // Se non è nell'old, è sicuramente nel new, evitiamo un else - if.
            if (effectiveOldOrderLinks.contains (orderLink)) {
                toRemoveApsOrdLnk.add (orderLink);
            } else {
                lnkContext.updateOrderLinkMissingRelatedEntity (prevOrderLinkEntity.getRequestEntity ());
                toInsertApsOrdLnks.add (orderLink);
            } // if - else
        } // for
        for (ApsLnkEntity prevOrderLinkEntity : lnkContext.getLeftoverPrevOrderLinks ()) {
            ApsOrdLnk orderLink = prevOrderLinkEntity.getLink ();
            // Se non è nell'old, è sicuramente nel new, evitiamo un else - if.
            if (effectiveOldOrderLinks.contains (orderLink)) {
                toRemoveApsOrdLnk.add (orderLink);
            } else {
                toInsertApsOrdLnks.add (orderLink);
            } // if - else
        } // for
        for (ApsLnkEntity prevOrderLinkEntity : lnkContext.getChangedItemPrevOrderLinks ()) {
            ApsOrdLnk orderLink = prevOrderLinkEntity.getLink ();
            if (effectiveOldOrderLinks.contains (orderLink)) {
                toRemoveApsOrdLnk.add (orderLink);
            } // if
        } // for
        for (ApsLnkEntity prevOrderLinkEntity : lnkContext.getItemQuantityNotValidOrderLinks ()) {
            ApsOrdLnk orderLink = prevOrderLinkEntity.getLink ();
            if (effectiveOldOrderLinks.contains (orderLink)) {
                toRemoveApsOrdLnk.add (orderLink);
            } // if
        } // for

        // Aggiorno i vecchi legami che hanno avuto variazione rispetto al nuovo, compresi controlli di quantità.
        for (ApsLnkEntity prevOrderLinkEntity : lnkContext.getPrevFullOrderLinks ()) {
            ApsOrdLnk orderLink = prevOrderLinkEntity.getLink ();
            if (effectiveOldOrderLinks.contains (orderLink)) {
                ApsOrdLnkOriginalField originalField = orderLinkOriginalFieldsByUid.get (orderLink.getUid ());
                if (
                    !NumberUtil.equalsBigDecimal (originalField.itmQty (), orderLink.getItmQty ()) ||
                    originalField.lnkFix () != orderLink.getLnkFix ()
                ) {
                    toUpdateApsOrdLnks.add (orderLink);
                } // if
                if (orderLink.getMatUid () != null) { ordLnksByMatUid.computeIfAbsent (orderLink.getMatUid (), (k) -> new HashSet<> ()).add (orderLink); }
                else if (orderLink.getDemUid () != null) { ordLnksByDemUid.computeIfAbsent (orderLink.getDemUid (), (k) -> new HashSet<> ()).add (orderLink); }
                if (orderLink.getShpOrdUid () != null) { ordLnksByShpOrdUid.computeIfAbsent (orderLink.getShpOrdUid (), (k) -> new HashSet<> ()).add (orderLink); }
                else if (orderLink.getSupUid () != null) { ordLnksBySupUid.computeIfAbsent (orderLink.getSupUid (), (k) -> new HashSet<> ()).add (orderLink); }
            } else {
                toInsertApsOrdLnks.add (orderLink);
            } // if - else
        } // for

        // Aggiorno i vecchi elementi.
        ApsOrdLnkDao.get ().updateAllBatch (toUpdateApsOrdLnks, defaultBatchOptions, context, wCon);

        // Inserisco i nuovi elementi.
        ApsOrdLnkDao.get ().insertBatch (toInsertApsOrdLnks, defaultBatchOptions, context, wCon);
        for (ApsOrdLnk toInsertApsOrdLnk : toInsertApsOrdLnks) {
            if (toInsertApsOrdLnk.getMatUid () != null) { ordLnksByMatUid.computeIfAbsent (toInsertApsOrdLnk.getMatUid (), (k) -> new HashSet<> ()).add (toInsertApsOrdLnk); }
            else if (toInsertApsOrdLnk.getDemUid () != null) { ordLnksByDemUid.computeIfAbsent (toInsertApsOrdLnk.getDemUid (), (k) -> new HashSet<> ()).add (toInsertApsOrdLnk); }
            if (toInsertApsOrdLnk.getShpOrdUid () != null) { ordLnksByShpOrdUid.computeIfAbsent (toInsertApsOrdLnk.getShpOrdUid (), (k) -> new HashSet<> ()).add (toInsertApsOrdLnk); }
            else if (toInsertApsOrdLnk.getSupUid () != null) { ordLnksBySupUid.computeIfAbsent (toInsertApsOrdLnk.getSupUid (), (k) -> new HashSet<> ()).add (toInsertApsOrdLnk); }
        } // for

        // Ottengo le prime fasi da ciclare successivamente, per il calcolo dello stato materiale in base ai successori.
        Set<Long> firstOperations = new HashSet<> ();
        for (ApsLnkOprEntity operation : lnkContext.getOperations ()) {
            ApsOpr opr = operation.getOperation ();
            // Carico le prime se non hanno dipendenze.
            if (ArrayUtil.isEmpty (opr.getPreOprLst ())) { firstOperations.add (operation.getUid ()); }
        } // for

        Set<Long> lastShopOrders = new HashSet<> ();
        Map<Long, List<ApsLnkOprEntity>> lastOperationsByShpOrdUid = lnkContext.getLastOperationsByShpOrdUid ();
        for (Entry<Long, List<ApsLnkOprEntity>> lastShopOrderOperationsEn : lastOperationsByShpOrdUid.entrySet ()) {
            List<ApsLnkOprEntity> lastShopOrderOperations = lastShopOrderOperationsEn.getValue ();
            // Carico le prime e le ultime se non hanno dipendenze.
            for (ApsLnkOprEntity lastShopOrderOperation : lastShopOrderOperations) {
                if (!lastShopOrderOperation.hasNxtOperationLinks ()) {
                    Long shopOrderUid = lastShopOrderOperationsEn.getKey ();
                    lastShopOrders.add (shopOrderUid);
                } // if
            } // for
        } // for


        // Calcolo:
        // - Lo stato materiali delle fasi.
        // Ricorsivamente considerando i successori.
        // Set delle fasi già "visitate", controllerà che una fase non sia al suo interno, altrimenti è un loop.
        Set<ApsLnkOprEntity> visitedOperations = new HashSet<> ();
        LinkedList<ApsLnkOprLnkEntity> visitedOperationsPath = new LinkedList<> ();
        boolean hasFoundNextLoop = lnkContext.calcOprFieldsByFirstOperations (firstOperations, visitedOperations, visitedOperationsPath);
        if (hasFoundNextLoop) {
            throw ApsLnkExcLogic.get ().errorLoopOperations (visitedOperationsPath, context, wCon);
        } // if

        // Calcolo:
        // - La priorità derivata degli ordini di produzione.
        // Ricorsivamente considerando i predecessori.
        // Set degli ordini di produzione già "visitati", controllerà che un ordine non sia al suo interno, altrimenti è un loop.
        Set<ApsLnkShpOrdEntity> visitedShopOrders = new LinkedHashSet<> ();
        LinkedList<ApsLnkShpOrdEntity> visitedShopOrdersPath = new LinkedList<> ();
        boolean hasFoundPrevLoop = lnkContext.calcShpOrdFieldsByLastShopOrders (lastShopOrders, visitedShopOrders, visitedShopOrdersPath);
        if (hasFoundPrevLoop) {
            throw ApsLnkExcLogic.get ().errorLoopShopOrders (visitedShopOrdersPath, context, wCon);
        } // if

        // Salvo i campi calcolati dei materiali in db.
        List<ApsMat> materialsToUpdate = new ArrayList<> ();
        for (ApsLnkMatEntity material : lnkContext.getMaterials ()) {
            ApsMat mat = material.getMaterial ();

            ApsLnkReqEntity requestEntity = requestEntityByMatUid.get (mat.getUid ());
            BigDecimal assignedQuantity = NumberUtil.subtractBigDecimal (requestEntity.getInitialItemQuantity (), requestEntity.getMissingItemQuantity ());
            ApsLnkMatEntity materialEntity = requestEntity.getMaterialEntity ();
            // Prima di effettuare l'aggiornamento del materiale, controllo se effettivamente i campi son variati.
            if (
                !NumberUtil.equalsBigDecimal (mat.getAssQty (), assignedQuantity) ||
                !NumberUtil.equalsBigDecimal (mat.getMisQty (), requestEntity.getMissingItemQuantity ()) ||
                !Objects.equals (mat.getTssSupStr (), materialEntity.getSupplyDateStart ())
            ) {
                mat.setAssQty (assignedQuantity);
                mat.setMisQty (requestEntity.getMissingItemQuantity ());
                mat.setTssSupStr (materialEntity.getSupplyDateStart ());
                materialsToUpdate.add (mat);
            } // if
        } // for
        ApsMatDao.get ().updateAllBatch (materialsToUpdate, defaultBatchOptions, context, wCon);

        // Salvo i campi calcolati delle fasi in db.
        List<ApsOpr> operationsToUpdate = new ArrayList<> ();
        for (ApsLnkOprEntity operation : lnkContext.getOperations ()) {
            ApsOpr opr = operation.getOperation ();
            // Prima di effettuare l'aggiornamento alla fase, controllo se effettivamente i campi son variati.
            if (
                !Objects.equals (opr.getTssSupStr (), operation.getSupplyStart ()) ||
                !NumberUtil.equalsLong (opr.getMatStsUid (), operation.getMaterialStatus ())
            ) {
                opr.setTssSupStr (operation.getSupplyStart ());
                opr.setMatStsUid (operation.getMaterialStatus ());
                operationsToUpdate.add (opr);
            } // if
        } // for
        ApsOprDao.get ().updateAllBatch (operationsToUpdate, defaultBatchOptions, context, wCon);

        // Salvo i campi calcolati degli ordini produzione in db.
        List<ApsShpOrd> shopOrdersToUpdate = new ArrayList<> ();
        for (ApsLnkShpOrdEntity shopOrder : lnkContext.getShopOrders ()) {
            ApsShpOrd shpOrd = shopOrder.getShopOrder ();

            // Aggiorniamo le quantità dell'ordine senza guardare i co-prodotti, al momento.
            ApsLnkSatEntity satisfactionEntity = satisfactionEntityByShpOrdUid.get (shpOrd.getUid ());
            ApsLnkItmQtyEntity quantity = satisfactionEntity.getQuantityByItem (satisfactionEntity.getItemByUid (shpOrd.getItmUid ()));
            BigDecimal assignedQuantity = NumberUtil.subtractBigDecimal (quantity.availableQuantity, quantity.leftQuantity);

            // Prima di effettuare l'aggiornamento all'ordine di produzione, controllo se effettivamente i campi son variati.
            if (
                !Objects.equals (shpOrd.getTssDemEnd (), shopOrder.getDemandEnd ()) ||
                !NumberUtil.equalsInt (shpOrd.getOrdPtyDer (), shopOrder.getDerivedPriority ()) ||
                !NumberUtil.equalsBigDecimal (shpOrd.getAssQty (), assignedQuantity) ||
                !NumberUtil.equalsBigDecimal (shpOrd.getLftQty (), quantity.leftQuantity)
            ) {
                shpOrd.setTssDemEnd (shopOrder.getDemandEnd ());
                shpOrd.setOrdPtyDer (shopOrder.getDerivedPriority ());
                shpOrd.setAssQty (assignedQuantity);
                shpOrd.setLftQty (quantity.leftQuantity);
                shopOrdersToUpdate.add (shpOrd);
            } // if
        } // for
        ApsShpOrdDao.get ().updateAllBatch (shopOrdersToUpdate, defaultBatchOptions, context, wCon);

        // Salvo i campi calcolati delle domande in db.
        List<ApsDem> demandsToUpdate = new ArrayList<> ();
        for (ApsLnkDemEntity demand : lnkContext.getDemands ()) {
            // Controllo se posso assegnare "ora" come data schedulata.
            // demand.setSchedulatedDateGreater (lnkConfig.now); // Non ha molto senso.
            SDateTime schedulatedDate = demand.getSchedulatedDate ();
            ApsDem dem = demand.getDemand ();

            ApsLnkReqEntity requestEntity = requestEntityByDemUid.get (dem.getUid ());
            BigDecimal assignedQuantity = NumberUtil.subtractBigDecimal (requestEntity.getInitialItemQuantity (), requestEntity.getMissingItemQuantity ());
            ApsLnkDemEntity demandEntity = requestEntity.getDemandEntity ();

            // Prima di effettuare l'aggiornamento alla domanda, controllo se effettivamente i campi son variati.
            if (
                !Objects.equals (dem.getTssLnkSch (), schedulatedDate) ||
                // !Objects.equals (dem.getTssSch (), schedulatedDate) ||
                !NumberUtil.equalsBigDecimal (dem.getAssQty (), assignedQuantity) ||
                !NumberUtil.equalsBigDecimal (dem.getMisQty (), requestEntity.getMissingItemQuantity ()) ||
                !Objects.equals (dem.getTssSupStr (), demandEntity.getSupplyDateStart ())
            ) {
                dem.setTssLnkSch (schedulatedDate);
                // dem.setTssSch (schedulatedDate); // Rimosso per ottimizzazione, tanto la scrive sempre la schedulazione.
                dem.setAssQty (assignedQuantity);
                dem.setMisQty (requestEntity.getMissingItemQuantity ());
                dem.setTssSupStr (demandEntity.getSupplyDateStart ());
                demandsToUpdate.add (dem);
            } // if
        } // for
        ApsDemDao.get ().updateAllBatch (demandsToUpdate, defaultBatchOptions, context, wCon);

        // Salvo i campi calcolati delle domande in db.
        List<ApsSup> suppliesToUpdate = new ArrayList<> ();
        for (ApsLnkSupEntity supply : lnkContext.getSupplies ()) {
            // Controllo se posso assegnare "ora" come data primo impegno.
            // supply.setFirstUseDateLower (lnkConfig.now); // Non ha molto senso.
            SDateTime firstUseDate = supply.getFirstUseDate ();
            ApsSup sup = supply.getSupply ();

            ApsLnkSatEntity satisfactionEntity = satisfactionEntityBySupUid.get (sup.getUid ());
            ApsLnkItmQtyEntity quantity = satisfactionEntity.getQuantityByItem (satisfactionEntity.getItemByUid (sup.getItmUid ()));
            BigDecimal assignedQuantity = NumberUtil.subtractBigDecimal (quantity.availableQuantity, quantity.leftQuantity);

            // Prima di effettuare l'aggiornamento all'approvvigionamento, controllo se effettivamente i campi son variati.
            if (
                !Objects.equals (sup.getTssLnkFstUse (), firstUseDate) ||
                // !Objects.equals (sup.getTssFstUse (), firstUseDate) ||
                !NumberUtil.equalsBigDecimal (sup.getAssQty (), assignedQuantity) ||
                !NumberUtil.equalsBigDecimal (sup.getLftQty (), quantity.leftQuantity)
            ) {
                sup.setTssLnkFstUse (firstUseDate);
                // sup.setTssFstUse (firstUseDate); // Rimosso per ottimizzazione, tanto la scrive sempre la schedulazione.
                sup.setAssQty (assignedQuantity);
                sup.setLftQty (quantity.leftQuantity);
                suppliesToUpdate.add (sup);
            } // if
        } // for
        ApsSupDao.get ().updateAllBatch (suppliesToUpdate, defaultBatchOptions, context, wCon);

        return toRemoveApsOrdLnk;
    } // insertApsOrdLnk

    /************************************************************************************/
    /** CalResAbs ***********************************************************************/
    /************************************************************************************/

    private CompareResult<CalResAbs> getResourceAbsences (List<CalResAbs> oldEtlItems, Map<String, CalResAbs> newItems, List<TmpField<?, CalResAbs>> fields, String delimiter,
        ExecutionContext context, SReadCon rCon, SReadRemCon rrCon) throws SException {

        ValueComparator<CalResAbs> comparator = new ValueComparator<> () {
            @Override public boolean equals (CalResAbs leftValue, CalResAbs rightValue) {
                boolean check = FctTmpUtil.get ().areEqual (leftValue, rightValue, fields);
                return check;
            } // equals
        };

        KeyGetter<String, CalResAbs> keyGetter = new KeyGetter<> () {
            @Override public String getKey (CalResAbs fld) {
                String key = CalResAbsTmp.get ().getCode (fld, delimiter);
                return key;
            } // getKey
        };

        CompareResult<CalResAbs> compResult = CollectionUtil.compare (oldEtlItems, newItems, comparator, keyGetter); // compare
        return compResult;
    } // getResourceAbsences

    private Map<String, Long> upsertCalResAbs (CompareResult<CalResAbs> result, Map<String, Long> resUidByResCode, List<TmpField<?, CalResAbs>> fields, String delimiter, ExecutionContext context, SWriteCon wCon) throws SException {
        Map<String, Long> toReturn = new HashMap<> ();
        for (CalResAbs toKeep : result.onlyLeft) {
            toReturn.put (CalResAbsTmp.get ().getCode (toKeep, delimiter), toKeep.getUid ());
        } // for
        for (CalResAbs toInsert : result.onlyRight) {
            boolean canInsert = alignCalResAbs (toInsert, resUidByResCode, true, context, delimiter, wCon);
            if (canInsert) {
                CalResAbs inserted = SysCalLogic.get ().post (toInsert, false, context, wCon);
                toReturn.put (CalResAbsTmp.get ().getCode (toInsert, delimiter), inserted.getUid ());
            } // if
        } // for
        for (ComparePair<CalResAbs> toUpdate : result.notEqual) {
            CalResAbs left = toUpdate.left;
            CalResAbs right = toUpdate.right;
            FctTmpUtil.get ().merge (left, right, fields);
            boolean canUpdate = alignCalResAbs (left, resUidByResCode, false, context, delimiter, wCon);
            if (canUpdate) {
                SysCalLogic.get ().updateAll (left, false, false, context, wCon);
                toReturn.put (CalResAbsTmp.get ().getCode (left, delimiter), left.getUid ());
            } // if
        } // for
        for (ComparePair<CalResAbs> toDoNothing : result.equal) {
            CalResAbs left = toDoNothing.left;
            toReturn.put (CalResAbsTmp.get ().getCode (left, delimiter), left.getUid ());
        } // for
        return toReturn;
    } // upsertCalResAbs

    private boolean alignCalResAbs (CalResAbs entity, Map<String, Long> resUidByResCode, boolean insert, ExecutionContext context, String delimiter, SWriteCon wCon) {
        Long resUid = resUidByResCode.get (entity.getRes ().getResCod ());
        // if (resUid == null) { throw ExceptionLogic.error (ApsTmpMessage.vrtImpResUidCalResAbs, context, wCon, entity.getRes ().getResCod (), insert ? "inserimento" : "modifica", entity.getUid ()); }
        if (resUid == null) { return false; }
        entity.setResUid (resUid);

        entity.setDayToCrt (entity.getDay ().getDayCod ());
        entity.setAbsSrcUid ("ETL");
        return true;
    } // alignCalResAbs

    /************************************************************************************/
    /** Utility *************************************************************************/
    /************************************************************************************/

    protected <T extends SModel, L extends SLanguageModel> void deleteBatch (SDaoBatch<T, L> dao, List<T> models, ExecutionContext context, SWriteCon wCon) throws SException {
        SBatchOptions batchOptions = SBatchOptions.of ();
        deleteBatch (dao, models, batchOptions, context, wCon);
    } // deleteBatch
    
    protected <T extends SModel, L extends SLanguageModel> void deleteBatch (SDaoBatch<T, L> dao, List<T> models, SBatchOptions batchOptions, ExecutionContext context, SWriteCon wCon) throws SException {
        LOGGER.info (String.format ("Start delete batch %s size %d", dao.meta ().tableName (), models.size ()));
        var start = Instant.now ();
        if (!models.isEmpty ()) {
            batchOptions = batchOptions == null ? SBatchOptions.of () : batchOptions;
            var result = dao.deleteBatch (models, batchOptions, context, wCon);
            checkDeleteBatchResult (result, dao.getClass ());
        }
        FctTmpUtil.get ().infoProcessingEnd (LOGGER, start, "End delete batch in");
    } // deleteBatch
    
    protected void checkDeleteBatchResult (SBatchResult<?> result, Class<?> clasz) {
        if (result.hasDiscarded ()) {
            LOGGER.warn ("Attenzione record non cancellati completamente in: %s".formatted (clasz.getSimpleName ()));
        } // if
    } // checkDeleteBatchResult

} // ApsTmpLogic
