package com.app.eynav.ui.add;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class Meet implements Comparable<Meet> , Parcelable {
    String typeMeet;
    String dateMeetInfo;
    String timeMeet;
    String placeMeet;
    int countNewImmigrant;
    int countNativeB;
    int countVolunteer;
    String placeNameEng;
    Double placeLatitude;
    Double placeLongitude;
    String cityRegion;
    String yearBorn;
    String languageMeet;
    Date dateM;
    public Meet(String typeMeet, String dateMeetInfo, String timeMeet, String placeMeet, int countNewImmigrant, int countNativeB, int countVolunteer, Date dateM, String placeNameEng, Double placeLatitude, Double placeLongitude, String cityRegion, String yearBorn, String languageMeet){
        this.typeMeet = typeMeet;
        this.dateMeetInfo = dateMeetInfo;
        this.timeMeet = timeMeet;
        this.placeMeet = placeMeet;
        this.countNewImmigrant = countNewImmigrant;
        this.countNativeB = countNativeB;
        this.countVolunteer = countVolunteer;
        this.dateM = dateM;
        this.placeNameEng = placeNameEng;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.yearBorn = yearBorn;
        this.cityRegion = cityRegion;
        this.languageMeet = languageMeet;
    }

    protected Meet(Parcel in) {
        typeMeet = in.readString();
        dateMeetInfo = in.readString();
        timeMeet = in.readString();
        placeMeet = in.readString();
        countNewImmigrant = in.readInt();
        countNativeB = in.readInt();
        countVolunteer = in.readInt();
        placeNameEng =  in.readString();
        placeLatitude = in.readDouble();
        placeLongitude  = in.readDouble();
        yearBorn = in.readString();
        cityRegion = in.readString();
        languageMeet =  in.readString();
    }

    public String getLanguageMeet() {
        return languageMeet;
    }

    public String getCityRegion() {
        return cityRegion;
    }

    public String getYearBorn() {
        return yearBorn;
    }

    public static final Creator<Meet> CREATOR = new Creator<Meet>() {
        @Override
        public Meet createFromParcel(Parcel in) {
            return new Meet(in);
        }

        @Override
        public Meet[] newArray(int size) {
            return new Meet[size];
        }
    };

    public Double getPlaceLatitude() {
        return placeLatitude;
    }

    public Double getPlaceLongitude() {
        return placeLongitude;
    }

    public String getPlaceNameEng() {
        return placeNameEng;
    }

    public String getTypeMeet() {
        return this.typeMeet;
    }
    public String getDateAndHourMeet() {
        return this.dateMeetInfo+" "+this.timeMeet;
    }

    public String getDateMeetInfo() {
        return dateMeetInfo;
    }

    public String getTimeMeet() {
        return timeMeet;
    }

    public String getPlaceMeet() {
        return this.placeMeet;
    }

    public int getCountNativeB() {
        return countNativeB;
    }

    public int getCountNewImmigrant() {
        return countNewImmigrant;
    }

    public int getCountVolunteer() {
        return countVolunteer;
    }

    public Date getDateM() {
        return dateM;
    }

    public void setPlaceLatitude(Double placeLatitude) {
        this.placeLatitude = placeLatitude;
    }

    public void setPlaceLongitude(Double placeLongitude) {
        this.placeLongitude = placeLongitude;
    }

    public void setPlaceNameEng(String placeNameEng) {
        this.placeNameEng = placeNameEng;
    }

    @Override
    public int compareTo(Meet s) {
        return getDateM().compareTo(s.getDateM());

    }

    @Override
    public String toString() {
        return "Meet{" +
                "typeMeet='" + typeMeet + '\'' +
                ", dateMeetInfo='" + dateMeetInfo + '\'' +
                ", timeMeet='" + timeMeet + '\'' +
                ", placeMeet='" + placeMeet + '\'' +
                ", countNewImmigrant=" + countNewImmigrant +
                ", countNativeB=" + countNativeB +
                ", countVolunteer=" + countVolunteer +
                ", dateM=" + dateM +
                '}';
    }

    private int numMonth(String s) {
        int month = 1;
        switch (s) {
            case "JAN":
                month = 1;
                break;
            case "FEB":
                month = 2;
                break;
            case "MAR":
                month = 3;
                break;
            case "APR":
                month = 4;
                break;
            case "MAY":
                month = 5;
                break;
            case "JUN":
                month = 6;
                break;
            case "JUL":
                month = 7;
                break;
            case "AUG":
                month = 8;
                break;
            case "SEP":
                month = 9;
                break;
            case "OCT":
                month = 10;
                break;
            case "NOV":
                month = 11;
                break;
            case "DEC":
                month = 12;
                break;
            default:
                month = 1;
                break;
        }
        month = month - 1;

        return month;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(typeMeet);
        dest.writeString(dateMeetInfo);
        dest.writeString(timeMeet);
        dest.writeString(placeMeet);
        dest.writeInt(countNewImmigrant);
        dest.writeInt(countNativeB);
        dest.writeInt(countVolunteer);

    }
}
