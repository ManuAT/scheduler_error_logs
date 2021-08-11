package com.nectar.failurelogsys.db.model;

import javax.persistence.Table;

@Table(name = "scheduler_failure_logs")
public class ErrorLog {
    private String date;
    private String scheduler_name;
    private String type_of_failure;
    private String description;

    public ErrorLog() {
        super();
    }

    public ErrorLog(String date, String scheduler_name, String type_of_failure, String description) {
        super();
        this.date = date;
        this.scheduler_name = scheduler_name;
        this.type_of_failure = type_of_failure;
        this.description = description;
    }

    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScheduler_name() {
        return scheduler_name;
    }

    public void setScheduler_name(String scheduler_name) {
        this.scheduler_name = scheduler_name;
    }

    public String getType_of_failure() {
        return type_of_failure;
    }

    public void setType_of_failure(String type_of_failure) {
        this.type_of_failure = type_of_failure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

    
}

