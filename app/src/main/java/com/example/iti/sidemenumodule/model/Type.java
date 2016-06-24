package com.example.iti.sidemenumodule.model;

/**
 * Created by Ahmed_telnet on 6/24/2016.
 */
public class Type {
    private Integer typeId;
    private String type;

    public Type(Integer typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
