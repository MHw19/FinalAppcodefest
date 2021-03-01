package com.lk.finalcodefestapp1.Model;

public class Product {

    String prdname;
    double price;
    String status;
    String prdimg;

    public Product() {
    }

    public Product(String prdname, double price, String status, String prdimg) {



        this.prdname = prdname;
        this.price = price;
        this.status = status;
        this.prdimg = prdimg;
    }

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrdimg() {
        return prdimg;
    }

    public void setPrdimg(String prdimg) {
        this.prdimg = prdimg;
    }
}
