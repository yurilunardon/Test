package net.synergy2.logic.fct.tmp;

import net.synergy2.db.base.SModel;

public class FctEqualsInput<M extends SModel> {

    public FctEqualsInput (M o1, M o2) {
        this.o1 = o1;
        this.o2 = o2;
    }
    
    public M o1;
    public M o2;

}
