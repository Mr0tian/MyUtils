package com.example.daotest.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.R;
import com.example.daotest.util.ContinuousClickUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.daotest.util.ContinuousClickUtils.SHORT_TIME;

/**
 * @author tian on 2019/9/2
 */
public class ButtonActivity extends AppCompatActivity {

    @InjectView(R.id.btn_click)
    Button btnClick;

    private long lastTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btn_click)
    public void onViewClicked() {
        if (ContinuousClickUtils.twoClick(SHORT_TIME)){
            Toast.makeText(this,"点击到了",Toast.LENGTH_SHORT).show();
        }
    }
}
