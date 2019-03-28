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


@Entity(tableName = "current_user")
public class Employee implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "mobile_phone")
    private String mobile_phone;

    @Nullable
    @ColumnInfo(name = "work_phone")
    private String work_phone;

    @Nullable
    @ColumnInfo(name = "home_phone")
    private String home_phone;

    @NonNull
    @ColumnInfo(name = "fullname")
    private String fullname;

    @Nullable
    @ColumnInfo(name = "email")
    private String email;

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

    @Ignore
    private List<String> groups;

    @Ignore
    public Employee(String fullname,
                    @NonNull String phone,
                    String email,
                    String company,
                    String position,
                    Address address,
                    String website) {

        this.fullname = fullname;
        this.mobile_phone = phone;
        this.email = email;
        this.company = company;
        this.position = position;
        this.address = address;
        this.website = website;
    }

    public Employee(@NonNull String mobile_phone, @NonNull String fullname)
    {
        this.mobile_phone = mobile_phone;
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
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

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setMobile_phone(@NonNull String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    @NonNull
    public String getMobile_phone() {
        return mobile_phone;
    }

    @Nullable
    public String getWork_phone() {
        return work_phone;
    }

    @Nullable
    public String getHome_phone() {
        return home_phone;
    }

    public void setWork_phone(@Nullable String work_phone) {
        this.work_phone = work_phone;
    }

    public void setHome_phone(@Nullable String home_phone) {
        this.home_phone = home_phone;
    }

    public String getEmail() {
        return email;
    }

//    public List<String> getGroups() {
//        return groups;
//    }
}
