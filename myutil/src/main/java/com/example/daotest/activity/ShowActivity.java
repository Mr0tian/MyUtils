package com.example.daotest.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.DaoUtil;
import com.example.daotest.R;
import com.example.daotest.bean.UserBean;
import com.example.daotest.dao.UserBeanDao;

import org.song.videoplayer.DemoQSVideoView;
import org.song.videoplayer.IVideoPlayer;
import org.song.videoplayer.PlayListener;
import org.song.videoplayer.QSVideo;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author tian on 2019/8/26
 */
public class ShowActivity extends AppCompatActivity {

    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.tv_user_password)
    TextView tvUserPassword;
    @InjectView(R.id.video_play)
    DemoQSVideoView videoPlay;

    private DaoUtil daoUtil = new DaoUtil();
    private UserBeanDao userBeanDao;

    //private String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    private String url = "http://cdn.hcloud.xshuai.com//upload/language_resource/30e946be-a3fb-4407-b104-034ec0c1ae09.mov";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.inject(this);
        userBeanDao = daoUtil.getUserBeanDao();

        UserBean userBean = userBeanDao.load((long) 10086);
        if (userBean != null) {
            tvUserName.setText(userBean.getName());
            tvUserPassword.setText(userBean.getPassword());
        }
        initVideo();
    }

    private void initVideo() {
        //设置标题
        //videoPlay.setUp(url, "这是我的视频标题");

        //设置多个清晰度
        videoPlay.setUp(QSVideo.Build(url).title("这是标清标题").definition("标清").resolution("标清 720P").build(),
                QSVideo.Build(url).title("这是高清标题").definition("高清").resolution("高清 1080P").build());
        //封面
        //videoPlay.getCoverImageView().setImageResource(R.mipmap.ic_launcher);

        //设置监听
        videoPlay.setPlayListener(new PlayListener() {
            @Override
            public void onStatus(int status) {
                //播放器的ui状态
                if (status == IVideoPlayer.STATE_AUTO_COMPLETE){

                    videoPlay.quitWindowFullscreen();//播放完成退出全屏
                }
            }

            @Override
            public void onMode(int mode) {
                //全屏/普通/浮窗..
            }

            @Override
            public void onEvent(int what, Integer... extra) {
                //播放事件 初始化完成/缓冲/出错/点击事件..
            }
        });
        //进入全屏的模式 0横屏 1竖屏 2传感器自动横竖屏 3根据视频比例自动确定横竖屏      -1什么都不做
        videoPlay.enterFullMode=3;
        videoPlay.play();


    }
}
