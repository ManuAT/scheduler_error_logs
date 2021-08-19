package com.nectar.failurelogsys.db.model;

import java.util.List;

public class OutputData {

    private String equip;
    private List<ErrorArray> errors;

    public OutputData() {
        super();
    }

    public OutputData(String equip, List<ErrorArray> errors) {
        this.equip = equip;
        this.errors = errors;
    }

    public String getEquip() {
        return equip;
    }

    public void setEquip(String equip) {
        this.equip = equip;
    }

    public List<ErrorArray> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorArray> errors) {
        this.errors = errors;
    }

    
    
}
