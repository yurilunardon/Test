package net.synergy2.rest.aps.tmp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import net.synergy2.base.connections.ConnectionsUtil;
import net.synergy2.base.connections.SConnectionFactory;
import net.synergy2.base.connections.SReadCon;
import net.synergy2.base.connections.SWriteRemCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.exceptions.SException;
import net.synergy2.base.rest.ApiInput;
import net.synergy2.base.rest.RestOutput;
import net.synergy2.base.rest.SynergyRestContext;
import net.synergy2.db.base.SModel;
import net.synergy2.db.sys.bas.AngSup;
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
import net.synergy2.logic.aps.tmp.ApsTmpTestLogic;
import net.synergy2.logic.fct.tmp.FctEqualsInput;
import net.synergy2.rest.util.ErrorResponse;

@Path("/spec/aps/tmp/test")
public final class ApsTmpTestSpecService {
    @Context private ServletContext servletContext;
    @Context private HttpServletRequest request;
    
    private final static String TEMP_DS = "jdbc/fct-tmp-ds";
     
    /*******************************************************************************/
    /** AngItm *********************************************************************/
    /*******************************************************************************/

    @Path ("/AngItm")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response insertAngItm (@Context UriInfo uriInfo, ApiInput<AngItm> input, @SynergyRestContext() ExecutionContext context) {
        return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngItm (input.entity, context, rCon, wrCon), context);
    } // insertAngItm

    @Path ("/AngItm")
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response updateAngItm (@Context UriInfo uriInfo, ApiInput<AngItm> input, @SynergyRestContext() ExecutionContext context) {
        return this.update ((rCon, wrCon) -> ApsTmpTestLogic.get ().updateAngItm (input.entity, context, rCon, wrCon), context);
    } // updateAngItm

    @Path ("/AngItm/check")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response checkAngItm (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngItm>> input, @SynergyRestContext() ExecutionContext context) {
        return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngItm (input.entity, context, rCon), context);
    } // checkAngItm
    
    
    /*******************************************************************************/
    /** AngItmVrn ******************************************************************/
    /*******************************************************************************/
    
    @Path ("/AngItmVrn")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response insertAngItmVrn (@Context UriInfo uriInfo, ApiInput<AngItmVrn> input, @SynergyRestContext() ExecutionContext context) {
        return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngItmVrn (input.entity, context, rCon, wrCon), context);
    } // insertAngItmVrn
    
    @Path ("/AngItmVrn")
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response updateAngItmVrn (@Context UriInfo uriInfo, ApiInput<AngItmVrn> input, @SynergyRestContext() ExecutionContext context) {
        return this.update ((rCon, wrCon) -> ApsTmpTestLogic.get ().updateAngItmVrn (input.entity, context, rCon, wrCon), context);
    } // updateAngItmVrn

    @Path ("/AngItmVrn/check")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response checkAngItmVrn (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngItmVrn>> input, @SynergyRestContext() ExecutionContext context) {
        return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngItmVrn (input.entity, context, rCon), context);
    } // checkAngItmVrn
    
    
    /*******************************************************************************/
    /** AngPrdCyc ******************************************************************/
    /*******************************************************************************/
    
    @Path ("/AngPrdCyc")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response insertAngPrdCyc (@Context UriInfo uriInfo, ApiInput<AngPrdCyc> input, @SynergyRestContext() ExecutionContext context) {
        return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngPrdCyc (input.entity, context, rCon, wrCon), context);
    } // insertAngPrdCyc
    
    @Path ("/AngPrdCyc")
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response updateAngPrdCyc (@Context UriInfo uriInfo, ApiInput<AngPrdCyc> input, @SynergyRestContext() ExecutionContext context) {
        return this.update ((rCon, wrCon) -> ApsTmpTestLogic.get ().updateAngPrdCyc (input.entity, context, rCon, wrCon), context);
    } // updateAngPrdCyc

    @Path ("/AngPrdCyc/check")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response checkAngPrdCyc (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngPrdCyc>> input, @SynergyRestContext() ExecutionContext context) {
        return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngPrdCyc (input.entity, context, rCon), context);
    } // checkAngPrdCyc
    
    
    /*******************************************************************************/
    /** AngPrdCycOpr ***************************************************************/
    /*******************************************************************************/
    
    @Path ("/AngPrdCycOpr")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response insertAngPrdCycOpr (@Context UriInfo uriInfo, ApiInput<AngPrdCycOpr> input, @SynergyRestContext() ExecutionContext context) {
        return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngPrdCycOpr (input.entity, context, rCon, wrCon), context);
    } // insertAngPrdCycOpr
    
    @Path ("/AngPrdCycOpr")
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response updateAngPrdCycOpr (@Context UriInfo uriInfo, ApiInput<AngPrdCycOpr> input, @SynergyRestContext() ExecutionContext context) {
        return this.update ((rCon, wrCon) -> ApsTmpTestLogic.get ().updateAngPrdCycOpr (input.entity, context, rCon, wrCon), context);
    } // updateAngPrdCycOpr

    @Path ("/AngPrdCycOpr/check")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response checkAngPrdCycOpr (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngPrdCycOpr>> input, @SynergyRestContext() ExecutionContext context) {
        return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngPrdCycOpr (input.entity, context, rCon), context);
    } // checkAngPrdCycOpr
    

    /*******************************************************************************/
    /** AngPrdCycOprLnk ************************************************************/
    /*******************************************************************************/
    
    @Path ("/AngPrdCycOprLnk")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response insertAngPrdCycOprLnk (@Context UriInfo uriInfo, ApiInput<AngPrdCycOprLnk> input, @SynergyRestContext() ExecutionContext context) {
        return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngPrdCycOprLnk (input.entity, context, rCon, wrCon), context);
    } // insertAngPrdCycOprLnk
    
    @Path ("/AngPrdCycOprLnk")
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response updateAngPrdCycOprLnk (@Context UriInfo uriInfo, ApiInput<AngPrdCycOprLnk> input, @SynergyRestContext() ExecutionContext context) {
        return this.update ((rCon, wrCon) -> ApsTmpTestLogic.get ().updateAngPrdCycOprLnk (input.entity, context, rCon, wrCon), context);
    } // updateAngPrdCycOprLnk

    @Path ("/AngPrdCycOprLnk/check")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response checkAngPrdCycOprLnk (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngPrdCycOprLnk>> input, @SynergyRestContext() ExecutionContext context) {
        return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngPrdCycOprLnk (input.entity, context, rCon), context);
    } // checkAngPrdCycOprLnk
    
    
    /*******************************************************************************/
    /** AngItmMdl ******************************************************************/
    /*******************************************************************************/
    
    @Path ("/AngItmMdl")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response insertAngItmMdl (@Context UriInfo uriInfo, ApiInput<AngItmMdl> input, @SynergyRestContext() ExecutionContext context) {
        return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngItmMdl (input.entity, context, rCon, wrCon), context);
    } // insertAngItmMdl
    
    @Path ("/AngItmMdl")
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response updateAngItmMdl (@Context UriInfo uriInfo, ApiInput<AngItmMdl> input, @SynergyRestContext() ExecutionContext context) {
        return this.update ((rCon, wrCon) -> ApsTmpTestLogic.get ().updateAngItmMdl (input.entity, context, rCon, wrCon), context);
    } // updateAngItmMdl

    @Path ("/AngItmMdl/check")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response checkAngItmMdl (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngItmMdl>> input, @SynergyRestContext() ExecutionContext context) {
        return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngItmMdl (input.entity, context, rCon), context);
    } // checkAngItmMdl
    
    
    /*******************************************************************************/
    /** AngItmMdlVrn ***************************************************************/
    /*******************************************************************************/
    
    @Path ("/AngItmMdlVrn")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response insertAngItmMdlVrn (@Context UriInfo uriInfo, ApiInput<AngItmMdlVrn> input, @SynergyRestContext() ExecutionContext context) {
        return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngItmMdlVrn (input.entity, context, rCon, wrCon), context);
    } // insertAngItmMdlVrn
    
    @Path ("/AngItmMdlVrn")
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response updateAngItmMdlVrn (@Context UriInfo uriInfo, ApiInput<AngItmMdlVrn> input, @SynergyRestContext() ExecutionContext context) {
        return this.update ((rCon, wrCon) -> ApsTmpTestLogic.get ().updateAngItmMdlVrn (input.entity, context, rCon, wrCon), context);
    } // updateAngItmMdlVrn

    @Path ("/AngItmMdlVrn/check")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response checkAngItmMdlVrn (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngItmMdlVrn>> input, @SynergyRestContext() ExecutionContext context) {
        return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngItmMdlVrn (input.entity, context, rCon), context);
    } // checkAngItmMdlVrn
    
    
    /*******************************************************************************/
    /** AngBom *********************************************************************/
    /*******************************************************************************/
    
    @Path ("/AngBom")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response insertAngBom (@Context UriInfo uriInfo, ApiInput<AngBom> input, @SynergyRestContext() ExecutionContext context) {
        return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngBom (input.entity, context, rCon, wrCon), context);
    } // insertAngBom
    
    @Path ("/AngBom")
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response updateAngBom (@Context UriInfo uriInfo, ApiInput<AngBom> input, @SynergyRestContext() ExecutionContext context) {
        return this.update ((rCon, wrCon) -> ApsTmpTestLogic.get ().updateAngBom (input.entity, context, rCon, wrCon), context);
    } // updateAngBom

    @Path ("/AngBom/check")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response checkAngBom (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngBom>> input, @SynergyRestContext() ExecutionContext context) {
        return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngBom (input.entity, context, rCon), context);
    } // checkAngBom
    
    
    /*******************************************************************************/
    /** AngUniMea ******************************************************************/
    /*******************************************************************************/
    
    @Path ("/AngUniMea")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response insertAngUniMea (@Context UriInfo uriInfo, ApiInput<AngUniMea> input, @SynergyRestContext() ExecutionContext context) {
        return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngUniMea (input.entity, context, rCon, wrCon), context);
    } // insertAngUniMea
    
    @Path ("/AngUniMea")
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response updateAngUniMea (@Context UriInfo uriInfo, ApiInput<AngUniMea> input, @SynergyRestContext() ExecutionContext context) {
        return this.update ((rCon, wrCon) -> ApsTmpTestLogic.get ().updateAngUniMea (input.entity, context, rCon, wrCon), context);
    } // updateAngUniMea

    @Path ("/AngUniMea/check")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response checkAngUniMea (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngUniMea>> input, @SynergyRestContext() ExecutionContext context) {
        return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngUniMea (input.entity, context, rCon), context);
    } // checkAngUniMea

    
    /*******************************************************************************/
    /** AngPrdVrnCat ***************************************************************/
    /*******************************************************************************/
    
    @Path ("/AngPrdVrnCat")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response insertAngPrdVrnCat (@Context UriInfo uriInfo, ApiInput<AngPrdVrnCat> input, @SynergyRestContext() ExecutionContext context) {
        return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngPrdVrnCat (input.entity, context, rCon, wrCon), context);
    } // insertAngPrdVrnCat

    @Path ("/AngPrdVrnCat")
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response updateAngPrdVrnCat (@Context UriInfo uriInfo, ApiInput<AngPrdVrnCat> input, @SynergyRestContext() ExecutionContext context) {
        return this.update ((rCon, wrCon) -> ApsTmpTestLogic.get ().updateAngPrdVrnCat (input.entity, context, rCon, wrCon), context);
    } // updateAngPrdVrnCat

    @Path ("/AngPrdVrnCat/check")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response checkAngPrdVrnCat (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngPrdVrnCat>> input, @SynergyRestContext() ExecutionContext context) {
        return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngPrdVrnCat (input.entity, context, rCon), context);
    } // checkAngPrdVrnCat
    
    
    /*******************************************************************************/
    /** AngPrdVrn ******************************************************************/
    /*******************************************************************************/

    @Path ("/AngPrdVrn")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response insertAngPrdVrn (@Context UriInfo uriInfo, ApiInput<AngPrdVrn> input, @SynergyRestContext() ExecutionContext context) {
        return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngPrdVrn (input.entity, context, rCon, wrCon), context);
    } // insertAngPrdVrn
    
    @Path ("/AngPrdVrn")
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response updateAngPrdVrn (@Context UriInfo uriInfo, ApiInput<AngPrdVrn> input, @SynergyRestContext() ExecutionContext context) {
        return this.update ((rCon, wrCon) -> ApsTmpTestLogic.get ().updateAngPrdVrn (input.entity, context, rCon, wrCon), context);
    } // updateAngPrdVrn

    @Path ("/AngPrdVrn/check")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response checkAngPrdVrn (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngPrdVrn>> input, @SynergyRestContext() ExecutionContext context) {
        return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngPrdVrn (input.entity, context, rCon), context);
    } // checkAngPrdVrn
    
    /*******************************************************************************/
    /** AngSup *********************************************************************/
    /*******************************************************************************/

    @Path ("/AngSup")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response insertSup (@Context UriInfo uriInfo, ApiInput<AngSup> input, @SynergyRestContext() ExecutionContext context) {
        return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngSup (input.entity, context, rCon, wrCon), context);
    } // insertAngSup
    
    @Path ("/AngSup")
    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response updateAngSup (@Context UriInfo uriInfo, ApiInput<AngSup> input, @SynergyRestContext() ExecutionContext context) {
        return this.update ((rCon, wrCon) -> ApsTmpTestLogic.get ().updateAngSup (input.entity, context, rCon, wrCon), context);
    } // updateAngSup

    @Path ("/AngSup/check")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response checkAngSup (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngSup>> input, @SynergyRestContext() ExecutionContext context) {
        return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngSup (input.entity, context, rCon), context);
    } // checkAngSup
    
    
    // /*******************************************************************************/
    // /** AngRes *********************************************************************/
    // /*******************************************************************************/
    //
    // @Path ("/AngRes")
    // @POST
    // @Consumes (MediaType.APPLICATION_JSON)
    // @Produces (MediaType.APPLICATION_JSON)
    // public Response insertAngRes (@Context UriInfo uriInfo, ApiInput<AngRes> input, @SynergyRestContext() ExecutionContext context) {
    //     return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngRes (input.entity, context, rCon, wrCon), context);
    // } // insertAngRes
    //
    // @Path ("/AngRes/check")
    // @POST
    // @Consumes (MediaType.APPLICATION_JSON)
    // @Produces (MediaType.APPLICATION_JSON)
    // public Response checkAngRes (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngRes>> input, @SynergyRestContext() ExecutionContext context) {
    //     return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngRes (input.entity, context, rCon), context);
    // } // checkAngRes
    //
    //
    // /*******************************************************************************/
    // /** AngResGrp ******************************************************************/
    // /*******************************************************************************/
    //
    // @Path ("/AngResGrp")
    // @POST
    // @Consumes (MediaType.APPLICATION_JSON)
    // @Produces (MediaType.APPLICATION_JSON)
    // public Response insertAngResGrp (@Context UriInfo uriInfo, ApiInput<AngResGrp> input, @SynergyRestContext() ExecutionContext context) {
    //     return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngResGrp (input.entity, context, rCon, wrCon), context);
    // } // insertAngResGrp
    //
    // @Path ("/AngResGrp/check")
    // @POST
    // @Consumes (MediaType.APPLICATION_JSON)
    // @Produces (MediaType.APPLICATION_JSON)
    // public Response checkAngResGrp (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngResGrp>> input, @SynergyRestContext() ExecutionContext context) {
    //     return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngResGrp (input.entity, context, rCon), context);
    // } // checkAngRes
    //
    //
    // /*******************************************************************************/
    // /** AngGrp *********************************************************************/
    // /*******************************************************************************/
    //
    // @Path ("/AngGrp")
    // @POST
    // @Consumes (MediaType.APPLICATION_JSON)
    // @Produces (MediaType.APPLICATION_JSON)
    // public Response insertAngGrp (@Context UriInfo uriInfo, ApiInput<AngGrp> input, @SynergyRestContext() ExecutionContext context) {
    //     return this.insert ((rCon, wrCon) -> ApsTmpTestLogic.get ().insertAngGrp (input.entity, context, rCon, wrCon), context);
    // } // insertAngGrp
    //
    // @Path ("/AngGrp/check")
    // @POST
    // @Consumes (MediaType.APPLICATION_JSON)
    // @Produces (MediaType.APPLICATION_JSON)
    // public Response checkAngGrp (@Context UriInfo uriInfo, ApiInput<FctEqualsInput<AngGrp>> input, @SynergyRestContext() ExecutionContext context) {
    //     return this.check ((rCon) -> ApsTmpTestLogic.get ().checkAngGrp (input.entity, context, rCon), context);
    // } // checkAngGrp
    
    
    /*******************************************************************************/
    /** UTILITY ********************************************************************/
    /*******************************************************************************/

    public <M extends SModel> Response insert (ApsTmpTestInsertGetter<M> insertGetter, ExecutionContext context) {
        SReadCon rCon = null;
        SWriteRemCon wrCon = null;
        try {
            rCon = SConnectionFactory.get ().getReadConnection ();
            wrCon = SConnectionFactory.get ().getRemoteWriteConnection (ConnectionsUtil.getDataSource (TEMP_DS));
            M entity = insertGetter.insert (rCon, wrCon);
            wrCon.commit ();
            return Response.ok ().entity (new RestOutput (entity, context)).build ();
        } catch (Throwable e) {
            ConnectionsUtil.safeRollback (wrCon);
            return ErrorResponse.get ().manageError (e, context, null);
        } finally {
            ConnectionsUtil.safeClose (rCon);
            ConnectionsUtil.safeClose (wrCon);
        } // try - catch - finally
    } // insert

    public <M extends SModel> Response update (ApsTmpTestUpdateGetter<M> updateGetter, ExecutionContext context) {
        SReadCon rCon = null;
        SWriteRemCon wrCon = null;
        try {
            rCon = SConnectionFactory.get ().getReadConnection ();
            wrCon = SConnectionFactory.get ().getRemoteWriteConnection (ConnectionsUtil.getDataSource (TEMP_DS));
            M entity = updateGetter.update (rCon, wrCon);
            wrCon.commit ();
            return Response.ok ().entity (new RestOutput (entity, context)).build ();
        } catch (Throwable e) {
            ConnectionsUtil.safeRollback (wrCon);
            return ErrorResponse.get ().manageError (e, context, null);
        } finally {
            ConnectionsUtil.safeClose (rCon);
            ConnectionsUtil.safeClose (wrCon);
        } // try - catch - finally
    } // insert

    public Response check (ApsTmpTestCheckGetter checkGetter, ExecutionContext context) {
        SReadCon rCon = null;
        try {
            rCon = SConnectionFactory.get ().getReadConnection ();
            RestOutput output = new RestOutput (checkGetter.check (rCon), context);
            return Response.ok ().entity (output).build ();
        } catch (Throwable e) {
            return ErrorResponse.get ().manageError (e, context, null);
        } finally {
            ConnectionsUtil.safeClose (rCon);
        } // try - catch - finally
    } // insert

    @FunctionalInterface
    private interface ApsTmpTestInsertGetter<M extends SModel> {
        M insert (SReadCon rCon, SWriteRemCon wrCon) throws SException;
    } // ApsTmpTestInsertGetter

    @FunctionalInterface
    private interface ApsTmpTestUpdateGetter<M extends SModel> {
        M update (SReadCon rCon, SWriteRemCon wrCon) throws SException;
    } // ApsTmpTestUpdateGetter

    @FunctionalInterface
    private interface ApsTmpTestCheckGetter {
        boolean check (SReadCon rCon) throws SException;
    } // ApsTmpTestInsertGetter
    
    
} // ApsTmpSpecService
