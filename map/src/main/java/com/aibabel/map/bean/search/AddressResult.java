package com.aibabel.map.bean.search;

import android.os.Parcel;
import android.os.Parcelable;

import com.aibabel.map.bean.LocationBean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by fytworks on 2018/11/27.
 */

public class AddressResult extends LitePalSupport implements Parcelable{

    private String business;
    private String addr;
    private String city;
    private String cityid;
    private String district;
    private LocationBean location;
    private String name;
    private String province;
    private String uid;
    private String tag;

    public AddressResult() {
    }


    protected AddressResult(Parcel in) {
        business = in.readString();
        addr = in.readString();
        city = in.readString();
        cityid = in.readString();
        district = in.readString();
        location = in.readParcelable(LocationBean.class.getClassLoader());
        name = in.readString();
        province = in.readString();
        uid = in.readString();
        tag = in.readString();
    }

    public static final Creator<AddressResult> CREATOR = new Creator<AddressResult>() {
        @Override
        public AddressResult createFromParcel(Parcel in) {
            return new AddressResult(in);
        }

        @Override
        public AddressResult[] newArray(int size) {
            return new AddressResult[size];
        }
    };

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(business);
        dest.writeString(addr);
        dest.writeString(city);
        dest.writeString(cityid);
        dest.writeString(district);
        dest.writeParcelable(location, flags);
        dest.writeString(name);
        dest.writeString(province);
        dest.writeString(uid);
        dest.writeString(tag);
    }
}
