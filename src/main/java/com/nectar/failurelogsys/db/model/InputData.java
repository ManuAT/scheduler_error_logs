package com.nectar.failurelogsys.db.model;

import java.util.List;

public class InputData {

    private long startDate;
    private long endDate;
    private String client;
    private List<String> equipments;


    public InputData() {
        super();
    }


    public InputData(long startDate, long endDate, String client, List<String> equipments) {
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


    public List<String> getEquipments() {
        return equipments;
    }


    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }

    
    
    
    
}

