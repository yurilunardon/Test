package net.synergy2.logic.fct.tmp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.synergy2.base.connections.ConnectionsUtil;
import net.synergy2.base.connections.SReadCon;
import net.synergy2.base.connections.SStm;
import net.synergy2.base.connections.SWriteCon;
import net.synergy2.base.connections.SWriteRemCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.exceptions.SApplicationException;
import net.synergy2.base.exceptions.SConnectionException;
import net.synergy2.base.exceptions.SException;
import net.synergy2.base.exceptions.SSqlException;
import net.synergy2.base.types.ISField;
import net.synergy2.base.util.datas.NumberUtil;
import net.synergy2.base.util.datas.StringUtil;
import net.synergy2.db.base.SModel;
import net.synergy2.db.base.batch.SBatchOptions;
import net.synergy2.db.base.batch.SBatchResult;
import net.synergy2.db.base.batch.SDaoBatch;
import net.synergy2.db.base.dao.TableDao;
import net.synergy2.db.base.dao.UserFieldsDao;
import net.synergy2.db.base.meta.Meta;
import net.synergy2.db.sys.upf.UpfIst;
import net.synergy2.logic.fct.tmp.common.FctTmpStore;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import net.synergy2.logic.fct.tmp.common.TmpField;
import net.synergy2.query.SQueryUtil;
import net.synergy2.rest.util.JsonUtil;

public abstract class AGenericTmp<M extends SModel, S extends FctTmpStore, D extends FctTmpStoreDeletion, C extends FctImportContext<S, D>> {

    protected String delimiter = "#_#"; // TODO Da prendere da configurazioni
    
    public Meta<M> getMeta () { return getDao ().meta (); } //  E' ricavato dal dao implementato dalla classe implementativa.
    
    public UserFieldsDao<M> getUserFieldsDao () { return userFieldsDao (); }
    
    protected boolean hasTempUserFields () { return false; }
    
    private boolean hasDaoUserFields (ExecutionContext context, SReadCon rCon) throws SException {
        Meta<M> meta = getDao ().meta ();
        UserFieldsDao<M> userFieldsDao = userFieldsDao ();
        return meta.hasUserFields () && userFieldsDao.hasDefinedUserFields (context, rCon);
    }
    
    private boolean hasUserFields (ExecutionContext context, SReadCon rCon) throws SException {
        return hasTempUserFields () && hasDaoUserFields (context, rCon);
    }

    public List<TmpField<?, M>> getFields (C ctx) throws SException {
        return getFields (ctx.getContext (), ctx.getWCon ());
    } // getFields
    
    public List<TmpField<?, M>> getFields (ExecutionContext context, SReadCon rCon) throws SException {
        List<TmpField<?, M>> fields = initFields ();
        fields.addAll (getUserFields (context, rCon));
        return fields;
    } // getFields

    public Set<String> getUserDefinitions (ExecutionContext context, SReadCon rCon) throws SException {
        return getUserFieldsDao ().getDefinedUserFields (context, rCon).stream ().map (upfDef -> upfDef.getFldNam ()).collect (Collectors.toSet ());
    }
    
    public List<TmpField<?, M>> getUserFields (C ctx) throws SException { return getUserFields (ctx.getContext (), ctx.getWCon ()); }
    
    public List<TmpField<?, M>> getUserFields (ExecutionContext context, SReadCon rCon) throws SException {
        // N.B.: Se ho upfist salvati a db, poi sono stati disabilitate le definizioni campi utente, i record vengono lasciati
        // perchè non caricato neanche i field.
        if (!hasUserFields (context, rCon)) { return List.of (); } 
        
        Set<String> userDefinitions = getUserDefinitions (context, rCon);

        var upfField = new TmpField<UpfIst, M> ()
            .setValueGetter ((entity) -> entity.getUserFields ())
            .setValueSetter ((value, entity) -> {
                if (value == null) {
                    entity.setUserFields (null);
                } else {
                    UpfIst userFields = entity.getUserFields () == null ? UpfIst.newInstance () : entity.getUserFields ();
                    for (int i = 1; i <= 20; i++) {
                        String iS = i + "";
                        String padded2 = StringUtil.fillLeft (iS, 2, '0', false).toString ();
                        Object fieldValue = value.getByFieldName ("Str_" + padded2);
                        userFields.setByFieldName ("Str_" + padded2, fieldValue);
                        fieldValue = value.getByFieldName ("Dec_" + padded2);
                        userFields.setByFieldName ("Dec_" + padded2, fieldValue);
                        fieldValue = value.getByFieldName ("Int_" + padded2);
                        userFields.setByFieldName ("Int_" + padded2, fieldValue);
                        fieldValue = value.getByFieldName ("Flg_" + padded2);
                        userFields.setByFieldName ("Flg_" + padded2, fieldValue);
                        fieldValue = value.getByFieldName ("Tss_" + padded2);
                        userFields.setByFieldName ("Tss_" + padded2, fieldValue);
                    } // for
                    for (int i = 1; i <= 5; i++) {
                        String iS = i + "";
                        String padded2 = StringUtil.fillLeft (iS, 2, '0', false).toString ();
                        Object fieldValue = value.getByFieldName ("Blb_" + padded2);
                        userFields.setByFieldName ("Blb_" + padded2, fieldValue);
                    } // for
                    entity.setUserFields (userFields);
                }
            })
            .setComparator ((value1, value2) -> (value1 == null && value2 == null) || (value1 != null && value2 != null && value1.equals (value2, false, false, false)))
            .setSqlFieldGetter (() -> {
                List<String> fields = new ArrayList<> ();
                for (int i = 1; i <= 20; i++) {
                    String iS = i + "";
                    String padded3 = StringUtil.fillLeft (iS, 3, '0', false).toString ();
                    fields.add ("ExtraString" + padded3);
                    fields.add ("ExtraDecimal" + padded3);
                    fields.add ("ExtraInteger" + padded3);
                    fields.add ("ExtraBoolean" + padded3);
                    fields.add ("ExtraTimestamp" + padded3);
                } // for
                for (int i = 1; i <= 5; i++) {
                    String iS = i + "";
                    String padded3 = StringUtil.fillLeft (iS, 3, '0', false).toString ();
                    fields.add ("ExtraBlob" + padded3);
                } // for
                return String.join (", ", fields);
            })
            .setSqlGetter ((rs, fieldName) -> {
                UpfIst upfIst = UpfIst.newInstance ();
                for (int i = 1; i <= 20; i++) {
                    String iS = i + "";
                    String padded2 = StringUtil.fillLeft (iS, 2, '0', false).toString ();
                    String padded3 = StringUtil.fillLeft (iS, 3, '0', false).toString ();

                    FctTmpUtil.get ().handleField ("Str_" + padded2, "ExtraString" + padded3, userDefinitions, rs.getString ("ExtraString" + padded3), upfIst::setS);
                    FctTmpUtil.get ().handleField ("Dec_" + padded2, "ExtraDecimal" + padded3, userDefinitions, rs.getBigDecimal ("ExtraDecimal" + padded3), upfIst::setF);
                    FctTmpUtil.get ().handleField ("Int_" + padded2, "ExtraInteger" + padded3, userDefinitions, rs.getLong ("ExtraInteger" + padded3), upfIst::setI);
                    FctTmpUtil.get ().handleField ("Flg_" + padded2, "ExtraBoolean" + padded3, userDefinitions, rs.getBoolean ("ExtraBoolean" + padded3), upfIst::setB);
                    FctTmpUtil.get ().handleField ("Tss_" + padded2, "ExtraTimestamp" + padded3, userDefinitions, rs.getTimestamp ("ExtraTimestamp" + padded3), upfIst::setT);
                } // for
                for (int i = 1; i <= 5; i++) {
                    String iS = i + "";
                    String padded2 = StringUtil.fillLeft (iS, 2, '0', false).toString ();
                    String padded3 = StringUtil.fillLeft (iS, 3, '0', false).toString ();

                    try {
                        FctTmpUtil.get ().handleField ("Blb_" + padded2, "ExtraBlob" + padded3, userDefinitions, FctTmpUtil.get ().getStringBuilder (rs, "ExtraBlob" + padded3, rCon), upfIst::setO);
                    } catch (SSqlException e) {
                        throw new RuntimeException (e);
                    }
                } // for
                return upfIst;
            });

        List<TmpField<?, M>> extraFields = new ArrayList<> ();
        extraFields.add (upfField);
        
        return extraFields;
    }

    public List<M> getList (C ctx) throws SException {
        var fields = getFields (ctx);
        var baseQuery = getBaseQuery (fields);
        return FctTmpUtil.get ().getList (fields, baseQuery, ctx.getTssImp (), getNewInstance (), ctx.getContext (), ctx.getWCon (), ctx.getRrCon ());
    } // getList

    public Map<String, M> getMapByCode (C ctx) throws SException {
        var fields = getFields (ctx);
        var baseQuery = getBaseQuery (fields);
        return FctTmpUtil.get ().getMapByCode (fields, baseQuery, ctx.getTssImp (), getNewInstance (), this::getCode, ctx.getContext (), ctx.getWCon (), ctx.getRrCon ());
    } // getMapByCode

    public List<M> getActiveList (C ctx) throws SException {
        return SQueryUtil.getActive (getAlias (), ctx.context, ctx.wCon);
    } // getActiveList

    protected abstract List<TmpField<?, M>> initFields ();

    public String getBaseQuery (List<TmpField<?, M>> fields) {
        return getBaseQuery (fields, getWhereCondition ());
    } // getBaseQuery

    private String getBaseQuery (List<TmpField<?, M>> fields, String whereCondition) {
        return FctTmpUtil.get ().getBaseQuery (getTempTableName (), fields, whereCondition);
    } // getBaseQuery

    public String getWhereCondition () { return null; } // getWhereCondition

    public abstract TableDao<M> getDao ();
    @SuppressWarnings("unchecked")
    private UserFieldsDao<M> userFieldsDao () {
        if (!getMeta ().hasUserFields ()) { return null; }
        TableDao<M> baseDao = getDao ();
        if (baseDao instanceof UserFieldsDao<?> userFieldsDao) {
            return (UserFieldsDao<M>) userFieldsDao;
        }
        return null;
    }
    public boolean hasDaoBatch () { return getDao () != null && getDao () instanceof SDaoBatch; }

    protected abstract String getTempTableName ();
    protected abstract Class<M> getModelClass ();
    protected abstract Supplier<M> getNewInstance ();
    public abstract String getCode (M entity);
    public abstract String getAlias ();
    public abstract Long getTableUid ();
    public abstract ISField getUidField ();
    public abstract String getVrtStoreKey ();

    public abstract SBatchResult<M> insertBatch (List<M> entities, SBatchOptions options, S store, ExecutionContext context, SWriteCon wCon) throws SException;
    public abstract SBatchResult<M> updateBatch (List<M> entities, List<M> oldEntities, SBatchOptions options, S store, ExecutionContext context, SWriteCon wCon) throws SException;
    public abstract M getInsert (M entity, ExecutionContext context, SWriteCon wCon) throws SException;
    public abstract M getUpdate (M entity, M oldEntity, ExecutionContext context, SWriteCon wCon) throws SException;

    public M getDummy (C ctx) throws SException {
        throw new SApplicationException ("Dummy non implementato");
    } // getDummy

    /** @return Inserisce l'entità nel database temporaneo. */
    public M insertToTemp (M entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        var fields = getFields (context, rCon);
        var fieldsWithSetter = new ArrayList<TmpField<?, M>> ();
        var sqlFields = new ArrayList<String> ();
        var sqlPlaceholders = new ArrayList<String> ();
        var sqlCodeFields = new ArrayList<String> ();
        var codeFieldsWithSetter = new ArrayList<TmpField<?, M>> ();

        for (TmpField<?, M> f : fields) {
            if (f.hasSqlSetter ()) {
                fieldsWithSetter.add (f);
                sqlFields.add (f.getSqlField ());
                sqlPlaceholders.add ("?");

                if (f.isCode ()) {
                    codeFieldsWithSetter.add (f);
                    sqlCodeFields.add (f.getSqlField () + " = ?");
                }
            } // if
        } // for
        if (sqlFields.isEmpty ()) {
            throw new SApplicationException ("empty fields setter");
        } // if
        if (sqlCodeFields.isEmpty ()) {
            throw new SApplicationException ("empty code fields setter");
        } // if
        
        SStm stm = null;
        String sql = "INSERT INTO %s (%s) VALUES (%s)".formatted (getTempTableName (), String.join (", ", sqlFields), String.join (", ", sqlPlaceholders));
        int counter = 1;

        try {
            stm = wrCon.prepareStatement (sql);
            for (TmpField<?, M> f : fieldsWithSetter) {
                f.applySqlValueSetter (stm, counter++, entity);
            } // for
            stm.execute ();
            stm.close ();
            var baseQuery = getBaseQuery (fields, String.join (" AND ", sqlCodeFields));
            return FctTmpUtil.get ().getOne (entity, fields, baseQuery, codeFieldsWithSetter, this.getNewInstance (), context, rCon, wrCon);
        } catch (SSqlException | SConnectionException e) {
            throw e;
        } catch (Throwable e) {
            throw new SSqlException (e, sql);
        } finally {
            ConnectionsUtil.safeClose (stm);
        } // try - catch - finally
    } // insertToTemp

    /** @return Aggiorna l'entità nel database temporaneo in base al suo codice logico. */
    public M updateToTemp (M entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        var fields = getFields (context, rCon);
        var updateFieldsWithSetter = new ArrayList<TmpField<?, M>> ();
        var updateCodeFieldsWithSetter = new ArrayList<TmpField<?, M>> ();
        var sqlFields = new ArrayList<String> ();
        var sqlCodeFields = new ArrayList<String> ();

        for (TmpField<?, M> f : fields) {
            if (f.hasSqlSetter ()) {
                updateFieldsWithSetter.add (f);
                sqlFields.add (f.getSqlField () + " = ?");

                if (f.isCode ()) {
                    updateCodeFieldsWithSetter.add (f);
                    sqlCodeFields.add (f.getSqlField () + " = ?");
                }
            } // if
        } // for
        if (sqlFields.isEmpty ()) {
            throw new SApplicationException ("empty fields setter");
        } // if
        if (sqlCodeFields.isEmpty ()) {
            throw new SApplicationException ("empty code fields setter");
        } // if

        SStm stm = null;
        String sql = "UPDATE %s SET %s WHERE %s".formatted (getTempTableName (), String.join (", ", sqlFields), String.join (" AND ", sqlCodeFields));
        int counter = 1;

        try {
            stm = wrCon.prepareStatement (sql);
            for (TmpField<?, M> f : updateFieldsWithSetter) {
                f.applySqlValueSetter (stm, counter++, entity);
            } // for
            for (TmpField<?, M> f : updateCodeFieldsWithSetter) {
                f.applySqlValueSetter (stm, counter++, entity);
            } // for
            stm.execute ();
            stm.close ();
            var baseQuery = getBaseQuery (fields, String.join (" AND ", sqlCodeFields));
            return FctTmpUtil.get ().getOne (entity, fields, baseQuery, updateCodeFieldsWithSetter, this.getNewInstance (), context, rCon, wrCon);
        } catch (SSqlException | SConnectionException e) {
            throw e;
        } catch (Throwable e) {
            throw new SSqlException (e, sql);
        } finally {
            ConnectionsUtil.safeClose (stm);
        } // try - catch - finally
    } // updateToTemp

    /**
     * Metodo che ritorna la copia dell'entità fornita chiamato il metodo getCopy dell'entità.
     * Di default non è implementato ma per quelle entità che fanno l'aggiornamento dei requisiti, e
     * quindi hanno bisogno dell'entità presente nel db per fare l'update, è richiesto.
     * 
     * @param entity Entità dalla quale creare una copia.
     * @return Copia dell'entità.
     */
    public M getCopy (M entity) {
        return JsonUtil.convertToModel (entity, getModelClass ());
    } // getCopy

    public abstract StoreSetter<M, C> getStoreSetter ();

    @FunctionalInterface
    public interface StoreSetter<M, C> {
        void accept (Map<String, Long> upsertedEntities, Map<String, M> removeCandidates, C ctx);
    } // StoreSetter

    public void setEntitiesOnStore (Map<String, Long> upsertedEntities, Map<String, M> removeCandidates, C ctx) {
        getStoreSetter ().accept (upsertedEntities, removeCandidates, ctx);
    } // setEntitiesOnStore

    public void align (M entity, C ctx, boolean insert) throws SException {
        throw new SApplicationException ("Align non implementato");
    } // align

    public Boolean getImpDel (M entity, C ctx) throws SException {
        // Di default se l'entità è stata inserita dalla risorsa di servizio per l'import allora può essere cancellata in import.
        return NumberUtil.equalsLong (entity.getRsi (), ctx.getContext ().getResource ()) ? null : false;
    } // getImpDel

    public void setImpDel (M entity, C ctx, Boolean impDel) throws SException {
        // Di default non serve settare il flag impDel e quindi non faccio nulla.
    } // setImpDel

    public boolean getImpUpd (M entity, C ctx) {
        // Di default se l'entità è stata inserita dalla risorsa di servizio per l'import allora può essere aggiornata in import.
        return NumberUtil.equalsLong (entity.getRsi (), ctx.getContext ().getResource ());
    } // getImpUpd
} // AGenericTmp
