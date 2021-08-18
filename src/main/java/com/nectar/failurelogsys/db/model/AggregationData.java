package com.nectar.failurelogsys.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aggregationdata")
public class AggregationData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "datetime")
    private Timestamp datetime;

    @Column(name = "consumption")
    private Double consumption;

    public AggregationData() {
        super();
    }

    public AggregationData(String name, Timestamp datetime, Double consumption) {
        super();
        this.name = name;
        this.datetime = datetime;
        this.consumption = consumption;
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

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    
    
    
}

