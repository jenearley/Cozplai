package edu.sjsu.jen.cozplai;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jen0e on 11/28/2017.
 */

public class Element {
    private String name;
    private int completion; //percentage completion

    public Element(String name){
        this.name = name;
        this.completion = 0;
    }

    //getters
    public String getName() { return name; }
    public int getCompletion() { return completion; }
    //setters
    public void setName(String name) { this.name = name; }
    public void setCompletion(int completion) { this.completion = completion; }

}
