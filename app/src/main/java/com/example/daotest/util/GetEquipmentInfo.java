package com.example.daotest.util;

import android.os.Build;

/**
 * @author tian on 2019/9/2
 */
public class GetEquipmentInfo {

    String brand,model,androidVersion,romname,romversion,sign,sdk;
    String device,product,cpu,board,display,id,version_codes_base,maker,user,tags;
    String hardware,host,unknown,type,time,radio,serial,cpu2;
    GetEquipmentInfo(){
        product = "产品 : " + Build.PRODUCT;
        cpu = "CPU_ABI : " + Build.CPU_ABI;
        tags = "标签 : " + Build.TAGS;
        version_codes_base = "VERSION_CODES.BASE : " + Build.VERSION_CODES.BASE;
        model = "型号 : " + Build.MODEL;
        sdk = "SDK : " + Build.VERSION.SDK;
        androidVersion = "Android版本 : " + Build.VERSION.RELEASE;
        device = "驱动 : " + Build.DEVICE;
        display = "DISPLAY : " + Build.DISPLAY;
        brand = "品牌 : " + Build.BRAND;
        board = "基板 : " + Build.BOARD;
        sign = "设备信息 : " + Build.FINGERPRINT;
        id = "版本号 : " + Build.ID;
        maker = "制造商 : " + Build.MANUFACTURER;
        user = "用户 : " + Build.USER;

        cpu2 = "CPU_ABI2 : " + Build.CPU_ABI2;
        hardware = "硬件 : " + Build.HARDWARE;
        host = "主机地址 : " + Build.HOST;
        unknown = "未知信息 : " + Build.UNKNOWN;
        type = "版本类型 : " + Build.TYPE;
        time = "时间 : " + String.valueOf(Build.TIME);
        radio = "Radio : " + Build.RADIO;
        serial = "序列号 : " + Build.SERIAL;


    }

}
