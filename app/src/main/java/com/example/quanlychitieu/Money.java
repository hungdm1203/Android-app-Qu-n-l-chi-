package com.example.quanlychitieu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Money implements Comparable<Money> {
    private int id;
    private String tk, typePrice, type, date, date2;
    private int price;
    private String note;


    public Money(int id, String tk, String typePrice, String type, String date, int price, String note) {
        this.id = id;
        this.tk = tk;
        this.type = type;
        this.note = note;
        this.date = date;
        this.date2 = customDate(this.date);
        this.typePrice = typePrice;
        this.price = price;
    }

    private String customDate(String s) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = sdf.parse(s);
            SimpleDateFormat res = new SimpleDateFormat("EEEE, dd/MM/yyyy", Locale.getDefault());
            String resultDateString = res.format(date);
            return resultDateString;
        } catch (Exception e) {
        }
        return null;
    }

    public int compareTo(Money m) {
        Date d1,d2;
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        try {
            d1=sdf.parse(this.date);
            d2=sdf.parse(m.date);
            return d1.compareTo(d2);

        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        this.date2=customDate(this.date);
    }

    public String getDate2() {
        return date2;
    }


    public String getTypePrice() {
        return typePrice;
    }

    public void setTypePrice(String typePrice) {
        this.typePrice = typePrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
