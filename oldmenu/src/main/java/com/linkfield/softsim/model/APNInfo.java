package com.linkfield.softsim.model;

import android.os.Parcel;
import android.os.Parcelable;

public class APNInfo implements Parcelable {
    private String APN;
    private String userName;
    private String userPass;
    private String authType;
    private String dialNum;
    private String netType;
    private String pdpType;

    public APNInfo(String APN, String userName, String userPass, String authType, String dialNum, String netType, String pdpType) {
        this.APN = APN;
        this.userName = userName;
        this.userPass = userPass;
        this.authType = authType;
        this.dialNum = dialNum;
        this.netType = netType;
        this.pdpType = pdpType;
    }

    protected APNInfo(Parcel in) {
        APN = in.readString();
        userName = in.readString();
        userPass = in.readString();
        authType = in.readString();
        dialNum = in.readString();
        netType = in.readString();
        pdpType = in.readString();
    }

    public String getAPN() {
        return APN;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getAuthType() {
        return authType;
    }

    public String getDialNum() {
        return dialNum;
    }

    public String getNetType() {
        return netType;
    }

    public String getPdpType() {
        return pdpType;
    }

    public static final Creator<APNInfo> CREATOR = new Creator<APNInfo>() {
        @Override
        public APNInfo createFromParcel(Parcel in) {
            return new APNInfo(in);
        }

        @Override
        public APNInfo[] newArray(int size) {
            return new APNInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(APN);
        parcel.writeString(userName);
        parcel.writeString(userPass);
        parcel.writeString(authType);
        parcel.writeString(dialNum);
        parcel.writeString(netType);
        parcel.writeString(pdpType);
    }

    @Override
    public String toString() {
        return "APNInfo{" +
                "APN='" + APN + '\'' +
                ", userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                ", authType='" + authType + '\'' +
                ", dialNum='" + dialNum + '\'' +
                ", netType='" + netType + '\'' +
                ", pdpType='" + pdpType + '\'' +
                '}';
    }
}
