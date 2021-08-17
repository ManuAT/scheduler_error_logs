package com.nectar.failurelogsys.db.beans;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "historydata")
public class HistoryData {

    @Id  
    private Long id;
    @Column(name = "name")
    private String name;
	@Column(name = "datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
    private Timestamp datetime;
    @Column(name = "data")
    private String data;

    
    public HistoryData() {
        super();
    }


    public HistoryData(String name, Timestamp datetime, String data) {
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


	public String getData() {
        return data;
    }


    public void setData(String data) {
        this.data = data;
    }


    
    
}

