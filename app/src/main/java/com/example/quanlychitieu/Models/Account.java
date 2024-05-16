package com.example.quanlychitieu.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Account implements Parcelable, Serializable {
    private String tk;
    private String mk;
    private byte[] hinhanh;

    public Account(String tk, String mk, byte[] hinhanh) {
        this.tk = tk;
        this.mk = mk;
        this.hinhanh=hinhanh;
    }

    // Constructor
    public Account(String tk, String mk) {
        this.tk = tk;
        this.mk = mk;
        this.hinhanh=null;
    }


    // Getter và Setter cho tk
    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    // Getter và Setter cho mk
    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }
    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    // Implement Parcelable interface
    protected Account(Parcel in) {
        tk = in.readString();
        mk = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tk);
        dest.writeString(mk);
    }

    // Implement Serializable interface
    private static final long serialVersionUID = 1L;

    // No-argument constructor for Serializable
    public Account() {
    }
}
