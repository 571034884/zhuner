package com.aibabel.map.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fytworks on 2018/12/6.
 */

public class RouteBean implements Parcelable{
    private String startName;//起始名称
    private String endName;//终点名称
    private int index;//0 驾车 1公交  2 步行
    private String mode;//driving transit walking
    private int locationWhere = 0;//0国外 1国内
    private String city;//城市
    private String coord_type;//wgs84  bd09ll
    private LocationBean startLoc;//起始经纬度
    private LocationBean endLoc;//终点经纬度

    public RouteBean() {
    }


    protected RouteBean(Parcel in) {
        startName = in.readString();
        endName = in.readString();
        index = in.readInt();
        mode = in.readString();
        locationWhere = in.readInt();
        city = in.readString();
        coord_type = in.readString();
        startLoc = in.readParcelable(LocationBean.class.getClassLoader());
        endLoc = in.readParcelable(LocationBean.class.getClassLoader());
    }

    public static final Creator<RouteBean> CREATOR = new Creator<RouteBean>() {
        @Override
        public RouteBean createFromParcel(Parcel in) {
            return new RouteBean(in);
        }

        @Override
        public RouteBean[] newArray(int size) {
            return new RouteBean[size];
        }
    };

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public LocationBean getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(LocationBean startLoc) {
        this.startLoc = startLoc;
    }

    public LocationBean getEndLoc() {
        return endLoc;
    }

    public void setEndLoc(LocationBean endLoc) {
        this.endLoc = endLoc;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLocationWhere() {
        return locationWhere;
    }

    public void setLocationWhere(int locationWhere) {
        this.locationWhere = locationWhere;
    }

    public String getCoord_type() {
        return coord_type;
    }

    public void setCoord_type(String coord_type) {
        this.coord_type = coord_type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startName);
        dest.writeString(endName);
        dest.writeInt(index);
        dest.writeString(mode);
        dest.writeInt(locationWhere);
        dest.writeString(city);
        dest.writeString(coord_type);
        dest.writeParcelable(startLoc, flags);
        dest.writeParcelable(endLoc, flags);
    }
}
