package com.linkfield.softsim.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class SoftSIMInfo implements Parcelable {
    public enum SIMType {
        NONE(-1), PREGLOBAL(0), GLOBAL(1), LOCAL(3),;
        private final int number;
        private SIMType(int number) {
            this.number = number;
        }
        public int getNumber() {
            return number;
        }
        public static SIMType forNumber(int value) {
            switch (value) {
                case -1: return NONE;
                case 0:  return PREGLOBAL;
                case 1:  return GLOBAL;
                case 2:  return GLOBAL;
                case 3:  return LOCAL;
                case 4:  return LOCAL;
                default: return null;
            }
        }

        @Override
        public String toString() {
            switch (number) {
                case -1: return "NONE";
                case 0:  return "PRE-GLOBAL";
                case 1:  return "GLOBAL";
                case 2:  return "GLOBAL";
                case 3:  return "LOCAL";
                case 4:  return "LOCAL";
                default: return "UNKNOWN";
            }
        }
    }

    private SIMType type;
    private String IMSI;
    private String ICCID;
    private List<APNInfo> APNList;
    private Date expireTime;

    public SoftSIMInfo(SIMType type, String IMSI, String ICCID, List<APNInfo> APNList, Date expireTime) {
        this.type = type;
        this.IMSI = IMSI;
        this.ICCID = ICCID;
        this.APNList = APNList;
        this.expireTime = expireTime;
    }

    public SIMType getType() {
        return type;
    }

    public String getIMSI() {
        return IMSI;
    }

    public String getICCID() {
        return ICCID;
    }

    public List<APNInfo> getAPNList() {
        return APNList;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    protected SoftSIMInfo(Parcel in) {
        type = SIMType.forNumber(in.readInt());
        expireTime = new Date(in.readLong());
        IMSI = in.readString();
        ICCID = in.readString();
        APNList = in.createTypedArrayList(APNInfo.CREATOR);
    }

    public static final Creator<SoftSIMInfo> CREATOR = new Creator<SoftSIMInfo>() {
        @Override
        public SoftSIMInfo createFromParcel(Parcel in) {
            return new SoftSIMInfo(in);
        }

        @Override
        public SoftSIMInfo[] newArray(int size) {
            return new SoftSIMInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type.getNumber());
        parcel.writeLong(expireTime.getTime());
        parcel.writeString(IMSI);
        parcel.writeString(ICCID);
        parcel.writeTypedList(APNList);
    }

    @Override
    public String toString() {
        return "SoftSIMInfo{" +
                "type=" + type +
                ", IMSI='" + IMSI + '\'' +
                ", ICCID='" + ICCID + '\'' +
                ", APNList=" + APNList +
                ", expireTime=" + expireTime +
                '}';
    }
}
