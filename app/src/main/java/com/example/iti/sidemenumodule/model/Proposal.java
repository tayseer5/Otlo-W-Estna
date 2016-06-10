package com.example.iti.sidemenumodule.model;

import java.util.Date;

/**
 * Created by Ahmed_telnet on 6/9/2016.
 */
public class Proposal {
    private Integer porpId;
    private Integer projectsforusers;
    private Integer users;
    private int price;
    private Date startDatePor;
    private Date deadLinePor;
    private String statusOfPorposa;

    public Integer getPorpId() {
        return porpId;
    }

    public void setPorpId(Integer porpId) {
        this.porpId = porpId;
    }

    public Integer getProjectsforusers() {
        return projectsforusers;
    }

    public void setProjectsforusers(Integer projectsforusers) {
        this.projectsforusers = projectsforusers;
    }

    public Integer getUsers() {
        return users;
    }

    public void setUsers(Integer users) {
        this.users = users;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getStartDatePor() {
        return startDatePor;
    }

    public void setStartDatePor(Date startDatePor) {
        this.startDatePor = startDatePor;
    }

    public Date getDeadLinePor() {
        return deadLinePor;
    }

    public void setDeadLinePor(Date deadLinePor) {
        this.deadLinePor = deadLinePor;
    }

    public String getStatusOfPorposa() {
        return statusOfPorposa;
    }

    public void setStatusOfPorposa(String statusOfPorposa) {
        this.statusOfPorposa = statusOfPorposa;
    }
}
