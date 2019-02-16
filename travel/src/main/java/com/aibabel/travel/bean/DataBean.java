package com.aibabel.travel.bean;

public class DataBean {

    private String idstring;
    private int id;
    private String name;
    private String cover;
    private int pid;
    private String pname;
    private String cover_path;
    private String map_image_path;
    private String navi_image_path;
    private String cityName;
    private String countryName;
    private Double latitude;
    private Double longitude;
    private String audiospath;
    private String imagespath;
    private int subCount;
    private String navi_image;
    private String map_image;
    private String audiossize;
    private String imagessize;
//    private String md5;
//    private String audiosurl;
//    private String imagesurl;


    public DataBean() {

    }

    public DataBean(String idstring, int id, String name, String cover, int pid, String pname, String cover_path, String map_image_path, String navi_image_path, String cityName, String countryName, Double latitude, Double longitude, String audiospath, String imagespath, int subCount, String navi_image, String map_image, String audiossize, String imagessize) {
        this.idstring = idstring;
        this.id = id;
        this.name = name;
        this.cover = cover;
        this.pid = pid;
        this.pname = pname;
        this.cover_path = cover_path;
        this.map_image_path = map_image_path;
        this.navi_image_path = navi_image_path;
        this.cityName = cityName;
        this.countryName = countryName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.audiospath = audiospath;
        this.imagespath = imagespath;
        this.subCount = subCount;
        this.navi_image = navi_image;
        this.map_image = map_image;
        this.audiossize = audiossize;
        this.imagessize = imagessize;
    }

    public String getIdstring() {
        return idstring;
    }

    public void setIdstring(String idstring) {
        this.idstring = idstring;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCover_path() {
        return cover_path;
    }

    public void setCover_path(String cover_path) {
        this.cover_path = cover_path;
    }

    public String getMap_image_path() {
        return map_image_path;
    }

    public void setMap_image_path(String map_image_path) {
        this.map_image_path = map_image_path;
    }

    public String getNavi_image_path() {
        return navi_image_path;
    }

    public void setNavi_image_path(String navi_image_path) {
        this.navi_image_path = navi_image_path;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAudiospath() {
        return audiospath;
    }

    public void setAudiospath(String audiospath) {
        this.audiospath = audiospath;
    }

    public String getImagespath() {
        return imagespath;
    }

    public void setImagespath(String imagespath) {
        this.imagespath = imagespath;
    }

    public int getSubCount() {
        return subCount;
    }

    public void setSubCount(int subCount) {
        this.subCount = subCount;
    }

    public String getNavi_image() {
        return navi_image;
    }

    public void setNavi_image(String navi_image) {
        this.navi_image = navi_image;
    }

    public String getMap_image() {
        return map_image;
    }

    public void setMap_image(String map_image) {
        this.map_image = map_image;
    }

    public String getAudiossize() {
        return audiossize;
    }

    public void setAudiossize(String audiossize) {
        this.audiossize = audiossize;
    }

    public String getImagessize() {
        return imagessize;
    }

    public void setImagessize(String imagessize) {
        this.imagessize = imagessize;
    }
}
