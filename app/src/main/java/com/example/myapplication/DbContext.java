package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.example.myapplication.model.NhanVien;

import java.util.ArrayList;

public class DbContext extends SQLiteOpenHelper {

    public DbContext(@Nullable Context context) {
        super(context, "QLNhanVien.sqlite", null, 1);
    }
    public void createTable(SQLiteDatabase db){
        String sql = "Create Table IF NOT EXISTS NhanVien(maNV int,tenNV nvarchar(50), PRIMARY KEY (maNV))";
        db.execSQL(sql);
    }
    public ArrayList<NhanVien> getData(String sql){
        return getDataTest(sql);
    }
    public  int getCount(String sql){
        return getDataTest(sql).size();
    }
    public int insert(NhanVien nv){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("maNV",nv.getMaNV());
        content.put("tenNV",nv.getTenNV());
        return ((int) db.insert("NhanVien", null, content));
        //db.close();
    }
    public ArrayList<NhanVien> getDataTest(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);
        ArrayList<NhanVien> nhanViens = new ArrayList<>();
        while(c.moveToNext()){
            NhanVien nv = new NhanVien();
            nv.setMaNV(c.getInt(c.getColumnIndex("maNV")));
            nv.setTenNV(c.getString(c.getColumnIndex("tenNV")));
            nhanViens.add(nv);
        }
        return nhanViens;
    }
    public int delete(String code){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("NhanVien","maNV =" + code,null);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
