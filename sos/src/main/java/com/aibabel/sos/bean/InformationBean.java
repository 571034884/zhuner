package com.aibabel.sos.bean;

/**
 * Created by Wuqinghua on 2018/6/30 0030.
 */
public class InformationBean {
    private String name ;
    private String country_number;
    private String tv_consulate_telephone;
    private String tv_consulate_address;
    private String tv_alarm_telephone;
    private String tv_emergency_telephone ;

    public InformationBean() {
        super();
    }

    @Override
    public String toString() {
        return "InformationBean{" +
                "name='" + name + '\'' +
                ", country_number='" + country_number + '\'' +
                ", tv_consulate_telephone='" + tv_consulate_telephone + '\'' +
                ", tv_consulate_address='" + tv_consulate_address + '\'' +
                ", tv_alarm_telephone='" + tv_alarm_telephone + '\'' +
                ", tv_emergency_telephone='" + tv_emergency_telephone + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry_number() {
        return country_number;
    }

    public void setCountry_number(String country_number) {
        this.country_number = country_number;
    }

    public String getTv_consulate_telephone() {
        return tv_consulate_telephone;
    }

    public void setTv_consulate_telephone(String tv_consulate_telephone) {
        this.tv_consulate_telephone = tv_consulate_telephone;
    }

    public String getTv_consulate_address() {
        return tv_consulate_address;
    }

    public void setTv_consulate_address(String tv_consulate_address) {
        this.tv_consulate_address = tv_consulate_address;
    }

    public String getTv_alarm_telephone() {
        return tv_alarm_telephone;
    }

    public void setTv_alarm_telephone(String tv_alarm_telephone) {
        this.tv_alarm_telephone = tv_alarm_telephone;
    }

    public String getTv_emergency_telephone() {
        return tv_emergency_telephone;
    }

    public void setTv_emergency_telephone(String tv_emergency_telephone) {
        this.tv_emergency_telephone = tv_emergency_telephone;
    }

    public InformationBean(String name, String country_number, String tv_consulate_telephone, String tv_consulate_address, String tv_alarm_telephone, String tv_emergency_telephone) {
        this.name = name;
        this.country_number = country_number;
        this.tv_consulate_telephone = tv_consulate_telephone;
        this.tv_consulate_address = tv_consulate_address;
        this.tv_alarm_telephone = tv_alarm_telephone;
        this.tv_emergency_telephone = tv_emergency_telephone;
    }
}
