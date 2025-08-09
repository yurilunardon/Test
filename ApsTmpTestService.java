package net.synergy2.tester.aps.tmp;

import java.util.List;
import net.synergy2.db.aps.sce.ApsSce;
import net.synergy2.db.base.SAlias;
import net.synergy2.db.sys.bas.AngSup;
import net.synergy2.db.sys.bas.AngSupImpl.AngSupFields;
import net.synergy2.db.sys.grp.AngGrp;
import net.synergy2.db.sys.grp.AngResGrp;
import net.synergy2.db.sys.itm.AngBom;
import net.synergy2.db.sys.itm.AngBomImpl.AngBomFields;
import net.synergy2.db.sys.itm.AngItm;
import net.synergy2.db.sys.itm.AngItmImpl.AngItmFields;
import net.synergy2.db.sys.itm.AngItmMdl;
import net.synergy2.db.sys.itm.AngItmMdlImpl.AngItmMdlFields;
import net.synergy2.db.sys.itm.AngItmMdlVrn;
import net.synergy2.db.sys.itm.AngItmMdlVrnImpl.AngItmMdlVrnFields;
import net.synergy2.db.sys.itm.AngItmVrn;
import net.synergy2.db.sys.itm.AngItmVrnImpl.AngItmVrnFields;
import net.synergy2.db.sys.itm.AngPrdCyc;
import net.synergy2.db.sys.itm.AngPrdCycImpl.AngPrdCycFields;
import net.synergy2.db.sys.itm.AngPrdCycOpr;
import net.synergy2.db.sys.itm.AngPrdCycOprImpl.AngPrdCycOprFields;
import net.synergy2.db.sys.itm.AngPrdCycOprLnk;
import net.synergy2.db.sys.itm.AngPrdCycOprLnkImpl.AngPrdCycOprLnkFields;
import net.synergy2.db.sys.itm.AngPrdCycTypImpl.AngPrdCycTypFields;
import net.synergy2.db.sys.itm.AngPrdVrn;
import net.synergy2.db.sys.itm.AngPrdVrnCat;
import net.synergy2.db.sys.itm.AngPrdVrnCatImpl.AngPrdVrnCatFields;
import net.synergy2.db.sys.itm.AngPrdVrnImpl.AngPrdVrnFields;
import net.synergy2.db.sys.itm.AngUniMea;
import net.synergy2.db.sys.itm.AngUniMeaImpl.AngUniMeaFields;
import net.synergy2.db.sys.res.AngRes;
import net.synergy2.db.sys.res.AngResImpl.AngResFields;
import net.synergy2.db.sys.grp.AngResGrpImpl.AngResGrpFields;
import net.synergy2.db.sys.grp.AngGrpImpl.AngGrpFields;
import net.synergy2.graphquery.SGraphQuery;
import net.synergy2.graphquery.SGraphQuery.Filter;
import net.synergy2.graphquery.SGraphQuery.Join;
import net.synergy2.logic.fct.tmp.FctEqualsInput;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil.ImportRestInput;
import net.synergy2.tester.base.RestTest;

public class ApsTmpTestService {

    public ApsTmpTestService (RestTest rest) {
        this.rest = rest;
    } 

    private final String baseUrl = "/spec/aps/tmp/test";

    private final RestTest rest;

    /************************************************************************************/
    /** IMPORT **************************************************************************/
    /************************************************************************************/

    public void importToAps (ApsSce scenario) {
        rest.post ("/spec/aps/tmp/ApsTmp/import/toAps/" + scenario.getUid (), new ImportRestInput (true, true), Object.class);
    }

    /************************************************************************************/
    /** AngItm **************************************************************************/
    /************************************************************************************/

    // public AngItm get (AngItm item) {
    //     return rest.get (rest.getByCodeUrl (item) + item.getSceCod (), AngItm.class);
    // } // get

    public List<AngItm> getActiveAngApsItm () {
        return rest.graphQueryGetMany (
            AngItm.class,
            SGraphQuery.from (SAlias.AngItm)
                .join (Join.with (SAlias.AngItm_FKI_Sup))
                .join (Join.with (SAlias.AngItm_FKI_UniMea))
                .filter (Filter.and (
                    Filter.eq (AngItmFields.LogDel, false),
                    Filter.eq (AngSupFields.LogDel, false),
                    Filter.eq (AngUniMeaFields.LogDel, false)
                ))
        );
    } // getActiveAngItm

    public AngItm post (AngItm item) {
        rest.post (baseUrl + "/AngItm", item, AngItm.class);
        return item;
    } // post

    public void checkEq (AngItm item1, AngItm item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngItm/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq

    // public AngItm put (AngItm item) {
    //     Params inputParams = itemSaveParams ();
    //     item = rest.put (item.getBaseUrl () + "/all", item, inputParams.get (), AngItm.class);
    //     return item;
    // } // put
    
    
    /************************************************************************************/
    /** AngItmVrn ***********************************************************************/
    /************************************************************************************/

    public List<AngItmVrn> getActiveAngItmVrn () {
        return rest.graphQueryGetMany (
            AngItmVrn.class,
            SGraphQuery.from (SAlias.AngItmVrn)
                .join (Join.with (SAlias.AngItmVrn_FKI_Itm).mustExist (true))
                .join (
                    Join.with (SAlias.AngItmVrn_FKI_Vrn).mustExist (true)
                        .join (Join.with (SAlias.AngPrdVrn_FKI_Cat).mustExist (true))
                )
                .filter (Filter.and (
                    Filter.eq (AngItmVrnFields.LogDel, false),
                    Filter.eq (AngItmFields.LogDel, false),
                    Filter.eq (AngPrdVrnFields.LogDel, false),
                    Filter.eq (AngPrdVrnCatFields.LogDel, false)
                ))
            );
    } // getActiveAngItmVrn

    public AngItmVrn post (AngItmVrn item) {
        rest.post (baseUrl + "/AngItmVrn", item, AngItmVrn.class);
        return item;
    } // post

    public void checkEq (AngItmVrn item1, AngItmVrn item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngItmVrn/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq
    
    
    /************************************************************************************/
    /** AngPrdCyc ***********************************************************************/
    /************************************************************************************/

    public List<AngPrdCyc> getActiveAngPrdCyc () {
        return rest.graphQueryGetMany (
            AngPrdCyc.class,
            SGraphQuery.from (SAlias.AngPrdCyc)
                .join (Join.with(SAlias.AngPrdCyc_FKI_Itm).mustExist (true))
                .join (Join.with(SAlias.AngPrdCyc_FKI_Typ).mustExist (true))
                .filter (Filter.and (
                    Filter.eq (AngPrdCycFields.LogDel, false),
                    Filter.eq (AngItmFields.LogDel, false),
                    Filter.eq (AngPrdCycTypFields.LogDel, false)
                ))
            );
    } // getActiveAngPrdCyc
    
    
    public AngPrdCyc post (AngPrdCyc item) {
        rest.post (baseUrl + "/AngPrdCyc", item, AngPrdCyc.class);
        return item;
    } // post

    public void checkEq (AngPrdCyc item1, AngPrdCyc item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngPrdCyc/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq
    
    
    /************************************************************************************/
    /** AngPrdCycOpr ********************************************************************/
    /************************************************************************************/
    
    public List<AngPrdCycOpr> getActiveAngPrdCycOpr () {
        return rest.graphQueryGetMany (
            AngPrdCycOpr.class, 
            SGraphQuery.from (SAlias.AngPrdCycOpr)
                .join (
                    Join.with (SAlias.AngPrdCycOpr_FKI_PrdCyc).mustExist (true)
                        .join (Join.with (SAlias.AngPrdCyc_FKI_Itm).mustExist (true))
                        .join (Join.with (SAlias.AngPrdCyc_FKI_Typ).mustExist (true))                        
                )              
                .join (
                    Join.with (SAlias.AngPrdCycOpr_FKI_ItmVrn).filter (Filter.eq (AngItmVrnFields.LogDel, false))
                        .join (
                            Join.with (SAlias.AngItmVrn_FKI_Itm).filter (Filter.eq (AngItmFields.LogDel, false))
                        )
                        .join (
                            Join.with (SAlias.AngItmVrn_FKI_Vrn).filter (Filter.eq (AngPrdVrnFields.LogDel, false))
                                .join (Join.with (SAlias.AngPrdVrn_FKI_Cat).filter (Filter.eq (AngPrdVrnCatFields.LogDel, false)))
                        )
                )
                .filter (Filter.and (
                    Filter.eq (AngPrdCycOprFields.LogDel, false),
                    Filter.eq (AngPrdCycFields.LogDel, false),
                    Filter.eq (AngPrdCycTypFields.LogDel, false),
                    Filter.eq (AngItmFields.LogDel, false)      
                ))
        );
    } // getActiveAngPrdCycOpr
    
    public AngPrdCycOpr post (AngPrdCycOpr item) {
        rest.post (baseUrl + "/AngPrdCycOpr", item, AngPrdCycOpr.class);
        return item;
    } // post

    public void checkEq (AngPrdCycOpr item1, AngPrdCycOpr item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngPrdCycOpr/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq
    
    
    /************************************************************************************/
    /** AngPrdCycOprLnk *****************************************************************/
    /************************************************************************************/

    public List<AngPrdCycOprLnk> getActiveAngPrdCycOprLnk() {
        return rest.graphQueryGetMany(
            AngPrdCycOprLnk.class,
            SGraphQuery.from(SAlias.AngPrdCycOprLnk)
                .join(
                    Join.with(SAlias.AngPrdCycOprLnk_FKI_PrePrdCycOpr).mustExist(true)
                        .join(
                            Join.with(SAlias.AngPrdCycOpr_FKI_PrdCyc).mustExist(true)
                                .join(Join.with(SAlias.AngPrdCyc_FKI_Itm).mustExist(true))
                                .join(Join.with(SAlias.AngPrdCyc_FKI_Typ).mustExist(true)) 
                        )
                        .join(
                            Join.with(SAlias.AngPrdCycOpr_FKI_ItmVrn).filter (Filter.eq(AngItmVrnFields.LogDel, false))
                                .join(Join.with(SAlias.AngItmVrn_FKI_Itm).filter (Filter.eq(AngItmFields.LogDel, false)))
                                .join(
                                    Join.with(SAlias.AngItmVrn_FKI_Vrn).filter (Filter.eq(AngPrdVrnFields.LogDel, false))
                                        .join(Join.with(SAlias.AngPrdVrn_FKI_Cat).filter (Filter.eq(AngPrdVrnCatFields.LogDel, false)))
                                )
                        )
                )
                .join(
                    Join.with(SAlias.AngPrdCycOprLnk_FKI_NxtPrdCycOpr).mustExist(true)
                        .join(
                            Join.with(SAlias.AngPrdCycOpr_FKI_PrdCyc).mustExist(true)
                                .join(Join.with(SAlias.AngPrdCyc_FKI_Typ).mustExist(true))
                                .join(Join.with(SAlias.AngPrdCyc_FKI_Itm).mustExist(true))
                        )
                        .join(
                            Join.with(SAlias.AngPrdCycOpr_FKI_ItmVrn).filter (Filter.eq(AngItmVrnFields.LogDel, false))
                                .join(Join.with(SAlias.AngItmVrn_FKI_Itm).filter (Filter.eq(AngItmFields.LogDel, false)))
                                .join(
                                    Join.with(SAlias.AngItmVrn_FKI_Vrn).filter (Filter.eq(AngPrdVrnFields.LogDel, false))
                                        .join(Join.with(SAlias.AngPrdVrn_FKI_Cat).filter (Filter.eq(AngPrdVrnCatFields.LogDel, false)))
                                )
                        )
                )
                .filter(Filter.and(
                    Filter.eq(AngPrdCycOprLnkFields.LogDel, false),
                    Filter.eq(AngPrdCycOprFields.LogDel, false),
                    Filter.eq(AngPrdCycFields.LogDel, false),
                    Filter.eq(AngPrdCycTypFields.LogDel, false),                    
                    Filter.eq(AngItmFields.LogDel, false)
                ))
        );
    } // getActiveAngPrdCycOprLnk

    
            
    public AngPrdCycOprLnk post (AngPrdCycOprLnk item) {
        rest.post (baseUrl + "/AngPrdCycOprLnk", item, AngPrdCycOprLnk.class);
        return item;
    } // post

    public void checkEq (AngPrdCycOprLnk item1, AngPrdCycOprLnk item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngPrdCycOprLnk/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq
    
   
    /************************************************************************************/
    /** AngItmMdl ***********************************************************************/
    /************************************************************************************/

    public List<AngItmMdl> getActiveAngItmMdl () {
        return rest.graphQueryGetMany (
            AngItmMdl.class,
                SGraphQuery.from (SAlias.AngItmMdl)
                .join (Join.with (SAlias.AngItmMdl_FKI_Itm).mustExist (true))
                .filter (Filter.and (
                    Filter.eq (AngItmMdlFields.LogDel, false),
                    Filter.eq (AngItmFields.LogDel, false)
                )));
    } // getActiveAngItmMdl
    
    
    public AngItmMdl post (AngItmMdl item) {
        rest.post (baseUrl + "/AngItmMdl", item, AngItmMdl.class);
        return item;
    } // post

    public void checkEq (AngItmMdl item1, AngItmMdl item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngItmMdl/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq
    
    
    /************************************************************************************/
    /** AngItmMdlVrn ********************************************************************/
    /************************************************************************************/

    public List<AngItmMdlVrn> getActiveAngItmMdlVrn () {
        return rest.graphQueryGetMany (
            AngItmMdlVrn.class,
            SGraphQuery.from (SAlias.AngItmMdlVrn)
            .join (
                Join.with (SAlias.AngItmMdlVrn_FKI_Mdl).mustExist (true)
                    .join (Join.with (SAlias.AngItmMdl_FKI_Itm).mustExist (true))
            )
            .join (
                Join.with (SAlias.AngItmMdlVrn_FKI_ItmVrn).mustExist (true)
                    .join (Join.with (SAlias.AngItmVrn_FKI_Itm).mustExist (true))
                    .join (
                        Join.with (SAlias.AngItmVrn_FKI_Vrn).mustExist (true)
                            .join (Join.with (SAlias.AngPrdVrn_FKI_Cat).mustExist (true))
                    )
            )
            .filter (Filter.and (
                Filter.eq (AngItmMdlVrnFields.LogDel, false),
                Filter.eq (AngItmMdlFields.LogDel, false),
                Filter.eq (AngItmFields.LogDel, false),
                Filter.eq (AngItmVrnFields.LogDel, false),
                Filter.eq (AngPrdVrnFields.LogDel, false),
                Filter.eq (AngPrdVrnCatFields.LogDel, false)
            )));
    } // getActiveAngItmMdlVrn
    
    
    public AngItmMdlVrn post (AngItmMdlVrn item) {
        rest.post (baseUrl + "/AngItmMdlVrn", item, AngItmMdlVrn.class);
        return item;
    } // post

    public void checkEq (AngItmMdlVrn item1, AngItmMdlVrn item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngItmMdlVrn/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq
    
    
    /************************************************************************************/
    /** AngBom **************************************************************************/
    /************************************************************************************/

    public List<AngBom> getActiveAngBom () {
        return rest.graphQueryGetMany (
            AngBom.class,
            SGraphQuery.from (SAlias.AngBom)
            .join (Join.with (SAlias.AngBom_FKI_PrdItm).mustExist (true))
            .join (Join.with (SAlias.AngBom_FKI_Itm).mustExist (true))
            .join (
                Join.with (SAlias.AngBom_FKI_ItmVrn)
                .join (Join.with (SAlias.AngItmVrn_FKI_Itm).mustExist (true))
                .join (
                    Join.with (SAlias.AngItmVrn_FKI_Vrn).mustExist (true)
                        .join (Join.with (SAlias.AngPrdVrn_FKI_Cat).mustExist (true))
                )
            )
            .filter (Filter.and (
                Filter.eq (AngBomFields.LogDel, false),
                Filter.eq (AngItmFields.LogDel, false),
                Filter.eq (AngItmVrnFields.LogDel, false),
                Filter.eq (AngPrdVrnFields.LogDel, false),
                Filter.eq (AngPrdVrnCatFields.LogDel, false)
            )));
    } // getActiveAngBom
    
    
    public AngBom post (AngBom item) {
        rest.post (baseUrl + "/AngBom", item, AngBom.class);
        return item;
    } // post

    public void checkEq (AngBom item1, AngBom item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngBom/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq

    
    /************************************************************************************/
    /** AngUniMea ***********************************************************************/
    /************************************************************************************/

    public List<AngUniMea> getActiveAngApsUniMea () {
        return rest.graphQueryGetMany (
            AngUniMea.class, 
            SGraphQuery.from (SAlias.AngUniMea)
            .filter (Filter.eq (AngUniMeaFields.LogDel, false)));
    } // getActiveAngUniMea

    public AngUniMea post (AngUniMea item) {
        rest.post (baseUrl + "/AngUniMea", item, AngUniMea.class);
        return item;
    } // post

    public void checkEq (AngUniMea item1, AngUniMea item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngUniMea/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq


    /************************************************************************************/
    /** AngPrdVrnCat ********************************************************************/
    /************************************************************************************/
    
    public List<AngPrdVrnCat> getActiveAngPrdVrnCat () {
        return rest.graphQueryGetMany (
            AngPrdVrnCat.class, 
            SGraphQuery.from (SAlias.AngPrdVrnCat)
            .filter (Filter.eq (AngPrdVrnCatFields.LogDel, false)));
    } // getActiveAngPrdVrnCat

    public AngPrdVrnCat post (AngPrdVrnCat item) {
        rest.post (baseUrl + "/AngPrdVrnCat", item, AngPrdVrnCat.class);
        return item;
    } // post

    public void checkEq (AngPrdVrnCat item1, AngPrdVrnCat item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngPrdVrnCat/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq
    
    
    /************************************************************************************/
    /** AngPrdVrn ***********************************************************************/
    /************************************************************************************/
    
    public List<AngPrdVrn> getActiveAngPrdVrn () {
        return rest.graphQueryGetMany (
            AngPrdVrn.class, 
            SGraphQuery.from (SAlias.AngPrdVrn)
            .join (Join.with(SAlias.AngPrdVrn_FKI_Cat).mustExist (true))
            .filter (Filter.and (
                Filter.eq (AngPrdVrnFields.LogDel, false),
                Filter.eq (AngPrdVrnCatFields.LogDel, false)
            )));
    } // getActiveAngPrdVrn

    public AngPrdVrn post (AngPrdVrn item) {
        rest.post (baseUrl + "/AngPrdVrn", item, AngPrdVrn.class);
        return item;
    } // post

    public void checkEq (AngPrdVrn item1, AngPrdVrn item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngPrdVrn/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq

    
    /************************************************************************************/
    /** AngSup **************************************************************************/
    /************************************************************************************/
    
    public List<AngSup> getActiveAngApsSup () {
        return rest.graphQueryGetMany (
            AngSup.class, 
            SGraphQuery.from (SAlias.AngSup)
            .filter (Filter.eq (AngSupFields.LogDel, false)));
    } // getActiveAngSup
    
    public AngSup post (AngSup sup) {
        rest.post (baseUrl + "/AngSup", sup, AngSup.class);
        return sup;
    } // post
    
    public void checkEq (AngSup sup1, AngSup sup2) {
        Boolean areEqual = rest.post (baseUrl + "/AngSup/check", new FctEqualsInput<> (sup1, sup2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq
    
    
    /************************************************************************************/
    /** AngRes **************************************************************************/
    /************************************************************************************/
    
    public List<AngRes> getActiveAngRes () {
        return rest.graphQueryGetMany (
            AngRes.class, 
            SGraphQuery.from (SAlias.AngRes)
            .filter (Filter.eq (AngResFields.LogDel, false))
        );
    } // getActiveAngRes

    public AngRes post (AngRes item) {
        rest.post (baseUrl + "/AngRes", item, AngRes.class);
        return item;
    } // post

    public void checkEq (AngRes item1, AngRes item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngRes/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq
    
    
    /************************************************************************************/
    /** AngResGrp ***********************************************************************/
    /************************************************************************************/
    
    public List<AngResGrp> getActiveAngResGrp () {
        return rest.graphQueryGetMany (
            AngResGrp.class, 
            SGraphQuery.from (SAlias.AngResGrp)
            .join (Join.with(SAlias.AngResGrp_FKI_Grp).mustExist (true))
            .filter (Filter.and (
                Filter.eq (AngResGrpFields.LogDel, false),
                Filter.eq (AngGrpFields.LogDel, false)
            ))
        );
    } // getActiveAngResGrp

    public AngResGrp post (AngResGrp item) {
        rest.post (baseUrl + "/AngResGrp", item, AngResGrp.class);
        return item;
    } // post

    public void checkEq (AngResGrp item1, AngResGrp item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngResGrp/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq
    
    
    /************************************************************************************/
    /** AngGrp **************************************************************************/
    /************************************************************************************/
    
    public List<AngGrp> getActiveAngGrp () {
        return rest.graphQueryGetMany (
            AngGrp.class, 
            SGraphQuery.from (SAlias.AngGrp)
            .filter (Filter.eq (AngGrpFields.LogDel, false))
        );
    } // getActiveAngGrp

    public AngGrp post (AngGrp item) {
        rest.post (baseUrl + "/AngGrp", item, AngGrp.class);
        return item;
    } // post

    public void checkEq (AngGrp item1, AngGrp item2) {
        Boolean areEqual = rest.post (baseUrl + "/AngGrp/check", new FctEqualsInput<> (item1, item2), null, Boolean.class, FctEqualsInput.class);
        rest.checkEq (true, areEqual);
    } // checkEq
    
    
    
    
    
    
    
    
    
}
