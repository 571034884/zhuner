package com.aibabel.map.bean.search;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fytworks on 2018/11/27.
 */

public class AddressData implements Parcelable,Serializable{
    private String message;
    private List<AddressResult> result;
    private int status;

    public AddressData() {
    }

    protected AddressData(Parcel in) {
        message = in.readString();
        result = in.createTypedArrayList(AddressResult.CREATOR);
        status = in.readInt();
    }

    public static final Creator<AddressData> CREATOR = new Creator<AddressData>() {
        @Override
        public AddressData createFromParcel(Parcel in) {
            return new AddressData(in);
        }

        @Override
        public AddressData[] newArray(int size) {
            return new AddressData[size];
        }
    };

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AddressResult> getResult() {
        return result;
    }

    public void setResult(List<AddressResult> result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeTypedList(result);
        dest.writeInt(status);
    }
}

