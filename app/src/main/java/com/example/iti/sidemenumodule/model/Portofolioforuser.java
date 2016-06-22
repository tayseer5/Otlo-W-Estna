package com.example.iti.sidemenumodule.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ITI on 6/10/2016.
 */
public class Portofolioforuser {
    private Integer portofolioId;
    private Category category;
    private Users users;
    private String portofolioDescription;
    private Set<String> portofolioimageses = new HashSet<String>(0);

    public Integer getPortofolioId() {
        return portofolioId;
    }

    public void setPortofolioId(Integer portofolioId) {
        this.portofolioId = portofolioId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getPortofolioDescription() {
        return portofolioDescription;
    }

    public void setPortofolioDescription(String portofolioDescription) {
        this.portofolioDescription = portofolioDescription;
    }

    public Set<String> getPortofolioimageses() {
        return portofolioimageses;
    }

    public void setPortofolioimageses(Set<String> portofolioimageses) {
        this.portofolioimageses = portofolioimageses;
    }
}
