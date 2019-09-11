package com.example.daotest.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author tian on 2019/9/4
 */
public class ViewAcitivity extends AppCompatActivity {

    @InjectView(R.id.link)
    TextView link;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.inject(this);
        link.setSelected(true);
    }
}
