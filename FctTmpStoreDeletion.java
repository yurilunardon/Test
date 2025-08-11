package net.synergy2.logic.fct.tmp;

import java.util.Map;
import net.synergy2.db.sys.bas.AngSup;
import net.synergy2.db.sys.itm.AngItm;
import net.synergy2.db.sys.itm.AngUniMea;
import net.synergy2.db.sys.lgs.AngWhs;

public class FctTmpStoreDeletion {

    private Map<String, AngWhs> toRemoveAngWhs = null;
    public Map<String, AngWhs> getToRemoveAngWhs () { return toRemoveAngWhs; }
    public FctTmpStoreDeletion setToRemoveAngWhs (Map<String, AngWhs> toRemoveAngWhs) { this.toRemoveAngWhs = toRemoveAngWhs; return this; }
    public AngWhs removeAngWhs (String key) { return toRemoveAngWhs.remove (key); }

    private Map<String, AngUniMea> toRemoveAngUniMea = null;
    public Map<String, AngUniMea> getToRemoveAngUniMea () { return toRemoveAngUniMea; }
    public FctTmpStoreDeletion setToRemoveAngUniMea (Map<String, AngUniMea> toRemoveAngUniMea) { this.toRemoveAngUniMea = toRemoveAngUniMea; return this; }
    public AngUniMea removeAngUniMea (String key) { return toRemoveAngUniMea.remove (key); }

    private Map<String, AngSup> toRemoveAngSup = null;
    public Map<String, AngSup> getToRemoveAngSup () { return toRemoveAngSup; }
    public FctTmpStoreDeletion setToRemoveAngSup (Map<String, AngSup> toRemoveAngSup) { this.toRemoveAngSup = toRemoveAngSup; return this; }
    public AngSup removeAngSup (String key) { return toRemoveAngSup.remove (key); }

    private Map<String, AngItm> toRemoveAngItm = null;
    public Map<String, AngItm> getToRemoveAngItm () { return toRemoveAngItm; }
    public FctTmpStoreDeletion setToRemoveAngItm (Map<String, AngItm> toRemoveAngItm) { this.toRemoveAngItm = toRemoveAngItm; return this; }
    public AngItm removeAngItm (String key) { return toRemoveAngItm.remove (key); }

} // ApsSuperImporter
