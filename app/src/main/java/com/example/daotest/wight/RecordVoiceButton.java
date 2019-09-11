package com.example.daotest.wight;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;


import com.example.daotest.R;
import com.example.daotest.util.VoiceManager;


/**
 * @author tian on 2019/9/6
 */
public class RecordVoiceButton extends AppCompatButton implements View.OnClickListener {

    private Dialog recordIndicator;
    private ImageView mVolumeIv,mIvPauseContinue,mIvComplete;
    private VoiceLineView voiceLineView;
    private TextView mRecorHintView;
    private Context mContext;
    private EnRecordVoiceListener enRecordVoiceListener;
    private VoiceManager voiceManager;


    public RecordVoiceButton(Context context) {
        super(context);
        init();
    }

    public RecordVoiceButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    public RecordVoiceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        voiceManager = VoiceManager.getInstance(mContext);
        setOnClickListener(this);
    }

    /**
     * 设置监听
     * @param enRecordVoiceListener
     */
    public void setEnrecordVoiceListener(EnRecordVoiceListener enRecordVoiceListener){
        this.enRecordVoiceListener = enRecordVoiceListener;
    }

    /**
     * 启动dialog
     */
    private void startRecordDialog(){
        recordIndicator = new Dialog(getContext(), R.style.record_voice_dialog);
        recordIndicator.setContentView(R.layout.dialog_record_voice);
        recordIndicator.setCanceledOnTouchOutside(false);
        recordIndicator.setCancelable(false);
        mVolumeIv = recordIndicator.findViewById(R.id.iv_voice);
        voiceLineView = recordIndicator.findViewById(R.id.voiceLine);
        mRecorHintView = recordIndicator.findViewById(R.id.tv_length);
        mRecorHintView.setText("00:00:00");
        mIvPauseContinue = recordIndicator.findViewById(R.id.iv_continue_or_pause);
        mIvComplete = recordIndicator.findViewById(R.id.iv_complete);
        recordIndicator.show();
        //暂停活继续
        mIvPauseContinue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voiceManager != null){
                    voiceManager.pauseOrStartVoiceRecord();
                }
            }
        });

        //完成
        mIvComplete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voiceManager != null){
                    voiceManager.stopVoiceRecord();
                }
                recordIndicator.dismiss();
            }
        });

    }





    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        startRecordDialog();
        voiceManager.setVoiceRecordListener(new VoiceManager.VoiceRecordCallBack() {
            @Override
            public void recDoing(long time, String strTime) {
                mRecorHintView.setText(strTime);
            }

            @Override
            public void recVoiceGrade(int grade) {
                voiceLineView.setVolume(grade);
            }

            @Override
            public void recStart(boolean init) {
                mIvPauseContinue.setImageResource(R.drawable.icon_pause);
                voiceLineView.setContinue();
            }

            @Override
            public void recPause(String str) {
                mIvPauseContinue.setImageResource(R.drawable.icon_continue);
                voiceLineView.setPause();
            }

            @Override
            public void recFinish(long length, String strLength, String path) {
                if (enRecordVoiceListener != null){
                    enRecordVoiceListener.onFinishRecord(length, strLength, path);
                }
            }

        });

        voiceManager.startVoiceRecord(Environment.getExternalStorageDirectory().getPath() + "/VoiceManager/audio");
    }

    /**
     * 结束回调监听
     */
    public interface EnRecordVoiceListener {
        void onFinishRecord(long length, String strLength, String filePath);
    }
}
