package com.lk.finalcodefestapp1.Model;

public class News {

    String ntitle;
    String ndescription;
    String nlocation;
    double  location_lat;
    double  location_lon;

    public News() {
    }

    public News(String ntitle, String ndescription, String nlocation, double location_lat, double location_lon) {
        this.ntitle = ntitle;
        this.ndescription = ndescription;
        this.nlocation = nlocation;
        this.location_lat = location_lat;
        this.location_lon = location_lon;
    }

    public String getNtitle() {
        return ntitle;
    }

    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public String getNdescription() {
        return ndescription;
    }

    public void setNdescription(String ndescription) {
        this.ndescription = ndescription;
    }

    public String getNlocation() {
        return nlocation;
    }

    public void setNlocation(String nlocation) {
        this.nlocation = nlocation;
    }

    public double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(double location_lat) {
        this.location_lat = location_lat;
    }

    public double getLocation_lon() {
        return location_lon;
    }

    public void setLocation_lon(double location_lon) {
        this.location_lon = location_lon;
    }
}
