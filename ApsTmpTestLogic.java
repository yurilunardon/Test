/* KEEP */
package net.synergy2.logic.aps.tmp;

import net.synergy2.base.connections.SReadCon;
import net.synergy2.base.connections.SWriteRemCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.exceptions.SException;
import net.synergy2.base.types.SSingletonHolder;
import net.synergy2.db.base.SModel;
import net.synergy2.db.sys.bas.AngSup;
import net.synergy2.db.sys.grp.AngGrp;
import net.synergy2.db.sys.grp.AngResGrp;
import net.synergy2.db.sys.itm.AngBom;
import net.synergy2.db.sys.itm.AngItm;
import net.synergy2.db.sys.itm.AngItmMdl;
import net.synergy2.db.sys.itm.AngItmMdlVrn;
import net.synergy2.db.sys.itm.AngItmVrn;
import net.synergy2.db.sys.itm.AngPrdCyc;
import net.synergy2.db.sys.itm.AngPrdCycOpr;
import net.synergy2.db.sys.itm.AngPrdCycOprLnk;
import net.synergy2.db.sys.itm.AngPrdVrn;
import net.synergy2.db.sys.itm.AngPrdVrnCat;
import net.synergy2.db.sys.itm.AngUniMea;
import net.synergy2.db.sys.res.AngRes;
import net.synergy2.logic.aps.tmp.impl.AngApsItmTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsSupTmp;
import net.synergy2.logic.aps.tmp.impl.AngApsUniMeaTmp;
import net.synergy2.logic.aps.tmp.impl.AngBomTmp;
import net.synergy2.logic.aps.tmp.impl.AngGrpTmp;
import net.synergy2.logic.aps.tmp.impl.AngItmMdlTmp;
import net.synergy2.logic.aps.tmp.impl.AngItmMdlVrnTmp;
import net.synergy2.logic.aps.tmp.impl.AngItmVrnTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdCycOprLnkTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdCycOprTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdCycTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdVrnCatTmp;
import net.synergy2.logic.aps.tmp.impl.AngPrdVrnTmp;
import net.synergy2.logic.aps.tmp.impl.AngResGrpTmp;
import net.synergy2.logic.aps.tmp.impl.AngResTmp;
import net.synergy2.logic.fct.tmp.FctEqualsInput;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApsTmpTestLogic {

    protected ApsTmpTestLogic () {}
    private static class Singleton {
        private static final SSingletonHolder<ApsTmpTestLogic> INSTANCE = new SSingletonHolder<> (ApsTmpTestLogic.class);
    } // Singleton
    public static ApsTmpTestLogic get () { return Singleton.INSTANCE.get (); }

    private static Logger LOGGER = LoggerFactory.getLogger (ApsTmpTestLogic.class);

    /*******************************************************************************/
    /** AngItm *********************************************************************/
    /*******************************************************************************/
    
    public void insertAngItm (AngItm item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngApsItmTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngItm

    public boolean checkAngItm (FctEqualsInput<AngItm> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngItm.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngApsItmTmp.get ().getFields (context, rCon));
    } // checkAngItm
    
    
    /*******************************************************************************/
    /** AngUniMea ******************************************************************/
    /*******************************************************************************/
    
    public void insertAngUniMea (AngUniMea item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngApsUniMeaTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngUniMea

    public boolean checkAngUniMea (FctEqualsInput<AngUniMea> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngUniMea.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngApsUniMeaTmp.get ().getFields (context, rCon));
    } // checkAngItm

    
    /*******************************************************************************/
    /** AngPrdVrnCat ***************************************************************/
    /*******************************************************************************/

    public void insertAngPrdVrnCat (AngPrdVrnCat item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngPrdVrnCatTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngPrdVrnCat

    public boolean checkAngPrdVrnCat (FctEqualsInput<AngPrdVrnCat> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngPrdVrnCat.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngPrdVrnCatTmp.get ().getFields (context, rCon));
    } // checkAngPrdVrnCat


    /*******************************************************************************/
    /** AngPrdVrn ******************************************************************/
    /*******************************************************************************/
    
    public void insertAngPrdVrn (AngPrdVrn item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngPrdVrnTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngPrdVrn

    public boolean checkAngPrdVrn (FctEqualsInput<AngPrdVrn> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngPrdVrn.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngPrdVrnTmp.get ().getFields (context, rCon));
    } // checkAngPrdVrn
    
    
    /*******************************************************************************/
    /** AngSup *********************************************************************/
    /*******************************************************************************/

    public void insertAngSup (AngSup item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngApsSupTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngSup

    public boolean checkAngSup (FctEqualsInput<AngSup> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngSup.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngApsSupTmp.get ().getFields (context, rCon));
    } // checkAngSup
    
    
    /*******************************************************************************/
    /** AngItmVrn ******************************************************************/
    /*******************************************************************************/

    public void insertAngItmVrn (AngItmVrn item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngItmVrnTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngItmVrn

    public boolean checkAngItmVrn (FctEqualsInput<AngItmVrn> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngItmVrn.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngItmVrnTmp.get ().getFields (context, rCon));
    } // checkAngItmVrn
    
    
    /*******************************************************************************/
    /** AngPrdCyc ******************************************************************/
    /*******************************************************************************/

    public void insertAngPrdCyc (AngPrdCyc item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngPrdCycTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngPrdCyc
    
    public boolean checkAngPrdCyc (FctEqualsInput<AngPrdCyc> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngPrdCyc.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngPrdCycTmp.get ().getFields (context, rCon));
    } // checkAngPrdCyc
    
    
    /*******************************************************************************/
    /** AngPrdCycOpr ***************************************************************/
    /*******************************************************************************/

    public void insertAngPrdCycOpr (AngPrdCycOpr item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngPrdCycOprTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngPrdCycOpr
    
    public boolean checkAngPrdCycOpr (FctEqualsInput<AngPrdCycOpr> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngPrdCycOpr.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngPrdCycOprTmp.get ().getFields (context, rCon));
    } // checkAngPrdCycOpr
    
    
    /*******************************************************************************/
    /** AngPrdCycOprLnk ************************************************************/
    /*******************************************************************************/

    public void insertAngPrdCycOprLnk (AngPrdCycOprLnk item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngPrdCycOprLnkTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngPrdCycOprLnk
    
    public boolean checkAngPrdCycOprLnk (FctEqualsInput<AngPrdCycOprLnk> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngPrdCycOprLnk.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngPrdCycOprLnkTmp.get ().getFields (context, rCon));
    } // checkAngPrdCycOprLnk
    
    
    /*******************************************************************************/
    /** AngItmMdl ******************************************************************/
    /*******************************************************************************/

    public void insertAngItmMdl (AngItmMdl item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngItmMdlTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngItmMdl
    
    public boolean checkAngItmMdl (FctEqualsInput<AngItmMdl> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngItmMdl.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngItmMdlTmp.get ().getFields (context, rCon));
    } // checkAngItmMdl
    
    
    /*******************************************************************************/
    /** AngItmMdlVrn ***************************************************************/
    /*******************************************************************************/

    public void insertAngItmMdlVrn(AngItmMdlVrn item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngItmMdlVrnTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngItmMdlVrn
    
    public boolean checkAngItmMdlVrn (FctEqualsInput<AngItmMdlVrn> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngItmMdlVrn.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngItmMdlVrnTmp.get ().getFields (context, rCon));
    } // checkAngItmMdlVrn
    
    
    /*******************************************************************************/
    /** AngBom *********************************************************************/
    /*******************************************************************************/

    public void insertAngBom(AngBom item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngBomTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngBom
    
    public boolean checkAngBom (FctEqualsInput<AngBom> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngBom.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngBomTmp.get ().getFields (context, rCon));
    } // checkAngBom
    
    
    /*******************************************************************************/
    /** AngRes *********************************************************************/
    /*******************************************************************************/

    public void insertAngRes (AngRes item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngResTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngRes
    
    public boolean checkAngRes (FctEqualsInput<AngRes> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngRes.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngResTmp.get ().getFields (context, rCon));
    } // checkAngRes
    
    
    /*******************************************************************************/
    /** AngResGrp ******************************************************************/
    /*******************************************************************************/

    public void insertAngResGrp (AngResGrp item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngResGrpTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngResGrp
    
    public boolean checkAngResGrp (FctEqualsInput<AngResGrp> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngResGrp.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngResGrpTmp.get ().getFields (context, rCon));
    } // checkAngResGrp
    
    
    /*******************************************************************************/
    /** AngGrp *********************************************************************/
    /*******************************************************************************/

    public void insertAngResGrp (AngGrp item, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (item);
        AngGrpTmp.get ().insertToTemp (item, context, rCon, wrCon);
    } // insertAngResGrp
    
    public boolean checkAngGrp (FctEqualsInput<AngGrp> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngGrp.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngGrpTmp.get ().getFields (context, rCon));
    } // checkAngResGrp
    
    
    /*******************************************************************************/
    /** Other **********************************************************************/
    /*******************************************************************************/    
    
    private <M extends SModel> void logInsert (M model) {
        LOGGER.info ("Insert %s %s".formatted (model.getClass ().getSimpleName (), model));
    } // logInsert
    private <M extends SModel> void logCheck (Class<M> clasz) { LOGGER.info ("Check %s".formatted (clasz.getSimpleName ())); } // logInsert

} // ApsTmpTestLogic
