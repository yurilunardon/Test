/* KEEP */
package net.synergy2.logic.aps.tmp;

import net.synergy2.base.connections.SReadCon;
import net.synergy2.base.connections.SWriteRemCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.exceptions.SException;
import net.synergy2.base.types.SSingletonHolder;
import net.synergy2.db.aps.opr.ApsItmCop;
import net.synergy2.db.aps.opr.ApsOpr;
import net.synergy2.db.aps.opr.ApsShpOrd;
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
import net.synergy2.logic.aps.tmp.impl.ApsItmCopTmp;
import net.synergy2.logic.aps.tmp.impl.ApsOprTmp;
import net.synergy2.logic.aps.tmp.impl.ApsShpOrdTmp;
import net.synergy2.logic.fct.tmp.FctEqualsInput;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApsTmpTestLogic {

    protected ApsTmpTestLogic () { }
    private static class Singleton {
        private static final SSingletonHolder<ApsTmpTestLogic> INSTANCE = new SSingletonHolder<> (ApsTmpTestLogic.class);
    } // Singleton
    public static ApsTmpTestLogic get () { return Singleton.INSTANCE.get (); }

    private static Logger LOGGER = LoggerFactory.getLogger (ApsTmpTestLogic.class);

    /*******************************************************************************/
    /** AngItm *********************************************************************/
    /*******************************************************************************/

    public AngItm insertAngItm (AngItm entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngApsItmTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngItm

    public AngItm updateAngItm (AngItm entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngApsItmTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngItm

    public boolean checkAngItm (FctEqualsInput<AngItm> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngItm.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngApsItmTmp.get ().getFields (context, rCon));
    } // checkAngItm


    /*******************************************************************************/
    /** AngUniMea ******************************************************************/
    /*******************************************************************************/

    public AngUniMea insertAngUniMea (AngUniMea entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngApsUniMeaTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngUniMea
    
    public AngUniMea updateAngUniMea (AngUniMea entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngApsUniMeaTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngUniMea

    public boolean checkAngUniMea (FctEqualsInput<AngUniMea> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngUniMea.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngApsUniMeaTmp.get ().getFields (context, rCon));
    } // checkAngItm


    /*******************************************************************************/
    /** AngPrdVrnCat ***************************************************************/
    /*******************************************************************************/

    public AngPrdVrnCat insertAngPrdVrnCat (AngPrdVrnCat entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngPrdVrnCatTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngPrdVrnCat

    public AngPrdVrnCat updateAngPrdVrnCat (AngPrdVrnCat entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngPrdVrnCatTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngPrdVrnCat

    public boolean checkAngPrdVrnCat (FctEqualsInput<AngPrdVrnCat> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngPrdVrnCat.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngPrdVrnCatTmp.get ().getFields (context, rCon));
    } // checkAngPrdVrnCat


    /*******************************************************************************/
    /** AngPrdVrn ******************************************************************/
    /*******************************************************************************/

    public AngPrdVrn insertAngPrdVrn (AngPrdVrn entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngPrdVrnTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngPrdVrn
    
    public AngPrdVrn updateAngPrdVrn (AngPrdVrn entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngPrdVrnTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngPrdVrn

    public boolean checkAngPrdVrn (FctEqualsInput<AngPrdVrn> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngPrdVrn.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngPrdVrnTmp.get ().getFields (context, rCon));
    } // checkAngPrdVrn


    /*******************************************************************************/
    /** AngSup *********************************************************************/
    /*******************************************************************************/

    public AngSup insertAngSup (AngSup entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngApsSupTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngSup
    
    public AngSup updateAngSup (AngSup entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngApsSupTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngSup

    public boolean checkAngSup (FctEqualsInput<AngSup> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngSup.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngApsSupTmp.get ().getFields (context, rCon));
    } // checkAngSup


    /*******************************************************************************/
    /** AngItmVrn ******************************************************************/
    /*******************************************************************************/

    public AngItmVrn insertAngItmVrn (AngItmVrn entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngItmVrnTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngItmVrn
    
    public AngItmVrn updateAngItmVrn (AngItmVrn entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngItmVrnTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngItmVrn

    public boolean checkAngItmVrn (FctEqualsInput<AngItmVrn> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngItmVrn.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngItmVrnTmp.get ().getFields (context, rCon));
    } // checkAngItmVrn


    /*******************************************************************************/
    /** AngPrdCyc ******************************************************************/
    /*******************************************************************************/

    public AngPrdCyc insertAngPrdCyc (AngPrdCyc entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngPrdCycTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngPrdCyc
    
    public AngPrdCyc updateAngPrdCyc (AngPrdCyc entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngPrdCycTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngPrdCyc

    public boolean checkAngPrdCyc (FctEqualsInput<AngPrdCyc> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngPrdCyc.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngPrdCycTmp.get ().getFields (context, rCon));
    } // checkAngPrdCyc


    /*******************************************************************************/
    /** AngPrdCycOpr ***************************************************************/
    /*******************************************************************************/

    public AngPrdCycOpr insertAngPrdCycOpr (AngPrdCycOpr entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngPrdCycOprTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngPrdCycOpr
    
    public AngPrdCycOpr updateAngPrdCycOpr (AngPrdCycOpr entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngPrdCycOprTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngPrdCycOpr

    public boolean checkAngPrdCycOpr (FctEqualsInput<AngPrdCycOpr> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngPrdCycOpr.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngPrdCycOprTmp.get ().getFields (context, rCon));
    } // checkAngPrdCycOpr


    /*******************************************************************************/
    /** AngPrdCycOprLnk ************************************************************/
    /*******************************************************************************/

    public AngPrdCycOprLnk insertAngPrdCycOprLnk (AngPrdCycOprLnk entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngPrdCycOprLnkTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngPrdCycOprLnk
    
    public AngPrdCycOprLnk updateAngPrdCycOprLnk (AngPrdCycOprLnk entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngPrdCycOprLnkTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngPrdCycOprLnk

    public boolean checkAngPrdCycOprLnk (FctEqualsInput<AngPrdCycOprLnk> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngPrdCycOprLnk.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngPrdCycOprLnkTmp.get ().getFields (context, rCon));
    } // checkAngPrdCycOprLnk


    /*******************************************************************************/
    /** AngItmMdl ******************************************************************/
    /*******************************************************************************/

    public AngItmMdl insertAngItmMdl (AngItmMdl entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngItmMdlTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngItmMdl
    
    public AngItmMdl updateAngItmMdl (AngItmMdl entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngItmMdlTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngItmMdl

    public boolean checkAngItmMdl (FctEqualsInput<AngItmMdl> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngItmMdl.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngItmMdlTmp.get ().getFields (context, rCon));
    } // checkAngItmMdl


    /*******************************************************************************/
    /** AngItmMdlVrn ***************************************************************/
    /*******************************************************************************/

    public AngItmMdlVrn insertAngItmMdlVrn (AngItmMdlVrn entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngItmMdlVrnTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngItmMdlVrn
    
    public AngItmMdlVrn updateAngItmMdlVrn (AngItmMdlVrn entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngItmMdlVrnTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngItmMdlVrn

    public boolean checkAngItmMdlVrn (FctEqualsInput<AngItmMdlVrn> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngItmMdlVrn.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngItmMdlVrnTmp.get ().getFields (context, rCon));
    } // checkAngItmMdlVrn


    /*******************************************************************************/
    /** AngBom *********************************************************************/
    /*******************************************************************************/

    public AngBom insertAngBom (AngBom entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngBomTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngBom
    
    public AngBom updateAngBom (AngBom entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngBomTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngBom

    public boolean checkAngBom (FctEqualsInput<AngBom> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngBom.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngBomTmp.get ().getFields (context, rCon));
    } // checkAngBom


    /*******************************************************************************/
    /** AngRes *********************************************************************/
    /*******************************************************************************/

    public AngRes insertAngRes (AngRes entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngResTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngRes
    
    public AngRes updateAngRes (AngRes entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngResTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngRes

    public boolean checkAngRes (FctEqualsInput<AngRes> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngRes.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngResTmp.get ().getFields (context, rCon));
    } // checkAngRes


    /*******************************************************************************/
    /** AngResGrp ******************************************************************/
    /*******************************************************************************/

    public AngResGrp insertAngResGrp (AngResGrp entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngResGrpTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngResGrp
    
    public AngResGrp updateAngResGrp (AngResGrp entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngResGrpTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngResGrp

    public boolean checkAngResGrp (FctEqualsInput<AngResGrp> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngResGrp.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngResGrpTmp.get ().getFields (context, rCon));
    } // checkAngResGrp


    /*******************************************************************************/
    /** AngGrp *********************************************************************/
    /*******************************************************************************/

    public AngGrp insertAngResGrp (AngGrp entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return AngGrpTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertAngResGrp
    
    public AngGrp updateAngGrp (AngGrp entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return AngGrpTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateAngGrp

    public boolean checkAngGrp (FctEqualsInput<AngGrp> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (AngGrp.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, AngGrpTmp.get ().getFields (context, rCon));
    } // checkAngResGrp
    
    
    /*******************************************************************************/
    /** ApsShpOrd ******************************************************************/
    /*******************************************************************************/

    public ApsShpOrd insertApsShpOrd (ApsShpOrd entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return ApsShpOrdTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertApsShpOrd

    public ApsShpOrd updateApsShpOrd (ApsShpOrd entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return ApsShpOrdTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateApsShpOrd

    public boolean checkApsShpOrd (FctEqualsInput<ApsShpOrd> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (ApsShpOrd.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, ApsShpOrdTmp.get ().getFields (context, rCon));
    } // checkApsShpOrd
    
    
    /*******************************************************************************/
    /** ApsItmCop ******************************************************************/
    /*******************************************************************************/

    public ApsItmCop insertApsItmCop (ApsItmCop entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return ApsItmCopTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertApsItmCop

    public ApsItmCop updateApsItmCop (ApsItmCop entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return ApsItmCopTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateApsItmCop

    public boolean checkApsItmCop (FctEqualsInput<ApsItmCop> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (ApsItmCop.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, ApsItmCopTmp.get ().getFields (context, rCon));
    } // checkApsItmCop
    
    
    /*******************************************************************************/
    /** ApsOpr *********************************************************************/
    /*******************************************************************************/

    public ApsOpr insertApsOpr (ApsOpr entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logInsert (entity);
        return ApsOprTmp.get ().insertToTemp (entity, context, rCon, wrCon);
    } // insertApsOpr

    public ApsOpr updateApsOpr (ApsOpr entity, ExecutionContext context, SReadCon rCon, SWriteRemCon wrCon) throws SException {
        logUpdate (entity);
        return ApsOprTmp.get ().updateToTemp (entity, context, rCon, wrCon);
    } // updateApsOpr

    public boolean checkApsOpr (FctEqualsInput<ApsOpr> input, ExecutionContext context, SReadCon rCon) throws SException {
        logCheck (ApsOpr.class);
        return FctTmpUtil.get ().areEqualByCode (input.o1, input.o2, ApsOprTmp.get ().getFields (context, rCon));
    } // checkApsOpr

    
    /*******************************************************************************/
    /** Other **********************************************************************/
    /*******************************************************************************/

    private <M extends SModel> void logInsert (M model) {
        LOGGER.info ("Insert %s %s".formatted (model.getClass ().getSimpleName (), model));
    } // logInsert
    private <M extends SModel> void logUpdate (M model) {
        LOGGER.info ("Update %s %s".formatted (model.getClass ().getSimpleName (), model));
    } // logUpdate
    private <M extends SModel> void logCheck (Class<M> clasz) { LOGGER.info ("Check %s".formatted (clasz.getSimpleName ())); } // logInsert

} // ApsTmpTestLogic
