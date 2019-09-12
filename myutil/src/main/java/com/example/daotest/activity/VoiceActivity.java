package com.example.daotest.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.R;
import com.example.daotest.adapter.VoiceAdapter;
import com.example.daotest.bean.Voice;
import com.example.daotest.util.PermissionUtils;
import com.example.daotest.wight.RecordVoiceButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author tian on 2019/9/6
 */
public class VoiceActivity extends AppCompatActivity {

    /**
     * 语音列表
     */
    @InjectView(R.id.lv)
    ListView lv;
    /**
     * 开始录音
     */
    @InjectView(R.id.button_rec)
    RecordVoiceButton buttonRec;

    private VoiceAdapter adapter;


    /**
     * 权限所需
     */
    private String[] permissionArray = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        ButterKnife.inject(this);
        PermissionUtils.checkPermissionArray(this, permissionArray, 2);
        adapter=new VoiceAdapter(this);
        lv.setAdapter(adapter);

        buttonRec.setEnrecordVoiceListener((length, strLength, filePath) ->
                adapter.add(new Voice(length,strLength,filePath)));

    }


}
