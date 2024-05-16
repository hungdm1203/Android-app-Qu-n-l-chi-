package com.example.quanlychitieu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DatabaseSQLite extends SQLiteOpenHelper {
    public DatabaseSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //truy van khong tra ve ket qua
    public void QueryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }


    //truy van tra ve ket qua
    public Cursor GetData(String sql){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    //them du lieu hinh anh voi mang byte[]
    public  void InsertIMG(String tk, byte[] img){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String sql="UPDATE account SET hinhanh=? WHERE tk=?";
        SQLiteStatement sqLiteStatement= sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(2,tk);
        sqLiteStatement.bindBlob(1,img);

        sqLiteStatement.executeInsert();
    }



    //them 1 nguoi dung moi
    public void InsertUser(Account acc){
        String sql="INSERT INTO account (tk,mk) VALUES('"+acc.getTk()+"','"+acc.getMk()+"')";
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }


    // them 1 khoan chi tieu
    public void InsertMoney(Money m) {
        String sql="INSERT INTO money(tk,typePrice,type,date,price,note) VALUES('"+m.getTk()+"','"+m.getTypePrice()+"','"+m.getType()+"','"+m.getDate()+"','"+m.getPrice()+"','"+m.getNote()+"')";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //them moi loi nhac
    public void InsertReminder(Reminder r){
        String sql = "INSERT INTO reminder(tk,time,note,status) VALUES('"+r.getTk()+"','"+r.getTime()+"','"+r.getNote()+"','"+r.getStatus()+"')";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

