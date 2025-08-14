package net.synergy2.logic.aps.tmp;

import net.synergy2.base.exceptions.SApplicationException;
import net.synergy2.base.exceptions.SException;
import net.synergy2.db.base.SModel;
import net.synergy2.logic.aps.tmp.common.ApsTmpStore;
import net.synergy2.logic.fct.tmp.AGenericTmp;

public abstract class AApsGenericTmp<M extends SModel> extends AGenericTmp<M, ApsTmpStore, ApsTmpStoreDeletion, ApsImportContext> {

    public void setSceUid (M model, Long sceUid) throws SException {
        throw new SApplicationException ("setSceUid non implementato");
    } // setSceUid

} // AGenericTmp
