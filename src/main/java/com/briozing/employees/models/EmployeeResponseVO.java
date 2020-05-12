package com.briozing.employees.models;

import java.util.List;

public class EmployeeResponseVO {
    private int id;
    private String name;
    private String emailId;
    public String country;

    public EmployeeResponseVO(long id, String name, String emailId, String country) {
    }

    public EmployeeResponseVO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> errors;


}
