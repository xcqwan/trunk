package com.gezbox.windmap.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zombie on 7/17/14.
 */
public class Location implements Parcelable {
    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel parcel) {
            return new Location(parcel);
        }

        @Override
        public Location[] newArray(int i) {
            return new Location[i];
        }
    };

    private float longitude;
    private float latitude;
    private String state;
    private String state_code;
    private String city;
    private String city_code;
    private String district;
    private String district_code;
    private String street;
    private String street_number;

    public Location() {

    }

    private Location(Parcel in) {
        longitude = in.readFloat();
        latitude = in.readFloat();
        state = in.readString();
        state_code = in.readString();
        city = in.readString();
        city_code = in.readString();
        district = in.readString();
        district_code = in.readString();
        street = in.readString();
        street_number = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(longitude);
        parcel.writeFloat(latitude);
        parcel.writeString(state);
        parcel.writeString(state_code);
        parcel.writeString(city);
        parcel.writeString(city_code);
        parcel.writeString(district);
        parcel.writeString(district_code);
        parcel.writeString(street);
        parcel.writeString(street_number);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }
}
