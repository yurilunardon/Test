package net.synergy2.tester.aps.tmp;

import java.util.ArrayList;
import java.util.List;
import net.synergy2.tester.base.Test;

public class ApsTmpTestList {
    
    public static List<Test> getTestList (boolean execLoadTests) {
        List<Test> list = new ArrayList<> ();

         list.add (new T0001_importInsert_V1 ());
//        list.add (new T0002_importUpdate_V1 ());
        
        return list;
    } // getTestList
    
} // ApsSceTestList
