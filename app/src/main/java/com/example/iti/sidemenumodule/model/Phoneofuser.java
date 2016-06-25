package com.example.iti.sidemenumodule.model;

/**
 * Created by ITI on 6/25/2016.
 */
public class Phoneofuser {
    private Integer phoneId;
    private String phoneNumber;

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    private String phoneType;

}
