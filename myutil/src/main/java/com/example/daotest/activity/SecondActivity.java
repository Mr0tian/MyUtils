package com.example.daotest.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.R;
import com.example.daotest.bean.Msg;
import com.example.daotest.util.CircularAnim;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author tian on 2019/9/3
 */
public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        //this.startService();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ButterKnife.inject(this);


    }

    /**
     * eventBus消息订阅
     */
    @SuppressLint("SetTextI18n")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void receiveMessage(Msg msg) {
        Log.i("TAG", "receive: " + msg.from);

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
