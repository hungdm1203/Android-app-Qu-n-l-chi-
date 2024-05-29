package com.example.quanlychitieu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.quanlychitieu.Models.Account;
import com.example.quanlychitieu.Models.KeHoachTN;
import com.example.quanlychitieu.Models.KeHoachCT;
import com.example.quanlychitieu.Models.Money;
import com.example.quanlychitieu.Models.Reminder;

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


    //them ke hoach chi tieu
    public void InsertKeHoachCT(KeHoachCT kh){
        String sql = "INSERT INTO khChiTieu(tk,ct1,ct2,ct3,ct4,ct5,ct6,ct7,ct8,ct9,ct10,ct11,ct12,month) VALUES('"+kh.getTk()+"','"+kh.getCt1() + "','" + kh.getCt2() + "','" + kh.getCt3() + "','" + kh.getCt4() + "','" + kh.getCt5() + "','" + kh.getCt6() + "','" + kh.getCt7() + "','" + kh.getCt8() + "','" + kh.getCt9() + "','" + kh.getCt10() + "','" + kh.getCt11() + "','" + kh.getCt12() + "','"+kh.getMonth()+"')";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void UpdateKeHoachCT(KeHoachCT kh) {
        String sql="UPDATE khChiTieu SET ct1='"+kh.getCt1()+"', ct2='"+kh.getCt2()+"', ct3='"+kh.getCt3()+"', ct4='"+kh.getCt4()+"'"
                + ", ct5='" + kh.getCt5() + "', ct6='" + kh.getCt6() + "', ct7='" + kh.getCt7() + "', ct8='" + kh.getCt8() + "',"
                +"ct9='" + kh.getCt9() + "', ct10='" + kh.getCt10() + "', ct11='" + kh.getCt11() + "', ct12='" + kh.getCt12() + "' WHERE month='" + kh.getMonth() + "' AND tk='" + kh.getTk() + "'";
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }

    //them ke hoach thu nhap
    public void InsertKeHoachTN(KeHoachTN kh){
        String sql= "INSERT INTO khThuNhap(tk,tn1,tn2,tn3,tn4,tn5,tn6,month) VALUES('" + kh.getTk() + "','" + kh.getTn1() + "','" + kh.getTn2() + "','" + kh.getTn3() + "','" + kh.getTn4() + "','" + kh.getTn5() + "','" + kh.getTn6() + "','" + kh.getMonth() + "')";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void UpdateKeHoachTN(KeHoachTN kh) {
        String sql="UPDATE khThuNhap SET tn1='"+kh.getTn1()+"', tn2='"+kh.getTn2()+"', tn3='"+kh.getTn3()+"', tn4='"+kh.getTn4()+"',"
                + "tn5='" + kh.getTn5() + "', tn6='" + kh.getTn6() + "'"
                +" WHERE month='" + kh.getMonth() + "' AND tk='" + kh.getTk() + "'";
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

