package net.synergy2.logic.fct.tmp.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.synergy2.db.sys.bas.AngSup;
import net.synergy2.db.sys.itm.AngItm;
import net.synergy2.db.sys.lgs.AngWhs;
import net.synergy2.logic.fct.tmp.impl.TmpWarningEntity;

public class FctTmpStore {

    public static final String MEASURE_UNITS_KEY = "measureUnits";
    public static final String VENDORS_KEY = "vendors";
    public static final String WAREHOUSES_KEY = "warehouses";
    public static final String ITEMS_KEY = "items";
    
    public FctTmpStore (Map<String, Set<String>> dependencies) {
        this.warnings = new ArrayList<> ();
        
        this.dependencies = dependencies;
        this.referenceDependencies = this.calcReferenceDependencies ();
        this.satisfiedDependencies = new HashSet<> ();
        this.runnableForNullByKey = new HashMap<> ();

        runnableForNullByKey.put (WAREHOUSES_KEY, () -> setWarehouses (null));
        runnableForNullByKey.put (ITEMS_KEY, () -> setItems (null));
        runnableForNullByKey.put (VENDORS_KEY, () -> setVendors (null));
        runnableForNullByKey.put (MEASURE_UNITS_KEY, () -> setMeasureUnits (null));

    } // FctTmpStore
    
    private List<TmpWarningEntity> warnings;

    protected final Map<String, Set<String>> dependencies;
    protected final Map<String, Set<String>> referenceDependencies;
    protected final Set<String> satisfiedDependencies;
    protected final Map<String, Runnable> runnableForNullByKey;

    /************************************************************************************/
    /** DATI **************************************************************************/
    /************************************************************************************/

    private Map<String, Long> measureUnits;
    private Map<String, Long> vendors;
    private Map<String, Long> warehouses;
    private Map<String, Long> items;

    private AngSup dummyAngSup;
    private AngItm dummyAngItm;
    private AngWhs dummyAngWhs;
    
    /************************************************************************************/
    /** SETTERS E GETTERS ***************************************************************/
    /************************************************************************************/

    public Map<String, Long> getMeasureUnits () { return measureUnits; } // getMeasureUnits
    public void setMeasureUnits (Map<String, Long> measureUnits) { this.measureUnits = measureUnits; } // setMeasureUnits
    public void addMeasureUnit (String code, long uid) { if (this.measureUnits == null) { this.measureUnits = new HashMap<> (); } if (!this.measureUnits.containsKey (code)) { this.measureUnits.put (code, uid); } } // addMeasureUnit

    public Map<String, Long> getVendors () { return vendors; } // getVendors
    public void setVendors (Map<String, Long> vendors) { this.vendors = vendors; } // setVendors
    public void addVendor (String code, long uid) { if (this.vendors == null) { this.vendors = new HashMap<> (); } if (!this.vendors.containsKey (code)) { this.vendors.put (code, uid); } } // addVendor

    public Map<String, Long> getWarehouses () { return warehouses; } // getWarehouses
    public void setWarehouses (Map<String, Long> warehouses) { this.warehouses = warehouses; } // setWarehouses
    public void addWarehouse (String code, long uid) { if (this.warehouses == null) { this.warehouses = new HashMap<> (); } if (!this.warehouses.containsKey (code)) { this.warehouses.put (code, uid); } } // addWarehouse

    public Map<String, Long> getItems () { return items; } // getItems
    public void setItems (Map<String, Long> items) { this.items = items; } // setItems
    public void addItem (String code, long uid) { if (this.items == null) { this.items = new HashMap<> (); } if (!this.items.containsKey (code)) { this.items.put (code, uid); } } // addItem

    public List<TmpWarningEntity> getWarnings () { return warnings; } // getWarningList
    public void setWarningList (List<TmpWarningEntity> warnings) { this.warnings = warnings; } // warningList
    public void addWarning (TmpWarningEntity warning) { this.warnings.add (warning); } // addWarning

    /*************************************************************************/
    /*** DUMMY ***************************************************************/
    /*************************************************************************/

    public AngSup getDummyAngSup () { return dummyAngSup; }
    public void setDummyAngSup (AngSup dummy) { dummyAngSup = dummy;}
    
    public AngItm getDummyAngItm () { return dummyAngItm; }
    public void setDummyAngItm (AngItm dummy) { dummyAngItm = dummy;}

    public AngWhs getDummyAngWhs () { return dummyAngWhs; }
    public void setDummyAngWhs (AngWhs dummy) { dummyAngWhs = dummy;}
    
    /************************************************************************************/
    /** UTIL ****************************************************************************/
    /************************************************************************************/
    
    public Map<String, Set<String>> calcReferenceDependencies () {
        Map<String, Set<String>> toReturn = new HashMap<> ();
        for (Entry<String, Set<String>> entry : this.dependencies.entrySet ()) {
            String key = entry.getKey ();
            Set<String> deps = entry.getValue ();
            for (String dep : deps) {
                toReturn.compute (dep, (refKey, references) -> {
                    if (references == null) { references = new HashSet<> (); }
                    references.add (key);
                    return references;
                });
            } // for
        } // for
        return toReturn;
    } // calcReferenceDependencies

    public boolean updateDependency (String key, String dependency) {
        this.referenceDependencies.get (key).remove (dependency);
        return this.referenceDependencies.get (key).isEmpty ();
    } // updateDependency
    
    public boolean calcSatisfiedDependency (String depKey, List<String> resolvedDependecies) {
        Set<String> dependencies = this.dependencies.get (depKey);
        this.satisfiedDependencies.add (depKey);
        if (dependencies != null) {
            for (String refKey : dependencies) {
                if (!this.satisfiedDependencies.contains (refKey)) {
                    // Elaborazione errata per dipendenza.
                    return false;
                } // if
                boolean allDependencyResolved = updateDependency (refKey, depKey);
                if (allDependencyResolved) {
                    resolvedDependecies.add (refKey);
                } // if
            } // for
        } // if
        if (!this.referenceDependencies.containsKey (depKey)) {
            resolvedDependecies.add (depKey);
        } // if
        return true;
    } // calcSatisfiedDependency
    
    public boolean calcSatisfiedDependencyObjects (String depKey) {
        List<String> resolvedDependecies = new ArrayList<> ();
        boolean result = calcSatisfiedDependency (depKey, resolvedDependecies);
        if (result && !resolvedDependecies.isEmpty ()) {
            for (String resolvedDependencyKey : resolvedDependecies) {
                Runnable runnableForNull = runnableForNullByKey.get (resolvedDependencyKey);
                // Può essere null, quando sono oggetti che non servono a nessuno, esempio l'associazione risorse-gruppi non serve come dipendenza a nessuno, 
                // per cui non è stata prevista nello store appositamente per performance
                if (runnableForNull != null) {
                    runnableForNull.run ();
                } // if
            } // for
        } // if
        return result;
    } // clearSatisfiedDependency

} // FctTmpStore
