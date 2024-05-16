package com.example.quanlychitieu.Models;

public class Reminder {
    private int id;
    private String tk,time,note;
    private int status;

    public Reminder(String tk, String time, String note) {
        this.tk = tk;
        this.time = time;
        this.note = note;
        this.status=1;
    }

    public Reminder(int id, String tk, String time, String note, int status) {
        this.id = id;
        this.tk = tk;
        this.time = time;
        this.note = note;
        this.status=status;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
