package com.brioal.sensormanager.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.brioal.baselib.base.BaseActivity;
import com.brioal.sensormanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LightSensorActivity extends BaseActivity {

    @Bind(R.id.act_light_tv)
    TextView mTvLight;

    SensorManager mSensorManager;
    Sensor mSensorLight;
    SensorEventListener mSensorListener;


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.act_light_sensor);
        ButterKnife.bind(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float value = event.values[0];
                mTvLight.setText("当前亮度:" + value + "1x");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(mSensorListener, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(mSensorListener);
        }
    }

    public static void startLight(Context context) {
        Intent intent = new Intent(context, LightSensorActivity.class);
        context.startActivity(intent);
    }

}
