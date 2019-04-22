package com.aibabel.launcher.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *==========================================================================================
 * @Author： 张文颖
 *
 * @Date：2019/3/22
 * 
 * @Desc：音乐播放实体bean
 *==========================================================================================
 */
public class MusicBean implements Parcelable {

    private String audioUrl;
    private String imageUrl;
    private String name;


    protected MusicBean(Parcel in) {
        audioUrl = in.readString();
        imageUrl = in.readString();
        name = in.readString();
    }

    public static final Creator<MusicBean> CREATOR = new Creator<MusicBean>() {
        @Override
        public MusicBean createFromParcel(Parcel in) {
            return new MusicBean(in);
        }

        @Override
        public MusicBean[] newArray(int size) {
            return new MusicBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(audioUrl);
        dest.writeString(imageUrl);
        dest.writeString(name);
    }

    public MusicBean(String audioUrl, String imageUrl, String name) {
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "audioUrl='" + audioUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
