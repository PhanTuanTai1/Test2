package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.myapplication.model.NhanVien;

import java.util.ArrayList;

public class SerivceUnBound extends Service {
    DbContext db;
    ArrayAdapter<NhanVien> adt;
    ListView lst;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
