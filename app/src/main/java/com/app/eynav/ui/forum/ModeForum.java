package com.app.eynav.ui.forum;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.eynav.ui.add.Meet;


public class ModeForum implements Parcelable,Comparable<ModeForum> {
    String pId, pTitle, pDescr, pImage, pTime, uid, uEmail, uDp, uName, pLike;


    public ModeForum(String pId, String pTitle, String pDescr, String pImage, String pTime, String uid, String uEmail, String uDp, String uName, String pLike) {
        this.pId = pId;
        this.pTitle = pTitle;
        this.pDescr = pDescr;
        this.pImage = pImage;
        this.pTime = pTime;
        this.uid = uid;
        this.uEmail = uEmail;
        this.uDp = uDp;
        this.uName = uName;
        this.pLike = pLike;
    }
    protected ModeForum(Parcel in) {
        pId = in.readString();
        pTitle = in.readString();
        pDescr = in.readString();
        pImage = in.readString();
        pTime = in.readString();
        uid = in.readString();
        uEmail = in.readString();
        uDp = in.readString();
        uName = in.readString();
        pLike = in.readString();

    }

    public static final Creator<ModeForum> CREATOR = new Creator<ModeForum>() {
        @Override
        public ModeForum createFromParcel(Parcel in) {
            return new ModeForum(in);
        }

        @Override
        public ModeForum[] newArray(int size) {
            return new ModeForum[size];
        }
    };

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpDescr() {
        return pDescr;
    }

    public void setpDescr(String pDescr) {
        this.pDescr = pDescr;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuDp() {
        return uDp;
    }

    public void setuDp(String uDp) {
        this.uDp = uDp;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getpLike() {
        return pLike;
    }

    public void setpLike(String pLike) {
        this.pLike = pLike;
    }

    @Override
    public String toString() {
        return "ModeForum{" +
                "pId='" + pId + '\'' +
                ", pTitle='" + pTitle + '\'' +
                ", pDescr='" + pDescr + '\'' +
                ", pImage='" + pImage + '\'' +
                ", pTime='" + pTime + '\'' +
                ", uid='" + uid + '\'' +
                ", uEmail='" + uEmail + '\'' +
                ", uDp='" + uDp + '\'' +
                ", uName='" + uName + '\'' +
                ", pLike='" + pLike + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pId);
        parcel.writeString(pTitle);
        parcel.writeString(pDescr);
        parcel.writeString(pImage);
        parcel.writeString(pTime);
        parcel.writeString(uid);
        parcel.writeString(uEmail);
        parcel.writeString(uDp);
        parcel.writeString(uName);
        parcel.writeString(pLike);

    }

    @Override
    public int compareTo(ModeForum modeForum) {
        return modeForum.getpTime().compareTo(getpTime());
    }

}
