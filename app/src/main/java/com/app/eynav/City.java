package com.app.eynav;

import android.os.Parcel;


public class City  {
    private int id;
    private String name;
    private String nameEng;
    private int regionInt;
    private String region;

    public City(int id, String name, String nameEng, int regionInt, String region) {
        this.id = id;
        this.name = name;
        this.nameEng = nameEng;
        this.regionInt = regionInt;
        this.region = region;

    }

    public City() {

    }

    protected City(Parcel in) {
        id = in.readInt();
        name = in.readString();
        nameEng = in.readString();
        regionInt = in.readInt();
        region = in.readString();
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEng() {
        return nameEng;
    }

    public int getRegionInt() {
        return regionInt;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameEng='" + nameEng + '\'' +
                ", regionInt=" + regionInt +
                ", region='" + region + '\'' +
                '}';
    }
}
