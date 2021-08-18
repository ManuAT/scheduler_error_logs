package com.nectar.failurelogsys.db.model;

public class InputData {

    long startDate;
    long endDate;
    String client;
    String[] equipments;


    public InputData() {
        super();
    }


    public InputData(long startDate, long endDate, String client, String[] equipments) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
        this.equipments = equipments;
    }


    public long getStartDate() {
        return startDate;
    }


    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }


    public long getEndDate() {
        return endDate;
    }


    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }


    public String getClient() {
        return client;
    }


    public void setClient(String client) {
        this.client = client;
    }


    public String[] getEquipments() {
        return equipments;
    }


    public void setEquipments(String[] equipments) {
        this.equipments = equipments;
    }

    
    
    
    
}

