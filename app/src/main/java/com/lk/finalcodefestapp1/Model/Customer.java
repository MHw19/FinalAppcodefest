package com.lk.finalcodefestapp1.Model;

public class Customer {

    String name;
    String email;
    String mobile;
    String gender;
    String imgurl;
    String nic;
    String FCMToken;

    public Customer() {
    }

    public Customer(String name, String email, String mobile, String gender, String imgurl, String nic, String FCMToken) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.imgurl = imgurl;
        this.nic = nic;
        this.FCMToken = FCMToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFCMToken() {
        return FCMToken;
    }

    public void setFCMToken(String FCMToken) {
        this.FCMToken = FCMToken;
    }
}
