package net.synergy2.logic.aps.tmp.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.synergy2.db.aps.att.AngApsAttVal;
import net.synergy2.db.aps.cst.AngApsCst;
import net.synergy2.db.aps.cst.AngApsCstGrp;
import net.synergy2.db.aps.opr.ApsOpr;
import net.synergy2.db.aps.opr.ApsShpOrd;
import net.synergy2.db.aps.res.AngApsRes;
import net.synergy2.db.aps.sce.ApsSceCst;
import net.synergy2.db.aps.sce.ApsSceRes;
import net.synergy2.db.sys.bas.AngCus;
import net.synergy2.db.sys.grp.AngGrp;
import net.synergy2.db.sys.itm.AngItmMdl;
import net.synergy2.db.sys.itm.AngItmVrn;
import net.synergy2.db.sys.itm.AngPrdCyc;
import net.synergy2.db.sys.itm.AngPrdCycOpr;
import net.synergy2.db.sys.itm.AngPrdVrn;
import net.synergy2.db.sys.itm.AngPrdVrnCat;
import net.synergy2.db.sys.job.AngJob;
import net.synergy2.db.sys.res.AngRes;
import net.synergy2.logic.fct.tmp.common.FctTmpStore;

public class ApsTmpStore extends FctTmpStore {

    public static final String JOBS_KEY = "jobs";
    public static final String SUB_JOBS_KEY = "subJobs";
    public static final String PLANTS_KEY = "plants";
    public static final String ATTRIBUTE_VALUES_KEY = "attributeValues";
    public static final String GROUPS_KEY = "groups";
    public static final String OPERATION_COLORS_KEY = "operationColors";
    public static final String SHOP_ORDER_ICONS_KEY = "shopOrderIcons";
    public static final String RESOURCES_KEY = "resources";
    public static final String RESOURCES_GROUPS_KEY = "resourcesGroups";
    public static final String PRIMARY_RESOURCES_KEY = "primaryResources";
    public static final String SCENARIO_RESOURCES_KEY = "scenarioResources";
    public static final String SECONDARY_CONSTRAINTS_KEY = "secondaryConstraints";
    public static final String SCENARIO_SECONDARY_CONSTRAINTS_KEY = "scenarioSecondaryConstraints";
    public static final String SECONDARY_CONSTRAINT_GROUPS_KEY = "secondaryConstraintGroups";
    public static final String SCENARIO_RESOURCES_CURRENT_SECONDARY_CONSTRAINTS_KEY = "scenarioPrimaryResourcesCurrentScenarioSecondaryConstraints";
    public static final String SCENARIO_RESOURCES_CURRENT_ATTRIBUTE_VALUES_KEY = "scenarioPrimaryResourcesCurrentAttributeValues";
    public static final String CUSTOMERS_KEY = "customers";
    public static final String ROUTES_KEY = "routes";
    public static final String ROUTE_ROWS_KEY = "routeRows";
    public static final String BOMS_KEY = "boms";
    public static final String VARIANT_CATEGORIES_KEY = "variantCategories";
    public static final String VARIANTS_KEY = "variants";
    public static final String ITEM_VARIANTS_KEY = "itemsVariants";
    public static final String ITEM_MODELS_KEY = "itemModels";
    public static final String ITEM_MODEL_VARIANTS_KEY = "itemModelVariants";
    public static final String ROUTE_ROW_LINKS_KEY = "routeRowLinks";
    public static final String ROUTE_ROW_SECONDARY_CONSTRAINTS_KEY = "routeRowSecondaryConstraints";
    public static final String ROUTE_ROW_SECONDARY_CONSTRAINT_GROUPS_KEY = "routeRowSecondaryConstraintGroups";
    public static final String ROUTE_ROW_ATTRIBUTE_VALUES_KEY = "routeRowAttributeValues";
    public static final String ROUTE_ROW_RESOURCE_EXCEPTIONS_KEY = "routeRowResourceExceptions";
    public static final String CUSTOMER_ORDERS_KEY = "customerOrders";
    public static final String DEMANDS_KEY = "demands";
    public static final String SUPPLY_ORDERS_KEY = "supplyOrders";
    public static final String SUPPLIES_KEY = "supplies";
    public static final String SHOP_ORDERS_KEY = "shopOrders";
    public static final String CO_PRODUCTS_KEY = "coProducts";
    public static final String AGGREGATED_OPERATIONS_KEY = "aggregatedOperations";
    public static final String MULTIPALLET_OPERATIONS_KEY = "multipalletOperations";
    public static final String OPERATIONS_KEY = "operations";
    public static final String MATERIALS_KEY = "materials";
    public static final String OPERATION_LINKS_KEY = "operationLinks";
    public static final String OPERATION_SCENARIO_SECONDARY_CONSTRAINTS_KEY = "operationScenarioSecondaryConstraints";
    public static final String OPERATION_SECONDARY_CONSTRAINT_GROUPS_KEY = "operationSecondaryConstraintGroups";
    public static final String OPERATION_ATTRIBUTE_VALUES_KEY = "operationAttributeValues";
    public static final String OPERATION_RESOURCE_EXCEPTIONS_KEY = "operationResourceExceptions";
    public static final String ORDER_LINKS_KEY = "orderLinks";
    public static final String QUOTATION_KEY = "quotations";
    
    public ApsTmpStore (Map<String, Set<String>> dependencies) {
        // Richiamo il costruttore della classe FctVrtStore per passargli le dipendenze necessarie.
        super (dependencies);

        runnableForNullByKey.put (JOBS_KEY, () -> setJobs (null));
        runnableForNullByKey.put (SUB_JOBS_KEY, () -> setSubJobs (null));
        runnableForNullByKey.put (OPERATION_COLORS_KEY, () -> setOperationColors (null));
        runnableForNullByKey.put (PLANTS_KEY, () -> setPlants (null));
        runnableForNullByKey.put (ATTRIBUTE_VALUES_KEY, () -> setAttributeValues (null));
        runnableForNullByKey.put (GROUPS_KEY, () -> setGroups (null));
        runnableForNullByKey.put (RESOURCES_KEY, () -> setResources (null));
        runnableForNullByKey.put (PRIMARY_RESOURCES_KEY, () -> setPrimaryResources (null));
        runnableForNullByKey.put (SCENARIO_RESOURCES_KEY, () -> setScenarioResources (null));
        runnableForNullByKey.put (SECONDARY_CONSTRAINTS_KEY, () -> setSecondaryConstraints (null));
        runnableForNullByKey.put (SCENARIO_SECONDARY_CONSTRAINTS_KEY, () -> setScenarioSecondaryConstraints (null));
        runnableForNullByKey.put (SECONDARY_CONSTRAINT_GROUPS_KEY, () -> setSecondaryConstraintGroups (null));
        runnableForNullByKey.put (SCENARIO_RESOURCES_CURRENT_SECONDARY_CONSTRAINTS_KEY, () -> setScenarioPrimaryResourcesCurrentScenarioSecondaryConstraints (null));
        runnableForNullByKey.put (SCENARIO_RESOURCES_CURRENT_ATTRIBUTE_VALUES_KEY, () -> setScenarioPrimaryResourcesCurrentAttributeValues (null));
        runnableForNullByKey.put (CUSTOMERS_KEY, () -> setCustomers (null));
        runnableForNullByKey.put (ROUTES_KEY, () -> setRoutes (null));
        runnableForNullByKey.put (ROUTE_ROWS_KEY, () -> setRouteRows (null));
        runnableForNullByKey.put (BOMS_KEY, () -> setBoms (null));
        runnableForNullByKey.put (VARIANT_CATEGORIES_KEY, () -> setVariantCategories (null));
        runnableForNullByKey.put (VARIANTS_KEY, () -> setVariants (null));
        runnableForNullByKey.put (ITEM_VARIANTS_KEY, () -> setItemVariants (null));
        runnableForNullByKey.put (ITEM_MODELS_KEY, () -> setItemModels (null));
        runnableForNullByKey.put (ITEM_MODEL_VARIANTS_KEY, () -> setItemModelVariants (null));
        runnableForNullByKey.put (ROUTE_ROW_LINKS_KEY, () -> setRouteRowLinks (null));
        runnableForNullByKey.put (ROUTE_ROW_SECONDARY_CONSTRAINTS_KEY, () -> setRouteRowSecondarioConstraints (null));
        runnableForNullByKey.put (ROUTE_ROW_SECONDARY_CONSTRAINT_GROUPS_KEY, () -> setRouteRowSecondaryConstraintGroups (null));
        runnableForNullByKey.put (ROUTE_ROW_ATTRIBUTE_VALUES_KEY, () -> setRouteRowAttributeValues (null));
        runnableForNullByKey.put (CUSTOMER_ORDERS_KEY, () -> setCustomerOrders (null));
        runnableForNullByKey.put (DEMANDS_KEY, () -> setDemands (null));
        runnableForNullByKey.put (SUPPLY_ORDERS_KEY, () -> setSupplyOrders (null));
        runnableForNullByKey.put (SUPPLIES_KEY, () -> setSupplies (null));
        runnableForNullByKey.put (SHOP_ORDERS_KEY, () -> setShopOrders (null));
        runnableForNullByKey.put (CO_PRODUCTS_KEY, () -> setCoProducts (null));
        runnableForNullByKey.put (AGGREGATED_OPERATIONS_KEY, () -> setAggregatedOperations (null));
        runnableForNullByKey.put (MULTIPALLET_OPERATIONS_KEY, () -> setMultipalletOperations (null));
        runnableForNullByKey.put (OPERATIONS_KEY, () -> setOperations (null));
        runnableForNullByKey.put (MATERIALS_KEY, () -> setMaterials (null));
        runnableForNullByKey.put (OPERATION_LINKS_KEY, () -> setOperationLinks (null));
        runnableForNullByKey.put (QUOTATION_KEY, () -> setQuotations (null));
    } // ApsTmpStore

    /************************************************************************************/
    /**  DATI  **************************************************************************/
    /************************************************************************************/
    
    private Long currentScenario;
    
    private Map<String, Long> jobs = new HashMap<> ();
    private Map<String, Long> subJobs = new HashMap<> ();
    private Map<String, Long> operationColors = new HashMap<> ();
    private Map<String, Long> shopOrderIcons = new HashMap<> ();
    private Map<String, Long> plants = new HashMap<> ();
    private Map<String, Long> attributeValues = new HashMap<> ();
    private Map<String, Long> groups = new HashMap<> ();
    private Map<String, Long> resources = new HashMap<> ();
    private Map<String, Long> primaryResources = new HashMap<> ();
    private Map<String, Long> scenarioResources = new HashMap<> ();
    private Map<String, Long> secondaryConstraints = new HashMap<> ();
    private Map<String, Long> scenarioSecondaryConstraints = new HashMap<> ();
    private Map<String, Long> secondaryConstraintGroups = new HashMap<> ();
    private Map<String, Long> scenarioPrimaryResourcesCurrentScenarioSecondaryConstraints = new HashMap<> ();
    private Map<String, Long> scenarioPrimaryResourcesCurrentAttributeValues = new HashMap<> ();
    private Map<String, Long> customers = new HashMap<> ();
    private Map<String, Long> routes = new HashMap<> ();
    private Map<String, Long> routeRows = new HashMap<> ();
    private Map<String, Long> boms = new HashMap<> ();
    private Map<String, Long> variantCategories = new HashMap<> ();
    private Map<String, Long> variants = new HashMap<> ();
    private Map<String, Long> itemVariants = new HashMap<> ();
    private Map<String, Long> itemModels = new HashMap<> ();
    private Map<String, Long> itemModelVariants = new HashMap<> ();
    private Map<String, Long> routeRowLinks = new HashMap<> ();
    private Map<String, Long> routeRowSecondaryConstraints = new HashMap<> ();
    private Map<String, Long> routeRowSecondaryConstraintGroups = new HashMap<> ();
    private Map<String, Long> routeRowAttributeValues = new HashMap<> ();
    private Map<String, Long> customerOrders = new HashMap<> ();
    private Map<String, Long> demands = new HashMap<> ();
    private Map<String, Long> supplyOrders = new HashMap<> ();
    private Map<String, Long> supplies = new HashMap<> ();
    private Map<String, Long> shopOrders = new HashMap<> ();
    private Map<String, Long> coProducts = new HashMap<> ();
    private Map<String, Long> aggregatedOperations = new HashMap<> ();
    private Map<String, Long> multipalletOperations = new HashMap<> ();
    private Map<String, Long> operations = new HashMap<> ();
    private Map<String, Long> materials = new HashMap<> ();
    private Map<String, Long> operationLinks = new HashMap<> ();
    private Map<String, Long> quotations = new HashMap<> ();
    
    private AngApsAttVal dummyAngApsAttVal;
    private AngApsCst dummyAngApsCst;
    private AngApsCstGrp dummyAngApsCstGrp;
    private AngApsRes dummyAngApsRes;
    private AngCus dummyAngCus;
    private AngGrp dummyAngGrp;
    private AngJob dummyAngJob;
    private AngPrdCyc dummyAngPrdCyc;
    private AngPrdCycOpr dummyAngPrdCycOpr;
    private AngPrdVrnCat dummyAngPrdVrnCat;
    private AngPrdVrn dummyAngPrdVrn;
    private AngItmVrn dummyAngItmVrn;
    private AngItmMdl dummyAngItmMdl;
    private AngRes dummyAngRes;
    private ApsSceCst dummyApsSceCst;
    private ApsSceRes dummyApsSceRes;
    private ApsShpOrd dummyApsShpOrd;
    private ApsOpr dummyApsOpr;
    // dummyApsOpr... in caso si evidenziassero questi casi su materiali etc.
    
    /************************************************************************************/
    /**  SETTERS E GETTERS  *************************************************************/
    /************************************************************************************/
    
    public void setCurrentScenario (long currentScenario) { this.currentScenario = currentScenario; }
    public Long getCurrentScenario () { return currentScenario; }

    public Map<String, Long> getJobs () { return jobs; } // getJobs
    public void setJobs (Map<String, Long> jobs) { this.jobs = jobs; } // setJobs
    public void addJob (String code, long uid) { if (this.jobs == null) { this.jobs = new HashMap<> (); } if (!this.jobs.containsKey (code)) { this.jobs.put (code, uid); } } // addJob

    public Map<String, Long> getSubJobs () { return subJobs; } // getSubJobs
    public void setSubJobs (Map<String, Long> subJobs) { this.subJobs = subJobs; } // setSubJobs
    public void addSubJob (String code, long uid) { if (this.subJobs == null) { this.subJobs = new HashMap<> (); } if (!this.subJobs.containsKey (code)) { this.subJobs.put (code, uid); } } // addSubJob

    public Map<String, Long> getOperationColors () { return operationColors; } // getOperationColors
    public void setOperationColors (Map<String, Long> operationColors) { this.operationColors = operationColors; } // setOperationColors
    public void addOperationColor (String code, long uid) { if (this.operationColors == null) { this.operationColors = new HashMap<> (); } if (!this.operationColors.containsKey (code)) { this.operationColors.put (code, uid); } } // addOperationColor
    
    public Map<String, Long> getShopOrderIcons () { return shopOrderIcons; } // getShopOrderIcons
    public void setShopOrderIcons (Map<String, Long> shopOrderIcons) { this.shopOrderIcons = shopOrderIcons; runnableForNullByKey.put (SHOP_ORDER_ICONS_KEY, () -> setShopOrderIcons (null)); } // setShopOrderIcons
    public void addShopOrderIcon (String code, long uid) { if (this.shopOrderIcons == null) { this.shopOrderIcons = new HashMap<> (); } if (!this.shopOrderIcons.containsKey (code)) { this.shopOrderIcons.put (code, uid); } } // addShopOrderIcon
    
    public Map<String, Long> getPlants () { return plants; } // getPlants
    public void setPlants (Map<String, Long> plants) { this.plants = plants; } // setPlants
    public void addPlant (String code, long uid) { if (this.plants == null) { this.plants = new HashMap<> (); } if (!this.plants.containsKey (code)) { this.plants.put (code, uid); } } // addPlant

    public Map<String, Long> getAttributeValues () { return attributeValues; } // getAttributeValues
    public void setAttributeValues (Map<String, Long> attributeValues) { this.attributeValues = attributeValues; } // setAttributeValues
    public void addAttributeValue (String code, long uid) { if (this.attributeValues == null) { this.attributeValues = new HashMap<> (); } if (!this.attributeValues.containsKey (code)) { this.attributeValues.put (code, uid); } } // addAttributeValue

    public Map<String, Long> getGroups () { return groups; } // getGroups
    public void setGroups (Map<String, Long> groups) { this.groups = groups; } // setGroups
    public void addGroup (String code, long uid) { if (this.groups == null) { this.groups = new HashMap<> (); } if (!this.groups.containsKey (code)) { this.groups.put (code, uid); } } // addGroup

    public Map<String, Long> getResources () { return resources; } // getResources
    public void setResources (Map<String, Long> resources) { this.resources = resources; } // setResources
    public void addResource (String code, long uid) { if (this.resources == null) { this.resources = new HashMap<> (); } if (!this.resources.containsKey (code)) { this.resources.put (code, uid); } } // addResource

    public Map<String, Long> getPrimaryResources () { return primaryResources; } // getPrimaryResources
    public void setPrimaryResources (Map<String, Long> primaryResources) { this.primaryResources = primaryResources; } // setPrimaryResources
    public void addPrimaryResource (String code, long uid) { if (this.primaryResources == null) { this.primaryResources = new HashMap<> (); } if (!this.primaryResources.containsKey (code)) { this.primaryResources.put (code, uid); } } // addPrimaryResource

    public Map<String, Long> getScenarioResources () { return scenarioResources; } // getScenarioResources
    public void setScenarioResources (Map<String, Long> scenarioResources) { this.scenarioResources = scenarioResources; } // setScenarioResources
    public void addScenarioResource (String code, long uid) { if (this.scenarioResources == null) { this.scenarioResources = new HashMap<> (); } if (!this.scenarioResources.containsKey (code)) { this.scenarioResources.put (code, uid); } } // addScenarioResource

    public Map<String, Long> getSecondaryConstraints () { return secondaryConstraints; } // getSecondaryConstraints
    public void setSecondaryConstraints (Map<String, Long> secondaryConstraints) { this.secondaryConstraints = secondaryConstraints; } // setSecondaryConstraints
    public void addSecondaryConstraint (String code, long uid) { if (this.secondaryConstraints == null) { this.secondaryConstraints = new HashMap<> (); } if (!this.secondaryConstraints.containsKey (code)) { this.secondaryConstraints.put (code, uid); } } // addSecondaryConstraint

    public Map<String, Long> getScenarioSecondaryConstraints () { return scenarioSecondaryConstraints; } // getScenarioSecondaryConstraints
    public void setScenarioSecondaryConstraints (Map<String, Long> scenarioSecondaryConstraints) { this.scenarioSecondaryConstraints = scenarioSecondaryConstraints; } //setScenarioSecondaryConstraints
    public void addScenarioSecondaryConstraint (String code, long uid) { if (this.scenarioSecondaryConstraints == null) { this.scenarioSecondaryConstraints = new HashMap<> (); } if (!this.scenarioSecondaryConstraints.containsKey (code)) { this.scenarioSecondaryConstraints.put (code, uid); } } // addScenarioSecondaryConstraint

    public Map<String, Long> getSecondaryConstraintGroups () { return secondaryConstraintGroups; } // getSecondaryConstraintGroups
    public void setSecondaryConstraintGroups (Map<String, Long> secondaryConstraintGroups) { this.secondaryConstraintGroups = secondaryConstraintGroups; } // setSecondaryConstraintGroups
    public void addSecondaryConstraintGroup (String code, long uid) { if (this.secondaryConstraintGroups == null) { this.secondaryConstraintGroups = new HashMap<> (); } if (!this.secondaryConstraintGroups.containsKey (code)) { this.secondaryConstraintGroups.put (code, uid); } } // addSecondaryConstraintGroup

    public Map<String, Long> getScenarioPrimaryResourcesCurrentScenarioSecondaryConstraints () { return scenarioPrimaryResourcesCurrentScenarioSecondaryConstraints; } // getScenarioPrimaryResourcesCurrentScenarioSecondaryConstraints
    public void setScenarioPrimaryResourcesCurrentScenarioSecondaryConstraints (Map<String, Long> scenarioPrimaryResourcesCurrentScenarioSecondaryConstraints) { this.scenarioPrimaryResourcesCurrentScenarioSecondaryConstraints = scenarioPrimaryResourcesCurrentScenarioSecondaryConstraints; } //setScenarioPrimaryResourcesCurrentScenarioSecondaryConstraints
    public void addScenarioPrimaryResourcesCurrentScenarioSecondaryConstraint (String code, long uid) { if (this.scenarioPrimaryResourcesCurrentScenarioSecondaryConstraints == null) { this.scenarioPrimaryResourcesCurrentScenarioSecondaryConstraints = new HashMap<> (); } if (!this.scenarioPrimaryResourcesCurrentScenarioSecondaryConstraints.containsKey (code)) { this.scenarioPrimaryResourcesCurrentScenarioSecondaryConstraints.put (code, uid); } } // addScenarioPrimaryResourcesCurrentScenarioSecondaryConstraint

    public Map<String, Long> getScenarioPrimaryResourcesCurrentAttributeValues () { return scenarioPrimaryResourcesCurrentAttributeValues; } // getScenarioPrimaryResourcesCurrentAttributeValues
    public void setScenarioPrimaryResourcesCurrentAttributeValues (Map<String, Long> scenarioPrimaryResourcesCurrentAttributeValues) { this.scenarioPrimaryResourcesCurrentAttributeValues = scenarioPrimaryResourcesCurrentAttributeValues; } //setScenarioPrimaryResourcesCurrentAttributeValues
    public void addScenarioPrimaryResourcesCurrentAttributeValue (String code, long uid) { if (this.scenarioPrimaryResourcesCurrentAttributeValues == null) { this.scenarioPrimaryResourcesCurrentAttributeValues = new HashMap<> (); } if (!this.scenarioPrimaryResourcesCurrentAttributeValues.containsKey (code)) { this.scenarioPrimaryResourcesCurrentAttributeValues.put (code, uid); } } // addScenarioPrimaryResourcesCurrentAttributeValue
    
    public Map<String, Long> getCustomers () { return customers; } // getCustomers
    public void setCustomers (Map<String, Long> customers) { this.customers = customers; } // setCustomers
    public void addCustomer (String code, long uid) { if (this.customers == null) { this.customers = new HashMap<> (); } if (!this.customers.containsKey (code)) { this.customers.put (code, uid); } } // addCustomer

    public Map<String, Long> getRoutes () { return routes; } // getRoutes
    public void setRoutes (Map<String, Long> routes) { this.routes = routes; } // setRoutes
    public void addRoute (String code, long uid) { if (this.routes == null) { this.routes = new HashMap<> (); } if (!this.routes.containsKey (code)) { this.routes.put (code, uid); } } // addRoute

    public Map<String, Long> getRouteRows () { return routeRows; } // getRouteRows
    public void setRouteRows (Map<String, Long> routeRows) { this.routeRows = routeRows; } // setRouteRows
    public void addRouteRow (String code, long uid) { if (this.routeRows == null) { this.routeRows = new HashMap<> (); } if (!this.routeRows.containsKey (code)) { this.routeRows.put (code, uid); } } // addRouteRow

    public Map<String, Long> getBoms () { return boms; } // getBoms
    public void setBoms (Map<String, Long> boms) { this.boms = boms; } // setBoms
    public void addBom (String code, long uid) { if (this.boms == null) { this.boms = new HashMap<> (); } if (!this.boms.containsKey (code)) { this.boms.put (code, uid); } } // addBom
    
    public Map<String, Long> getVariantCategories () { return variantCategories; } // getVariantCategories
    public void setVariantCategories (Map<String, Long> variantCategories) { this.variantCategories = variantCategories; } // setVariantCategories
    public void addVariantCategory (String code, long uid) { if (this.variantCategories == null) { this.variantCategories = new HashMap<> (); } if (!this.variantCategories.containsKey (code)) { this.variantCategories.put (code, uid); } } // addVariantCategory

    public Map<String, Long> getVariants () { return variants; } // getVariants
    public void setVariants (Map<String, Long> variants) { this.variants = variants; } // setVariants
    public void addVariant (String code, long uid) { if (this.variants == null) { this.variants = new HashMap<> (); } if (!this.variants.containsKey (code)) { this.variants.put (code, uid); } } // addVariant

    public Map<String, Long> getItemVariants () { return itemVariants; } // getItemVariants
    public void setItemVariants (Map<String, Long> itemVariants) { this.itemVariants = itemVariants; } // setItemVariants
    public void addItemVariant (String code, long uid) { if (this.itemVariants == null) { this.itemVariants = new HashMap<> (); } if (!this.itemVariants.containsKey (code)) { this.itemVariants.put (code, uid); } } // addItemVariant

    public Map<String, Long> getItemModels () { return itemModels; } // getItemModels
    public void setItemModels (Map<String, Long> itemModels) { this.itemModels = itemModels; } // setItemModels
    public void addItemModel (String code, long uid) { if (this.itemModels == null) { this.itemModels = new HashMap<> (); } if (!this.itemModels.containsKey (code)) { this.itemModels.put (code, uid); } } // addItemModel

    public Map<String, Long> getItemModelVariants () { return itemModelVariants; } // getItemModelVariants
    public void setItemModelVariants (Map<String, Long> itemModelVariants) { this.itemModelVariants = itemModelVariants; } // setItemModelVariants
    public void addItemModelVariant (String code, long uid) { if (this.itemModelVariants == null) { this.itemModelVariants = new HashMap<> (); } if (!this.itemModelVariants.containsKey (code)) { this.itemModelVariants.put (code, uid); } } // addItemModelVariant

    public Map<String, Long> getRouteRowLinks () { return routeRowLinks; } // getRouteRowLinks
    public void setRouteRowLinks (Map<String, Long> routeRowLinks) { this.routeRowLinks = routeRowLinks; } // setRouteRowLinks
    public void addRouteRowLink (String code, long uid) { if (this.routeRowLinks == null) { this.routeRowLinks = new HashMap<> (); } if (!this.routeRowLinks.containsKey (code)) { this.routeRowLinks.put (code, uid); } } // addRouteRowLink

    public Map<String, Long> getRouteRowSecondaryConstraints () { return routeRowSecondaryConstraints; } // getRouteRowSecondaryConstraints
    public void setRouteRowSecondarioConstraints (Map<String, Long> routeRowSecondaryConstraints) { this.routeRowSecondaryConstraints = routeRowSecondaryConstraints; } // setRouteRowSecondarioConstraints
    public void addRouteRowSecondarioConstraint (String code, long uid) { if (this.routeRowSecondaryConstraints == null) { this.routeRowSecondaryConstraints = new HashMap<> (); } if (!this.routeRowSecondaryConstraints.containsKey (code)) { this.routeRowSecondaryConstraints.put (code, uid); } } // addRouteRowSecondarioConstraint

    public Map<String, Long> getRouteRowSecondaryConstraintGroups () { return routeRowSecondaryConstraintGroups; } // getRouteRowSecondaryConstraintGroups
    public void setRouteRowSecondaryConstraintGroups (Map<String, Long> routeRowSecondaryConstraintGroups) { this.routeRowSecondaryConstraintGroups = routeRowSecondaryConstraintGroups; } // setRouteRowSecondaryConstraintGroups
    public void addRouteRowSecondaryConstraintGroup (String code, long uid) { if (this.routeRowSecondaryConstraintGroups == null) { this.routeRowSecondaryConstraintGroups = new HashMap<> (); } if (!this.routeRowSecondaryConstraintGroups.containsKey (code)) { this.routeRowSecondaryConstraintGroups.put (code, uid); } } // addRouteRowSecondaryConstraintGroup

    public Map<String, Long> getRouteRowAttributeValues () { return routeRowAttributeValues; } // getRouteRowAttributeValues
    public void setRouteRowAttributeValues (Map<String, Long> routeRowAttributeValues) { this.routeRowAttributeValues = routeRowAttributeValues; } // setRouteRowAttributeValues
    public void addRouteRowAttributeValue (String code, long uid) { if (this.routeRowAttributeValues == null) { this.routeRowAttributeValues = new HashMap<> (); } if (!this.routeRowAttributeValues.containsKey (code)) { this.routeRowAttributeValues.put (code, uid); } } // addRouteRowAttributeValue

    public Map<String, Long> getCustomerOrders () { return customerOrders; } // getCustomerOrders
    public void setCustomerOrders (Map<String, Long> customerOrders) { this.customerOrders = customerOrders; } // setCustomerOrders
    public void addCustomerOrder (String code, long uid) { if (this.customerOrders == null) { this.customerOrders = new HashMap<> (); } if (!this.customerOrders.containsKey (code)) { this.customerOrders.put (code, uid); } } // addCustomerOrder

    public Map<String, Long> getDemands () { return demands; } // getDemands
    public void setDemands (Map<String, Long> demands) { this.demands = demands; } // setDemands
    public void addDemand (String code, long uid) { if (this.demands == null) { this.demands = new HashMap<> (); } if (!this.demands.containsKey (code)) { this.demands.put (code, uid); } } // addDemand

    public Map<String, Long> getSupplyOrders () { return supplyOrders; } // getSupplyOrders
    public void setSupplyOrders (Map<String, Long> supplyOrders) { this.supplyOrders = supplyOrders; } // setSupplyOrders
    public void addSupplyOrder (String code, long uid) { if (this.supplyOrders == null) { this.supplyOrders = new HashMap<> (); } if (!this.supplyOrders.containsKey (code)) { this.supplyOrders.put (code, uid); } } // addSupplyOrder

    public Map<String, Long> getSupplies () { return supplies; } // getSupplies
    public void setSupplies (Map<String, Long> supplies) { this.supplies = supplies; } // setSupplies
    public void addSupply (String code, long uid) { if (this.supplies == null) { this.supplies = new HashMap<> (); } if (!this.supplies.containsKey (code)) { this.supplies.put (code, uid); } } // addSupplie

    public Map<String, Long> getShopOrders () { return shopOrders; } // getShopOrders
    public void setShopOrders (Map<String, Long> shopOrders) { this.shopOrders = shopOrders; } // setShopOrders
    public void addShopOrder (String code, long uid) { if (this.shopOrders == null) { this.shopOrders = new HashMap<> (); } if (!this.shopOrders.containsKey (code)) { this.shopOrders.put (code, uid); } } // addShopOrder

    public Map<String, Long> getCoProducts () { return coProducts; } // getCoProducts
    public void setCoProducts (Map<String, Long> coProducts) { this.coProducts = coProducts; } // setCoProducts
    public void addCoProduct (String code, long uid) { if (this.coProducts == null) { this.coProducts = new HashMap<> (); } if (!this.coProducts.containsKey (code)) { this.coProducts.put (code, uid); } } // addCoProduct

    public Map<String, Long> getAggregatedOperations () { return aggregatedOperations; } // getAggregatedOperations
    public void setAggregatedOperations (Map<String, Long> aggregatedOperations) { this.aggregatedOperations = aggregatedOperations; } // setAggregatedOperations
    public void addAggregatedOperation (String code, long uid) { if (this.aggregatedOperations == null) { this.aggregatedOperations = new HashMap<> (); } if (!this.aggregatedOperations.containsKey (code)) { this.aggregatedOperations.put (code, uid); } } // addAggregatedOperation

    public Map<String, Long> getMultipalletOperations () { return multipalletOperations; } // getMultipalletOperations
    public void setMultipalletOperations (Map<String, Long> multipalletOperations) { this.multipalletOperations = multipalletOperations; } // setMultipalletOperations
    public void addMultipalletOperation (String code, long uid) { if (this.multipalletOperations == null) { this.multipalletOperations = new HashMap<> (); } if (!this.multipalletOperations.containsKey (code)) { this.multipalletOperations.put (code, uid); } } // addMultipalletOperation

    public Map<String, Long> getOperations () { return operations; } // getOperations
    public void setOperations (Map<String, Long> operations) { this.operations = operations; } // setOperations
    public void addOperation (String code, long uid) { if (this.operations == null) { this.operations = new HashMap<> (); } if (!this.operations.containsKey (code)) { this.operations.put (code, uid); } } // addOperation

    public Map<String, Long> getMaterials () { return materials; } // getMaterials
    public void setMaterials (Map<String, Long> materials) { this.materials = materials; } // setMaterials
    public void addMaterial (String code, long uid) { if (this.materials == null) { this.materials = new HashMap<> (); } if (!this.materials.containsKey (code)) { this.materials.put (code, uid); } } // addMaterial

    public Map<String, Long> getOperationLinks () { return operationLinks; } // getOperationLinks
    public void setOperationLinks (Map<String, Long> operationLinks) { this.operationLinks = operationLinks; } // setOperationLinks
    public void addOperationLink (String code, long uid) { if (this.operationLinks == null) { this.operationLinks = new HashMap<> (); } if (!this.operationLinks.containsKey (code)) { this.operationLinks.put (code, uid); } } // add

    public Map<String, Long> getQuotations () { return quotations; } // getQuotations
    public void setQuotations (Map<String, Long> quotations) { this.quotations = quotations; } // setQuotations
    public void addQuotation (String code, long uid) { if (this.quotations == null) { this.quotations = new HashMap<> (); } if (!this.quotations.containsKey (code)) { this.quotations.put (code, uid); } } // add
    
    /*************************************************************************/
    /*** DUMMY ***************************************************************/
    /*************************************************************************/
    
    public AngApsAttVal getDummyAngApsAttVal () { return dummyAngApsAttVal; }
    public void setDummyAngApsAttVal (AngApsAttVal dummy) { dummyAngApsAttVal = dummy; }

    public AngApsCst getDummyAngApsCst () { return dummyAngApsCst; }
    public void setDummyAngApsCst (AngApsCst dummy) { dummyAngApsCst = dummy; }

    public AngApsCstGrp getDummyAngApsCstGrp () { return dummyAngApsCstGrp; }
    public void setDummyAngApsCstGrp (AngApsCstGrp dummy) { dummyAngApsCstGrp = dummy; }

    public AngApsRes getDummyAngApsRes () { return dummyAngApsRes; }
    public void setDummyAngApsRes (AngApsRes dummy) { dummyAngApsRes = dummy; }

    public AngCus getDummyAngCus () { return dummyAngCus; }
    public void setDummyAngCus (AngCus dummy) { dummyAngCus = dummy; }

    public AngGrp getDummyAngGrp () { return dummyAngGrp; }
    public void setDummyAngGrp (AngGrp dummy) { dummyAngGrp = dummy; }

    public AngJob getDummyAngJob () { return dummyAngJob; }
    public void setDummyAngJob (AngJob dummy) { dummyAngJob = dummy; }

    public AngPrdCyc getDummyAngPrdCyc () { return dummyAngPrdCyc; }
    public void setDummyAngPrdCyc (AngPrdCyc dummy) { dummyAngPrdCyc = dummy; }

    public AngPrdCycOpr getDummyAngPrdCycOpr () { return dummyAngPrdCycOpr; }
    public void setDummyAngPrdCycOpr (AngPrdCycOpr dummy) { dummyAngPrdCycOpr = dummy; }

    public AngPrdVrnCat getDummyAngPrdVrnCat () { return dummyAngPrdVrnCat; }
    public void setDummyAngPrdVrnCat (AngPrdVrnCat dummy) { dummyAngPrdVrnCat = dummy; }

    public AngPrdVrn getDummyAngPrdVrn () { return dummyAngPrdVrn; }
    public void setDummyAngPrdVrn (AngPrdVrn dummy) { dummyAngPrdVrn = dummy; }

    public AngItmVrn getDummyAngItmVrn () { return dummyAngItmVrn; }
    public void setDummyAngItmVrn (AngItmVrn dummy) { dummyAngItmVrn = dummy; }

    public AngItmMdl getDummyAngItmMdl () { return dummyAngItmMdl; }
    public void setDummyAngItmMdl (AngItmMdl dummy) { dummyAngItmMdl = dummy; }

    public AngRes getDummyAngRes () { return dummyAngRes; }
    public void setDummyAngRes (AngRes dummy) { dummyAngRes = dummy; }

    public ApsOpr getDummyApsOpr () { return dummyApsOpr; }
    public void setDummyApsOpr (ApsOpr dummy) { dummyApsOpr = dummy; }

    public ApsSceCst getDummyApsSceCst () { return dummyApsSceCst; }
    public void setDummyApsSceCst (ApsSceCst dummy) { dummyApsSceCst = dummy; }

    public ApsSceRes getDummyApsSceRes () { return dummyApsSceRes; }
    public void setDummyApsSceRes (ApsSceRes dummy) { dummyApsSceRes = dummy; }

    public ApsShpOrd getDummyApsShpOrd () { return dummyApsShpOrd; }
    public void setDummyApsShpOrd (ApsShpOrd dummy) { dummyApsShpOrd = dummy; }

} // ApsTmpStore
