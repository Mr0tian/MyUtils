package com.example.daotest;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.example.daotest.dao.DaoMaster;
import com.example.daotest.util.PermissionUtils;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

/**
 * @author tian on 2019/8/26
 */
public class App extends Application {

    public static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();

        /*android7之后,相机崩溃问题修复*/
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        mContext = getApplicationContext();

        //GreenDao的初始化
        GreenDaoManager.getInstance();

    }

    public static Context getmAppContext(){

        return mContext;

    }
}
