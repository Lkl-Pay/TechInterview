package com.example.crud.api;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Customer implements Parcelable {

    private double _created;
    private String _data_type;
    private boolean _is_deleted;
    private double _modified;
    private String _self_link;
    private String _user;
    private String _uuid;
    private String name;
    private int age;
    private String card;
    private double amount;

    public Customer(){

    }

    protected Customer(Parcel in) {
        _created = in.readDouble();
        _data_type = in.readString();
        _is_deleted = in.readByte() != 0;
        _modified = in.readDouble();
        _self_link = in.readString();
        _user = in.readString();
        _uuid = in.readString();
        name = in.readString();
        age = in.readInt();
        card = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public String getAmount() { return String.valueOf(amount); }

    public void setAmount(double amount) { this.amount = amount; }

    public double getCreated() {
        return _created;
    }

    public void setCreated(double created) {
        this._created = created;
    }

    public String getDataType() {
        return _data_type;
    }

    public void setDataType(String dataType) {
        this._data_type = dataType;
    }

    public boolean isDeleted() {
        return _is_deleted;
    }

    public void setDeleted(boolean deleted) {
        _is_deleted = deleted;
    }

    public String getSelfLink() { return _self_link; }

    public void setSelfLink(String selfLink) {
        this._self_link = selfLink;
    }

    public String getUser() {
        return _user;
    }

    public void setUser(String user) {
        this._user = user;
    }

    public String getUuid() {
        return _uuid;
    }

    public void setUuid(String uuid) {
        this._uuid = _uuid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() { return String.valueOf(age); }
    public void setAge(int age) {
        this.age = age;
    }

    public String getCard() {
        return card;
    }
    public void setCard(String card) {
        this.card = card;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDouble(_created);
        dest.writeString(_data_type);
        dest.writeByte((byte) (_is_deleted ? 1 : 0));
        dest.writeDouble(_modified);
        dest.writeString(_self_link);
        dest.writeString(_user);
        dest.writeString(_uuid);
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(card);
        dest.writeDouble(amount);
    }
}
