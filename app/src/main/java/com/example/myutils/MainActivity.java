package com.example.myutils;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.ListView;

import com.example.permission.util.PermissionUtils;
import com.example.voice.adapter.VoiceAdapter;
import com.example.voice.bean.Voice;
import com.example.voice.wight.RecordVoiceButton;

public class MainActivity extends AppCompatActivity {

    ListView lv;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        buttonRec = findViewById(R.id.button_rec);

        PermissionUtils.checkPermissionArray(this, permissionArray, 2);
        adapter=new VoiceAdapter(this);
        lv.setAdapter(adapter);

        buttonRec.setEnrecordVoiceListener((length, strLength, filePath) ->
                adapter.add(new Voice(length,strLength,filePath)));

    }

}
