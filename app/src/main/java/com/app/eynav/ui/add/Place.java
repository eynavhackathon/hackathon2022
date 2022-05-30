package com.app.eynav.ui.add;


public class Place {
    String namePlace;
    String nameEng;
    double latitude;
    double longitude;
    String type;
    String city;
    String phone;
    String timeOpen;
    public Place(String namePlace,String nameEng, double latitude,double longitude,String type,String city,String phone,String timeOpen){
        this.namePlace = namePlace;
        this.nameEng = nameEng;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.city = city;
        this.phone = phone;
        this.timeOpen = timeOpen;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public String getNameEng() {
        return nameEng;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getTimeOpen() {
        return timeOpen;
    }

    public String getType() {
        return type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Place{" +
                "namePlace='" + namePlace + '\'' +
                ", nameEng='" + nameEng + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", type='" + type + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", timeOpen='" + timeOpen + '\'' +
                '}';
    }
}
