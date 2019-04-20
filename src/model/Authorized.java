package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Authorized extends RecursiveTreeObject<Authorized>  implements java.io.Serializable  {
    private String name;

    public Authorized(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
