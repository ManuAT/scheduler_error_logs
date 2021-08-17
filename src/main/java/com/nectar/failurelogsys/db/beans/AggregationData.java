package com.nectar.failurelogsys.db.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aggregationdata")
public class AggregationData {

    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "datetime")
    private String datetime;
    @Column(name = "consumption")
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

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    
    
    
}

