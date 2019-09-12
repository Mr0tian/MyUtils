package com.example.daotest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.R;
import com.example.daotest.bean.Msg;
import com.example.daotest.util.CircularAnim;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author tian on 2019/9/3
 */
public class AnimActivity extends AppCompatActivity {


    @InjectView(R.id.btn_hint)
    Button btnHint;

    @InjectView(R.id.btn_show)
    Button btnShow;
    @InjectView(R.id.img_show)
    ImageView imgShow;
    @InjectView(R.id.btn_change)
    Button btnChange;
    @InjectView(R.id.btn_new)
    Button btnNew;

    private boolean show = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.btn_hint, R.id.btn_show, R.id.btn_change,R.id.btn_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_hint:
                CircularAnim.hide(btnHint).go();
                break;
            case R.id.btn_show:
                CircularAnim.show(btnHint).go();
                break;
            case R.id.btn_change:
                if (show) {
                    CircularAnim.show(imgShow).triggerView(btnChange).go();
                } else {
                    CircularAnim.hide(imgShow).triggerView(btnChange).go();

                }
                show = !show;
                break;

            case R.id.btn_new:
                Msg msg = new Msg();
                msg.text = "呵呵哒";
                //跨activity通信用sticky 接收的时候要将sticky设置为true
                EventBus.getDefault().postSticky(msg);
                startActivity(new Intent(AnimActivity.this, SecondActivity.class));
             /*   //水波般铺满指定颜色并启动一个Activity:
                CircularAnim.fullActivity(AnimActivity.this, btnNew)
                        .colorOrImageRes(R.color.colorPrimary)

                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {


                            }
                        });
*/
                break;
        }


    }


}
