package com.example.voice.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.voice.R;
import com.example.voice.base.BaseCommonAdapter;
import com.example.voice.bean.Voice;
import com.example.voice.util.ViewHolderUtil;
import com.example.voice.util.VoiceManager;

/**
 * @author tian on 2019/9/11
 */
public class VoiceAdapter extends BaseCommonAdapter<Voice> {

    // 语音动画
    private AnimationDrawable voiceAnimation;
    private VoiceManager voiceManager;
    private int lastPosition = -1;

    public VoiceAdapter(Context context) {
        super(context);
        voiceManager =  VoiceManager.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_voice_list, parent, false);
        }
        final ImageView ivVoic = ViewHolderUtil.get(convertView, R.id.iv_voice);
        final LinearLayout llRoot = ViewHolderUtil.get(convertView, R.id.ll_root);
        TextView tvLength = ViewHolderUtil.get(convertView, R.id.tv_length);
        final Voice voice = getData().get(position);
        tvLength.setText(voice.getStrLength());
        //播放
        llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceAnimation != null) {
                    voiceAnimation.stop();
                    voiceAnimation.selectDrawable(0);
                }
                if (voiceManager.isPlaying()&&lastPosition == position) {
                    voiceManager.stopPlay();
                }else{
                    voiceManager.stopPlay();
                    voiceAnimation = (AnimationDrawable) ivVoic.getBackground();
                    voiceAnimation.start();
                    voiceManager.setVoicePlayListener(new VoiceManager.VoicePlayCallBack() {
                        @Override
                        public void voiceTotalLength(long time, String strTime) {

                        }

                        @Override
                        public void playDoing(long time, String strTime) {

                        }

                        @Override
                        public void playPause() {

                        }

                        @Override
                        public void playStart() {

                        }

                        @Override
                        public void playFinish() {
                            if (voiceAnimation != null) {
                                voiceAnimation.stop();
                                voiceAnimation.selectDrawable(0);
                            }
                        }
                    });
                    voiceManager.startPlay(voice.getFilePath());
                }
                lastPosition = position;

            }
        });

        return convertView;
    }
}
