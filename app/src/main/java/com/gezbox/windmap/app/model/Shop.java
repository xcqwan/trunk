package com.gezbox.windmap.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zombie on 14-11-12.
 */
public class Shop implements Parcelable{
    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel parcel) {
            return new Shop(parcel);
        }

        @Override
        public Shop[] newArray(int i) {
            return new Shop[i];
        }
    };

    private String name;
    private String tel;
    private String phone;
    private String subscriber_tel;
    private String images;
    private boolean has_android;
    private Location location;
    private double upload_latitude;
    private double upload_longitude;

    public Shop() {

    }

    private Shop(Parcel in) {
        name = in.readString();
        tel = in.readString();
        phone = in.readString();
        subscriber_tel = in.readString();
        images = in.readString();
        has_android = in.readByte() != 0;
        location = in.readParcelable(Location.class.getClassLoader());
        upload_latitude = in.readDouble();
        upload_longitude = in.readDouble();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubscriber_tel() {
        return subscriber_tel;
    }

    public void setSubscriber_tel(String subscriber_tel) {
        this.subscriber_tel = subscriber_tel;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public boolean isHas_android() {
        return has_android;
    }

    public void setHas_android(boolean has_android) {
        this.has_android = has_android;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getUpload_longitude() {
        return upload_longitude;
    }

    public void setUpload_longitude(double upload_longitude) {
        this.upload_longitude = upload_longitude;
    }

    public double getUpload_latitude() {
        return upload_latitude;
    }

    public void setUpload_latitude(double upload_latitude) {
        this.upload_latitude = upload_latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(tel);
        parcel.writeString(phone);
        parcel.writeString(subscriber_tel);
        parcel.writeString(images);
        parcel.writeByte((byte) (has_android ? 1 : 0));
        parcel.writeParcelable(location, i);
        parcel.writeDouble(upload_latitude);
        parcel.writeDouble(upload_longitude);
    }
}
