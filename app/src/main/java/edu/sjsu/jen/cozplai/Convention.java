package edu.sjsu.jen.cozplai;

/**
 * Created by jen0e on 12/8/17.
 */

public class Convention {
    private String name;
    private String address;
    private String date;

    public Convention(String name) {
        this.name = name;
        this.address = "";
        this.date = "00/00/0000";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
