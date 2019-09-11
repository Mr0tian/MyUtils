package com.example.daotest.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daotest.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author tian on 2019/9/2
 * 运动检测
 */
public class MovementActivity extends AppCompatActivity implements SensorEventListener {

    public static float swRoll;
    public static float swPitch;
    public static float swAzimuth;

    /**
     * 陀螺仪管理器及其对象
     */
    public static SensorManager mSensormanager;
    public static Sensor accelerometer;
    public static Sensor magnetoretor;

    public static float[] mAccelerometer = null;
    public static float[] mGeomagnetio = null;
    @InjectView(R.id.tv_azimuth)
    TextView tvAzimuth;
    @InjectView(R.id.tv_pitch)
    TextView tvPitch;
    @InjectView(R.id.tv_roll)
    TextView tvRoll;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);
        ButterKnife.inject(this);
        mSensormanager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = mSensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetoretor = mSensormanager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensormanager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensormanager.registerListener(this, magnetoretor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensormanager.unregisterListener(this, accelerometer);
        mSensormanager.unregisterListener(this, magnetoretor);

    }

    /****************************监听陀螺仪变化的方法**************************************/
    @Override
    public void onSensorChanged(SensorEvent event) {
        // onSensorChanged gets called for each sensor so we have to remember the values
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAccelerometer = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetio = event.values;
        }
        if (mAccelerometer != null && mGeomagnetio != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mAccelerometer, mGeomagnetio);

            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                // at this point, orientation contains the azimuth(direction), pitch and roll values
                double azimuth = 180 * orientation[0] / Math.PI;
                double pitch = 180 * orientation[1] / Math.PI;
                double roll = 180 * orientation[2] / Math.PI;

                tvAzimuth.setText("azimuth:"+azimuth);
                tvPitch.setText("pitch: " + pitch);
                tvRoll.setText("roll : " + roll );
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    /*************************************************************************************/
}
