package com.nectar.failurelogsys.db.model;


public class OutputData {

    String equip;
    ErrorArray[] errors;

    public OutputData() {
        super();
    }

    public OutputData(String equip, ErrorArray[] errors) {
        this.equip = equip;
        this.errors = errors;
    }

    public String getEquip() {
        return equip;
    }

    public void setEquip(String equip) {
        this.equip = equip;
    }

    public ErrorArray[] getErrors() {
        return errors;
    }

    public void setErrors(ErrorArray[] errors) {
        this.errors = errors;
    }

    
    
}
