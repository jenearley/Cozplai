package edu.sjsu.jen.cozplai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jen0e on 11/28/2017.
 */

public class CostumeElement {
    private static String name;
    private static Double cost;
    private static int completion; //percentage completion
    private static List<String> steps; //steps to take to complete element
    private static String makeOrBuy; //can be "make" or "buy"

    public CostumeElement(String name, String makeOrBuy){
        this.name = name;
        this.makeOrBuy = makeOrBuy;
        this.cost = 0.00;
        this.completion = 0;
        this.steps = new ArrayList<>();
    }

    //getters
    public static String getName() { return name; }
    public static Double getCost() { return cost; }
    public static int getCompletion() { return completion; }
    public static List<String> getSteps() { return steps; }
    public static String getMakeOrBuy() { return makeOrBuy; }

    //setters
    public static void setName(String name) { CostumeElement.name = name; }
    public static void setCost(Double cost) { CostumeElement.cost = cost; }
    public static void setCompletion(int completion) { CostumeElement.completion = completion; }
    public static void setSteps(List<String> steps) { CostumeElement.steps = steps; }
    public static void setMakeOrBuy(String makeOrBuy) { CostumeElement.makeOrBuy = makeOrBuy; }

}
