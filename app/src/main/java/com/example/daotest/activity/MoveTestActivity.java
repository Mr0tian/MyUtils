package com.example.daotest.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.util.ShakeDetector;

/**
 * @author tian on 2019/9/2
 */

public class MoveTestActivity extends AppCompatActivity implements ShakeDetector.onShakeListener{

    ShakeDetector shakeDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shakeDetector = new ShakeDetector(this);
        shakeDetector.setOnShakeListener(this);
        shakeDetector.registerListener();
    }

    @Override
    public void onShake() {
        Log.i("TAG","动了");
        Toast.makeText(this,"动了",Toast.LENGTH_SHORT).show();
    }
}
