package com.example.gamezale.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Entity(tableName = "contacts")
public class Contact implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "mobile_phone")
    private String mobile_phone;

    @NonNull
    @ColumnInfo(name = "full_name")
    private String full_name;

    @Nullable
    @ColumnInfo(name = "work_phone")
    private String work_phone;

    @Nullable
    @ColumnInfo(name = "home_phone")
    private String home_phone;

    @Nullable
    @ColumnInfo(name = "email")
    private String email;

    @Nullable
    @ColumnInfo(name = "group")
    private String group;

    @Nullable
    @ColumnInfo(name = "company")
    private String company;

    @Nullable
    @ColumnInfo(name = "position")
    private String position;

    @Nullable
    @Embedded
    private Address address;

    @Nullable
    @ColumnInfo(name = "website")
    private String website;

    public Contact(@NonNull String mobile_phone, @NonNull String full_name)
    {
        this.mobile_phone = mobile_phone;
        this.full_name = full_name;
    }

    public void setMobile_phone(@NonNull String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setWork_phone(String work_phone) {
        this.work_phone = work_phone;
    }

    public void setHome_phone(String home_phone) {
        this.home_phone = home_phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @NonNull
    public String getMobile_phone() {
        return mobile_phone;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getWork_phone() {
        return work_phone;
    }

    public String getHome_phone() {
        return home_phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }

    public String getCompany() {
        return company;
    }

    public String getPosition() {
        return position;
    }

    public Address getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }
}
