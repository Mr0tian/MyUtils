package com.example.daotest.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.R;
import com.example.daotest.util.PictureUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * @author tian on 2019/8/27
 */
public class ImgSelectActivity extends AppCompatActivity {

    @InjectView(R.id.img_click)
    ImageView imgClick;

    private static final int REQUEST_IMAGE = 0X12;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_img);
        ButterKnife.inject(this);


    }

    @OnClick(R.id.img_click)
    public void onViewClicked() {

        //打开多图上传控件
        MultiImageSelector.create(getApplicationContext())
                .showCamera(true)
                .count(9)
                .multi()
                .start(this,REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE){
            if (resultCode == RESULT_OK){
                //获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (path.size() >0){
                    Bitmap bitmap = PictureUtil.getCompressImageFromPath(path.get(0));
                    imgClick.setImageBitmap(bitmap);
                }
            }
        }
    }
}
