package com.nectar.failurelogsys.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scheduler_failure_logs")
public class ErrorLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "equipment_name")
    private String equipmentName;

    @Column(name = "scheduler_name")
    private String schedulerName;

    @Column(name = "type_of_failure")
    private String typeOfFailure;

    @Column(name = "description")
    private String description;

    @Column(name = "client")
    private String client;

    public ErrorLog(Timestamp date, String schedulerName, String typeOfFailure, String description,String equipmentName,String client) {
        super();
        this.date = date;
        this.schedulerName = schedulerName;
        this.typeOfFailure = typeOfFailure;
        this.description = description;
        this.equipmentName = equipmentName;
        this.client= client;
    }

    public ErrorLog() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getSchedulerName() {
        return schedulerName;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public String getTypeOfFailure() {
        return typeOfFailure;
    }

    public void setTypeOfFailure(String typeOfFailure) {
        this.typeOfFailure = typeOfFailure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}

