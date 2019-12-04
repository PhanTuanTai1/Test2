package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etA,etB;
    ListView listView;
    ArrayAdapter<NhanVien> adt;
    ArrayList<NhanVien> listNhanVien;
    DbContext db = new DbContext(this);
    private IMyAidlInterface iMyAidlInterface;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etA  = findViewById(R.id.etA);
        etB = findViewById(R.id.etB);
        listView = findViewById(R.id.listView);
        Connect();
    }

    public void LoadList(View v) {
        try{
            if(iMyAidlInterface == null) Connect();
            int result = iMyAidlInterface.getCount("select * from NhanVien");
            listNhanVien = new ArrayList<>();
            for (NhanVien nv : iMyAidlInterface.getData("select * from NhanVien")) {
                listNhanVien.add(nv);
            }
            adt = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listNhanVien);
            listView.setAdapter(adt);

        }catch (Exception e){
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void LoadListLocal() {
        try{
            if(iMyAidlInterface == null) Connect();
            int result = iMyAidlInterface.getCount("select * from NhanVien");
            //Toast.makeText(this, iMyAidlInterface + "", Toast.LENGTH_SHORT).show();
            listNhanVien = new ArrayList<>();
            for (NhanVien nv : iMyAidlInterface.getData("select * from NhanVien")) {
                listNhanVien.add(nv);
            }
            adt = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listNhanVien);
            listView.setAdapter(adt);

        }catch (Exception e){
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void Connect(){
        Intent intent = new Intent("com.example.myapplication.ImplementService.BIND");
        intent.setPackage("com.example.myapplication");
        boolean isConnected = bindService(intent,connection, Context.BIND_AUTO_CREATE);
        if(isConnected){
            Toast.makeText(this, "Service is connected", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, iMyAidlInterface + "", Toast.LENGTH_SHORT).show();
        }
    }
    public void add(View v){
        int manv = Integer.parseInt(etA.getText().toString());
        String tennv = etB.getText().toString();

        try{
            NhanVien nv = new NhanVien();
            nv.setMaNV(manv);
            nv.setTenNV(tennv);
            Bundle b = new Bundle();
            b.putParcelable("nv",nv);
            int result = iMyAidlInterface.add(b);
            if(result > 0) Toast.makeText(this, result + "", Toast.LENGTH_SHORT).show();
            //double result = iMyAidlInterface.cong(a,b);
            //Toast.makeText(this, result + "", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
