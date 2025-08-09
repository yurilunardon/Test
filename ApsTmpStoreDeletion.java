package net.synergy2.logic.aps.tmp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.synergy2.db.aps.att.AngApsAttVal;
import net.synergy2.db.aps.att.AngApsPrdCycOprAtt;
import net.synergy2.db.aps.cst.AngApsCst;
import net.synergy2.db.aps.cst.AngApsCstGrp;
import net.synergy2.db.aps.cst.AngApsPrdCycOprCst;
import net.synergy2.db.aps.cst.AngApsPrdCycOprCstGrp;
import net.synergy2.db.aps.opr.AngApsOprClr;
import net.synergy2.db.aps.opr.AngApsShpOrdIco;
import net.synergy2.db.aps.opr.ApsCusOrd;
import net.synergy2.db.aps.opr.ApsDem;
import net.synergy2.db.aps.opr.ApsItmCop;
import net.synergy2.db.aps.opr.ApsMat;
import net.synergy2.db.aps.opr.ApsOpr;
import net.synergy2.db.aps.opr.ApsOprAtt;
import net.synergy2.db.aps.opr.ApsOprCst;
import net.synergy2.db.aps.opr.ApsOprCstGrp;
import net.synergy2.db.aps.opr.ApsOprLnk;
import net.synergy2.db.aps.opr.ApsOprResExp;
import net.synergy2.db.aps.opr.ApsShpOrd;
import net.synergy2.db.aps.opr.ApsSup;
import net.synergy2.db.aps.opr.ApsSupOrd;
import net.synergy2.db.aps.res.AngApsRes;
import net.synergy2.db.aps.sce.ApsSceCst;
import net.synergy2.db.aps.sce.ApsSceRes;
import net.synergy2.db.aps.sce.ApsSceResCurAttVal;
import net.synergy2.db.aps.sce.ApsSceResCurCst;
import net.synergy2.db.sys.bas.AngCus;
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
import net.synergy2.db.sys.job.AngJob;
import net.synergy2.db.sys.job.AngSubJob;
import net.synergy2.db.sys.lgs.AngWhs;
import net.synergy2.db.sys.org.OrgLvl;
import net.synergy2.db.sys.res.AngRes;
import net.synergy2.logic.fct.tmp.FctTmpStoreDeletion;

public class ApsTmpStoreDeletion extends FctTmpStoreDeletion {

    public ApsTmpStoreDeletion () { super (); }
    
    /*******************************************************************************/
    /** Dati anagrafici ************************************************************/
    /*******************************************************************************/

    private Map<String, AngWhs> toRemoveAngWhs = null;
    public Map<String, AngWhs> getToRemoveAngWhs () { return toRemoveAngWhs; }
    public List<AngWhs> getToRemoveAngWhsList () { return new ArrayList<> (toRemoveAngWhs.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngWhs (Map<String, AngWhs> toRemoveAngWhs) { this.toRemoveAngWhs = toRemoveAngWhs; return this; }
    public void clearAngWhs () { this.toRemoveAngWhs = null; }
    public AngWhs removeAngWhs (String key) { return toRemoveAngWhs.remove (key); }

    private Map<String, AngJob> toRemoveAngJob = null;
    public Map<String, AngJob> getToRemoveAngJob () { return toRemoveAngJob; }
    public List<AngJob> getToRemoveAngJobList () { return new ArrayList<> (toRemoveAngJob.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngJob (Map<String, AngJob> toRemoveAngJob) { this.toRemoveAngJob = toRemoveAngJob; return this; }
    public void clearAngJob () { this.toRemoveAngJob = null; }
    public AngJob removeAngJob (String key) { return toRemoveAngJob.remove (key); }

    private Map<String, AngSubJob> toRemoveAngSubJob = null;
    public Map<String, AngSubJob> getToRemoveAngSubJob () { return toRemoveAngSubJob; }
    public List<AngSubJob> getToRemoveAngSubJobList () { return new ArrayList<> (toRemoveAngSubJob.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngSubJob (Map<String, AngSubJob> toRemoveAngSubJob) { this.toRemoveAngSubJob = toRemoveAngSubJob; return this; }
    public void clearAngSubJob () { this.toRemoveAngSubJob = null; }
    public AngSubJob removeAngSubJob (String key) { return toRemoveAngSubJob.remove (key); }

    private Map<String, AngUniMea> toRemoveAngUniMea = null;
    public Map<String, AngUniMea> getToRemoveAngUniMea () { return toRemoveAngUniMea; }
    public List<AngUniMea> getToRemoveAngUniMeaList () { return new ArrayList<> (toRemoveAngUniMea.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngUniMea (Map<String, AngUniMea> toRemoveAngUniMea) { this.toRemoveAngUniMea = toRemoveAngUniMea; return this; }
    public void clearAngUniMea () { this.toRemoveAngUniMea = null; }
    public AngUniMea removeAngUniMea (String key) { return toRemoveAngUniMea.remove (key); }

    private Map<String, AngApsOprClr> toRemoveAngApsOprClr = null;
    public Map<String, AngApsOprClr> getToRemoveAngApsOprClr () { return toRemoveAngApsOprClr; }
    public List<AngApsOprClr> getToRemoveAngApsOprClrList () { return new ArrayList<> (toRemoveAngApsOprClr.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngApsOprClr (Map<String, AngApsOprClr> toRemoveAngApsOprClr) { this.toRemoveAngApsOprClr = toRemoveAngApsOprClr; return this; }
    public void clearAngApsOprClr () { this.toRemoveAngApsOprClr = null; }
    public AngApsOprClr removeAngApsOprClr (String key) { return toRemoveAngApsOprClr.remove (key); }

    private Map<String, AngApsShpOrdIco> toRemoveAngApsShpOrdIco = null;
    public Map<String, AngApsShpOrdIco> getToRemoveAngApsShpOrdIco () { return toRemoveAngApsShpOrdIco; }
    public List<AngApsShpOrdIco> getToRemoveAngApsShpOrdIcoList () { return new ArrayList<> (toRemoveAngApsShpOrdIco.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngApsShpOrdIco (Map<String, AngApsShpOrdIco> toRemoveAngApsShpOrdIco) { this.toRemoveAngApsShpOrdIco = toRemoveAngApsShpOrdIco; return this; }
    public void clearAngApsShpOrdIco () { this.toRemoveAngApsShpOrdIco = null; }
    public AngApsShpOrdIco removeAngApsShpOrdIco (String key) { return toRemoveAngApsShpOrdIco.remove (key); }

    private Map<String, OrgLvl> toRemoveOrgLvl = null;
    public Map<String, OrgLvl> getToRemoveOrgLvl () { return toRemoveOrgLvl; }
    public List<OrgLvl> getToRemoveOrgLvlList () { return new ArrayList<> (toRemoveOrgLvl.values ()); }
    public ApsTmpStoreDeletion setToRemoveOrgLvl (Map<String, OrgLvl> toRemoveOrgLvl) { this.toRemoveOrgLvl = toRemoveOrgLvl; return this; }
    public void clearOrgLvl () { this.toRemoveOrgLvl = null; }
    public OrgLvl removeOrgLvl (String key) { return toRemoveOrgLvl.remove (key); }

    private Map<String, AngApsAttVal> toRemoveAngApsAttVal = null;
    public Map<String, AngApsAttVal> getToRemoveAngApsAttVal () { return toRemoveAngApsAttVal; }
    public List<AngApsAttVal> getToRemoveAngApsAttValList () { return new ArrayList<> (toRemoveAngApsAttVal.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngApsAttVal (Map<String, AngApsAttVal> toRemoveAngApsAttVal) { this.toRemoveAngApsAttVal = toRemoveAngApsAttVal; return this; }
    public void clearAngApsAttVal () { this.toRemoveAngApsAttVal = null; }
    public AngApsAttVal removeAngApsAttVal (String key) { return toRemoveAngApsAttVal.remove (key); }

    private Map<String, AngGrp> toRemoveAngGrp = null;
    public Map<String, AngGrp> getToRemoveAngGrp () { return toRemoveAngGrp; }
    public List<AngGrp> getToRemoveAngGrpList () { return new ArrayList<> (toRemoveAngGrp.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngGrp (Map<String, AngGrp> toRemoveAngGrp) { this.toRemoveAngGrp = toRemoveAngGrp; return this; }
    public void clearAngGrp () { this.toRemoveAngGrp = null; }
    public AngGrp removeAngGrp (String key) { return toRemoveAngGrp.remove (key); }

    private Map<String, AngRes> toRemoveAngRes = null;
    public Map<String, AngRes> getToRemoveAngRes () { return toRemoveAngRes; }
    public List<AngRes> getToRemoveAngResList () { return new ArrayList<> (toRemoveAngRes.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngRes (Map<String, AngRes> toRemoveAngRes) { this.toRemoveAngRes = toRemoveAngRes; return this; }
    public void clearAngRes () { this.toRemoveAngRes = null; }
    public AngRes removeAngRes (String key) { return toRemoveAngRes.remove (key); }

    private Map<String, AngApsRes> toRemoveAngApsRes = null;
    public Map<String, AngApsRes> getToRemoveAngApsRes () { return toRemoveAngApsRes; }
    public List<AngApsRes> getToRemoveAngApsResList () { return new ArrayList<> (toRemoveAngApsRes.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngApsRes (Map<String, AngApsRes> toRemoveAngApsRes) { this.toRemoveAngApsRes = toRemoveAngApsRes; return this; }
    public void clearAngApsRes () { this.toRemoveAngApsRes = null; }
    public AngApsRes removeAngApsRes (String key) { return toRemoveAngApsRes.remove (key); }

    private Map<String, ApsSceRes> toRemoveApsSceRes = null;
    public Map<String, ApsSceRes> getToRemoveApsSceRes () { return toRemoveApsSceRes; }
    public List<ApsSceRes> getToRemoveApsSceResList () { return new ArrayList<> (toRemoveApsSceRes.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsSceRes (Map<String, ApsSceRes> toRemoveApsSceRes) { this.toRemoveApsSceRes = toRemoveApsSceRes; return this; }
    public void clearApsSceRes () { this.toRemoveApsSceRes = null; }
    public ApsSceRes removeApsSceRes (String key) { return toRemoveApsSceRes.remove (key); }

    private Map<String, AngResGrp> toRemoveAngResGrp = null;
    public Map<String, AngResGrp> getToRemoveAngResGrp () { return toRemoveAngResGrp; }
    public List<AngResGrp> getToRemoveAngResGrpList () { return new ArrayList<> (toRemoveAngResGrp.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngResGrp (Map<String, AngResGrp> toRemoveAngResGrp) { this.toRemoveAngResGrp = toRemoveAngResGrp; return this; }
    public void clearAngResGrp () { this.toRemoveAngResGrp = null; }
    public AngResGrp removeAngResGrp (String key) { return toRemoveAngResGrp.remove (key); }

    private Map<String, AngApsCst> toRemoveAngApsCst = null;
    public Map<String, AngApsCst> getToRemoveAngApsCst () { return toRemoveAngApsCst; }
    public List<AngApsCst> getToRemoveAngApsCstList () { return new ArrayList<> (toRemoveAngApsCst.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngApsCst (Map<String, AngApsCst> toRemoveAngApsCst) { this.toRemoveAngApsCst = toRemoveAngApsCst; return this; }
    public void clearAngApsCst () { this.toRemoveAngApsCst = null; }
    public AngApsCst removeAngApsCst (String key) { return toRemoveAngApsCst.remove (key); }

    private Map<String, ApsSceCst> toRemoveApsSceCst = null;
    public Map<String, ApsSceCst> getToRemoveApsSceCst () { return toRemoveApsSceCst; }
    public List<ApsSceCst> getToRemoveApsSceCstList () { return new ArrayList<> (toRemoveApsSceCst.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsSceCst (Map<String, ApsSceCst> toRemoveApsSceCst) { this.toRemoveApsSceCst = toRemoveApsSceCst; return this; }
    public void clearApsSceCst () { this.toRemoveApsSceCst = null; }
    public ApsSceCst removeApsSceCst (String key) { return toRemoveApsSceCst.remove (key); }

    private Map<String, AngApsCstGrp> toRemoveAngApsCstGrp = null;
    public Map<String, AngApsCstGrp> getToRemoveAngApsCstGrp () { return toRemoveAngApsCstGrp; }
    public List<AngApsCstGrp> getToRemoveAngApsCstGrpList () { return new ArrayList<> (toRemoveAngApsCstGrp.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngApsCstGrp (Map<String, AngApsCstGrp> toRemoveAngApsCstGrp) { this.toRemoveAngApsCstGrp = toRemoveAngApsCstGrp; return this; }
    public void clearAngApsCstGrp () { this.toRemoveAngApsCstGrp = null; }
    public AngApsCstGrp removeAngApsCstGrp (String key) { return toRemoveAngApsCstGrp.remove (key); }

    private Map<String, AngSup> toRemoveAngSup = null;
    public Map<String, AngSup> getToRemoveAngSup () { return toRemoveAngSup; }
    public List<AngSup> getToRemoveAngSupList () { return new ArrayList<> (toRemoveAngSup.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngSup (Map<String, AngSup> toRemoveAngSup) { this.toRemoveAngSup = toRemoveAngSup; return this; }
    public void clearAngSup () { this.toRemoveAngSup = null; }
    public AngSup removeAngSup (String key) { return toRemoveAngSup.remove (key); }

    private Map<String, AngCus> toRemoveAngCus = null;
    public Map<String, AngCus> getToRemoveAngCus () { return toRemoveAngCus; }
    public List<AngCus> getToRemoveAngCusList () { return new ArrayList<> (toRemoveAngCus.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngCus (Map<String, AngCus> toRemoveAngCus) { this.toRemoveAngCus = toRemoveAngCus; return this; }
    public void clearAngCus () { this.toRemoveAngCus = null; }
    public AngCus removeAngCus (String key) { return toRemoveAngCus.remove (key); }

    private Map<String, AngItm> toRemoveAngItm = null;
    public Map<String, AngItm> getToRemoveAngItm () { return toRemoveAngItm; }
    public List<AngItm> getToRemoveAngItmList () { return new ArrayList<> (toRemoveAngItm.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngItm (Map<String, AngItm> toRemoveAngItm) { this.toRemoveAngItm = toRemoveAngItm; return this; }
    public void clearAngItm () { this.toRemoveAngItm = null; }
    public AngItm removeAngItm (String key) { return toRemoveAngItm.remove (key); }

    private Map<String, AngPrdVrnCat> toRemoveAngPrdVrnCat = null;
    public Map<String, AngPrdVrnCat> getToRemoveAngPrdVrnCat () { return toRemoveAngPrdVrnCat; }
    public List<AngPrdVrnCat> getToRemoveAngPrdVrnCatList () { return new ArrayList<> (toRemoveAngPrdVrnCat.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngPrdVrnCat (Map<String, AngPrdVrnCat> toRemoveAngPrdVrnCat) { this.toRemoveAngPrdVrnCat = toRemoveAngPrdVrnCat; return this; }
    public void clearAngPrdVrnCat () { this.toRemoveAngPrdVrnCat = null; }
    public AngPrdVrnCat removeAngPrdVrnCat (String key) { return toRemoveAngPrdVrnCat.remove (key); }

    private Map<String, AngPrdVrn> toRemoveAngPrdVrn = null;
    public Map<String, AngPrdVrn> getToRemoveAngPrdVrn () { return toRemoveAngPrdVrn; }
    public List<AngPrdVrn> getToRemoveAngPrdVrnList () { return new ArrayList<> (toRemoveAngPrdVrn.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngPrdVrn (Map<String, AngPrdVrn> toRemoveAngPrdVrn) { this.toRemoveAngPrdVrn = toRemoveAngPrdVrn; return this; }
    public void clearAngPrdVrn () { this.toRemoveAngPrdVrn = null; }
    public AngPrdVrn removeAngPrdVrn (String key) { return toRemoveAngPrdVrn.remove (key); }

    private Map<String, AngItmVrn> toRemoveAngItmVrn = null;
    public Map<String, AngItmVrn> getToRemoveAngItmVrn () { return toRemoveAngItmVrn; }
    public List<AngItmVrn> getToRemoveAngItmVrnList () { return new ArrayList<> (toRemoveAngItmVrn.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngItmVrn (Map<String, AngItmVrn> toRemoveAngItmVrn) { this.toRemoveAngItmVrn = toRemoveAngItmVrn; return this; }
    public void clearAngItmVrn () { this.toRemoveAngItmVrn = null; }
    public AngItmVrn removeAngItmVrn (String key) { return toRemoveAngItmVrn.remove (key); }

    private Map<String, AngItmMdl> toRemoveAngItmMdl = null;
    public Map<String, AngItmMdl> getToRemoveAngItmMdl () { return toRemoveAngItmMdl; }
    public List<AngItmMdl> getToRemoveAngItmMdlList () { return new ArrayList<> (toRemoveAngItmMdl.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngItmMdl (Map<String, AngItmMdl> toRemoveAngItmMdl) { this.toRemoveAngItmMdl = toRemoveAngItmMdl; return this; }
    public void clearAngItmMdl () { this.toRemoveAngItmMdl = null; }
    public AngItmMdl removeAngItmMdl (String key) { return toRemoveAngItmMdl.remove (key); }

    private Map<String, AngItmMdlVrn> toRemoveAngItmMdlVrn = null;
    public Map<String, AngItmMdlVrn> getToRemoveAngItmMdlVrn () { return toRemoveAngItmMdlVrn; }
    public List<AngItmMdlVrn> getToRemoveAngItmMdlVrnList () { return new ArrayList<> (toRemoveAngItmMdlVrn.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngItmMdlVrn (Map<String, AngItmMdlVrn> toRemoveAngItmMdlVrn) { this.toRemoveAngItmMdlVrn = toRemoveAngItmMdlVrn; return this; }
    public void clearAngItmMdlVrn () { this.toRemoveAngItmMdlVrn = null; }
    public AngItmMdlVrn removeAngItmMdlVrn (String key) { return toRemoveAngItmMdlVrn.remove (key); }

    private Map<String, AngPrdCyc> toRemoveAngPrdCyc = null;
    public Map<String, AngPrdCyc> getToRemoveAngPrdCyc () { return toRemoveAngPrdCyc; }
    public List<AngPrdCyc> getToRemoveAngPrdCycList () { return new ArrayList<> (toRemoveAngPrdCyc.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngPrdCyc (Map<String, AngPrdCyc> toRemoveAngPrdCyc) { this.toRemoveAngPrdCyc = toRemoveAngPrdCyc; return this; }
    public void clearAngPrdCyc () { this.setToRemoveAngPrdCyc (null); }
    public AngPrdCyc removeAngPrdCyc (String key) { return toRemoveAngPrdCyc.remove (key); }

    private Map<String, AngPrdCycOpr> toRemoveAngPrdCycOpr = null;
    public Map<String, AngPrdCycOpr> getToRemoveAngPrdCycOpr () { return toRemoveAngPrdCycOpr; }
    public List<AngPrdCycOpr> getToRemoveAngPrdCycOprList () { return new ArrayList<> (toRemoveAngPrdCycOpr.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngPrdCycOpr (Map<String, AngPrdCycOpr> toRemoveAngPrdCycOpr) { this.toRemoveAngPrdCycOpr = toRemoveAngPrdCycOpr; return this; }
    public void clearAngPrdCycOpr () { this.setToRemoveAngPrdCycOpr (null); }
    public AngPrdCycOpr removeAngPrdCycOpr (String key) { return toRemoveAngPrdCycOpr.remove (key); }

    private Map<String, AngBom> toRemoveAngBom = null;
    public Map<String, AngBom> getToRemoveAngBom () { return toRemoveAngBom; }
    public List<AngBom> getToRemoveAngBomList () { return new ArrayList<> (toRemoveAngBom.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngBom (Map<String, AngBom> toRemoveAngBom) { this.toRemoveAngBom = toRemoveAngBom; return this; }
    public void clearAngBom () { this.setToRemoveAngBom (null); }
    public AngBom removeAngBom (String key) { return toRemoveAngBom.remove (key); }

    private Map<String, AngPrdCycOprLnk> toRemoveAngPrdCycOprLnk = null;
    public Map<String, AngPrdCycOprLnk> getToRemoveAngPrdCycOprLnk () { return toRemoveAngPrdCycOprLnk; }
    public List<AngPrdCycOprLnk> getToRemoveAngPrdCycOprLnkList () { return new ArrayList<> (toRemoveAngPrdCycOprLnk.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngPrdCycOprLnk (Map<String, AngPrdCycOprLnk> toRemoveAngPrdCycOprLnk) { this.toRemoveAngPrdCycOprLnk = toRemoveAngPrdCycOprLnk; return this; }
    public void clearAngPrdCycOprLnk () { this.setToRemoveAngPrdCycOprLnk (null); }
    public AngPrdCycOprLnk removeAngPrdCycOprLnk (String key) { return toRemoveAngPrdCycOprLnk.remove (key); }

    private Map<String, AngApsPrdCycOprCst> toRemoveAngApsPrdCycOprCst = null;
    public Map<String, AngApsPrdCycOprCst> getToRemoveAngApsPrdCycOprCst () { return toRemoveAngApsPrdCycOprCst; }
    public List<AngApsPrdCycOprCst> getToRemoveAngApsPrdCycOprCstList () { return new ArrayList<> (toRemoveAngApsPrdCycOprCst.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngApsPrdCycOprCst (Map<String, AngApsPrdCycOprCst> toRemoveAngApsPrdCycOprCst) { this.toRemoveAngApsPrdCycOprCst = toRemoveAngApsPrdCycOprCst; return this; }
    public void clearAngApsPrdCycOprCst () { this.setToRemoveAngApsPrdCycOprCst (null); }
    public AngApsPrdCycOprCst removeAngApsPrdCycOprCst (String key) { return toRemoveAngApsPrdCycOprCst.remove (key); }

    private Map<String, AngApsPrdCycOprCstGrp> toRemoveAngApsPrdCycOprCstGrp = null;
    public Map<String, AngApsPrdCycOprCstGrp> getToRemoveAngApsPrdCycOprCstGrp () { return toRemoveAngApsPrdCycOprCstGrp; }
    public List<AngApsPrdCycOprCstGrp> getToRemoveAngApsPrdCycOprCstGrpList () { return new ArrayList<> (toRemoveAngApsPrdCycOprCstGrp.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngApsPrdCycOprCstGrp (Map<String, AngApsPrdCycOprCstGrp> toRemoveAngApsPrdCycOprCstGrp) { this.toRemoveAngApsPrdCycOprCstGrp = toRemoveAngApsPrdCycOprCstGrp; return this; }
    public void clearAngApsPrdCycOprCstGrp () { this.setToRemoveAngApsPrdCycOprCstGrp (null); }
    public AngApsPrdCycOprCstGrp removeAngApsPrdCycOprCstGrp (String key) { return toRemoveAngApsPrdCycOprCstGrp.remove (key); }

    private Map<String, AngApsPrdCycOprAtt> toRemoveAngApsPrdCycOprAtt = null;
    public Map<String, AngApsPrdCycOprAtt> getToRemoveAngApsPrdCycOprAtt () { return toRemoveAngApsPrdCycOprAtt; }
    public List<AngApsPrdCycOprAtt> getToRemoveAngApsPrdCycOprAttList () { return new ArrayList<> (toRemoveAngApsPrdCycOprAtt.values ()); }
    public ApsTmpStoreDeletion setToRemoveAngApsPrdCycOprAtt (Map<String, AngApsPrdCycOprAtt> toRemoveAngApsPrdCycOprAtt) { this.toRemoveAngApsPrdCycOprAtt = toRemoveAngApsPrdCycOprAtt; return this; }
    public void clearAngApsPrdCycOprAtt () { this.setToRemoveAngApsPrdCycOprAtt (null); }
    public AngApsPrdCycOprAtt removeAngApsPrdCycOprAtt (String key) { return toRemoveAngApsPrdCycOprAtt.remove (key); }

    
    /*******************************************************************************/
    /** Dati operativi *************************************************************/
    /*******************************************************************************/
    
    private Map<String, ApsCusOrd> toRemoveApsCusOrd = null;
    public Map<String, ApsCusOrd> getToRemoveApsCusOrd () { return toRemoveApsCusOrd; }
    public List<ApsCusOrd> getToRemoveApsCusOrdList () { return new ArrayList<> (toRemoveApsCusOrd.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsCusOrd (Map<String, ApsCusOrd> toRemoveApsCusOrd) { this.toRemoveApsCusOrd = toRemoveApsCusOrd; return this; }
    public void clearApsCusOrd() { this.toRemoveApsCusOrd = null; }
    public ApsCusOrd removeApsCusOrd (String key) { return toRemoveApsCusOrd.remove (key); }

    private Map<String, ApsDem> toRemoveApsDem = null;
    public Map<String, ApsDem> getToRemoveApsDem () { return toRemoveApsDem; }
    public List<ApsDem> getToRemoveApsDemList () { return new ArrayList<> (toRemoveApsDem.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsDem (Map<String, ApsDem> toRemoveApsDem) { this.toRemoveApsDem = toRemoveApsDem; return this; }
    public void clearApsDem() { this.toRemoveApsDem = null; }
    public ApsDem removeApsDem (String key) { return toRemoveApsDem.remove (key); }

    private Map<String, ApsSupOrd> toRemoveApsSupOrd = null;
    public Map<String, ApsSupOrd> getToRemoveApsSupOrd () { return toRemoveApsSupOrd; }
    public List<ApsSupOrd> getToRemoveApsSupOrdList () { return new ArrayList<> (toRemoveApsSupOrd.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsSupOrd (Map<String, ApsSupOrd> toRemoveApsSupOrd) { this.toRemoveApsSupOrd = toRemoveApsSupOrd; return this; }
    public void clearApsSupOrd() { this.toRemoveApsSupOrd = null; }
    public ApsSupOrd removeApsSupOrd (String key) { return toRemoveApsSupOrd.remove (key); }

    private Map<String, ApsSup> toRemoveApsSup = null;
    public Map<String, ApsSup> getToRemoveApsSup () { return toRemoveApsSup; }
    public List<ApsSup> getToRemoveApsSupList () { return new ArrayList<> (toRemoveApsSup.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsSup (Map<String, ApsSup> toRemoveApsSup) { this.toRemoveApsSup = toRemoveApsSup; return this; }
    public void clearApsSup() { this.toRemoveApsSup = null; }
    public ApsSup removeApsSup (String key) { return toRemoveApsSup.remove (key); }

    private Map<String, ApsShpOrd> toRemoveApsShpOrd = null;
    public Map<String, ApsShpOrd> getToRemoveApsShpOrd () { return toRemoveApsShpOrd; }
    public List<ApsShpOrd> getToRemoveApsShpOrdList () { return new ArrayList<> (toRemoveApsShpOrd.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsShpOrd (Map<String, ApsShpOrd> toRemoveApsShpOrd) { this.toRemoveApsShpOrd = toRemoveApsShpOrd; return this; }
    public void clearApsShpOrd() { this.toRemoveApsShpOrd = null; }
    public ApsShpOrd removeApsShpOrd (String key) { return toRemoveApsShpOrd.remove (key); }

    private Map<String, ApsItmCop> toRemoveApsItmCop = null;
    public Map<String, ApsItmCop> getToRemoveApsItmCop () { return toRemoveApsItmCop; }
    public List<ApsItmCop> getToRemoveApsItmCopList () { return new ArrayList<> (toRemoveApsItmCop.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsItmCop (Map<String, ApsItmCop> toRemoveApsItmCop) { this.toRemoveApsItmCop = toRemoveApsItmCop; return this; }
    public void clearApsItmCop() { this.toRemoveApsItmCop = null; }
    public ApsItmCop removeApsItmCop (String key) { return toRemoveApsItmCop.remove (key); }

    private Map<String, ApsOpr> toRemoveApsOprAgg = null;
    public Map<String, ApsOpr> getToRemoveApsOprAgg () { return toRemoveApsOprAgg; }
//    public List<ApsOpr> getToRemoveApsOprAggList () { return new ArrayList<> (toRemoveApsOprAgg.values ()); }
    public List<ApsOpr> getToRemoveApsOprAggList () { return toRemoveApsOprAgg == null ? Collections.emptyList() : new ArrayList<>(toRemoveApsOprAgg.values()); }
    public ApsTmpStoreDeletion setToRemoveApsOprAgg (Map<String, ApsOpr> toRemoveApsOprAgg) { this.toRemoveApsOprAgg = toRemoveApsOprAgg; return this; }
    public void clearApsOprAgg() { this.toRemoveApsOprAgg = null; }
    public ApsOpr removeApsOprAgg (String key) { return toRemoveApsOprAgg.remove (key); }

    private Map<String, ApsOpr> toRemoveApsOprMulPal = null;
    public Map<String, ApsOpr> getToRemoveApsOprMulPal () { return toRemoveApsOprMulPal; }
//    public List<ApsOpr> getToRemoveApsOprMulPalList () { return new ArrayList<> (toRemoveApsOprMulPal.values ()); }
    public List<ApsOpr> getToRemoveApsOprMulPalList () { return toRemoveApsOprMulPal == null ? Collections.emptyList() : new ArrayList<>(toRemoveApsOprMulPal.values()); }
    public ApsTmpStoreDeletion setToRemoveApsOprMulPal (Map<String, ApsOpr> toRemoveApsOprMulPal) { this.toRemoveApsOprMulPal = toRemoveApsOprMulPal; return this; }
    public void clearApsOprMulPal() { this.toRemoveApsOprMulPal = null; }
    public ApsOpr removeApsOprMulPal (String key) { return toRemoveApsOprMulPal.remove (key); }

    private Map<String, ApsOpr> toRemoveApsOpr = null;
    public Map<String, ApsOpr> getToRemoveApsOpr () { return toRemoveApsOpr; }
//    public List<ApsOpr> getToRemoveApsOprList () { return new ArrayList<> (toRemoveApsOpr.values ()); }
    public List<ApsOpr> getToRemoveApsOprList () { return toRemoveApsOpr == null ? Collections.emptyList() : new ArrayList<>(toRemoveApsOpr.values()); }
    public ApsTmpStoreDeletion setToRemoveApsOpr (Map<String, ApsOpr> toRemoveApsOpr) { this.toRemoveApsOpr = toRemoveApsOpr; return this; }
    public void clearApsOpr() { this.toRemoveApsOpr = null; }
    public ApsOpr removeApsOpr (String key) { return toRemoveApsOpr.remove (key); }

    private Map<String, ApsMat> toRemoveApsMat = null;
    public Map<String, ApsMat> getToRemoveApsMat () { return toRemoveApsMat; }
    public List<ApsMat> getToRemoveApsMatList () { return new ArrayList<> (toRemoveApsMat.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsMat (Map<String, ApsMat> toRemoveApsMat) { this.toRemoveApsMat = toRemoveApsMat; return this; }
    public void clearApsMat() { this.toRemoveApsMat = null; }
    public ApsMat removeApsMat (String key) { return toRemoveApsMat.remove (key); }

    private Map<String, ApsOprLnk> toRemoveApsOprLnk = null;
    public Map<String, ApsOprLnk> getToRemoveApsOprLnk () { return toRemoveApsOprLnk; }
    public List<ApsOprLnk> getToRemoveApsOprLnkList () { return new ArrayList<> (toRemoveApsOprLnk.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsOprLnk (Map<String, ApsOprLnk> toRemoveApsOprLnk) { this.toRemoveApsOprLnk = toRemoveApsOprLnk; return this; }
    public void clearApsOprLnk() { this.toRemoveApsOprLnk = null; }
    public ApsOprLnk removeApsOprLnk (String key) { return toRemoveApsOprLnk.remove (key); }

    private Map<String, ApsOprCstGrp> toRemoveApsOprCstGrp = null;
    public Map<String, ApsOprCstGrp> getToRemoveApsOprCstGrp () { return toRemoveApsOprCstGrp; }
    public List<ApsOprCstGrp> getToRemoveApsOprCstGrpList () { return new ArrayList<> (toRemoveApsOprCstGrp.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsOprCstGrp (Map<String, ApsOprCstGrp> toRemoveApsOprCstGrp) { this.toRemoveApsOprCstGrp = toRemoveApsOprCstGrp; return this; }
    public void clearApsOprCstGrp() { this.toRemoveApsOprCstGrp = null; }
    public ApsOprCstGrp removeApsOprCstGrp (String key) { return toRemoveApsOprCstGrp.remove (key); }

    private Map<String, ApsOprCst> toRemoveApsOprCst = null;
    public Map<String, ApsOprCst> getToRemoveApsOprCst () { return toRemoveApsOprCst; }
    public List<ApsOprCst> getToRemoveApsOprCstList () { return new ArrayList<> (toRemoveApsOprCst.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsOprCst (Map<String, ApsOprCst> toRemoveApsOprCst) { this.toRemoveApsOprCst = toRemoveApsOprCst; return this; }
    public void clearApsOprCst() { this.toRemoveApsOprCst = null; }
    public ApsOprCst removeApsOprCst (String key) { return toRemoveApsOprCst.remove (key); }

    private Map<String, ApsOprAtt> toRemoveApsOprAtt = null;
    public Map<String, ApsOprAtt> getToRemoveApsOprAtt () { return toRemoveApsOprAtt; }
    public List<ApsOprAtt> getToRemoveApsOprAttList () { return new ArrayList<> (toRemoveApsOprAtt.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsOprAtt (Map<String, ApsOprAtt> toRemoveApsOprAtt) { this.toRemoveApsOprAtt = toRemoveApsOprAtt; return this; }
    public void clearApsOprAtt() { this.toRemoveApsOprAtt = null; }
    public ApsOprAtt removeApsOprAtt (String key) { return toRemoveApsOprAtt.remove (key); }

    private Map<String, ApsOprResExp> toRemoveApsOprResExp = null;
    public Map<String, ApsOprResExp> getToRemoveApsOprResExp () { return toRemoveApsOprResExp; }
    public List<ApsOprResExp> getToRemoveApsOprResExpList () { return new ArrayList<> (toRemoveApsOprResExp.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsOprResExp (Map<String, ApsOprResExp> toRemoveApsOprResExp) { this.toRemoveApsOprResExp = toRemoveApsOprResExp; return this; }
    public void clearApsOprResExp() { this.toRemoveApsOprResExp = null; }
    public ApsOprResExp removeApsOprResExp (String key) { return toRemoveApsOprResExp.remove (key); }

    private Map<String, ApsSceResCurCst> toRemoveApsSceResCurCst = null;
    public Map<String, ApsSceResCurCst> getToRemoveApsSceResCurCst () { return toRemoveApsSceResCurCst; }
    public List<ApsSceResCurCst> getToRemoveApsSceResCurCstList () { return new ArrayList<> (toRemoveApsSceResCurCst.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsSceResCurCst (Map<String, ApsSceResCurCst> toRemoveApsSceResCurCst) { this.toRemoveApsSceResCurCst = toRemoveApsSceResCurCst; return this; }
    public void clearApsSceResCurCst() { this.toRemoveApsSceResCurCst = null; }
    public ApsSceResCurCst removeApsSceResCurCst (String key) { return toRemoveApsSceResCurCst.remove (key); }

    private Map<String, ApsSceResCurAttVal> toRemoveApsSceResCurAttVal = null;
    public Map<String, ApsSceResCurAttVal> getToRemoveApsSceResCurAttVal () { return toRemoveApsSceResCurAttVal; }
    public List<ApsSceResCurAttVal> getToRemoveApsSceResCurAttValList () { return new ArrayList<> (toRemoveApsSceResCurAttVal.values ()); }
    public ApsTmpStoreDeletion setToRemoveApsSceResCurAttVal (Map<String, ApsSceResCurAttVal> toRemoveApsSceResCurAttVal) { this.toRemoveApsSceResCurAttVal = toRemoveApsSceResCurAttVal; return this; }
    public void clearApsSceResCurAttVal() { this.toRemoveApsSceResCurAttVal = null; }
    public ApsSceResCurAttVal removeApsSceResCurAttVal (String key) { return toRemoveApsSceResCurAttVal.remove (key); }


} // ApsSuperImporter
