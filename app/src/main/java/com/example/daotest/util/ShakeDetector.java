package com.example.daotest.util;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * @author tian on 2019/9/2
 * 摇动检测器
 */
public class ShakeDetector implements SensorEventListener {
    private static final String TAG = ShakeDetector.class.getSimpleName();

    /**
     * 检测震动灵敏度,值越大越不灵敏
     */
    private static final double SHAKE_SHRESHOLD = 7000d;
    private Context mContext;
    private long lastTime ;
    private float last_x;
    private float last_y;
    private float last_z;

    private SensorManager sensorManager;
    private onShakeListener shakeListener;
    /**
     * 构造
     * @param context
     */
    public ShakeDetector(Context context){
        mContext = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    /**
     * 注册传感器
     */
    public boolean registerListener() {

        if (sensorManager != null) {
            Sensor sensor = sensorManager
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (sensor != null) {
                this.sensorManager.registerListener(this, sensor,
                        SensorManager.SENSOR_DELAY_GAME);

                return true;
            }
        }
        return false;
    }

    /**
     * 反注册传感器
     */
    public void unRegisterListener() {
        if (sensorManager != null){
            sensorManager.unregisterListener(this);
        }
    }

    public void setOnShakeListener(onShakeListener listener){
        shakeListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //if (event.sensor.getType() == SensorManager.SENSOR_ACCELEROMETER) {
        long curTime = java.lang.System.currentTimeMillis();
        if ((curTime - lastTime) > 10) {
            long diffTime = (curTime - lastTime);
            lastTime = curTime;
            float x = event.values[0];
            float	y = event.values[1];
            float z = event.values[2];
            float speed = Math.abs(x + y + z - last_x - last_y - last_z)
                    / diffTime * 10000;
            if (speed > SHAKE_SHRESHOLD) {
                // 检测到摇晃后执行的代码
                shakeListener.onShake();
            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
        //}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    /**
     *
     * @author Nono
     *
     */
    public interface onShakeListener{
        public void onShake();
    }

}
