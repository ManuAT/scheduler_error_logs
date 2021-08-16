package com.nectar.failurelogsys.db.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// import java.sql.Date;
@Entity
@Table(name = "historydata")
public class HistoryData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private Long id;
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

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
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


    
    
}

