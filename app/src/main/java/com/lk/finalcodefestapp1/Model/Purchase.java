package com.lk.finalcodefestapp1.Model;

import java.util.Date;

public class Purchase {


      String prdname;
      String price;

      Date date;


      String customer;


    public Purchase() {
    }

    public Purchase(String prdname, String price, Date date, String customer) {
        this.prdname = prdname;
        this.price = price;
        this.date = date;
        this.customer = customer;
    }

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
