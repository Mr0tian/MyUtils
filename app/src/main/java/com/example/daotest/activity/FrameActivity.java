package com.example.daotest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author tian on 2019/9/4
 */
public class FrameActivity extends AppCompatActivity {

    int[] names = new int[]{R.id.view01,R.id.view02,R.id.view03,R.id.view04,R.id.view05,R.id.view06};

    TextView[] views = new TextView[names.length];


    class MyHandler extends Handler {
        private WeakReference<FrameActivity> activity;
        public MyHandler(WeakReference<FrameActivity> activity){
            this.activity = activity;
        }

        private int currentColor = 0;
        //定义一个颜色数组
        int[] colors = new int[]{R.color.blackColor,R.color.colorAccent,R.color.colorMain,R.color.colorPrimary,R.color.colorPrimaryDark,R.color.whiteColor};

        @Override
        public void handleMessage(@NonNull Message msg) {
            //表面消息来自本程序所发送
            if (msg.what == 0x123){
                for (int i=0,len = activity.get().names.length;i<len;i++){
                    activity.get().views[i].setBackgroundResource(colors[(i+currentColor) % colors.length]);
                }
                currentColor++;
            }
            super.handleMessage(msg);
        }
    }

    private MyHandler myHandler = new MyHandler(new WeakReference<>(this));
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        for (int i= 0; i<names.length;i++){
            views[i] = findViewById(names[i]);
        }
        //定义一个线性周期性改变currentColor变量值
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //发送一条空消息通知系统改变6个TextView组件的颜色
                myHandler.sendEmptyMessage(0x123);
            }
        },0,200);

    }
}
