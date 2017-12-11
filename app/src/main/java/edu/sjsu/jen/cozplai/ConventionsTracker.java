package edu.sjsu.jen.cozplai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jen0e on 12/8/17.
 */

public class ConventionsTracker {

    public static List<Convention> conventions = new ArrayList<>();

    static {

    }

    public static void addConvention(Convention convention, DataHelper dataHelper){
        conventions.add(convention);
        dataHelper.addConvention(convention);
    }


}
