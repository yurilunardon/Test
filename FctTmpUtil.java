package net.synergy2.logic.fct.tmp.common;

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;

import net.synergy2.base.connections.ConnectionsUtil;
import net.synergy2.base.connections.SReadCon;
import net.synergy2.base.connections.SReadRemCon;
import net.synergy2.base.connections.SWriteCon;
import net.synergy2.base.connections.enums.ConnectionType;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.exceptions.SApplicationException;
import net.synergy2.base.exceptions.SConnectionException;
import net.synergy2.base.exceptions.SException;
import net.synergy2.base.exceptions.SSqlException;
import net.synergy2.base.types.IMessageInstaller;
import net.synergy2.base.types.SDate;
import net.synergy2.base.types.SDateTime;
import net.synergy2.base.types.SSingletonHolder;
import net.synergy2.base.types.STime;
import net.synergy2.base.util.datas.StringUtil;
import net.synergy2.base.util.devel.DebugUtil;
import net.synergy2.db.base.SCounter;
import net.synergy2.db.base.SLanguageModel;
import net.synergy2.db.base.SModel;
import net.synergy2.db.base.batch.SBatchOptions;
import net.synergy2.db.base.batch.SBatchResult;
import net.synergy2.db.base.batch.SDaoBatch;
import net.synergy2.logic.fct.tmp.impl.TmpWarningEntity;
import net.synergy2.logic.sys.tra.SysTraLogic;
import net.synergy2.rest.client.SynergyHttpClient;
import net.synergy2etl.pentaho.model.PentahoOutputModel;
import net.synergy2etl.pentaho.model.PentahoOutputModel.PentahoResult;
import net.synergy2etl.pentaho.model.PentahoRestOutput;

public class FctTmpUtil {

    protected FctTmpUtil () {}
    private static class Singleton {
        private static final SSingletonHolder<FctTmpUtil> INSTANCE = new SSingletonHolder<> (FctTmpUtil.class);
    } // Singleton
    public static FctTmpUtil get () { return Singleton.INSTANCE.get (); }

    /************************************************************************************/
    /** Utilità gestione entità tmp *****************************************************/
    /************************************************************************************/

    public SDate getDate (Timestamp ts) {
        SDate dt = null;
        if (ts != null) { dt = new SDate (ts); }
        return dt;
    } // getDate

    public SDateTime getDateTime (Timestamp ts) {
        SDateTime dt = null;
        if (ts != null) { dt = new SDateTime (ts); }
        return dt;
    } // getDateTime

    public STime getTime (Timestamp ts) {
        STime dt = null;
        if (ts != null) { dt = new STime (ts); }
        return dt;
    } // getTime
    
    public StringBuilder getStringBuilder (final ResultSet rs, final String colName, final SReadCon rCon) throws SSqlException {
        try {
            if (rCon.getType () == ConnectionType.POSTGRESQL) {
                final byte[] bdata = rs.getBytes (colName);
                if (rs.wasNull ()) { return null; }
                return new StringBuilder (new String (bdata, StandardCharsets.UTF_8));
            } else {
                final Blob blob = rs.getBlob (colName);
                if (blob == null) { return null; }
                final byte[] bdata = blob.getBytes (1, (int) blob.length ());
                return new StringBuilder (new String (bdata, StandardCharsets.UTF_8));
            }
        } catch (final Throwable e) {
            throw new SSqlException (e, "Reading Blob at colName " + colName);
        }
    }

    public <E> String selectFields (List<TmpField<?, E>> fields) {
        return fields.stream ()
            .map ( (f) -> f.getSqlField ())
            .filter (f -> f != null)
            .collect (Collectors.joining (", ")) + " ";
    } // selectFields

    public <E> boolean areEqual (E newEntity, E oldEntity, List<TmpField<?, E>> fields) {
        boolean equal = true;
        Iterator<TmpField<?, E>> fieldIt = fields.iterator ();
        while (equal && fieldIt.hasNext ()) {
            TmpField<?, E> field = fieldIt.next ();
            equal = field.isValueEqual (newEntity, oldEntity);
        } // while
        return equal;
    } // areEqual
    
    public <E> boolean areEqualByCode (E newEntity, E oldEntity, List<TmpField<?, E>> fields) {
        boolean equal = true;
        Iterator<TmpField<?, E>> fieldIt = fields.iterator ();
        while (equal && fieldIt.hasNext ()) {
            TmpField<?, E> field = fieldIt.next ();
            equal = field.isValueEqualByCode (newEntity, oldEntity);
        } // while
        return equal;
    } // areEqualByCode

    public <E> void merge (E entity, E entityToMerge, List<TmpField<?, E>> fields) {
        for (TmpField<?, E> field : fields) {
            field.mergeFieldValue (entity, entityToMerge);
        } // for
    } // merge

    public <E> void raw (E entity, ResultSet rs, List<TmpField<?, E>> fields) throws SQLException {
        for (TmpField<?, E> field : fields) {
            field.applySqlValueGetter (rs, entity);
        } // for
    } // raw

    public <E> String getBaseQuery (String tmpTableName, List<TmpField<?, E>> fields, String condition) {
        StringBuilder sql = new StringBuilder ("SELECT ");
        sql.append (selectFields (fields));
        sql.append ("FROM ").append (tmpTableName).append (" WHERE 1 = 1 ");
        if (!StringUtil.isEmpty (condition)) { sql.append (" AND " + condition); }
        return sql.toString ();
    } // getBaseQuery

    public <E> E getOne (E entity, List<TmpField<?, E>> fields, String baseQuery, List<TmpField <?, E>> whereValues, Supplier<E> newInstanceGetter, ExecutionContext context, SReadCon rCon, SReadRemCon rrCon) throws SConnectionException, SSqlException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        StringBuilder sql = null;
        int counter = 1;
        try {
            sql = new StringBuilder (baseQuery);
            stm = rrCon.prepareStatement (sql.toString ());
            if (whereValues != null) {
                for (TmpField <?, E> value : whereValues) {
                    value.applySqlValueSetter (stm, counter, entity);
                }
            }
            rs = stm.executeQuery ();
            E row = null;
            if (rs.next ()) {
                row = newInstanceGetter.get ();
                raw (row, rs, fields);
            }
            return row;
        } catch (SQLException e) {
            throw new SSqlException (e, 1, newInstanceGetter.get ().getClass ().getSimpleName (), null, sql.toString ());
        } finally {
            ConnectionsUtil.safeClose (rs, stm);
        } // try - catch - finally
    }

    public <E> List<E> getList (List<TmpField<?, E>> fields, String baseQuery, SDateTime tssImp, Supplier<E> newInstanceGetter, ExecutionContext context, SReadCon rCon, SReadRemCon rrCon) throws SConnectionException, SSqlException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        StringBuilder sql = null;
        List<E> toReturn = new ArrayList<> ();
        var counter = new SCounter (1);
        try {
            sql = new StringBuilder (baseQuery);
            if (tssImp != null) {
                sql.append ("AND (Tsi > ? OR Tsu > ?) ");
            } // if
            stm = rrCon.prepareStatement (sql.toString ());
            if (tssImp != null) {
                stm.setTimestamp (counter.value++, tssImp);
                stm.setTimestamp (counter.value++, tssImp);
            } // if
            rs = stm.executeQuery ();
            E row = null;
            while (rs.next ()) {
                row = newInstanceGetter.get ();
                raw (row, rs, fields);
                if (row != null) { toReturn.add (row); }
            } // while
            return toReturn;
        } catch (SQLException e) {
            throw new SSqlException (e, 1, newInstanceGetter.get ().getClass ().getSimpleName (), null, sql.toString ());
        } finally {
            ConnectionsUtil.safeClose (rs, stm);
        } // try - catch - finally
    } // getList
    
    public <E> Map<String, E> getMapByCode (List<TmpField<?, E>> fields, String baseQuery, SDateTime tssImp, Supplier<E> newInstanceGetter, Function<E, String> getCodeApplier, ExecutionContext context, SReadCon rCon, SReadRemCon rrCon) throws SConnectionException, SSqlException {
        Map<String, E> toReturn = new HashMap<> ();
        return getMapByCode (toReturn, fields, baseQuery, tssImp, newInstanceGetter, getCodeApplier, context, rCon, rrCon);
    } // getMapByCode

    public <E> Map<String, E> getMapByCodeWithSort (List<TmpField<?, E>> fields, String baseQuery, SDateTime tssImp, Supplier<E> newInstanceGetter, Function<E, String> getCodeApplier, ExecutionContext context, SReadCon rCon, SReadRemCon rrCon) throws SConnectionException, SSqlException {
        Map<String, E> toReturn = new LinkedHashMap<> ();
        return getMapByCode (toReturn, fields, baseQuery, tssImp, newInstanceGetter, getCodeApplier, context, rCon, rrCon);
    } // getMapByCode
    
    private <E> Map<String, E> getMapByCode (Map<String, E> toReturn, List<TmpField<?, E>> fields, String baseQuery, SDateTime tssImp, Supplier<E> newInstanceGetter, Function<E, String> getCodeApplier, ExecutionContext context, SReadCon rCon, SReadRemCon rrCon) throws SConnectionException, SSqlException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        StringBuilder sql = null;
        var counter = new SCounter (1);
        try {
            sql = new StringBuilder (baseQuery);
            if (tssImp != null) {
                sql.append ("AND (Tsi > ? OR Tsu > ?) ");
            } // if
            stm = rrCon.prepareStatement (sql.toString ());
            if (tssImp != null) {
                stm.setTimestamp (counter.value++, tssImp);
                stm.setTimestamp (counter.value++, tssImp);
            } // if
            rs = stm.executeQuery ();

            E row = null;
            while (rs.next ()) {
                row = newInstanceGetter.get ();
                raw (row, rs, fields);
                toReturn.put (getCodeApplier.apply (row), row);
            } // while
            return toReturn;
        } catch (SQLException e) {
            throw new SSqlException (e, 1, newInstanceGetter.get ().getClass ().getSimpleName (), null, sql.toString ());
        } finally {
            ConnectionsUtil.safeClose (rs, stm);
        } // try - catch - finally
    } // getMapByCode

    /************************************************************************************/
    /** Utilità logiche import **********************************************************/
    /************************************************************************************/

    public record ImportRestInput (boolean impBasDat, boolean impOpeDat) {}
    
    public <T> void handleField (String tempColumn, String sysColumn, Set<String> userDefinitions, T value, BiConsumer<String, T> setter) {
        if (userDefinitions.contains (tempColumn)) {
            setter.accept (tempColumn, value);
        }
    }

    public String convertExtraFieldToColumnDef (String u) {
        String[] s = StringUtil.splitIgnoringQuotes (u, '_');
        String sNum = StringUtil.fillLeft (s[s.length - 1], 3, '0', false).toString ();
        if (u.startsWith ("Str_")) { return "ExtraString" + sNum; }
        if (u.startsWith ("Dec_")) { return "ExtraDecimal" + sNum; }
        if (u.startsWith ("Int_")) { return "ExtraInteger" + sNum; }
        if (u.startsWith ("Flg_")) { return "ExtraBoolean" + sNum; }
        if (u.startsWith ("Tss_")) { return "ExtraTimestamp" + sNum; }
        if (u.startsWith ("Blb_")) { return "ExtraBlob" + sNum; }
        return "";
    } // convertExtraFieldToColumnDef

    public void createWarning (IMessageInstaller messageLabel, List<Object> values, String keyLabel, Boolean insert, FctTmpStore store, ExecutionContext context, SReadCon rCon) {
        String msg = SysTraLogic.get ().buildMsg (messageLabel.getCode (), values, context, rCon);
        store.addWarning (new TmpWarningEntity (msg, keyLabel, insert));
    } // createWarning

    public void infoProcessingStart (Logger logger, String message) {
        logger.info (String.format ("%s", message));
    } // printProcessingStart

    public Instant infoProcessingEnd (Logger logger, Instant start, String message) {
        Instant end = Instant.now ();
        long diff = Duration.between (start, end).getSeconds ();

        logger.info (String.format ("%s: %02d min %02d sec", message, (diff % 3600) / 60, (diff % 60)));
        return end;
    } // printProcessingEnd

    public void debugUsage (String name) {
        DebugUtil.toDebuggers ("-------------: " + name + " -------------");

        Runtime runtime = Runtime.getRuntime ();

        /* Total number of processors or cores available to the JVM */
        DebugUtil.toDebuggers ("Available processors (cores): " +
            runtime.availableProcessors ());

        /* Total amount of free memory available to the JVM */
        DebugUtil.toDebuggers ("Free memory: " +
            humanReadableByteCountSI (runtime.freeMemory ()));

        /* Total amount of allocated memory available to the JVM */
        DebugUtil.toDebuggers ("Allocated memory: " +
            humanReadableByteCountSI ((runtime.totalMemory () - runtime.freeMemory ())));

        /* This will return Long.MAX_VALUE if there is no preset limit */
        long maxMemory = runtime.maxMemory ();
        /* Maximum amount of memory the JVM will attempt to use */
        DebugUtil.toDebuggers ("Maximum memory: " +
            (maxMemory == Long.MAX_VALUE ? "no limit" : humanReadableByteCountSI (maxMemory)));

        /* Total memory currently in use by the JVM */
        DebugUtil.toDebuggers ("Total memory: " +
            humanReadableByteCountSI (runtime.totalMemory ()));
    } // printUsage

    public String humanReadableByteCountSI (long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        } // if
        CharacterIterator ci = new StringCharacterIterator ("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next ();
        } // while
        return String.format ("%.1f %cB", bytes / 1000.0, ci.current ());
    } // humanReadableByteCountSI

    public boolean clearObjectsAndFlushMemory (FctTmpStore store, String key) {
        debugUsage (key);
        boolean result = store.calcSatisfiedDependencyObjects (key);
        if (result) {
            clearMemory ();
        } // if
        return result;
    } // clearObjectsAndFlushMemory
    
    public List<Object> asObjects (Object... objects) {
        List<Object> toReturn = new ArrayList<> (Arrays.asList (objects));
        return toReturn;
    } // asObjects

    public void clearMemory () {
        System.gc ();
    } // clearMemory
    
    public <T extends SModel, L extends SLanguageModel> void insertBatch (SDaoBatch<T, L> dao, List<T> models, Logger logger, ExecutionContext context, SWriteCon wCon) throws SException {
        SBatchOptions batchOptions = SBatchOptions.of ();
        insertBatch (dao, models, logger, batchOptions, context, wCon);
    } // insertBatch
    
    public <T extends SModel, L extends SLanguageModel> void insertBatch (SDaoBatch<T, L> dao, List<T> models, Logger logger, SBatchOptions batchOptions, ExecutionContext context, SWriteCon wCon) throws SException {
        var result = dao.insertBatch (models, batchOptions, context, wCon);
        checkInsertBatchResult (logger, result, dao.getClass ());
    } // insertBatch
    
    public <T extends SModel, L extends SLanguageModel> void updateBatch (SDaoBatch<T, L> dao, List<T> models, Logger logger, ExecutionContext context, SWriteCon wCon) throws SException {
        SBatchOptions batchOptions = SBatchOptions.of ();
        updateBatch (dao, models, logger, batchOptions, context, wCon);
    } // updateBatch
    
    public <T extends SModel, L extends SLanguageModel> void updateBatch (SDaoBatch<T, L> dao, List<T> models, Logger logger, SBatchOptions batchOptions, ExecutionContext context, SWriteCon wCon) throws SException {
        var result = dao.insertBatch (models, batchOptions, context, wCon);
        checkUpdateBatchResult (logger, result, dao.getClass ());
    } // updateBatch
    
    public <T extends SModel, L extends SLanguageModel> void deleteBatch (SDaoBatch<T, L> dao, List<T> models, Logger logger, ExecutionContext context, SWriteCon wCon) throws SException {
        SBatchOptions batchOptions = SBatchOptions.of ();
        deleteBatch (dao, models, logger, batchOptions, context, wCon);
    } // deleteBatch
    
    public <T extends SModel, L extends SLanguageModel> void deleteBatch (SDaoBatch<T, L> dao, List<T> models, Logger logger, SBatchOptions batchOptions, ExecutionContext context, SWriteCon wCon) throws SException {
        var result = dao.deleteBatch (models, batchOptions, context, wCon);
        checkDeleteBatchResult (logger, result, dao.getClass ());
    } // deleteBatch
    
    public void checkInsertBatchResult (Logger logger, SBatchResult<?> result, Class<?> clasz) {
        if (result.hasDiscarded ()) {
            logger.warn ("Warning! Records not completly inserted: %s".formatted (clasz.getSimpleName ()));
        } // if
    } // checkInsertBatchResult
    
    public void checkUpdateBatchResult (Logger logger, SBatchResult<?> result, Class<?> clasz) {
        if (result.hasDiscarded ()) {
            logger.warn ("Warning! Records not completly updated: %s".formatted (clasz.getSimpleName ()));
        } // if
    } // checkUpdateBatchResult
    
    public void checkDeleteBatchResult (Logger logger, SBatchResult<?> result, Class<?> clasz) {
        if (result.hasDiscarded ()) {
            logger.warn ("Warning! Records not completly removed: %s".formatted (clasz.getSimpleName ()));
        } // if
    } // checkDeleteBatchResult
    
    /************************************************************************************/
    /** UTILITA CHIAMATE A UN JOB PENTAHO ***********************************************/
    /************************************************************************************/
    
    /** Chiamata rest che avvia le trasformazioni dell'ETL */
    public PentahoOutputModel startETL (String name, Object payload, UriBuilder uri, String token) throws SConnectionException, SApplicationException {    // Ottengo l'URL di Pentaho dalle opzioni globali
        final UriBuilder serverUrl = uri.path ("v1").path ("etl").path ("startandwait");
        try {
            Response response = SynergyHttpClient.get ()
                .property (ClientProperties.READ_TIMEOUT, null)
                .target (serverUrl)
                .queryParam ("name", name)
                .request (MediaType.APPLICATION_JSON).accept (MediaType.APPLICATION_JSON)
                .header ("token", token)
                .post (Entity.entity (payload, MediaType.APPLICATION_JSON));
            if (response.getStatus () != 200) {
                System.err.println ("Error [status " + response.getStatus () + "] \n");
                throw new SApplicationException ("Error [status " + response.getStatus () + "]");
            } // if
            return response.readEntity (new GenericType<PentahoRestOutput<PentahoOutputModel>> () {}).result;
        } catch (ProcessingException e) {
            e.printStackTrace ();
            throw new SConnectionException ("Unreachable etl server");
        } // try - catch
    } // startETL
    
    public PentahoResult getPentahoResult (PentahoOutputModel restOutput) throws SApplicationException {
        if (!restOutput.result) {
            if (restOutput.data != null) {
                int index = restOutput.data.unexpectedError.errorMessage.lastIndexOf (") :");
                String formattedMsg = restOutput.data.unexpectedError.errorMessage.substring (index + 4);
                throw new SApplicationException ("ETL: " + formattedMsg, -1);
            } else {
                throw new SApplicationException ("ETL: " + "Errore ETL", -1);
            } // if - else
        } // if
        if (restOutput.data != null) {
            return restOutput.data.result;
        } else {
            return new PentahoResult (null, null, null, null, null);
        } // if - else
    } // getPentahoResult

} // FctUtil
