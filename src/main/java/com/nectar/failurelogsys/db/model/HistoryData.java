package com.nectar.failurelogsys.db.model;

// import java.sql.Date;

public class HistoryData {
    private String name;
    private String datetime;
    private String data;

    
    public HistoryData() {
        super();
    }


    public HistoryData(String name, String datetime, String data) {
        super();
        this.name = name;
        this.datetime = datetime;
        this.data = data;
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


    public String getData() {
        return data;
    }


    public void setData(String data) {
        this.data = data;
    }


    // @Override
    // public String toString() {
    //     // TODO Auto-generated method stub
    //     return super.toString();
    // }

    
    
}

