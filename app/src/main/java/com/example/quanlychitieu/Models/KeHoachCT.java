package com.example.quanlychitieu.Models;

public class KeHoachCT {
    private int id;
    private String tk;
    private int ct1,ct2,ct3,ct4,ct5,ct6,ct7,ct8,ct9,ct10,ct11,ct12, sum;
    private String month;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public KeHoachCT(int id, String tk, int ct1, int ct2, int ct3, int ct4, int ct5, int ct6, int ct7, int ct8, int ct9, int ct10, int ct11, int ct12, String month) {
        this.id = id;
        this.tk = tk;
        this.ct1 = ct1;
        this.ct2 = ct2;
        this.ct3 = ct3;
        this.ct4 = ct4;
        this.ct5 = ct5;
        this.ct6 = ct6;
        this.ct7 = ct7;
        this.ct8 = ct8;
        this.ct9 = ct9;
        this.ct10 = ct10;
        this.ct11 = ct11;
        this.ct12 = ct12;
        this.month = month;
        this.sum = ct1 + ct2 + ct3 + ct4 + ct5 + ct6 + ct7 + ct8 + ct9 + ct10 + ct11 + ct12;
    }

    public KeHoachCT(String tk, int ct1, int ct2, int ct3, int ct4, int ct5, int ct6, int ct7, int ct8, int ct9, int ct10, int ct11, int ct12, String month) {
        this.tk = tk;
        this.ct1 = ct1;
        this.ct2 = ct2;
        this.ct3 = ct3;
        this.ct4 = ct4;
        this.ct5 = ct5;
        this.ct6 = ct6;
        this.ct7 = ct7;
        this.ct8 = ct8;
        this.ct9 = ct9;
        this.ct10 = ct10;
        this.ct11 = ct11;
        this.ct12 = ct12;
        this.month = month;
        this.sum = ct1 + ct2 + ct3 + ct4 + ct5 + ct6 + ct7 + ct8 + ct9 + ct10 + ct11 + ct12;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public int getCt1() {
        return ct1;
    }

    public void setCt1(int ct1) {
        this.ct1 = ct1;
    }

    public int getCt2() {
        return ct2;
    }

    public void setCt2(int ct2) {
        this.ct2 = ct2;
    }

    public int getCt3() {
        return ct3;
    }

    public void setCt3(int ct3) {
        this.ct3 = ct3;
    }

    public int getCt4() {
        return ct4;
    }

    public void setCt4(int ct4) {
        this.ct4 = ct4;
    }

    public int getCt5() {
        return ct5;
    }

    public void setCt5(int ct5) {
        this.ct5 = ct5;
    }

    public int getCt6() {
        return ct6;
    }

    public void setCt6(int ct6) {
        this.ct6 = ct6;
    }

    public int getCt7() {
        return ct7;
    }

    public void setCt7(int ct7) {
        this.ct7 = ct7;
    }

    public int getCt8() {
        return ct8;
    }

    public void setCt8(int ct8) {
        this.ct8 = ct8;
    }

    public int getCt9() {
        return ct9;
    }

    public void setCt9(int ct9) {
        this.ct9 = ct9;
    }

    public int getCt10() {
        return ct10;
    }

    public void setCt10(int ct10) {
        this.ct10 = ct10;
    }

    public int getCt11() {
        return ct11;
    }

    public void setCt11(int ct11) {
        this.ct11 = ct11;
    }

    public int getCt12() {
        return ct12;
    }

    public void setCt12(int ct12) {
        this.ct12 = ct12;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
