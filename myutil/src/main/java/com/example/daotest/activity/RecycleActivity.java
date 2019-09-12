package com.example.daotest.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daotest.R;
import com.example.daotest.adapter.MyAdapter;
import com.example.daotest.bean.OrderData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author tian on 2019/8/27
 */
public class RecycleActivity extends AppCompatActivity {

    String s = "{\"data\":[{\"id\":\"SX9999999610\",\"attrName\":\"购买人信息\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":6,\"valueTypeString\":\"Other\",\"attrValueList\":null,\"isMust\":false,\"inputMin\":0,\"inputMax\":30,\"customControlsName\":\"IdCardRecognition\",\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"PolicyHolderInfoTab\",\"attrGroupId\":\"DT0000000275\",\"attrGroupName\":\"个人信息\"},{\"id\":\"SX9999999645\",\"attrName\":\"扫描行驶证\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":6,\"valueTypeString\":\"Other\",\"attrValueList\":null,\"isMust\":false,\"inputMin\":0,\"inputMax\":30,\"customControlsName\":\"DrivingLicenseRecognition\",\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"ScanAutoFillInfoHidden\",\"attrGroupId\":\"DT0000000276\",\"attrGroupName\":\"车辆信息\"},{\"id\":\"SX9999999600\",\"attrName\":\"购车价格\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":6,\"valueTypeString\":\"Other\",\"attrValueList\":null,\"isMust\":true,\"inputMin\":0,\"inputMax\":30,\"customControlsName\":\"Price\",\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"CarPrices\",\"attrGroupId\":\"DT0000000276\",\"attrGroupName\":\"车辆信息\"},{\"id\":\"SX9999999652\",\"attrName\":\"投保人\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":1,\"valueTypeString\":\"Input\",\"attrValueList\":null,\"isMust\":true,\"inputMin\":0,\"inputMax\":20,\"customControlsName\":null,\"patternCondition\":\"\",\"patternDesc\":null,\"attrKeyName\":\"PolicyHolderName\",\"attrGroupId\":\"DT0000000275\",\"attrGroupName\":\"个人信息\"},{\"id\":\"SX9999999643\",\"attrName\":\"购车时间\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":6,\"valueTypeString\":\"Other\",\"attrValueList\":null,\"isMust\":true,\"inputMin\":0,\"inputMax\":30,\"customControlsName\":\"DateTimePicker\",\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"PurchaseTime\",\"attrGroupId\":\"DT0000000276\",\"attrGroupName\":\"车辆信息\"},{\"id\":\"SX9999999651\",\"attrName\":\"联系电话\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":1,\"valueTypeString\":\"Input\",\"attrValueList\":null,\"isMust\":true,\"inputMin\":0,\"inputMax\":11,\"customControlsName\":null,\"patternCondition\":\"^1(2|3|4|5|7|8|9)\\\\d{9}$\",\"patternDesc\":null,\"attrKeyName\":\"TelForPolicyHolder\",\"attrGroupId\":\"DT0000000275\",\"attrGroupName\":\"个人信息\"},{\"id\":\"SX9999999606\",\"attrName\":\"车型\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":6,\"valueTypeString\":\"Other\",\"attrValueList\":null,\"isMust\":true,\"inputMin\":0,\"inputMax\":30,\"customControlsName\":\"SelectCar\",\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"VehicleModel\",\"attrGroupId\":\"DT0000000276\",\"attrGroupName\":\"车辆信息\"},{\"id\":\"SX9999999650\",\"attrName\":\"证件类型\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":3,\"valueTypeString\":\"SelectSingle\",\"attrValueList\":[\"身份证\",\"统一社会信用代码\"],\"isMust\":true,\"inputMin\":0,\"inputMax\":30,\"customControlsName\":null,\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"IdTypeForPolicyHolder\",\"attrGroupId\":\"DT0000000275\",\"attrGroupName\":\"个人信息\"},{\"id\":\"SX9999999599\",\"attrName\":\"车牌号码\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":6,\"valueTypeString\":\"Other\",\"attrValueList\":null,\"isMust\":false,\"inputMin\":0,\"inputMax\":30,\"customControlsName\":\"CarNumber\",\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"PlateNumber\",\"attrGroupId\":\"DT0000000276\",\"attrGroupName\":\"车辆信息\"},{\"id\":\"SX9999999649\",\"attrName\":\"证件号码\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":1,\"valueTypeString\":\"Input\",\"attrValueList\":null,\"isMust\":true,\"inputMin\":0,\"inputMax\":18,\"customControlsName\":null,\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"IdNumberForPolicyHolder\",\"attrGroupId\":\"DT0000000275\",\"attrGroupName\":\"个人信息\"},{\"id\":\"SX9999999598\",\"attrName\":\"车架号码\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":6,\"valueTypeString\":\"Other\",\"attrValueList\":[\"http://zhichepinhz.oss-cn-hangzhou.aliyuncs.com/zcs/XSZ.png\"],\"isMust\":true,\"inputMin\":0,\"inputMax\":17,\"customControlsName\":\"InputImg\",\"patternCondition\":\"^[A-Za-z0-9]{0,17}$\",\"patternDesc\":\"车架号只能由数字字母组成\",\"attrKeyName\":\"FrameNumber\",\"attrGroupId\":\"DT0000000276\",\"attrGroupName\":\"车辆信息\"},{\"id\":\"SX9999999608\",\"attrName\":\"车主信息\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":6,\"valueTypeString\":\"Other\",\"attrValueList\":null,\"isMust\":false,\"inputMin\":0,\"inputMax\":30,\"customControlsName\":\"IdCardRecognition\",\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"OwnerInfoTab\",\"attrGroupId\":\"DT0000000275\",\"attrGroupName\":\"个人信息\"},{\"id\":\"SX9999999597\",\"attrName\":\"发动机号\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":6,\"valueTypeString\":\"Other\",\"attrValueList\":[\"http://zhichepinhz.oss-cn-hangzhou.aliyuncs.com/zcs/XSZ.png\"],\"isMust\":true,\"inputMin\":0,\"inputMax\":20,\"customControlsName\":\"InputImg\",\"patternCondition\":\"^[A-Za-z0-9]{0,20}$\",\"patternDesc\":\"发动机号只能由数字或字母组成\",\"attrKeyName\":\"EngineNo\",\"attrGroupId\":\"DT0000000276\",\"attrGroupName\":\"车辆信息\"},{\"id\":\"SX9999999647\",\"attrName\":\"车主为购买人\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":2,\"valueTypeString\":\"Switch\",\"attrValueList\":[\"OwnerName\",\"TelForOwner\",\"IdTypeForOwner\",\"IdNumberForOwner\"],\"isMust\":true,\"inputMin\":0,\"inputMax\":30,\"customControlsName\":null,\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"OwnerAsPolicyHolder\",\"attrGroupId\":\"DT0000000275\",\"attrGroupName\":\"个人信息\"},{\"id\":\"SX9999999638\",\"attrName\":\"行驶证注册时间\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":6,\"valueTypeString\":\"Other\",\"attrValueList\":null,\"isMust\":false,\"inputMin\":0,\"inputMax\":30,\"customControlsName\":\"DateTimePicker\",\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"LicenseRegistrationTime\",\"attrGroupId\":\"DT0000000276\",\"attrGroupName\":\"车辆信息\"},{\"id\":\"SX9999999646\",\"attrName\":\"车主姓名\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":1,\"valueTypeString\":\"Input\",\"attrValueList\":null,\"isMust\":true,\"inputMin\":0,\"inputMax\":20,\"customControlsName\":null,\"patternCondition\":\"\",\"patternDesc\":null,\"attrKeyName\":\"OwnerName\",\"attrGroupId\":\"DT0000000275\",\"attrGroupName\":\"个人信息\"},{\"id\":\"SX9999999614\",\"attrName\":\"联系电话\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":1,\"valueTypeString\":\"Input\",\"attrValueList\":null,\"isMust\":true,\"inputMin\":0,\"inputMax\":11,\"customControlsName\":null,\"patternCondition\":\"^1(2|3|4|5|7|8|9)\\\\d{9}$\",\"patternDesc\":null,\"attrKeyName\":\"TelForOwner\",\"attrGroupId\":\"DT0000000275\",\"attrGroupName\":\"个人信息\"},{\"id\":\"SX9999999613\",\"attrName\":\"证件类型\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":3,\"valueTypeString\":\"SelectSingle\",\"attrValueList\":[\"身份证\",\"统一社会信用代码\"],\"isMust\":true,\"inputMin\":0,\"inputMax\":30,\"customControlsName\":\"Picker\",\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"IdTypeForOwner\",\"attrGroupId\":\"DT0000000275\",\"attrGroupName\":\"个人信息\"},{\"id\":\"SX9999999612\",\"attrName\":\"证件号码\",\"classId\":null,\"classCode\":\"001015\",\"attrClass\":5,\"valueType\":1,\"valueTypeString\":\"Input\",\"attrValueList\":null,\"isMust\":true,\"inputMin\":0,\"inputMax\":18,\"customControlsName\":null,\"patternCondition\":null,\"patternDesc\":null,\"attrKeyName\":\"IdNumberForOwner\",\"attrGroupId\":\"DT0000000275\",\"attrGroupName\":\"个人信息\"}]}";

    List<OrderData.DataBean> list;
    Map<String, List<OrderData.DataBean>> listMap;
    List<OrderData.DataBean> adpterList;
    Gson gson = new Gson();
    @InjectView(R.id.recycle_view)
    RecyclerView recycleView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        ButterKnife.inject(this);

        initDate();

        Log.i("TAG", listMap.toString());
        initView();

        initRecycleView();

    }



    private void initView() {

        adpterList = new ArrayList<>();
        for (OrderData.DataBean dataBean : listMap.get("个人信息")) {
            adpterList.add(dataBean);
        }
       /* for (String key : listMap.keySet()) {
            for (OrderData.DataBean dataBean : listMap.get(key)) {
                adpterList.add(dataBean);
            }
        }*/
        String s = gson.toJson(adpterList);
        Log.i("TAG", s);



    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(this,adpterList);
        recycleView.setAdapter(adapter );
    }

    private void initDate() {

        OrderData orderData = gson.fromJson(s, OrderData.class);
        list = orderData.getData();
        listMap = new HashMap<>();

        for (OrderData.DataBean data : list) {
            List<OrderData.DataBean> tempList = listMap.get(data.getAttrGroupName());
            /*如果取不到数据,那么直接new一个空的ArrayList**/
            if (tempList == null) {
                tempList = new ArrayList<>();
                tempList.add(data);
                listMap.put(data.getAttrGroupName(), tempList);
            } else {
                /*某个sku之前已经存放过了,则直接追加数据到原来的List里**/
                tempList.add(data);
            }
        }
    }
}
