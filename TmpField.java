package net.synergy2.logic.fct.tmp.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

import net.synergy2.base.connections.SStm;

public class TmpField<V, E> {

    private Function<E, V> valueGetter;
    private BiConsumer<V, E> valueSetter;
    private BiPredicate<V, V> comparator;
    private BiPredicate<V, V> comparatorByCode;
    private BiConsumer<E, E> referenceMerger;
    private Supplier<String> sqlFieldGetter;
    private ApsErpFieldSqlGetter<V> sqlGetter;
    private ApsErpFieldSqlSetter<E> sqlSetter;
    private ApsErpFieldValueMerger<E> valueMerger;
    
    public TmpField () { } // ApsErpField

    public TmpField<V, E> setValueGetter (Function<E, V> valueGetter) { this.valueGetter = valueGetter; return this; } // setValueGetter
    public TmpField<V, E> setValueSetter (BiConsumer<V, E> valueSetter) { this.valueSetter = valueSetter; return this; } // setValueSetter
    public TmpField<V, E> setReferenceMerger (BiConsumer<E, E> referenceMerger) { this.referenceMerger = referenceMerger; return this; } // setreferenceMerger
    public TmpField<V, E> setComparator (BiPredicate<V, V> comparator) { this.comparator = comparator; return this; } // setComparator
    public TmpField<V, E> setComparatorByCode (BiPredicate<V, V> comparatorByCode) { this.comparatorByCode = comparatorByCode; return this; } // setComparatorByCode
    public TmpField<V, E> setSqlFieldGetter (Supplier<String> sqlFieldGetter) { this.sqlFieldGetter = sqlFieldGetter; return this; } // setSqlFieldGetter
    public TmpField<V, E> setSqlGetter (ApsErpFieldSqlGetter<V> sqlGetter) { this.sqlGetter = sqlGetter; return this; } // setSqlGetter
    public TmpField<V, E> setSqlSetter (ApsErpFieldSqlSetter<E> sqlSetter) { this.sqlSetter = sqlSetter; return this; } // setSqlSetter
    public TmpField<V, E> setValueMerger (ApsErpFieldValueMerger<E> valueMerger) { this.valueMerger = valueMerger; return this; } // setValueMerger
    
    /** @return Data l'entità, ottiene il valore di un campo. */
    public V getValue (E entity) { return valueGetter.apply (entity); } // getValue
    
    /** @return Dato il valore e l'entità, imposta correttamente il valore al suo interno. */
    public void setValue (V value, E entity) { valueSetter.accept (value, entity); } // setValue
    
    /** @return Data l'entità in db e quella in arrivo, permettere di fare operazioni complesse. */
    public void applyReferenceMerger (E entity, E incomingEntity) { referenceMerger.accept (entity, incomingEntity); } // applyReferenceMerger
    
    /** @return Date due entità, effettua un confronto di specifico tra esse. */
    public boolean isValueEqual (E entity1, E entity2) { return comparator.test (getValue (entity1), getValue (entity2)); } // isValueEqual
    
    /** @return Date due entità, effettua un confronto di specifico tra esse (valutandone il codice). */
    public boolean isValueEqualByCode (E entity1, E entity2) { 
        // Per evitare duplicazione comparator e comparatorByCode in metodi con tipi statici (String, bool etc..)
        var cmp = comparatorByCode != null ? comparatorByCode : comparator;
        if(cmp == null) return true;
        return cmp.test (getValue (entity1), getValue (entity2)); 
    } // isValueEqualByCode
    
    /** @return Ottiene la stringa precedentemente impostata dalla funzione callback. */
    public String getSqlField () { return sqlFieldGetter != null ? sqlFieldGetter.get () : null; } // getSqlField
    
    /** @return Imposta sull'entità il valore ottenuto da un result set sql. */
    public void applySqlValueGetter (ResultSet rs, E entity) throws SQLException {
        V value = sqlGetter.getValue (rs, getSqlField ());
        setValue (value, entity);
    } // applySqlValueGetter
    
    /** @return Determina se presente la callback per impostare il valore in un sql. */
    public boolean hasSqlSetter () { return this.sqlSetter != null; }
    public void applySqlValueSetter (SStm stm, int index, E entity) throws SQLException {
        sqlSetter.setValue (stm, index, entity);
    } // applySqlValueSetter
    
    /** @return Determina, se impostata, se il valore del record di destra può essere mergiato in quello di sinistra. */
    public boolean canMergeValue (E entity, E incomingEntity) {
        return valueMerger == null ? true : valueMerger.canMerge (entity, incomingEntity);
    } // canMergeValue
    
    /** @return Copia il valore di destra in quello di sinistra, date due entità, se possibile. */
    public void mergeFieldValue (E entity, E incomingEntity) {
        if (this.canMergeValue (entity, incomingEntity)) {
            V value = this.getValue (incomingEntity);
            this.setValue (value, entity);
            if (this.referenceMerger != null) {
                this.applyReferenceMerger (entity, incomingEntity);
            } // if
        } // if
    } // mergeFieldValue

    @FunctionalInterface
    public interface ApsErpFieldSqlGetter<V> {
        public V getValue (ResultSet rs, String fieldName) throws SQLException;
    } // ApsErpFieldSqlGetter

    @FunctionalInterface
    public interface ApsErpFieldSqlSetter<E> {
        public void setValue (SStm stm, int index, E entity) throws SQLException;
    } // ApsErpFieldSqlSetter
    
    @FunctionalInterface
    public interface ApsErpFieldValueMerger<E> {
        public boolean canMerge (E entity, E incomingEntity);
    } // ApsErpFieldValueMerger
    
    @Override
    public String toString () {
        String fieldName = this.sqlFieldGetter != null ? this.sqlFieldGetter.get () : "";
        return fieldName;
    } // toString
    
} // TmpField

