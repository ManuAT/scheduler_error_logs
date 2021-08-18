package com.nectar.failurelogsys.db.model;

public class ErrorArray {

    long date;
    String error;
    String description;

    public ErrorArray() {
        super();
    }

    public ErrorArray(long date, String error, String description) {
        this.date = date;
        this.error = error;
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    
}
