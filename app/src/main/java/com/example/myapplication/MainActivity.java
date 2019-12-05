package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
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
    Context c;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = this;
        setContentView(R.layout.activity_main);
        etA  = findViewById(R.id.etA);
        etB = findViewById(R.id.etB);
        listView = findViewById(R.id.listView);
        Intent intent = new Intent("com.example.myapplication.ImplementService.BIND");
        intent.setPackage("com.example.myapplication");
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
                LoadListLocal(iMyAidlInterface);

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder b = new AlertDialog.Builder(c);
                pos = i;
                b.setMessage("Bạn có muốn xóa không ?");
                b.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int code = listNhanVien.get(pos).getMaNV();
                        try{
                            int result = iMyAidlInterface.delete(Integer.toString(code));
                            if(result > 0)
                                Toast.makeText(MainActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                                LoadListLocal(iMyAidlInterface);
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                b.show();
                return false;
            }
        });
        bindService(intent,connection,BIND_AUTO_CREATE);

    }
    public  void Test(){
        Intent intent = new Intent();
        intent.setClassName("com.example.myapplication","com.example.myapplication.ServiceUnbound");
        ComponentName a  = startService(intent);
        Toast.makeText(this,  a.toString(), Toast.LENGTH_SHORT).show();
    }
    public void LoadList(View v) {
        try{
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
    public void LoadListLocal(IMyAidlInterface iMyAidlInterface) {
        try{
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
