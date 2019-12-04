package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.example.myapplication.model.NhanVien;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ImplementService extends Service{
    DbContext db = new DbContext(this);
    private IMyAidlInterface.Stub service = new IMyAidlInterface.Stub() {
        @Override
        public double cong(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public double tru(int a, int b) throws RemoteException {
            return a - b;
        }

        @Override
        public double nhan(int a, int b) throws RemoteException {
            return a * b;
        }

        @Override
        public double chia(int a, int b) throws RemoteException {
            return a / b;
        }

        @Override
        public int add(Bundle object) throws RemoteException {
            object.setClassLoader(getClass().getClassLoader());
            NhanVien nv = object.getParcelable("nv");
            return db.insert(nv);
        }

        @Override
        public List<NhanVien> getData(String sql) throws RemoteException {

            return   db.getData(sql);

        }

        @Override
        public int getCount(String sql) throws RemoteException {
            return   db.getCount(sql);
        }


    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return service;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DbContext db = new DbContext(this);
        db.createTable(db.getWritableDatabase());
    }
}
