package com.twocity.apps.latte.data.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by twocity on 14-2-18.
 */
public class Geo {

    private String longitude;

    private String latitude;

    private String city;

    private String province;

    @SerializedName("city_name")
    private String cityName;

    @SerializedName("province_name")
    private String provinceName;

    private String address;

    private String pinyin;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", cityName='" + cityName + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", address='" + address + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
