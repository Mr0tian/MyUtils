package com.example.voice.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author tian on 2019/9/6
 * 录音管理类（包括录音和播放）
 */
public class VoiceManager {

    private static volatile VoiceManager voiceManager;
    public final int MSG_TIME_INTERVAL = 100;
    /**
     * 多媒體,例如声音的状态
     */
    public final int MEDIA_STATE_UNDEFINE = 200;
    public final int MEDIA_STATE_RECORD_STOP = 210;
    public final int MEDIA_STATE_RECORD_DOING = 220;
    public final int MEDIA_STATE_RECORD_PAUSE = 230;
    public final int MEDIA_STATE_PLAY_STOP = 310;
    public final int MEDIA_STATE_PLAY_DOING = 320;
    public final int MEDIA_STATE_PLAY_PAUSE = 330;

    private ArrayList<File> mRecList = new ArrayList<>();
    private Context mContext = null;
    private SeekBar mSBPlayProgress;
    private int mSavedState, mDeviceState = MEDIA_STATE_UNDEFINE;
    private MediaRecorder mMediaRecorder = null;
    private MediaPlayer mMediaPlayer = null;
    private String mRecTimePrev;
    private long mRecTimeSum = 0;

    /**
     * 录音文件存放的位置(文件夹)
     */
    private String recordFilePath = "";

    /**
     * 播放音频文件位置
     */
    private String playFilePath;
    /**
     * 录音监听
     */
    private VoiceRecordCallBack voiceRecordCallBack;
    private ObtainDecibelThread mThread;

    /**
     * 播放监听
     */
    private VoicePlayCallBack voicePlayCallBack;

    private VoiceManager(Context context) {
        this.mContext = context;
    }

    /**
     * 获取单例
     *
     * @param context
     * @return
     */
    public static VoiceManager getInstance(Context context) {
        if (voiceManager == null) {
            //添加线程锁,保证所有线程中只有一个VoiceManger对象
            synchronized (VoiceManager.class) {
                if (voiceManager == null) {
                    voiceManager = new VoiceManager(context);
                }
            }
        }
        return voiceManager;
    }

    /**
     * 播放器结束监听
     */
    private MediaPlayer.OnCompletionListener mPlayCompetedListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mDeviceState = MEDIA_STATE_PLAY_STOP;
            mHandler.removeMessages(MSG_TIME_INTERVAL);
            mMediaPlayer.stop();
            mMediaPlayer.release();
            if (mSBPlayProgress != null) {
                mSBPlayProgress.setProgress(0);
            }
            if (voicePlayCallBack != null) {
                voicePlayCallBack.playFinish();
            }
        }
    };

    /**
     * 播放或录音handler
     */
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @SuppressLint("DefaultLocale")
        @Override
        public void handleMessage(Message msg) {
            VoiceTimeUtils ts;
            int current;
            try {
                //注意,在library中不能使用switch,需替换成if else
                if (msg.what == MSG_TIME_INTERVAL) {

                    //录音
                    if (mDeviceState == MEDIA_STATE_RECORD_DOING) {
                        ts = VoiceTimeUtils.timeSpanToNow(mRecTimePrev);
                        mRecTimeSum += ts.mDiffSecond;
                        mRecTimePrev = VoiceTimeUtils.getTimeStrFromMillis(ts.mNowTime);
                        ts = VoiceTimeUtils.timeSpanSecond(mRecTimeSum);
                        //回调录音时间
                        if (voiceRecordCallBack != null) {
                            voiceRecordCallBack.recDoing(mRecTimeSum, String.format("%02d:%02d:%02d", ts.mSpanHour, ts.mSpanMinute, ts.mSpanSecond));
                        }
                        mHandler.sendEmptyMessageDelayed(MSG_TIME_INTERVAL, 1000);
                    }
                    //播放
                    else if (mDeviceState == MEDIA_STATE_PLAY_DOING) {
                        current = mMediaPlayer.getCurrentPosition();
                        if (mSBPlayProgress != null) {
                            mSBPlayProgress.setProgress(current);
                        }
                        ts = VoiceTimeUtils.timeSpanSecond(current / 1000);
                        //回调播放进度
                        if (voicePlayCallBack != null) {
                            voicePlayCallBack.playDoing(current / 1000, String.format("%02d:%02d:%02d",
                                    ts.mSpanHour, ts.mSpanMinute, ts.mSpanSecond));
                        }
                        mHandler.sendEmptyMessageDelayed(MSG_TIME_INTERVAL, 1000);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    /*********************************录音操作begin***************************/

    /**
     * 录音监听
     *
     * @param callBack
     */
    public void setVoiceRecordListener(VoiceRecordCallBack callBack) {
        voiceRecordCallBack = callBack;
    }

    /**
     * 开始录音(外部调)
     *
     * @param filePath 音频存放文件夹
     */
    public void startVoiceRecord(String filePath) {

        if (!isSDCardAvailable()) {
            return;
        }
        this.recordFilePath = filePath;
        startVoiceRecord(true);

    }

    /**
     * 继续或暂停录音
     */
    public void pauseOrStartVoiceRecord() {
        if (mDeviceState == MEDIA_STATE_RECORD_DOING) {
            mDeviceState = MEDIA_STATE_RECORD_PAUSE;
            stopRecorder(mMediaRecorder, true);
            mMediaRecorder = null;
            voiceRecordCallBack.recPause("已暂停");
        } else {
            startVoiceRecord(false);
        }
    }

    /**
     * 完成录音
     */
    @SuppressLint("DefaultLocale")
    public void stopVoiceRecord() {
        try {
            mHandler.removeMessages(MSG_TIME_INTERVAL);
            mDeviceState = MEDIA_STATE_RECORD_STOP;
            stopRecorder(mMediaRecorder, true);
            mMediaRecorder = null;
            if (VoiceTimeUtils.timeSpanSecond(mRecTimeSum).mSpanSecond == 0) {
                Toast.makeText(mContext, "时间过短", Toast.LENGTH_SHORT).show();
            } else {
                File file = getOutputVoiceFile(mRecList);
                if (file != null && file.length() > 0) {
                    //清除因暂停录音而产生的临时文件
                    cleanFieArrayList(mRecList);
                    //TODO 这里可以返回数据 setResult
                    final VoiceTimeUtils ts = VoiceTimeUtils.timeSpanSecond(mRecTimeSum);
                    //完成录音
                    if (voiceRecordCallBack != null) {
                        voiceRecordCallBack.recFinish(mRecTimeSum, String.format("%02d:%02d:%02d",
                                ts.mSpanHour, ts.mSpanMinute, ts.mSpanSecond), file.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始录音(内部调)
     *
     * @param init 是否是初始化录音还是暂停后再录音
     */
    private void startVoiceRecord(boolean init) {
        if (!isSDCardAvailable()) {
            return;
        }
        if (init) {
            mRecTimeSum = 0;
            cleanFieArrayList(mRecList);
        }
        //录音前停止播放回调
        if (voicePlayCallBack != null) {
            voicePlayCallBack.playFinish();
        }
        //停止录音
        stopRecorder(mMediaRecorder, true);
        mMediaRecorder = null;

        //停止播放
        stopMedia(mMediaPlayer, true);
        mMediaPlayer = null;

        //开始录音
        mMediaRecorder = new MediaRecorder();
        File file = prepareRecorder(mMediaRecorder, true);
        if (file != null) {
            //开始录音回调
            if (voiceRecordCallBack != null) {
                voiceRecordCallBack.recStart(init);
            }
            mDeviceState = MEDIA_STATE_RECORD_DOING;
            mRecTimePrev = VoiceTimeUtils.getTimeStrFromMillis(System.currentTimeMillis());
            mRecList.add(file);

            mHandler.removeMessages(MSG_TIME_INTERVAL);
            mHandler.sendEmptyMessage(MSG_TIME_INTERVAL);

        }

    }


    /**
     * 监听录音声音频率大小
     */
    private class ObtainDecibelThread extends Thread {

        private volatile boolean running = true;

        public void exit() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mMediaRecorder == null || !running) {
                    break;
                }
                try {
                    final double ratio = mMediaRecorder.getMaxAmplitude() / 150;
                    if (ratio != 0 && voiceRecordCallBack != null) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //分贝
                                double db = 0;
                                if (ratio > 1) {
                                    db = (int) (20 * Math.log10(ratio));
                                    voiceRecordCallBack.recVoiceGrade((int) db);
                                }
                            }
                        });
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    /**
     * 合并录音
     */
    private File getOutputVoiceFile(ArrayList<File> list) {

        String mMinutel = VoiceTimeUtils.getTime();
        File recDirFile = recAudioDir(recordFilePath);

        //创建音频文件,合并的文件放在这里
        File resFile = new File(recDirFile, mMinutel + ".amr");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(resFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //list 里面为暂停录音 所产生的 几段录音文件的名字,中间几段文件的减去前面的6个字节头文件
        for (int i = 0; i < list.size(); i++) {
            File file = list.get(i);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] myByte = new byte[fileInputStream.available()];
                //文件长度
                int length = myByte.length;
                //头文件
                if (i == 0) {
                    while (fileInputStream.read(myByte) != -1) {
                        fileOutputStream.write(myByte, 0, length);
                    }
                }
                //之后的文件,去掉头文件就可以了
                else {
                    while (fileInputStream.read(myByte) != -1) {
                        fileOutputStream.write(myByte, 6, length - 6);
                    }
                }
                fileOutputStream.flush();
                fileInputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //结束后关流
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resFile;

    }


    /**
     * 清空暂停录音所产生的几段录音文件
     *
     * @param list
     */
    private void cleanFieArrayList(ArrayList<File> list) {
        for (File file : list) {
            file.delete();
        }
        list.clear();
    }


    /*********************************播放操作start***************************/

    /**
     * 播放监听
     *
     * @param callBack
     */
    public void setVoicePlayListener(VoicePlayCallBack callBack) {
        voicePlayCallBack = callBack;
    }

    /**
     * 播放SeekBar 监听
     *
     * @param seekBar
     */
    public void setSeekBarListener(SeekBar seekBar) {
        mSBPlayProgress = seekBar;

        if (mSBPlayProgress != null) {
            mSBPlayProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    mHandler.removeMessages(MSG_TIME_INTERVAL);
                    mSavedState = mDeviceState;
                    if (mSavedState == MEDIA_STATE_PLAY_DOING) {
                        pauseMedia(mMediaPlayer);
                    }
                }

                @SuppressLint("DefaultLocale")
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    mHandler.removeMessages(MSG_TIME_INTERVAL);
                    VoiceTimeUtils ts = VoiceTimeUtils.timeSpanSecond(progress / 1000);
                    //播放进度
                    if (voicePlayCallBack != null) {
                        voicePlayCallBack.playDoing(progress / 1000, String.format("%02d:%02d:%02d",
                                ts.mSpanHour, ts.mSpanMinute, ts.mSpanSecond));
                    }

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    seektoMeddia(mMediaPlayer, mSBPlayProgress.getProgress());

                    if (mSavedState == MEDIA_STATE_PLAY_DOING) {
                        playMedia(mMediaPlayer);
                        mHandler.sendEmptyMessage(MSG_TIME_INTERVAL);
                    }
                }
            });
        }

    }

    /**
     * 开始播放(外部调)
     *
     * @param filePath 音频存放文件夹
     */
    public void startPlay(String filePath) {
        if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
            if (voicePlayCallBack != null) {
                voicePlayCallBack.playFinish();
            }
            Toast.makeText(mContext, "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        } else {
            playFilePath = filePath;
            startPlay(true);
        }
    }

    /**
     * 开始播放(内部调)
     *
     * @param init
     */
    @SuppressLint("DefaultLocale")
    private void startPlay(boolean init) {
        try {
            stopRecorder(mMediaRecorder, true);
            mMediaRecorder = null;

            stopMedia(mMediaPlayer, true);

            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(mPlayCompetedListener);

            if (prepareMedia(mMediaPlayer, playFilePath)) {
                mDeviceState = MEDIA_STATE_PLAY_DOING;
                //总时间长度
                long totalTime = mMediaPlayer.getDuration() / 1000;
                VoiceTimeUtils ts = VoiceTimeUtils.timeSpanSecond(totalTime);
                String voiceLength = String.format("%02d:%02d:%02d",
                        ts.mSpanHour, ts.mSpanMinute, ts.mSpanSecond);
                //播放进度回调
                if (voicePlayCallBack != null) {
                    voicePlayCallBack.voiceTotalLength(totalTime, voiceLength);
                    voicePlayCallBack.playDoing(0, "00:00:00");
                }
                if (mSBPlayProgress != null) {
                    mSBPlayProgress.setMax(Math.max(1, mMediaPlayer.getDuration()));
                }
                if (init) {
                    if (mSBPlayProgress != null) {
                        mSBPlayProgress.setProgress(0);
                    }
                    seektoMeddia(mMediaPlayer, 0);
                } else {
                    seektoMeddia(mMediaPlayer, mSBPlayProgress.getProgress());
                }
                if (playMedia(mMediaPlayer)) {
                    mHandler.removeMessages(MSG_TIME_INTERVAL);
                    mHandler.sendEmptyMessage(MSG_TIME_INTERVAL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("播放出错了", e.getMessage());
        }
    }

    /**
     * 继续或暂停播放
     */
    public void ontinueOrPausePlay() {
        if (mDeviceState == MEDIA_STATE_PLAY_DOING) {
            mDeviceState = MEDIA_STATE_PLAY_PAUSE;
            pauseMedia(mMediaPlayer);
            //暂停
            if (voicePlayCallBack != null) {
                voicePlayCallBack.playPause();
            }
        } else if (mDeviceState == MEDIA_STATE_PLAY_PAUSE) {
            mDeviceState = MEDIA_STATE_PLAY_DOING;
            playMedia(mMediaPlayer);
            //播放中
            mHandler.removeMessages(MSG_TIME_INTERVAL);
            mHandler.sendEmptyMessage(MSG_TIME_INTERVAL);
        } else if (mDeviceState == MEDIA_STATE_PLAY_STOP) {
            //播放
            if (!TextUtils.isEmpty(playFilePath)) {
                startPlay(false);
            }
        }
    }


    /**
     * 停止播放
     */
    public void stopPlay() {
        mHandler.removeMessages(MSG_TIME_INTERVAL);
        mDeviceState = MEDIA_STATE_PLAY_STOP;
        stopMedia(mMediaPlayer, true);
        mMediaPlayer = null;
    }


    /**
     * 是否在播放中
     *
     * @return
     */
    public boolean isPlaying() {
        return mDeviceState == MEDIA_STATE_PLAY_DOING;
    }

    /*********************************播放操作end***************************/
    /**
     * 播放录音准备工作
     *
     * @param mp
     * @param file
     * @return
     */
    private boolean prepareMedia(MediaPlayer mp, String file) {
        boolean result = false;
        try {
            mp.setDataSource(file);
            mp.prepare();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 播放录音开始
     *
     * @param mp
     * @return
     */
    private boolean playMedia(MediaPlayer mp) {
        boolean result = false;
        try {
            if (mp != null) {
                mp.start();
                result = true;
            }
        } catch (Exception e) {

        }
        return result;
    }


    /**
     * 拖动播放进度条
     */
    private boolean seektoMeddia(MediaPlayer mp, int pos) {
        boolean result = false;
        try {
            if (mp != null && pos >= 0) {
                mp.seekTo(pos);
                result = true;
            }
        } catch (Exception e) {

        }
        return result;
    }


    /**
     * 停止播放
     *
     * @param mp
     * @param release
     * @return
     */
    private boolean stopMedia(MediaPlayer mp, boolean release) {
        boolean result = false;
        try {
            if (mp != null) {
                mp.stop();
                if (release) {
                    mp.release();
                }
                result = true;
            }
        } catch (Exception e) {

        }
        return result;
    }


    /**
     * 暂停播放
     *
     * @param mp
     * @return
     */
    private boolean pauseMedia(MediaPlayer mp) {
        boolean result = false;
        try {
            if (mp != null) {
                mp.pause();
                result = true;
            }
        } catch (Exception e) {

        }
        return result;
    }


    /**
     * 最后停止录音
     *
     * @param mr
     * @param release
     * @retrun
     */
    private boolean stopRecorder(MediaRecorder mr, boolean release) {
        boolean result = false;
        try {
            if (mr != null) {
                mr.stop();
                if (release) {
                    mr.release();
                }
                result = true;
            }
            if (mThread != null) {
                mThread.exit();
                mThread = null;
            }
        } catch (Exception e) {
            if (mThread != null) {
                mThread.exit();
                mThread = null;
            }
        }
        return result;
    }


    /**
     * 录音准备工作,开始录音
     *
     * @param mr
     * @param start
     * @return
     */
    private File prepareRecorder(MediaRecorder mr, boolean start) {
        File recFile = null;
        if (mr == null) {
            return null;
        }
        try {
            String path = recAudioDir(recordFilePath).getAbsolutePath();
            recFile = new File(path, VoiceTimeUtils.getTime() + ".amr");
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);
            mr.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mr.setOutputFile(recFile.getAbsolutePath());
            mr.prepare();
            if (start) {
                mr.start();
                if (mThread == null) {
                    mThread = new ObtainDecibelThread();
                    mThread.start();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recFile;
    }

    /**
     * 停止录音和播放
     */
    public void stopRecordAndPlay() {
        stopRecorder(mMediaRecorder, true);
        mMediaRecorder = null;
        stopMedia(mMediaPlayer, true);
        mMediaPlayer = null;
    }


    /**
     * 录音回调监听
     */
    public interface VoiceRecordCallBack {
        /**
         * 录音中
         */
        void recDoing(long time, String strTime);

        /**
         * 录音中的声音频率等级
         *
         * @param grade
         */
        void recVoiceGrade(int grade);

        /**
         * 录音开始
         *
         * @param init
         */
        void recStart(boolean init);

        /**
         * 录音暂停
         *
         * @param str
         */
        void recPause(String str);

        /**
         * 录音结束
         */
        void recFinish(long length, String strLength, String path);
    }

    /**
     * 播放录音回调监听
     */
    public interface VoicePlayCallBack {

        /**
         * 音频长度
         * 指定的某个时间段,以秒为单位
         */
        void voiceTotalLength(long time, String strTime);

        /**
         * 播放中
         * 指定的某个时间段,以秒为单位
         */
        void playDoing(long time, String strTime);

        /**
         * 播放暂停
         */
        void playPause();

        /**
         * 播放开始
         */
        void playStart();

        /**
         * 播放结束
         */
        void playFinish();

    }

    /**
     * SD卡是否可用
     */
    public static boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 存储录音文件的方法
     *
     * @param path 存储地址
     */
    public static File recAudioDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

}
