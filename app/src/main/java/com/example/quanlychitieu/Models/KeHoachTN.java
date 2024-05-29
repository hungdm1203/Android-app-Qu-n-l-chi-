package com.example.quanlychitieu.Models;

public class KeHoachTN {
    private int id;
    private String tk;
    private int tn1,tn2,tn3,tn4,tn5,tn6,sum;
    private String month;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public KeHoachTN(int id, String tk, int tn1, int tn2, int tn3, int tn4, int tn5, int tn6, String month) {
        this.id = id;
        this.tk = tk;
        this.tn1 = tn1;
        this.tn2 = tn2;
        this.tn3 = tn3;
        this.tn4 = tn4;
        this.tn5 = tn5;
        this.tn6 = tn6;
        this.month = month;
        this.sum = tn1 + tn2 + tn3 + tn4 + tn5 + tn6;
    }
    public KeHoachTN(String tk, int tn1, int tn2, int tn3, int tn4, int tn5, int tn6, String month) {
        this.tk = tk;
        this.tn1 = tn1;
        this.tn2 = tn2;
        this.tn3 = tn3;
        this.tn4 = tn4;
        this.tn5 = tn5;
        this.tn6 = tn6;
        this.month = month;
        this.sum = tn1 + tn2 + tn3 + tn4 + tn5 + tn6;
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

    public int getTn1() {
        return tn1;
    }

    public void setTn1(int tn1) {
        this.tn1 = tn1;
    }

    public int getTn2() {
        return tn2;
    }

    public void setTn2(int tn2) {
        this.tn2 = tn2;
    }

    public int getTn3() {
        return tn3;
    }

    public void setTn3(int tn3) {
        this.tn3 = tn3;
    }

    public int getTn4() {
        return tn4;
    }

    public void setTn4(int tn4) {
        this.tn4 = tn4;
    }

    public int getTn5() {
        return tn5;
    }

    public void setTn5(int tn5) {
        this.tn5 = tn5;
    }

    public int getTn6() {
        return tn6;
    }

    public void setTn6(int tn6) {
        this.tn6 = tn6;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
