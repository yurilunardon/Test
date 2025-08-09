package net.synergy2.logic.aps.tmp.common;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientProperties;
import net.synergy2.base.connections.SReadCon;
import net.synergy2.base.context.ExecutionContext;
import net.synergy2.base.exceptions.SApplicationException;
import net.synergy2.base.exceptions.SConnectionException;
import net.synergy2.base.exceptions.SQueryException;
import net.synergy2.base.exceptions.SSqlException;
import net.synergy2.base.fw.AppConstants;
import net.synergy2.base.rest.ApiInput;
import net.synergy2.base.types.SSingletonHolder;
import net.synergy2.db.aps.opt.ApsOptDao;
import net.synergy2.db.aps.opt.ApsOptions;
import net.synergy2.db.base.SAlias;
import net.synergy2.db.sys.AngAppInf;
import net.synergy2.db.sys.etl.AngEtlSrv;
import net.synergy2.db.sys.etl.AngEtlSrvDao;
import net.synergy2.logic.fct.tmp.common.FctTmpUtil;
import net.synergy2.query.SQuery;
import net.synergy2.rest.client.SynergyHttpClient;
import net.synergy2etl.pentaho.model.PentahoOutputModel;
import net.synergy2etl.pentaho.model.PentahoOutputModel.PentahoResult;
import net.synergy2etl.pentaho.model.PentahoRestOutput;


public class ApsDatabaseTmpUtil {

    protected ApsDatabaseTmpUtil () {}
    private static class Singleton {
        private static final SSingletonHolder<ApsDatabaseTmpUtil> INSTANCE = new SSingletonHolder<> (ApsDatabaseTmpUtil.class);
    } // Singleton
    public static ApsDatabaseTmpUtil get () { return Singleton.INSTANCE.get (); }

    /************************************************************************************/
    /**  IMPORT DATI DA GESTIONALE A DATABASE TEMPORANEO ********************************/
    /************************************************************************************/
 
    // Importa tutti gli scenari tramite una chiamata alla trasformazione dell'ETL
    public PentahoResult importScenarios (String token, ExecutionContext context, SReadCon rCon) throws SSqlException, SConnectionException, SApplicationException {
        PentahoResult result = runPentahoJob ("JobApsImportScenari", null, token, context, rCon);
        return result;
    } // importScenarios

    // Importa tutti i dati tramite una chiamata alla trasformazione dell'ETL
    public PentahoResult importData (String scenario, boolean importBaseData, boolean importOperationalData, String token, ExecutionContext context, SReadCon rCon)
        throws SSqlException, SConnectionException, SApplicationException, SQueryException {
        HashMap<String, HashMap<String, String>> payload = new HashMap<> ();
        HashMap<String, String> values = new HashMap<> ();

        // Ottengo le applicazioni installate.
        Set<Long> appsUid = new HashSet<> ();
        SQuery s = SQuery.of (context, rCon).from (SAlias.AngAppInf, "AppInf", true);
        s.whereActive ("AppInf");
        s.execute ();
        s.readMergedValuesAndCast (AngAppInf.runtimeClass (), (appInf, isUpdate) -> {
            appsUid.add (appInf.getUid ());
        });
        s.close ();

        values.put ("SCENARIO", scenario);
        values.put ("DATI_BASE", importBaseData ? "1" : "0");
        // Se nelle applicazioni installate c'è DBM allora effettuo l'import dei dati base completi
        // (che tiene conto per le anagrafiche comuni la trasformazione più completa) altrimenti uso quelli di APS.
        values.put ("DATI_BASE_COMPLETI", appsUid.contains (AppConstants.AppId.DBM) ? "1" : "0");
        values.put ("DATI_OPERATIVI", importOperationalData ? "1" : "0");
        values.put ("APS", "1");
        payload.put ("params", values);

        PentahoResult result = runPentahoJob ("JobApsImportCompleto", payload, token, context, rCon);
        return result;
    } // importData

    /************************************************************************************/
    /**  EXPORT DATI DA DATABASE TEMPORANEO A GESTIONALE ********************************/
    /************************************************************************************/

    // Esporta tutti i dati (ApsOpr) tramite una chiamata alla trasformazione dell'ETL
    public PentahoResult exportData (String scenario, String token, ExecutionContext context, SReadCon rCon) throws SSqlException, SConnectionException, SApplicationException {
        PentahoResult result = runPentahoJob ("JobApsExportDatiOperativi", ApiInput.of (null, Map.of ("SCENARIO", scenario)), token, context, rCon);
        return result;
    } // exportData

    /************************************************************************************/
    /**  IMPORT DATI DA ENTITÁ ESTERNE A DATABASE TEMPORANEO ****************************/
    /************************************************************************************/

    public PentahoResult importExternalData (String token, ExecutionContext context, SReadCon rCon) throws SConnectionException, SApplicationException, SSqlException {
        PentahoResult result = runPentahoJob ("JobApsExtraImport", null, token, context, rCon);
        return result;
    } // importExternalData

    /************************************************************************************/
    /**  GALILEO DOCUMENTALE ************************************************************/
    /************************************************************************************/
    
    public PentahoResult getGDDocumentViaPentaho (Object payload, String token, ExecutionContext context, SReadCon rCon) throws SConnectionException, SApplicationException, SSqlException {
        UriBuilder pentahoUrl = getPentahoUrl (context, rCon);
        
        PentahoOutputModel restOutput = null;
        
        final UriBuilder serverUrl = pentahoUrl.path ("v1").path ("etl").path ("getGDDocument");
        try {
            Response response = SynergyHttpClient.get ()
                .property (ClientProperties.READ_TIMEOUT, null)
                .target (serverUrl)
                .request (MediaType.APPLICATION_JSON).accept (MediaType.APPLICATION_JSON)
                .header ("token", token)
                .post (Entity.entity (payload, MediaType.APPLICATION_JSON));
            if (response.getStatus () != 200) {
                System.err.println ("Error [status " + response.getStatus () + "] \n");
                throw new SApplicationException ("Error [status " + response.getStatus () + "]");
            } // if
            restOutput = response.readEntity (new GenericType<PentahoRestOutput<PentahoOutputModel>> () {}).result;
        } catch (ProcessingException e) {
            e.printStackTrace ();
            throw new SConnectionException ("Unreachable etl server");
        } // try - catch

        return FctTmpUtil.get ().getPentahoResult (restOutput);
    } // getGDDocumentViaPentaho
    
    /************************************************************************************/
    /**  CHIAMATA GENERICA A UN JOB *****************************************************/
    /************************************************************************************/

    public PentahoResult runPentahoJob (String jobName, Object payload, String token, ExecutionContext context, SReadCon rCon) throws SConnectionException, SApplicationException, SSqlException {
        UriBuilder pentahoUrl = getPentahoUrl (context, rCon);

        PentahoOutputModel restOutput = FctTmpUtil.get ().startETL (jobName, payload, pentahoUrl, token);

        return FctTmpUtil.get ().getPentahoResult (restOutput);
    } // runPentahoJob
    
    private UriBuilder getPentahoUrl (ExecutionContext context, SReadCon rCon) throws SSqlException, SConnectionException, SApplicationException { // Ottengo l'URL di Pentaho dalle opzioni globali
        ApsOptions options = ApsOptDao.get ().getOptions (context, rCon);
        String pentahoUrl = null;
        if (options.getApsEtlSrvUid () != null) {
            AngEtlSrv etlSrv = AngEtlSrvDao.get ().getById (options.getApsEtlSrvUid (), context, rCon);
            pentahoUrl = etlSrv != null ? etlSrv.getSrvEtlRstBasUrl () : null;
        } // if
        
        try {
            return UriBuilder.fromUri (new URI ((pentahoUrl != null ? pentahoUrl : "http://localhost/synergy2-pentaho/ws")));
        } catch (URISyntaxException e) {
            throw new SApplicationException ("ETL: " + "Malformed pentaho web services url", -1);
        } // try - catch
    } // getPentahoUrl
    
} // ApsDatabaseTmpUtil
