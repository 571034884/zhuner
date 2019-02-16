package com.aibabel.map.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/27.
 */

public class LocationBean extends LitePalSupport implements Parcelable,Serializable{
    private double lat;
    private double lng;

    public LocationBean() {
    }

    public LocationBean(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    protected LocationBean(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<LocationBean> CREATOR = new Creator<LocationBean>() {
        @Override
        public LocationBean createFromParcel(Parcel in) {
            return new LocationBean(in);
        }

        @Override
        public LocationBean[] newArray(int size) {
            return new LocationBean[size];
        }
    };

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }

    @Override
    public String toString() {
        return getLat()+","+getLng();
    }
}
