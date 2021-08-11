package com.nectar.failurelogsys.db.model;

public class AggregationData {
    private String name;
    private String datetime;
    private String consumption;

    public AggregationData() {
        super();
    }

    public AggregationData(String name, String datetime, String consumption) {
        super();
        this.name = name;
        this.datetime = datetime;
        this.consumption = consumption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    
    
    
}

