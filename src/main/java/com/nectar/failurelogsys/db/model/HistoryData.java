package com.nectar.failurelogsys.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
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
    
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "datetime")
    private Timestamp datetime;

    @Column(name = "data")
    private Double data;

    
    public HistoryData() {
        super();
    }


    public HistoryData(String name, Timestamp datetime, Double data) {
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


    public Timestamp getDatetime() {
        return datetime;
    }


    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }


    public Double getData() {
        return data;
    }


    public void setData(Double data) {
        this.data = data;
    }


    
    
}

