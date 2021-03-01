package com.lk.finalcodefestapp1.Model;

public class Ticket {

    String title;
    String description;
    String option;
    String cusID;


    public Ticket() {
    }

    public Ticket(String title, String description, String option, String cusID) {
        this.title = title;
        this.description = description;
        this.option = option;
        this.cusID = cusID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }
}
