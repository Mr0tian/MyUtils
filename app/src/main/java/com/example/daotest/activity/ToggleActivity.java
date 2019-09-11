package com.example.daotest.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author tian on 2019/9/5
 */
public class ToggleActivity extends AppCompatActivity {
    @InjectView(R.id.toggle)
    ToggleButton toggle;
    @InjectView(R.id.switcher)
    Switch switcher;
    @InjectView(R.id.test)
    LinearLayout test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_button);
        ButterKnife.inject(this);

        //lamder 表达式
        CompoundButton.OnCheckedChangeListener listener = ((button, isChecked) -> {
            if (isChecked){
                //设置LinearLayout 垂直布局
                test.setOrientation(LinearLayout.VERTICAL);
                toggle.setChecked(true);
                switcher.setChecked(true);
            }else {
                //设置LinearLayout水平布局
                test.setOrientation(LinearLayout.HORIZONTAL);
                toggle.setChecked(false);
                switcher.setChecked(false);
            }

        });
        toggle.setOnCheckedChangeListener(listener);
        switcher.setOnCheckedChangeListener(listener);
    }



}
