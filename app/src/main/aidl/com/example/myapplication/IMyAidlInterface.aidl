// IMyAidlInterface.aidl
package com.example.myapplication;
import com.example.myapplication.model.NhanVien;
// Declare any non-default types here with import statements
interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    double cong(int a,int b);
    double tru(int a,int b);
    double nhan(int a,int b);
    double chia(int a,int b);
    int add(in Bundle object);
    List<NhanVien> getData(String sql);
    int getCount(String sql);
    int delete(String code);
}
